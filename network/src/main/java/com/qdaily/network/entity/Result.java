package com.qdaily.network.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class Result<T> implements Serializable{

    private boolean flag;

    @SerializedName("msg")
    private String message;

    @SerializedName("errCode")
    private int errorCode;

    @SerializedName("object")
    private T resultData;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                ", errorCode=" + errorCode +
                ", resultData=" + resultData +
                '}';
    }
}
