package com.ross53.cobar.enums;

public enum InfoCodeStatus {

    UNPROCESS("0", 0),
    PROCESSING("1", 1),
    PROCESSED("2", 2);

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

    private InfoCodeStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (InfoCodeStatus c : InfoCodeStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static InfoCodeStatus getbyIndex(int index) {
        for (InfoCodeStatus c : InfoCodeStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public static InfoCodeStatus getbyName(String name) {
        for (InfoCodeStatus c : InfoCodeStatus.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
