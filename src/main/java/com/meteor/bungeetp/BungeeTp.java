package com.meteor.bungeetp;

import com.meteor.bungeetp.command.CommandManager;
import com.meteor.bungeetp.message.MessageHandler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeTp extends JavaPlugin {
    public static BungeeTp instance;

    private CommandManager commandManager;
    public static BukkitAudiences adventure;

    private boolean enableBc;
    private MessageHandler messageHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        instance = this;
        (commandManager = new CommandManager(this)).init();
        adventure = BukkitAudiences.create(this);
        getCommand("btp").setExecutor(commandManager);
        this.enableBc = getConfig().getBoolean("setting.bc",false);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.messageHandler = new MessageHandler();
        getServer().getMessenger().registerIncomingPluginChannel(this, MessageHandler.channel,messageHandler);
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public boolean isEnableBc() {
        return enableBc;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
        adventure.close();
    }

}
