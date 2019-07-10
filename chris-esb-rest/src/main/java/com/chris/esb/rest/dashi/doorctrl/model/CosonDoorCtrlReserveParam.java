package com.chris.esb.rest.dashi.doorctrl.model;

import java.io.Serializable;
import java.util.List;

public class CosonDoorCtrlReserveParam implements Serializable {
   private static final long serialVersionUID = 1L;

   private List<CosonDoorCtrlReqDTO> params;

    public List<CosonDoorCtrlReqDTO> getParams() {
        return params;
    }

    public void setParams(List<CosonDoorCtrlReqDTO> params) {
        this.params = params;
    }
}
