// ICallBack.aidl
package com.qdaily.frame.mbus.aidl;

import com.qdaily.frame.mbus.aidl.EventHolder;

interface ICallBack {
    void invoke(in EventHolder eventHolder);
}
