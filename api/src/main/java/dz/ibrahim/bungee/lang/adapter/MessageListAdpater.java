package dz.ibrahim.bungee.lang.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import dz.ibrahim.bungee.hover.HoverCreator;
import dz.ibrahim.bungee.util.Replacer;
import dz.ibrahim.bungee.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MessageListAdpater {

    private String path;
    private List<String> values;

    public void send(CommandSender sender, Replacer... replacers) {
        for (final String value : values) {
            String message = ChatColor.translateAlternateColorCodes('&', value);

            for (final Replacer replacer : replacers) {
                message = replacer.replace(message);
            }

            final String[] arrayClicks = StringUtil.substringsBetween(message, "[[", "]]");
            final HoverCreator hover = new HoverCreator();

            if (arrayClicks == null || arrayClicks.length == 0) {
                sender.sendMessage(message);
                continue;
            }

            for (int i = 0; i < arrayClicks.length; i++) {
                final String clickable = arrayClicks[i];
                final String[] messages = clickable.split(",");

                hover.message(this.getStringBefore(clickable, message));
                hover.message(messages[0]);
                if (messages.length > 1) {
                    hover.text(messages[1]);
                }

                if (messages.length > 2) {
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
    }

    public List<String> get(Replacer... replacers) {
        List<String> values = new ArrayList<>();

        for (final String value : this.values) {
            String message = ChatColor.translateAlternateColorCodes('&', value);

            for (final Replacer replacer : replacers) {
                message = replacer.replace(message);
            }

            values.add(message);
        }


        return values;
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
