package dz.ibrahim.bungee.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Replacer {

    private final String from;
    private final String to;

    public <V> Replacer(String from, V to) {
        this.from = from;
        this.to = to.toString();
    }

    public Replacer(String from) {
        this.from = from;
        this.to = "";
    }

    public String replace(String fullMessage) {
        return fullMessage.replaceAll("%" + from + "%", to);
    }
}
