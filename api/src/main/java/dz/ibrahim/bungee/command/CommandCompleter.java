package dz.ibrahim.bungee.command;

import java.util.List;

public class CommandCompleter {

    private final CompleterCallback callback;
    private final String argument;
    private final List<String> aliases;

    public CommandCompleter(CompleterCallback callback, String argument, List<String> aliases) {
        this.callback = callback;
        this.argument = argument;
        this.aliases = aliases;
    }

    public CompleterCallback getCallback() {
        return this.callback;
    }

    public String getArgument() {
        return this.argument;
    }

    public List<String> getAliases() {
        return this.aliases;
    }
}
