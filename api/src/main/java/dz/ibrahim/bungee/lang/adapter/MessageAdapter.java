package dz.ibrahim.bungee.lang.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import dz.ibrahim.bungee.hover.HoverCreator;
import dz.ibrahim.bungee.util.Replacer;
import dz.ibrahim.bungee.util.StringUtil;

@AllArgsConstructor
@Getter
@Setter
public class MessageAdapter {

    private String path;
    private String value;

    public void send(CommandSender sender, Replacer... replacers) {
        String message = ChatColor.translateAlternateColorCodes('&', this.value);

        for (final Replacer replacer : replacers) {
            message = replacer.replace(message);
        }



        final String[] arrayClicks = StringUtil.substringsBetween(message, "[[", "]]");
        final HoverCreator hover = new HoverCreator();

        if (arrayClicks == null || arrayClicks.length == 0) {
            sender.sendMessage(message);
            return;
        }

        for (int i = 0; i < arrayClicks.length; i++) {
            final String clickable = arrayClicks[i];
            final String[] messages = clickable.split(",");

            hover.message(this.getStringBefore(clickable, message));
            hover.message(messages[0]);
            if (messages[1] != null) hover.text(messages[1]);

            if (messages[2] != null) {
                final String action = messages[2];

                if (action.toLowerCase().startsWith("run:")) {
                    hover.command(action.replace("run:", ""));
                } else if (action.toLowerCase().startsWith("suggest:")) {
                    hover.suggest(action.replace("suggest:", ""));
                }
            }


            if ((i + 1) == arrayClicks.length) hover.message(this.getStringAfter(clickable, message));
        }

        hover.send(sender);
    }

    public String get(Replacer... replacers) {
        String message = ChatColor.translateAlternateColorCodes('&', this.value);

        for (final Replacer replacer : replacers) {
            message = replacer.replace(message);
        }

        return message;
    }

    private String getStringBefore(String msg, String fullMessage) {
        msg = "[[" + msg + "]]";
        final String before = StringUtil.substringBefore(fullMessage, msg);
        String between = StringUtil.substringAfterLast(before, "]]");
        if (between == null || between.equals("")) {
            between = before;
        }
        return between;
    }

    private String getStringAfter(String msg, String fullMessage) {
        msg = "[[" + msg + "]]";
        return StringUtil.substringAfter(fullMessage, msg);
    }
}
