package com.chris.esb.rest.dashi.doorctrl.service;

import com.alibaba.fastjson.JSONObject;
import com.chris.esb.rest.dashi.doorctrl.model.DoorCtrlReserveParam;
import com.chris.esb.rest.dashi.doorctrl.model.RemoteOpenDoorParam;
import com.chris.esb.rest.springboot.utils.CommonResponse;

public interface DoorCtrlMgrService {

    /**
     * 远程开门
     * @param param
     * @return
     */
    CommonResponse remoteOpenDoor(RemoteOpenDoorParam param);

    CommonResponse doorCtrlReserve4Coson(JSONObject jsonObject);

    CommonResponse doorCtrlReserve(DoorCtrlReserveParam param);
}
