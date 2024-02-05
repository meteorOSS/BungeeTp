package com.meteor.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TpaRequest implements SerializableRequest{

    private String sender;
    private String target;

    private long createTime;

    public String getType(){
        return "tpa";
    }

    @Override
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeUTF(getType());
        dataOutputStream.writeUTF(sender);
        dataOutputStream.writeUTF(target);
        dataOutputStream.writeLong(createTime);
        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public TpaRequest fromBytes(byte[] bytes) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        dataInputStream.readUTF();
        this.sender = dataInputStream.readUTF();
        this.target = dataInputStream.readUTF();
        this.createTime = dataInputStream.readLong();
        return this;
    }

    // 请求是否已过期
    public boolean isExpr(){

        return (System.currentTimeMillis() -createTime) > (15*1000);

    }
}
