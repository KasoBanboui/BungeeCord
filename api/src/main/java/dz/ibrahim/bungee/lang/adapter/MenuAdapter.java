package dz.ibrahim.bungee.lang.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import dz.ibrahim.bungee.util.Replacer;

@AllArgsConstructor
@Getter
@Setter
public class MenuAdapter {

    private String path;
    private String title_name;

    public String get(Replacer... replacers) {
        String title_name = ChatColor.translateAlternateColorCodes('&', this.title_name);

        for (final Replacer replacer : replacers) {
            title_name = replacer.replace(title_name);
        }

        if (title_name != null && title_name.length() > 32) {
            title_name = title_name.substring(0, 32);
        }

        return title_name;
    }
}
