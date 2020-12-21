package dz.ibrahim.bungee.command;

import net.md_5.bungee.api.CommandSender;

public interface CommandCallback {

    void accept(CommandSender sender, String[] args) throws CommandException;

}