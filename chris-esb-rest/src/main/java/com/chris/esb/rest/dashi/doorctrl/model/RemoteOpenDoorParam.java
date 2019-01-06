package com.chris.esb.rest.dashi.doorctrl.model;

import java.io.Serializable;

public class RemoteOpenDoorParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String doorCtrlIP;

    private String macAddr;

    private Integer doorReaderNo;

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
}
