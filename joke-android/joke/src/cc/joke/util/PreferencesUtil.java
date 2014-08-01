package cc.joke.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferencesUtil
{

    private PreferencesUtil()
    {
    }

    public static SharedPreferences getPreferences(final Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Editor getEditor(final Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static void setPreferences(Context context, String preference, String key, boolean value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setPreferences(Context context, String preference, String key, long value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void setPreferences(Context context, String preference, String key, String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(Context context, String preference, String key, String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setPreferences(Context context, String preference, String key, int value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setPreferences(Context context, String preference, String key, float value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static boolean getPreference(Context context, String preference, String key, boolean defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static long getPreference(Context context, String preference, String key, long defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static int getPreference(Context context, String preference, String key, int defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static float getPreference(Context context, String preference, String key, float defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }
}
