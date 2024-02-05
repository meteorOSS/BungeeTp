package com.meteor.bungeetp.command;

import com.meteor.bungeetp.BungeeTp;
import com.meteor.jellylib.command.AbstractCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager extends AbstractCommandManager {
    public CommandManager(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        register(new TpacceptCommand(BungeeTp.instance));
        register(new TpaCommand(BungeeTp.instance));
        register(new TpadenyCommand(BungeeTp.instance));
        register(new HelpCommand(BungeeTp.instance));
        register(new TpaCommand(BungeeTp.instance));
        register(new ReloadCommand(BungeeTp.instance));
    }
}
