package com.meteor.bungeetp.command;

import com.meteor.bungeetp.TeleportsManager;
import com.meteor.jellylib.command.Icmd;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TpadenyCommand extends Icmd {
    public TpadenyCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public String label() {
        return "tpadeny";
    }

    @Override
    public String getPermission() {
        return "bungeetp.tpadeny";
    }

    @Override
    public boolean playersOnly() {
        return true;
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        TeleportsManager.denyTpa((Player) commandSender);
    }
}
