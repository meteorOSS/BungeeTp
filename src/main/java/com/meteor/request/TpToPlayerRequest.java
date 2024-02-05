package com.meteor.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TpToPlayerRequest implements SerializableRequest{

    private String source;

    private String target;

    @Override
    public String getType() {
        return "tp";
    }

    @Override
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeUTF(getType());
        dataOutputStream.writeUTF(source);
        dataOutputStream.writeUTF(target);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public TpToPlayerRequest fromBytes(byte[] bytes) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        dataInputStream.readUTF();
        this.source = dataInputStream.readUTF();
        this.target = dataInputStream.readUTF();
        return this;
    }
}
