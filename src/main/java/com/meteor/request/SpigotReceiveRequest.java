package com.meteor.request;


/**
 * spigot子服接收到tpa请求
 */
public class SpigotReceiveRequest extends TpaRequest{

    public SpigotReceiveRequest(TpaRequest tpaRequest){
        super(tpaRequest.getSender(), tpaRequest.getTarget(),System.currentTimeMillis());
    }

    @Override
    public String getType() {
        return "receive_tpa";
    }
}
