package com.meteor.bungeetp.command;

import com.meteor.bungeetp.TeleportsManager;
import com.meteor.jellylib.command.Icmd;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TpacceptCommand extends Icmd {
    public TpacceptCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public String label() {
        return "tpaccept";
    }

    @Override
    public String getPermission() {
        return "bungeetp.tpaccept";
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
        TeleportsManager.acceptTpa((Player) commandSender);
    }
}
