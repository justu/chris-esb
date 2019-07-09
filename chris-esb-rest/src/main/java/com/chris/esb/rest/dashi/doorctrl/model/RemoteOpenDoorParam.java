package com.chris.esb.rest.dashi.doorctrl.model;

import java.io.Serializable;

public class RemoteOpenDoorParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String doorCtrlIP;

    private String macAddr;

    private Integer doorReaderNo;

    private String doorCtrlProvier;

    private String doorId;

    public RemoteOpenDoorParam() {
    }

    public RemoteOpenDoorParam(String doorCtrlIP, String macAddr, Integer doorReaderNo) {
        this.doorCtrlIP = doorCtrlIP;
        this.macAddr = macAddr;
        this.doorReaderNo = doorReaderNo;
    }

    public String getDoorCtrlIP() {
        return doorCtrlIP;
    }

    public void setDoorCtrlIP(String doorCtrlIP) {
        this.doorCtrlIP = doorCtrlIP;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public Integer getDoorReaderNo() {
        return doorReaderNo;
    }

    public void setDoorReaderNo(Integer doorReaderNo) {
        this.doorReaderNo = doorReaderNo;
    }

    public String getDoorCtrlProvier() {
        return doorCtrlProvier;
    }

    public void setDoorCtrlProvier(String doorCtrlProvier) {
        this.doorCtrlProvier = doorCtrlProvier;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }
}
