package dz.ibrahim.bungee.hover;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class HoverCreator {

    private final List<HoverMessage> messageParts;

    public HoverCreator() {
        this.messageParts = new ArrayList<>();
    }

    public HoverCreator message(String message) {
        this.messageParts.add(new HoverMessage(message));
        return this;
    }

    public HoverCreator text(String text) {
        this.messageParts.get(this.messageParts.size() - 1).text(text);
        return this;
    }

    public HoverCreator suggest(String suggest) {
        this.messageParts.get(this.messageParts.size() - 1).suggest(suggest);
        return this;
    }

    public HoverCreator command(String command) {
        this.messageParts.get(this.messageParts.size() - 1).command(command);
        return this;
    }

    public HoverCreator send(CommandSender sender) {
        List<TextComponent> components = new ArrayList<>();

        for (HoverMessage hoverMessage : this.messageParts) {
            String text = ChatColor.translateAlternateColorCodes('&', hoverMessage.getName());
            TextComponent component = new TextComponent(new ComponentBuilder(text).create());

            if (hoverMessage.getText() != null) {
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage.getText()).create()));
            }

            if (hoverMessage.getCommand() != null) {
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + hoverMessage.getCommand()));
            }

            if (hoverMessage.getSuggest() != null) {
                component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + hoverMessage.getSuggest()));
            }
            components.add(component);
        }

        sender.sendMessage(components.toArray(new TextComponent[0]));
        return this;
    }
}
