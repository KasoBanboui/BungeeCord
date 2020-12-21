package dz.ibrahim.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.PlayerCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CustomCommand extends PlayerCommand {

    private CommandArgument defaultArgument;
    private final List<CommandArgument> arguments = new ArrayList<>();
    private CommandCompleter defaultCompleter;
    private final List<CommandCompleter> completers = new ArrayList<>();

    public CustomCommand(String name) {
        this(name, null);
    }

    public CustomCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public final void execute(CommandSender sender, String[] args) {
        try {
            runCommand(sender, args);
        } catch (dz.ibrahim.bungee.command.CommandException e) {
            if (e.getMessage() != null) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
            if (e.getRunnable() != null) e.getRunnable().run();
            if (e.getEnumeration() != null) e.sendLangMessage(sender);
        }
    }

    private void runCommand(CommandSender sender, String[] args) throws CommandException {
        if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
            throw new CommandException("§cYou don't have permission");
        }

        if (args.length > 0) {
            final String arg = args[0];
            for (CommandArgument argument : this.arguments) {
                if (argument.getArgument() == null) continue;
                if (arg.equalsIgnoreCase(argument.getArgument())) {
                    String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                    argument.getCallback().accept(sender, newArgs);
                    return;
                } else {
                    if (argument.getAliases() == null) continue;
                    for (String aliase : argument.getAliases()) {
                        if (arg.equalsIgnoreCase(aliase)) {
                            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                            argument.getCallback().accept(sender, newArgs);
                            return;
                        }
                    }
                }

            }
        }

        if (this.defaultArgument != null) this.defaultArgument.getCallback().accept(sender, args);
    }

    private Iterable<String> runCompleter(CommandSender sender, String[] args) {
        if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
            return Collections.emptyList();
        }

        List<String> strings = new ArrayList<>();

        if (args.length > 1) {
            final String arg = args[0];
            for (CommandCompleter completer : this.completers) {
                if (completer.getArgument() == null) continue;
                if (arg.equalsIgnoreCase(completer.getArgument())) {
                    String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                    strings = completer.getCallback().accept(sender, newArgs);
                } else {
                    if (completer.getAliases() == null) continue;
                    for (String aliase : completer.getAliases()) {
                        if (arg.equalsIgnoreCase(aliase)) {
                            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                            strings = completer.getCallback().accept(sender, newArgs);
                        }
                    }
                }

            }
        }

        if (strings != null && !strings.isEmpty()) {
            return strings;
        }

        if (strings == null) strings = new ArrayList<>();

        if (this.defaultCompleter != null && this.defaultCompleter.getCallback() != null) {
            final List<String> completers = this.defaultCompleter.getCallback().accept(sender, args);
            if (completers != null && !completers.isEmpty()) strings.addAll(completers);
        } else {
            if (args.length == 1) {
                for (CommandArgument argument : this.arguments) {
                    if (argument.getArgument() == null) continue;
                    strings.add(argument.getArgument());
                }
            }
        }

        return strings.isEmpty() ? super.onTabComplete(sender, args) : strings;
    }

    @Override
    public final Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return this.runCompleter(sender, args);
    }

    public boolean isPlayer(CommandSender sender) {
        return sender instanceof ProxiedPlayer;
    }

    public void checkPlayer(CommandSender sender) throws CommandException {
        if (!this.isPlayer(sender)) throw new CommandException("§cYou must be a player.");
    }

    public final void setDefaultCompleter(CompleterCallback callback) {
        this.defaultCompleter = new CommandCompleter(callback, null, null);
    }

    public final void addCompleter(CompleterCallback callback, String argument, String... aliases) {
        this.completers.add(new CommandCompleter(callback, argument, Arrays.asList(aliases)));
    }

    public final void addArgument(CommandCallback callback, String argument, String... aliases) {
        this.arguments.add(new CommandArgument(callback, argument, Arrays.asList(aliases)));
    }

    public final void setDefaultArgument(CommandCallback callback) {
        this.defaultArgument = new CommandArgument(callback, null, null);
    }
}
