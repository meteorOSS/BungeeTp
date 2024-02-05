package com.meteor.request;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface SerializableRequest {
    String getType();
    byte[] toBytes() throws IOException;

    // 从字节数组反序列化对象
    SerializableRequest fromBytes(byte[] bytes) throws IOException;
}
