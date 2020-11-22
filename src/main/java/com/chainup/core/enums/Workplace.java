package com.chainup.core.enums;

/**
 * @author lili
 * @date 2020/11/22 12:10
 * @see
 * @since
 */
public enum  Workplace {
    LAN_HAI(1, "蓝海中心"),
    JIA_HUA(2, "嘉华中心");


    private int type;
    private String description;

    Workplace(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public static String descriptionByType(int type) {
        for (Workplace value : values()) {
            if (value.type == type) {
                return value.description;
            }
        }
        return LAN_HAI.description;
    }


    Workplace(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }


    public byte byteStatus() {
        return (byte) type;
    }

}
