package com.meteor.bungeetp.message;

import com.meteor.bungeetp.BungeeTp;
import com.meteor.bungeetp.TeleportsManager;
import com.meteor.request.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageHandler implements PluginMessageListener {
    public static final String channel = "bungeetp:bc";


    private void teleport(TpToPlayerRequest tpToPlayerRequest){
        Bukkit.getPlayer(tpToPlayerRequest.getSource()).teleport(Bukkit.getPlayer(tpToPlayerRequest.getTarget()).getLocation());
    }

    public void onPluginMessageReceived(String channel, Player player, SerializableRequest serializableRequest){
        try {
            onPluginMessageReceived(channel,player,serializableRequest.toBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if(channel.equalsIgnoreCase(channel)){
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(message));
            try {
                String type = dataInputStream.readUTF();
                if(type.equalsIgnoreCase("receive_tpa")){
                    TpaRequest tpaRequest = new TpaRequest().fromBytes(message);
                    TeleportsManager.receiveTpaRequest(tpaRequest);
                }else if(type.equalsIgnoreCase("tp")){
                    TpToPlayerRequest tpToPlayerRequest = new TpToPlayerRequest().fromBytes(message);

                    Player target = Bukkit.getPlayerExact(tpToPlayerRequest.getTarget());
                    if(target!=null){
                        Bukkit.getScheduler().runTaskLater(BungeeTp.instance,()->{
                            Player playerExact = Bukkit.getPlayerExact(tpToPlayerRequest.getSource());
                            if(playerExact!=null){
                                teleport(tpToPlayerRequest);
                            }
                        },20L);
                    }
                }else if(type.equalsIgnoreCase("call-accept-sender")) {
                    TpToPlayerRequest tpToPlayerRequest = new CallTpaAcceptToSenderRequest().fromBytes(message);
                    TeleportsManager.callAcceptTpaToSender((CallTpaAcceptToSenderRequest) tpToPlayerRequest);
                }else if(type.equalsIgnoreCase("call-deny-sender")) {
                    CallTpaDenyToSenderRequest denyToSenderRequest = (CallTpaDenyToSenderRequest) new CallTpaDenyToSenderRequest().fromBytes(message);
                    TeleportsManager.callDenyTpaToSender(denyToSenderRequest);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
