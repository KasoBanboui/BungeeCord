package dz.ibrahim.bungee.command;

import java.util.List;

public class CommandArgument {

    private final CommandCallback callback;
    private final String argument;
    private final List<String> aliases;

    public CommandArgument(CommandCallback callback, String argument, List<String> aliases) {
        this.callback = callback;
        this.argument = argument;
        this.aliases = aliases;
    }

    public CommandCallback getCallback() {
        return this.callback;
    }

    public String getArgument() {
        return this.argument;
    }

    public List<String> getAliases() {
        return this.aliases;
    }
}
