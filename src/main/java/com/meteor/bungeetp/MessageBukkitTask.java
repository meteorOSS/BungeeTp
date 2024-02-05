package com.meteor.bungeetp;

import com.meteor.request.SerializableRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;


public class MessageBukkitTask extends BukkitRunnable {

    private SerializableRequest serializableRequest;

    public MessageBukkitTask(SerializableRequest serializableRequest){
        this.serializableRequest = serializableRequest;
    }

    @Override
    public void run() {
        try {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendPluginMessage(BungeeTp.instance,"BungeeCord",serializableRequest.toBytes());
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
