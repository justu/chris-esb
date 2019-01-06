package com.chris.esb.rest.dashi.doorctrl.service;

import com.chris.esb.rest.dashi.doorctrl.model.DoorCtrlAuthParam;
import com.chris.esb.rest.dashi.doorctrl.model.DoorCtrlReserveParam;
import com.chris.esb.rest.dashi.doorctrl.model.RemoteOpenDoorParam;
import com.chris.esb.rest.springboot.utils.CommonResponse;

import java.util.List;

public interface DoorCtrlMgrService {

    /**
     * 远程开门
     * @param param
     * @return
     */
    CommonResponse remoteOpenDoor(RemoteOpenDoorParam param);

    CommonResponse doorCtrlReserve(DoorCtrlReserveParam param);
}
