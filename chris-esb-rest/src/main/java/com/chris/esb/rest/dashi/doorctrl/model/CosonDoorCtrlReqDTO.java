package com.chris.esb.rest.dashi.doorctrl.model;

import java.io.Serializable;

public class CosonDoorCtrlReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer doorId;
    private Integer mappingDoorId;
    private String doorCtrlIp;
    private String userCardId;
    private String startTime;
    private String endTime;

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public Integer getMappingDoorId() {
        return mappingDoorId;
    }

    public void setMappingDoorId(Integer mappingDoorId) {
        this.mappingDoorId = mappingDoorId;
    }

    public String getDoorCtrlIp() {
        return doorCtrlIp;
    }

    public void setDoorCtrlIp(String doorCtrlIp) {
        this.doorCtrlIp = doorCtrlIp;
    }

    public String getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(String userCardId) {
        this.userCardId = userCardId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
