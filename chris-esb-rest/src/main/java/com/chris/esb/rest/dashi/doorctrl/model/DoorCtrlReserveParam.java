package com.chris.esb.rest.dashi.doorctrl.model;

import java.io.Serializable;
import java.util.List;

public class DoorCtrlReserveParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<DoorCtrlAuthParam> doorCtrlAuthList;

    public List<DoorCtrlAuthParam> getDoorCtrlAuthList() {
        return doorCtrlAuthList;
    }

    public void setDoorCtrlAuthList(List<DoorCtrlAuthParam> doorCtrlAuthList) {
        this.doorCtrlAuthList = doorCtrlAuthList;
    }
}
