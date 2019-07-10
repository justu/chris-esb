package com.chris.esb.rest.dashi.doorctrl.service;

import com.chris.esb.rest.dashi.doorctrl.model.CosonDoorCtrlReserveParam;
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

    CommonResponse doorCtrlReserve4Coson(CosonDoorCtrlReserveParam param);

    CommonResponse doorCtrlReserve(DoorCtrlReserveParam param);
}
