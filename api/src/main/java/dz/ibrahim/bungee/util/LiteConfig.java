package dz.ibrahim.bungee.util;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LiteConfig {

    @Ignore
    public final File CONFIG_FILE;
    @Ignore
    public Configuration config;
    @Ignore
    public final Map<String, Field> fieldMap = new LinkedHashMap<>();
    @Ignore
    private final String name;
    @Ignore
    private final File path;

    public LiteConfig(String name, File path) {
        this.name = name;
        this.path = path;
        this.CONFIG_FILE = path;
        if (!this.CONFIG_FILE.exists()) {
            if (this.CONFIG_FILE.getParentFile() != null && !this.CONFIG_FILE.getParentFile().exists()) {
                this.CONFIG_FILE.getParentFile().mkdir();
            }
            try {
                this.CONFIG_FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadConfig(Object instance) {
        for (final Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Ignore.class)) continue;
            final String name = field.getName().toLowerCase().replaceAll("_", "-");
            this.fieldMap.put(name, field);
            try {
                if (field.getType() == float.class) {
                    float f = (float) field.get(instance);
                    field.set(this, this.getFloat(name, f));
                } else {
                    field.set(this, this.get(name, field.get(instance)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, this.CONFIG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveValues(Object instance) {
        for (Map.Entry<String, Field> fieldEntry : this.fieldMap.entrySet()) {
            final String path = fieldEntry.getKey();
            final Field field = fieldEntry.getValue();
            try {
                this.set(path, field.get(instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.save();
    }

    public void removeFile() {
        this.CONFIG_FILE.delete();
    }

    public final String path() {
        return this.path.getName().replace(".yml", "");
    }
    public final String name() {
        return this.name;
    }

    protected void set(String path, Object val)
    {
        this.config.set( path, val );
    }

    public Object get(String path, Object def) {
        return this.config.get(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean( path, this.config.getBoolean( path ) );
    }

    public double getDouble(String path, double def) {
        return this.config.getDouble( path, this.config.getDouble( path ) );
    }

    public float getFloat(String path, float def) {
        return (float) getDouble( path, def);
    }

    public int getInt(String path, int def) {
        return this.config.getInt( path, this.config.getInt( path ) );
    }

    public <T> List<T> getList(String path, List<T> def) {
        return (List<T>) this.config.getList( path, this.config.getList( path ) );
    }

    public String getString(String path, String def) {
        return this.config.getString( path, this.config.getString( path ) );
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Ignore {}
}
