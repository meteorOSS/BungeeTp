package com.meteor.bungeetp.command;

import com.meteor.bungeetp.TeleportsManager;
import com.meteor.jellylib.command.Icmd;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpCommand extends Icmd {
    public HelpCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public String label() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "bungeetp.use.help";
    }

    @Override
    public boolean playersOnly() {
        return false;
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        TeleportsManager.sendJsonMessage(commandSender,"message.help",null);
    }
}
