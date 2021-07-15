package com.haha.base.model.enums;

public enum SpreadTypeEnum {
    READ(0, "读扩散"),
    WRITE(1, "写扩散");

    private int code;
    private String msg;

    SpreadTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
