package it.gamerover.nocollisions;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static Field getField(Class<?> searchClass, String name) throws Exception {
        Field findField = null;
        try {
            findField = searchClass.getField(name);
        } catch (Exception ex) {
            findField = searchClass.getDeclaredField(name);
            findField.setAccessible(true);
        }
        return findField;
    }
}
