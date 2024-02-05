package com.meteor.bungee;

import com.meteor.bungee.message.MessageHandler;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeTp extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new MessageHandler(this));
        getProxy().registerChannel("bungeetp:bc");
    }

    @Override
    public void onDisable() {
        getProxy().getPluginManager().unregisterListeners(this);
        getProxy().unregisterChannel("bungeetp:bc");
    }
}
