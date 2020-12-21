package dz.ibrahim.bungee.lang;

import lombok.Getter;
import dz.ibrahim.bungee.IBungee;
import dz.ibrahim.bungee.lang.adapter.*;
import dz.ibrahim.bungee.util.LiteConfig;
import dz.ibrahim.bungee.util.Reflection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Lang {

    private final String name;
    private LiteConfig messageConfig, messageListConfig, scoreboardConfig, menuConfig;
    private final List<Lang> langs;
    private final List<MenuAdapter> menuAdapters;
    private final List<MessageAdapter> messageAdapters;
    private final List<MessageListAdpater> messageListAdapters;
    private final List<ScoreboardAdapter> scoreboardAdapters;
    private final LangHandler handler;

    public Lang(LangHandler handler, final String name) {
        this.name = name;
        this.handler = handler;
        this.langs = new ArrayList<>();
        this.menuAdapters = new ArrayList<>();
        this.messageAdapters = new ArrayList<>();
        this.messageListAdapters = new ArrayList<>();
        this.scoreboardAdapters = new ArrayList<>();
        this.loadAdapters();
    }

    public void loadAdapters() {
        File dir = new File("lang");
        String parent = String.format("lang/%1s/", this.name);
        if (!dir.exists()) dir.mkdir();
        this.messageConfig = new LiteConfig("others", new File(parent, "others.yml"));
        this.messageListConfig = new LiteConfig("messages", new File(parent, "messages.yml"));
        this.scoreboardConfig = new LiteConfig("scoreboard", new File(parent, "scoreboards.yml"));
        this.menuConfig = new LiteConfig("menus", new File(parent, "menus.yml"));

        for (Enum<?> e : this.handler.getEnums()) {
            final String path = this.reflection.fetch(this.reflection.access(e.getDeclaringClass(), "path"), e);
            final String type = this.reflection.fetch(this.reflection.access(e.getDeclaringClass(), "type"), e);
            switch (type) {
                case "messages": this.loadMessages(path, e);
                break;
                case "others": this.loadOthers(path ,e);
                break;
                case "menus": this.loadMenus(path, e);
                break;
                case "scoreboards": this.loadScoreboards(path, e);
                break;
            }
        }

        this.messageListConfig.save();
        this.messageConfig.save();
        this.menuConfig.save();
        this.scoreboardConfig.save();
    }

    private final Reflection reflection = new Reflection();

    private void loadMessages(String path, Enum<?> e) {
        final List<String> messages = this.reflection.fetch(this.reflection.access(e.getDeclaringClass(), "messages"), e);
        this.messageListAdapters.add(new MessageListAdpater(path, this.messageListConfig.getList(path, messages)));
    }

    private void loadMenus(String path, Enum<?> e) {
        final String titleName = this.reflection.fetch(this.reflection.access(e.getDeclaringClass(), "titleName"), e);
        this.menuAdapters.add(new MenuAdapter(path, this.menuConfig.getString(path + ".titleName", titleName)));
    }

    private void loadOthers(String path, Enum<?> e) {
        final String message = this.reflection.fetch(this.reflection.access(e.getDeclaringClass(), "message"), e);
        this.messageAdapters.add(new MessageAdapter(path, this.messageConfig.getString(path, message)));
    }

    private void loadScoreboards(String path, Enum<?> e) {
        final List<String> lines = this.reflection.fetch(this.reflection.access(e.getDeclaringClass(), "lines"), e);
        this.scoreboardAdapters.add(new ScoreboardAdapter(path, this.scoreboardConfig.getList(path, lines)));
    }

    public MenuAdapter getMenuAdapter(String path) {
        for (MenuAdapter adapter : this.menuAdapters) {
            if (adapter.getPath().equals(path)) return adapter;
        }

        return null;
    }

    public MessageAdapter getMessageAdapter(String path) {
        for (MessageAdapter adapter : this.messageAdapters) {
            if (adapter.getPath().equals(path)) return adapter;
        }

        return null;
    }

    public MessageListAdpater getMessageListAdapter(String path) {
        for (MessageListAdpater adapter : this.messageListAdapters) {
            if (adapter.getPath().equals(path)) return adapter;
        }

        return null;
    }

    public ScoreboardAdapter getScoreboardAdapter(String path) {
        for (ScoreboardAdapter adapter : this.scoreboardAdapters) {
            if (adapter.getPath().equals(path)) return adapter;
        }

        return null;
    }

    public void delete() {
        this.messageConfig.removeFile();
        this.messageListConfig.removeFile();
        this.menuConfig.removeFile();
        this.scoreboardConfig.removeFile();
        IBungee.getInstance().getLangHandler().getLangs().remove(this);
    }
}
