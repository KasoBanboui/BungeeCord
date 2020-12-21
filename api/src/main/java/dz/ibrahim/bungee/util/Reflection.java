package dz.ibrahim.bungee.util;

import com.google.common.base.Joiner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Reflection {

    public Reflection() {
    }

    private <T> Constructor<T> constructor(Class<T> class_, Class<?> ... arrclass) {
        try {
            Constructor<T> constructor = class_.getDeclaredConstructor(arrclass);
            constructor.setAccessible(true);
            return constructor;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalArgumentException(noSuchMethodException);
        }
    }

    private <T> T fetchConstructor(Constructor<T> constructor, Object ... arrobject) {
        try {
            return constructor.newInstance(arrobject);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            throw new IllegalArgumentException(reflectiveOperationException);
        }
    }

    public Field access(Class class_, String string) {
        try {
            Field field = class_.getDeclaredField(string);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new IllegalArgumentException(class_.getSimpleName() + ":" + string, noSuchFieldException);
        }
    }

    public Field access(Class class_, String ... arrstring) {
        for (String string : arrstring) {
            try {
                Field field = class_.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException ignored) {}
        }
        throw new IllegalArgumentException(class_.getSimpleName() + ":" + Joiner.on((String)",").join((Object[])arrstring));
    }

    public <T> T fetch(Field field, Object object) {
        try {
            return (T) field.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    public <T> void setLocalField(Class class_, Object object, String string, T t) {
        this.set(this.access(class_, string), object, t);
    }

    public <T> void set(Field field, Object object, T t) {
        try {
            field.set(object, t);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    public <T> T getLocalField(Class class_, Object object, String string) {
        return this.fetch(this.access(class_, string), object);
    }
}

