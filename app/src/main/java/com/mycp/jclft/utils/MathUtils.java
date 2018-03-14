package com.mycp.jclft.utils;

/**
 * Created by 17073822 on 2017/7/27.
 * <p>
 * You may think you know what the following code does.
 * But you don't. Trust me.
 * Fiddle with it, and you'll spend many a sleepless
 * night cursing the moment you thought you'd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 */

public class MathUtils {
    public static final int str2Int(String strValue) {
        return str2Int(strValue, 0);
    }

    public static final int str2Int(String strValue, int defValue) {
        try {
            defValue = Integer.valueOf(strValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return defValue;
        }
    }

    public static final float str2Float(String strValue) {
        return str2Float(strValue, 0.0F);
    }

    public static final float str2Float(String strValue, float defValue) {
        try {
            defValue = Float.valueOf(strValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return defValue;
        }
    }

    public static final double str2Double(String strValue) {
        return str2Double(strValue, 0.0D);
    }

    public static final double str2Double(String strValue, double defValue) {
        try {
            defValue = Double.valueOf(strValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return defValue;
        }
    }


    public static final long str2long(String strValue) {
        long value = 0;
        try {
            value = Long.valueOf(strValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }


}
