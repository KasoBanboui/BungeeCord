package dz.ibrahim.bungee.command;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.command.PlayerCommand;

import java.util.Arrays;
import java.util.List;

public class CommandBuilder {

    public CommandBuilder(Plugin plugin, List<CustomCommand> commands) {
        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        for (PlayerCommand command : commands) {
            pluginManager.registerCommand(plugin, command);
        }
    }

    public CommandBuilder(Plugin plugin, CustomCommand... commands) {
        this(plugin, Arrays.asList(commands));
    }
}
