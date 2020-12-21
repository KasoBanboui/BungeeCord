package dz.ibrahim.bungee.lang.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import dz.ibrahim.bungee.util.Replacer;
import dz.ibrahim.bungee.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ScoreboardAdapter {

    private String path;
    private List<String> lines;

    public List<String> get(List<Replacer> replacers) {
        final List<String> messages = new ArrayList<>();
        for (String message : this.lines) {
            message = ChatColor.translateAlternateColorCodes('&', message);

            for (final Replacer replacer : replacers) {
                message = replacer.replace(message);
            }

            message = message.replaceAll("%online%", String.valueOf(ProxyServer.getInstance().getOnlineCount()));
            if (!StringUtil.isEmpty(message)) messages.add(message);
        }

        int count = 0;
        final String longest = ChatColor.stripColor(Collections.max(messages, Comparator.comparingInt(String::length)));
        final String sizedLine = this.getSizedLine(longest);
        for (String message : messages) {
            if (message.contains("%line_sized%")) {
                messages.set(count, message.replace("%line_sized%", sizedLine));
            }
            count++;
        }

        return messages;
    }

    private String getSizedLine(String max) {
        final StringBuilder builder = new StringBuilder("§7§m");
        int size = max.length();
        if (size <= 12) {
            builder.append("---------------");
        } else {
            for (int i = 0 ; i < size; i++) {
                builder.append("-");
            }
        }
        return builder.toString();
    }
}
