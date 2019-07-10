package com.chris.esb.rest.dashi.doorctrl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.esb.common.model.JDBCParam;
import com.chris.esb.common.service.EsbConfigService;
import com.chris.esb.common.utils.JDBCUtils4SQLServer;
import com.chris.esb.rest.dashi.doorctrl.model.*;
import com.chris.esb.rest.dashi.doorctrl.service.DoorCtrlMgrService;
import com.chris.esb.rest.springboot.utils.CommonException;
import com.chris.esb.rest.springboot.utils.CommonResponse;
import com.chris.esb.rest.springboot.utils.RestTemplateUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("doorCtrlMgrService")
public class DoorCtrlMgrServiceImpl implements DoorCtrlMgrService {

    private static final String SUCCESS = "00";
    private static String DOOR_CTRL_SEND_PORT = "DOOR_CTRL_SEND_PORT";
    private static String DOOR_CTRL_RECEIVE_PORT = "DOOR_CTRL_RECEIVE_PORT";
    private static int NO_ZERO = 0;
    private static int NO_ONE = 1;
    private static int NO_TWO = 2;
    private static int NO_THREEE = 3;
    private static String SQLSERVER_CONFIG = "SQLSERVER_CONFIG";
    private static Logger log = Logger.getLogger(DoorCtrlMgrServiceImpl.class);

    @Value("${chris.esb.doorCtrlUrl.coson}")
    private String doorCtrlUrl;

    @Autowired
    private EsbConfigService esbConfigService;

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Override
    public CommonResponse remoteOpenDoor(RemoteOpenDoorParam doorController) {
        if (ObjectUtils.nullSafeEquals("dasDoorCtrlProvider", doorController.getDoorCtrlProvier())) {
            return this.remoteOpenDoor4Das(doorController);
        } else {
            return this.remoteOpenDoor4Coson(doorController);
        }
    }

    private CommonResponse remoteOpenDoor4Coson(RemoteOpenDoorParam doorController) {
        String result = this.restTemplateUtils.httpGetUrlVariable(this.doorCtrlUrl + "open_door", String.class, ImmutableMap.of("target_ip", doorController.getDoorCtrlIP(), "door", doorController.getDoorId()));
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (ObjectUtils.nullSafeEquals("0", jsonObject.getInteger("status"))) {
            return CommonResponse.ok();
        } else {
            return CommonResponse.error(jsonObject.getString("msg"));
        }
    }

    private CommonResponse remoteOpenDoor4Das(RemoteOpenDoorParam doorController) {
        String deviceIP = doorController.getDoorCtrlIP();
        String deviceMacAddr = doorController.getMacAddr();
        int readerNo = doorController.getDoorReaderNo();
        DatagramSocket client = null;
        DatagramPacket sendPacket;
        DatagramPacket receivePacket;
        try {
            client = new DatagramSocket();
            client.setSoTimeout(3000);
            byte[] sendBuf = this.buildSendBuff(readerNo, deviceMacAddr);

            InetAddress addr = InetAddress.getByName(deviceIP);

            sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, getSendPort());
            client.send(sendPacket);

            byte[] data2 = new byte[1024];
            receivePacket = new DatagramPacket(data2, data2.length, addr, getReceivePort());
            log.error("设备[" + deviceMacAddr + "] 发送指令= {" + this.bytesToHexString(sendBuf) + "}");
            client.receive(receivePacket);
            String receiveData = this.bytesToHexString(receivePacket.getData());
            log.error("设备[{" + deviceMacAddr + "}] 接收指令= {" + receiveData + "}");
            return this.parseReceiveData(receiveData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("远程开门异常！原因：" + e.getMessage());
            return CommonResponse.error("远程开门异常！请联系管理员");
        } finally {
            IOUtils.closeQuietly(client);
        }
    }

    private CommonResponse parseReceiveData(String receiveData) {
        String openDoorValue = receiveData.substring(18, 20);
        if (ObjectUtils.nullSafeEquals(SUCCESS, openDoorValue)) {
            return CommonResponse.ok();
        } else {
            return CommonResponse.error("开门失败！");
        }
    }

    private byte[] buildSendBuff(int readerNo, String deviceMacAddr) {
        byte[] sendBuf = new byte[12];
        byte[] bytes = new byte[3];
        bytes[0] = (byte) Integer.parseInt(deviceMacAddr.substring(0, 2), 16);
        bytes[1] = (byte) Integer.parseInt(deviceMacAddr.substring(2, 4), 16);
        bytes[2] = (byte) Integer.parseInt(deviceMacAddr.substring(4, 6), 16);
        sendBuf[0] = (byte) 0x55;
        sendBuf[1] = (byte) 0x04;
        sendBuf[2] = bytes[0];
        sendBuf[3] = bytes[1];
        sendBuf[4] = bytes[2];
        sendBuf[5] = (byte) (0x2D);
        sendBuf[6] = (byte) 0x00;
        sendBuf[7] = (byte) 0x01;
        sendBuf[8] = (byte) (this.parseReaderNo(readerNo) + 1);
        sendBuf[9] = (byte) 0x00;
        int path = 0;
        for (int i = 0; i < 10; i++) {
            path = path + (sendBuf[i] & 0xff);
        }
        sendBuf[10] = (byte) ((path >> 8) & 0xff);
        sendBuf[11] = (byte) (path & 0xff);
        return sendBuf;
    }

