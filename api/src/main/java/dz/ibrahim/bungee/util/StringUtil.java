package dz.ibrahim.bungee.util;

import java.util.ArrayList;

public class StringUtil {

    public static boolean isEmpty(CharSequence var0) {
        return var0 == null || var0.length() == 0;
    }

    public static String substringBeforeLast(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            int var2 = var0.lastIndexOf(var1);
            return var2 == -1 ? var0 : var0.substring(0, var2);
        } else {
            return var0;
        }
    }

    public static String substringAfterLast(String var0, String var1) {
        if (isEmpty(var0)) {
            return var0;
        } else if (isEmpty(var1)) {
            return "";
        } else {
            int var2 = var0.lastIndexOf(var1);
            return var2 != -1 && var2 != var0.length() - var1.length() ? var0.substring(var2 + var1.length()) : "";
        }
    }

    public static String removeEnd(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            return var0.endsWith(var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
        } else {
            return var0;
        }
    }

    public static String substringBefore(String var0, String var1) {
        if (!isEmpty(var0) && var1 != null) {
            if (var1.isEmpty()) {
                return "";
            } else {
                int var2 = var0.indexOf(var1);
                return var2 == -1 ? var0 : var0.substring(0, var2);
            }
        } else {
            return var0;
        }
    }

    public static String substringAfter(String var0, String var1) {
        if (isEmpty(var0)) {
            return var0;
        } else if (var1 == null) {
            return "";
        } else {
            int var2 = var0.indexOf(var1);
            return var2 == -1 ? "" : var0.substring(var2 + var1.length());
        }
    }

    public static String[] substringsBetween(String var0, String var1, String var2) {
        if (var0 != null && !isEmpty(var1) && !isEmpty(var2)) {
            int var3 = var0.length();
            if (var3 == 0) {
                return new String[0];
            } else {
                int var4 = var2.length();
                int var5 = var1.length();
                ArrayList var6 = new ArrayList();

                int var9;
                for(int var7 = 0; var7 < var3 - var4; var7 = var9 + var4) {
                    int var8 = var0.indexOf(var1, var7);
                    if (var8 < 0) {
                        break;
                    }

                    var8 += var5;
                    var9 = var0.indexOf(var2, var8);
                    if (var9 < 0) {
                        break;
                    }

                    var6.add(var0.substring(var8, var9));
                }

                return var6.isEmpty() ? null : (String[])var6.toArray(new String[var6.size()]);
            }
        } else {
            return null;
        }
    }
}
