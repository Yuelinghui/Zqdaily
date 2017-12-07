package com.qdaily.network.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class Meta implements Serializable {

    @SerializedName("status")
    private int resultStatus;

    @SerializedName("msg")
    private String resultMessage;

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String msg) {
        this.resultMessage = msg;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "resultStatus=" + resultStatus +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