    private int parseReaderNo(int readerNo) {
        if (NO_ZERO == readerNo || NO_ONE == readerNo) {
            return readerNo;
        } else if (NO_TWO == readerNo) {
            return readerNo + 1;
        } else if (NO_THREEE == readerNo) {
            return (readerNo << 1) + 1;
        } else {
            throw new RuntimeException("读头号[" + readerNo + "]不正确！");
        }
    }
    /**
     * 数组转换成十六进制字符串
     * @return HexString
     */
    private final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private int getSendPort() {
        return Integer.valueOf(this.esbConfigService.getValueByKey(DOOR_CTRL_SEND_PORT));
    }


    private int getReceivePort() {
        return Integer.valueOf(this.esbConfigService.getValueByKey(DOOR_CTRL_RECEIVE_PORT));
    }


    @Override
    public CommonResponse doorCtrlReserve4Coson(CosonDoorCtrlReserveParam param) {
        List<CosonDoorCtrlReqDTO> list = param.getParams();
        list.forEach(item -> {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("pwd", "888888");
            paramMap.put("door", "1111");
            paramMap.put("door_time", "00000000");
            paramMap.put("user_id", item.getUserCardId());
            paramMap.put("user_type", "1");
            paramMap.put("valid_time", item.getEndTime());
            this.restTemplateUtils.httpPostMediaTypeFromData(this.doorCtrlUrl + "task_cardpower?id=1&target_id=" + item.getDoorCtrlIp(),
                    String.class, paramMap);

        });
        return CommonResponse.ok();
    }
    /**
     * 门禁预约
     * @param param
     * @return
     */
    @Override
    public CommonResponse doorCtrlReserve(DoorCtrlReserveParam param) {
        String sql = "INSERT INTO NDr2_AuthorSet1 ([CardID], [DoorID], [PassWord], [DueDate], [AuthorType], [AuthorStatus], [UserTimeGrp], [DownLoaded], [FirstDownLoaded], [PreventCard], [StartTime]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JDBCParam jdbcParam = this.buildJDBCParam();
        jdbcParam.setSql(sql);
        CommonResponse resp = CommonResponse.ok();
        try {
            this.validateParam(param);
            JDBCUtils4SQLServer.saveOrUpdate(jdbcParam, (ps) -> {
                List<DoorCtrlAuthParam> doorAuthList = param.getDoorCtrlAuthList();
                for (int i = 0; i < doorAuthList.size(); i++) {
                    DoorCtrlAuthParam item = doorAuthList.get(i);
                    ps.setInt(1, item.getCardID());
                    ps.setInt(2, item.getDoorID());
                    ps.setString(3, item.getPassword());
                    ps.setTimestamp(4, new Timestamp(item.getDueDate().getTime()));
                    ps.setInt(5, item.getAuthorType());
                    ps.setInt(6, item.getAuthorStatus());
                    ps.setInt(7, item.getUserTimeGrp());
                    ps.setInt(8, item.getDownLoaded());
                    ps.setInt(9, item.getFirstDownLoaded());
                    ps.setInt(10, item.getPreventCard());
                    ps.setTimestamp(11, new Timestamp(item.getStartTime().getTime()));
                    ps.addBatch();
                }
            });
            log.error("门禁");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("门禁预约异常！原因：" + e.getMessage());
            resp = CommonResponse.error("门禁预约异常！原因：" + e.getMessage());
        }
        return resp;
    }

    private void validateParam(DoorCtrlReserveParam param) {
        if (ObjectUtils.isEmpty(param) || CollectionUtils.isEmpty(param.getDoorCtrlAuthList())) {
            throw new CommonException("参数为空");
        }
    }

    private JDBCParam buildJDBCParam() {
        String json = this.esbConfigService.getValueByKey(SQLSERVER_CONFIG);
        log.error("jdbcParam json = " + json);
        JDBCParam jdbcParam = JSONObject.parseObject(json, JDBCParam.class);
        return jdbcParam;
    }

    public static void main(String[] args) {
        DoorCtrlAuthParam param = new DoorCtrlAuthParam();
        param.setCardID(7788520);
        param.setDoorID(77);
        param.setPassword("000");
        param.setDueDate(new Date());
        param.setAuthorType(1);
        param.setAuthorStatus(1);
        param.setUserTimeGrp(2);
        param.setDownLoaded(2);
        param.setFirstDownLoaded(3);
        param.setPreventCard(1);
        param.setStartTime(new Date());
        System.out.println(JSONObject.toJSONString(param));
    }
}
