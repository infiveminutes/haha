package com.haha.base.model;

import com.haha.base.model.consts.ReturnCode;

public class BaseResult<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static final BaseResult SUCCESS = createSuccess("成功");

    public static final BaseResult SYSTEM_ERROR = createError("系统错误, 请稍后再试");



    public static BaseResult createError(String msg) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(ReturnCode.fail);
        baseResult.setMsg(msg);
        return baseResult;
    }

    public static <T>BaseResult<T> createSuccess(T t) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(ReturnCode.success);
        baseResult.setData(t);
        return baseResult;
    }
}
