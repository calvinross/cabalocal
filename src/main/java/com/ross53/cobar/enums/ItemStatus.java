package com.ross53.cobar.enums;

public enum ItemStatus {

    UNDO("0", 0),
    COMPLETED("1", 1),
    COMPLETEPART("2", 2),
    REJECT("3", 3),
    UNKNOWERR("4", 4),
    FINISHED("5",5);

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

    private ItemStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ItemStatus c : ItemStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static ItemStatus getbyIndex(int index) {
        for (ItemStatus c : ItemStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public static ItemStatus getbyName(String name) {
        for (ItemStatus c : ItemStatus.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
