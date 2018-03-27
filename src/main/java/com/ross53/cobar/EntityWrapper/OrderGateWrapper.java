package com.ross53.cobar.EntityWrapper;

public class OrderGateWrapper {

    private Integer gateStatus;

    private String operatorName;

    public Integer getGateStatus() {
        return gateStatus;
    }

    public void setGateStatus(Integer gateStatus){
        this.gateStatus = gateStatus;
    }

    public String getOperatorName(){
        return operatorName;
    }

    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }
}
