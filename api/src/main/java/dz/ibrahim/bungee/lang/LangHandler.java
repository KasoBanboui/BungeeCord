package dz.ibrahim.bungee.lang;

import dz.ibrahim.bungee.util.Reflection;
import dz.ibrahim.bungee.util.StringUtil;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class LangHandler {

    private final List<Lang> langs;
    private final List<Enum<?>> enums;
    public final Lang ENGLISH;

    public LangHandler() {
        this.langs = new ArrayList<>();
        this.enums = new ArrayList<>();

        this.register(LangBungee.class);

        this.ENGLISH = this.createLang("English");
        File dir = new File("lang");
        if (!dir.exists()) dir.mkdir();
        File[] files = dir.listFiles();
        if (files == null) return;
        for (final File file : files) {
            if (file.isDirectory() || !file.getName().endsWith(".yml")) continue;
            final String name = StringUtil.removeEnd(file.getName(), "");
            if (name.contains(" ")) continue;
            if (this.getLang(name) != null) continue;
            this.langs.add(new Lang(this, name));
        }
    }

    public Lang createLang(String name) {
        if (name.contains(" ")) {
            System.out.println("Cannot create a lang if the name contains spacers");
            return null;
        }

        if (this.getLang(name) != null) {
            System.out.println("The lang named '" + name + "' already exists");
            return null;
        }

        final Lang lang = new Lang(this, name);
        lang.loadAdapters();
        this.langs.add(lang);
        return lang;
    }

    public Lang getLang(String name) {
        for (Lang lang : this.getLangs()) {
            if (lang.getName().equalsIgnoreCase(name)) return lang;
        }

        return null;
    }
    private final Reflection reflection = new Reflection();

    public void register(Class<?>... classes) {
        this.register(Arrays.asList(classes));
    }

    public void register(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (clazz.isEnum()) {
                Enum<?>[] enums = (Enum<?>[]) clazz.getEnumConstants();
                this.enums.addAll(Arrays.asList(enums));
            }
        }

        for (Lang lang : this.langs) {
            lang.loadAdapters();
        }
    }
}
