package com.ross53.cobar.enums;


public enum OrderStatus {
    START("START", 0),
    COOKING("COOKING", 1),
    COMPLETE("COMPLETE", 2),
    FINISHED("FINISHED",3),
    ALL("ALL", 100);

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

    private OrderStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static OrderStatus getbyIndex(int index) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public static OrderStatus getbyName(String name) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}

