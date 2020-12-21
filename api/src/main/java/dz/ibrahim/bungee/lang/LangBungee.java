package dz.ibrahim.bungee.lang;

import dz.ibrahim.bungee.lang.adapter.MessageAdapter;
import dz.ibrahim.bungee.util.Replacer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

@RequiredArgsConstructor
@Getter
public enum LangBungee {
    SECONDS("date.seconds", "seconds"),
    SECOND("date.second", "second"),
    MINUTES("date.minutes", "minutes"),
    MINUTE("date.minute", "minute"),
    HOURS("date.hours", "hours"),
    HOUR("date.hour", "hour"),
    DAY("date.day", "days"),
    DAYS("date.days", "days"),
    WEEK("date.week", "week"),
    WEEKS("date.weeks", "weeks"),
    MONTHS("date.months", "months"),
    MONTH("date.month", "months"),
    YEARS("date.years", "years"),
    YEAR("date.year", "year"),
    NOW("date.now", "now"),
    PERMANENT("date.permanent", "permanent"),
    FORMAT_SECONDS("date.format-seconds", "%minutes% minutes and %seconds% seconds"),
    FIRST_PAGE("page.first", "&cPrevious Page"),
    LAST_PAGE("page.last", "&aNext Page"),
    PREVIOUS_PAGE("page.previous", "&7First Page"),
    NEXT_PAGE("page.next", "&7Last Page"),
    CHOOSE_YOUR_TIMEZONE("choose-your-timezone", "§bChoose your timezone");


    private final String path;
    private final String message;
    private final String type = "others";

    public String get(CommandSender sender, Replacer... replacers) {
        if (sender == null) sender = ProxyServer.getInstance().getConsole();
        MessageAdapter adapter = sender.getLang().getMessageAdapter(this.path);
        if (adapter == null) return "";
        return adapter.get(replacers);
    }

    public void send(CommandSender sender, Replacer... replacers) {
        if (this.path == null) return;
        final MessageAdapter adapter = sender.getLang().getMessageAdapter(this.path);
        if (adapter == null) return;
        adapter.send(sender, replacers);
    }
}
