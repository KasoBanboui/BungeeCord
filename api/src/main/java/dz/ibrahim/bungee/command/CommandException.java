package dz.ibrahim.bungee.command;

import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import dz.ibrahim.bungee.util.Reflection;
import dz.ibrahim.bungee.util.Replacer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Getter
public class CommandException extends Exception {

    private final String message;
    private final Runnable runnable;
    private final Enum<?> enumeration;
    private final Replacer[] replacers;

    public CommandException(String message) {
        this(message, null);
    }

    public CommandException(String message, Runnable runnable, Enum<?> enumeration, Replacer... replacers) {
        this.message = message;
        this.runnable = runnable;
        this.enumeration = enumeration;
        this.replacers =  replacers;
    }

    public CommandException(Runnable runnable, Enum<?> enumeration, Replacer... replacers) {
        this(null, runnable, enumeration, replacers);
    }

    public CommandException(String message, Enum<?> enumeration, Replacer... replacers) {
        this(message, null, enumeration, replacers);
    }

    public CommandException(Enum<?> enumeration, Replacer... replacers) {
        this(null, null, enumeration, replacers);
    }

    public CommandException(Runnable runnable) {
        this(null, runnable, null);
    }

    public void sendLangMessage(CommandSender sender) {
        if (this.enumeration == null) return;
        try {
            final Class<?> clazz = this.enumeration.getDeclaringClass();
            final Reflection reflection = new Reflection();
            final Field type = reflection.access(clazz, "type");
            final String value = reflection.fetch(type, this.enumeration);
            switch (value.toLowerCase()) {
                case "others":
                case "messages": {
                    final Method sendMethod = clazz.getMethod("send", CommandSender.class, Replacer[].class);
                    sendMethod.invoke(this.enumeration, sender, this.replacers);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
