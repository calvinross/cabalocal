package com.ross53.cobar.EntityWrapper;

import com.ross53.cobar.domain.InfoCode;

import java.util.List;

public class InfoCodeWrapper {

    public List<InfoCode> getInfoCodes() {
        return infoCode;
    }

    public void setInfoCodes(List<InfoCode> infoCodes) {
        this.infoCode = infoCodes;
    }

    private List<InfoCode> infoCode;

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    private Integer storeID;

}
