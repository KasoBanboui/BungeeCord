package dz.ibrahim.bungee.hover;

import lombok.Getter;

@Getter
public class HoverMessage {

    private final String name;
    private String text, suggest, command;

    public HoverMessage(String name) {
        this.name = name;
    }

    public void text(String text) {
        this.text = text;
    }

    public void suggest(String suggest) {
        this.suggest = suggest;
    }

    public void command(String command) {
        this.command = command;
    }
}
