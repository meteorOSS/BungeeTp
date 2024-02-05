package com.meteor.bungeetp.command;

import com.meteor.bungeetp.TeleportsManager;
import com.meteor.jellylib.command.Icmd;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class TpaCommand extends Icmd {
    public TpaCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public String label() {
        return "tpa";
    }

    @Override
    public String getPermission() {
        return "bungeetp.tpa";
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
    public List<String> getTab(Player p, int i, String[] args) {
        List<String> ps = new ArrayList<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            ps.add(pl.getName());
        }
        return ps;
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if(strings.length!=2) return;
        TeleportsManager.tpaRequest(commandSender.getName(),strings[1]);
    }
}
