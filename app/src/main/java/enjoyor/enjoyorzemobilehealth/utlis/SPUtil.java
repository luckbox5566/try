package enjoyor.enjoyorzemobilehealth.utlis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/18.
 * SharedPreferences工具类
 */

public class SPUtil {
    private static final String FILE_NAME = "enjoyor_ydlc_data";

    /**
     * @param context 上下文对象
     * @param name    xml文件名称
     * @return
     */
    private static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * @param context 上下文对象
     * @param name    xml文件名称
     * @return
     */
    private static SharedPreferences.Editor getEditor(Context context, String name) {
        return getSharedPreferences(context, name).edit();
    }

    /**
     * 存入Int类型的值
     *
     * @param context 上下文对象
     * @param key     键值
     * @param val     value值
     */
    public static void putIntVal(Context context, String key, int val) {
        SharedPreferences.Editor editor = getEditor(context, FILE_NAME);
        editor.putInt(key, val);
        editor.commit();
    }

    /**
     * 存入String类型的值
     *
     * @param context 上下文对象
     * @param key     键值
     * @param val     value值
     */
    public static void putStringVal(Context context, String key, String val) {
        SharedPreferences.Editor editor = getEditor(context, FILE_NAME);
        editor.putString(key, val);
        editor.commit();
    }

    /**
     * 存入boolean类型的值
     *
     * @param context 上下文对象
     * @param key     键值
     * @param val     value值
     */
    public static void putBooleanVal(Context context, String key, boolean val) {
        SharedPreferences.Editor editor = getEditor(context, FILE_NAME);
        editor.putBoolean(key, val);
        editor.commit();
    }

    /**
     * 存入float类型的值
     *
     * @param context 上下文对象
     * @param key     键值
     * @param val     value值
     */
    public static void putFloatVal(Context context, String key, float val) {
        SharedPreferences.Editor editor = getEditor(context, FILE_NAME);
        editor.putFloat(key, val);
        editor.commit();
    }

    /**
     * 存入long类型的值
     *
     * @param context 上下文对象
     * @param key     键值
     * @param val     value值
     */
    public static void putLongVal(Context context, String key, long val) {
        SharedPreferences.Editor editor = getEditor(context, FILE_NAME);
        editor.putLong(key, val);
        editor.commit();
    }

    /**
     * 取出int类型的值
     *
     * @param context  上下文对象
     * @param key      键值
     * @param defValue 设定的默认值
     * @return
     */
    public static int getIntVal(Context context, String key, int defValue) {
        SharedPreferences sp = getSharedPreferences(context, FILE_NAME);
        int value = sp.getInt(key, defValue);
        return value;
    }

    /**
     * 取出String类型的值
     *
     * @param context  上下文对象
     * @param key      键值
     * @param defValue 设定的默认值
     * @return
     */
    public static String getStringVal(Context context, String key, String defValue) {
        SharedPreferences sp = getSharedPreferences(context, FILE_NAME);
        String value = sp.getString(key, defValue);
        return value;
    }

    /**
     * 取出boolean类型的值
     *
     * @param context  上下文对象
     * @param key      键值
     * @param defValue 设定的默认值
     * @return
     */
    public static boolean getBooleanVal(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSharedPreferences(context, FILE_NAME);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    /**
     * 取出float类型的值
     *
     * @param context  上下文对象
     * @param key      键值
     * @param defValue 设定的默认值
     * @return
     */
    public static float getFloatVal(Context context, String key, float defValue) {
        SharedPreferences sp = getSharedPreferences(context, FILE_NAME);
        float value = sp.getFloat(key, defValue);
        return value;
    }

    /**
     * 取出long类型的值
     *
     * @param context  上下文对象
     * @param key      键值
     * @param defValue 设定的默认值
     * @return
     */
    public static long getLongVal(Context context, String key, long defValue) {
        SharedPreferences sp = getSharedPreferences(context, FILE_NAME);
        long value = sp.getLong(key, defValue);
        return value;
    }
}
