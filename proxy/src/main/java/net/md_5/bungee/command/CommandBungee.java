package net.md_5.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class CommandBungee extends Command
{

    public CommandBungee()
    {
        super("bungee");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        sender.sendMessage( ChatColor.YELLOW + "This server is running ICord version 1.16.4 by md_5" );
        sender.sendMessage( ChatColor.YELLOW + "Edited by Ibrahim (Bramsou#0114)" );
    }
}
