package dz.ibrahim.bungee.command;

import net.md_5.bungee.api.CommandSender;

import java.util.List;

public interface CompleterCallback {

    List<String> accept(CommandSender sender, String[] args);
}
