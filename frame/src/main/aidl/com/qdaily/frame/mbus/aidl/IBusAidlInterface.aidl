// IBusAidlInterface.aidl
package com.qdaily.frame.mbus.aidl;

import com.qdaily.frame.mbus.aidl.EventHolder;
import com.qdaily.frame.mbus.aidl.ICallBack;
// Declare any non-default types here with import statements

interface IBusAidlInterface {
    void attach(ICallBack cb);
    void detach(ICallBack cb);
    void invokeEvent(in EventHolder eventHolder);
}
