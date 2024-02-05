package com.meteor.request;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通知已接受传送请求
 */
@Data
@AllArgsConstructor
public class CallTpaDenyToSenderRequest extends TpToPlayerRequest{

    public CallTpaDenyToSenderRequest(String source, String target){
        super(source,target);
    }

    @Override
    public String getType() {
        return "call-deny-sender";
    }

}
