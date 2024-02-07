package com.meteor.bungee.message;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.meteor.request.SerializableRequest;
import com.meteor.request.SpigotReceiveRequest;
import com.meteor.request.TpToPlayerRequest;
import com.meteor.request.TpaRequest;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@AllArgsConstructor
public class MessageHandler implements Listener {

    private Plugin plugin;

    private void sendData(SerializableRequest serializableRequest){

        ServerInfo[] servers = plugin.getProxy().getServers().values().toArray(new ServerInfo[0]);

        for (ServerInfo server : servers) {
            try {
                server.sendData("bungeetp:bc", serializableRequest.toBytes());
            } catch (IOException e) {
                plugin.getLogger().info("send data error");
                e.printStackTrace();
            }
        }
    }



    @EventHandler
    public void onReceiveMessage(PluginMessageEvent pluginMessageEvent){
        String tag = pluginMessageEvent.getTag();
        System.out.println(tag);
        if(tag.equalsIgnoreCase("BungeeCord")){
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(pluginMessageEvent.getData()));
            try {
                String type = dataInputStream.readUTF();
                if(type.equalsIgnoreCase("tpa")){
                    SpigotReceiveRequest tpaRequest = new SpigotReceiveRequest(new TpaRequest().fromBytes(pluginMessageEvent.getData()));
                    sendData(tpaRequest);
                }else if(type.equalsIgnoreCase("tp")){
                    TpToPlayerRequest tpToPlayerRequest = new TpToPlayerRequest().fromBytes(pluginMessageEvent.getData());
                    ProxiedPlayer player = plugin.getProxy().getPlayer(tpToPlayerRequest.getSource());

                    ServerInfo info = player.getServer().getInfo();

                    ProxiedPlayer target = plugin.getProxy().getPlayer(tpToPlayerRequest.getTarget());

                    if(!info.getName().equalsIgnoreCase(target.getServer().getInfo().getName())){
                        player.connect(target.getServer().getInfo());
                    }

                    sendData(tpToPlayerRequest);
                }else if(type.equalsIgnoreCase("call-accept-sender")){
                    TpToPlayerRequest tpToPlayerRequest = new TpToPlayerRequest().fromBytes(pluginMessageEvent.getData());
                    sendData(tpToPlayerRequest);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
