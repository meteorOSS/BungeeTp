package com.meteor.bungeetp;


import com.meteor.bungeetp.message.MessageHandler;
import com.meteor.request.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportsManager {

    private static final Map<Player,TpaRequest> tpaRequestMap = new ConcurrentHashMap<>();

    // 发送tpa请求
    public static void tpaRequest(String sender,String target){

        if(sender.equalsIgnoreCase(target)){
            sendJsonMessage(Bukkit.getPlayer(sender),"message.targetIsSelf",null);
            return;
        }

        TpaRequest tpaRequest = new TpaRequest(sender,target,System.currentTimeMillis());

        if(BungeeTp.instance.isEnableBc())
            new MessageBukkitTask(tpaRequest).run();
        else {
            if(Bukkit.getPlayerExact(target)!=null){
                tpaRequestMap.put(Bukkit.getPlayer(target),tpaRequest);
                receiveTpaRequest(tpaRequest);
            }
        }
    }

    // 通知接收传送请求
    public static void callDenyTpaToSender(CallTpaDenyToSenderRequest tpToPlayerRequest){
        Player playerExact = Bukkit.getPlayerExact(tpToPlayerRequest.getSource());
        if(playerExact!=null){
            Map<String,String> map = new HashMap();
            map.put("player",tpToPlayerRequest.getTarget());
            sendJsonMessage(playerExact,"message.denySender",map);
        }
    }

    // 通知接收传送请求
    public static void callAcceptTpaToSender(CallTpaAcceptToSenderRequest tpToPlayerRequest){
        Player playerExact = Bukkit.getPlayerExact(tpToPlayerRequest.getSource());
        if(playerExact!=null){
            Map<String,String> map = new HashMap();
            map.put("player",tpToPlayerRequest.getTarget());
            sendJsonMessage(playerExact,"message.acceptSender",map);
        }
    }

    // 拒绝传送请求
    public static void denyTpa(Player player){

        if(!tpaRequestMap.containsKey(player) || tpaRequestMap.get(player).isExpr()){
            sendJsonMessage(player,"message.notExistRequest",null);
            return;
        }

        TpaRequest tpaRequest = tpaRequestMap.get(player);
        Map<String,String> map = new HashMap<>();
        map.put("player",tpaRequest.getSender());
        sendJsonMessage(player,"message.denyReceive",map);
        if(BungeeTp.instance.isEnableBc()){
            new MessageBukkitTask(new CallTpaDenyToSenderRequest(tpaRequest.getSender(),tpaRequest.getTarget())).run();
        }else {
            callDenyTpaToSender(new CallTpaDenyToSenderRequest(tpaRequest.getSender(),tpaRequest.getTarget()));
        }

        tpaRequestMap.remove(player);
    }

    // 接受传送请求
    public static void acceptTpa(Player player){


        if(!tpaRequestMap.containsKey(player) || tpaRequestMap.get(player).isExpr()){
            sendJsonMessage(player,"message.notExistRequest",null);
            return;
        }

        TpaRequest tpaRequest = tpaRequestMap.get(player);

        TpToPlayerRequest tpToPlayerRequest = new TpToPlayerRequest(tpaRequest.getSender(),tpaRequest.getTarget());

        if(BungeeTp.instance.isEnableBc()){
            new MessageBukkitTask(tpToPlayerRequest).run();
            new MessageBukkitTask(new CallTpaAcceptToSenderRequest(tpaRequest.getSender(),tpaRequest.getTarget())).run();
        }else {
            BungeeTp.instance.getMessageHandler().onPluginMessageReceived(MessageHandler.channel,player,tpToPlayerRequest);
            callAcceptTpaToSender(new CallTpaAcceptToSenderRequest(tpaRequest.getSender(),tpaRequest.getTarget()));
        }
        Map<String,String> map = new HashMap<>();
        map.put("player",tpaRequest.getSender());
        sendJsonMessage(player,"message.acceptReceive",map);
        tpaRequestMap.remove(player);
    }

    public static void sendJsonMessage(CommandSender commandSender, String path, Map<String,String> context){
        String string = BungeeTp.instance.getConfig().getString(path);
        if(context!=null){
            string = string.replace("{player}",context.get("player"));
        }
        Component deserialize = JSONComponentSerializer.json().deserialize(string);
        if(commandSender instanceof CommandSender){
            BungeeTp.adventure.sender(commandSender).sendMessage(deserialize);
        }else {
            BungeeTp.adventure.player((Player) commandSender).sendMessage(deserialize);
        }
    }


    // 收到tp请求
    public static void receiveTpaRequest(TpaRequest spigotReceiveRequest){
        Player player = Bukkit.getPlayer(spigotReceiveRequest.getTarget());
        if(player!=null){
            tpaRequestMap.put(player,spigotReceiveRequest);
            Map<String,String> context = new HashMap<>();
            context.put("player",spigotReceiveRequest.getSender());
            sendJsonMessage(player,"message.teleport-request",context);
        }
    }

}