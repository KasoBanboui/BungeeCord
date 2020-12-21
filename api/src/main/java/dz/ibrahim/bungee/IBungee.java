package dz.ibrahim.bungee;

import dz.ibrahim.bungee.lang.LangHandler;
import lombok.Getter;

@Getter
public class IBungee {

    private static final IBungee instance = new IBungee();

    private final LangHandler langHandler;

    public IBungee() {
        this.langHandler = new LangHandler();
    }

    public static IBungee getInstance() {
        return instance;
    }
}
