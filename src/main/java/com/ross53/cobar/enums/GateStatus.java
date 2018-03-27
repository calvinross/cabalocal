package com.ross53.cobar.enums;

public enum GateStatus {

    Open("0", 0),
    Close("1", 1);

    private String name;
    private int index;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private GateStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (GateStatus c : GateStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static GateStatus getbyIndex(int index) {
        for (GateStatus c : GateStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public static GateStatus getbyName(String name) {
        for (GateStatus c : GateStatus.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
