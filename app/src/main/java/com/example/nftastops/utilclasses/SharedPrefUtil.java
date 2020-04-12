package com.example.nftastops.utilclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class SharedPrefUtil {

    public static <E> void saveTasksToSharedPrefs(Context context, E obj, String key) {
        if (context == null) return;
        SharedPreferences appSharedPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        String json = (String) obj;
        if (!key.equals(Constants.TOKEN)) {
            Gson gson = new Gson();
            json = gson.toJson(obj); //tasks is an ArrayList instance variable
        }
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static <E> E getTasksFromSharedPrefs(Context context, String key) {
        if (context == null) return null;
        Gson gson = new Gson();
        String json = getRawTasksFromSharedPrefs(context, key);
        E value = gson.fromJson(json, new TypeToken<E>() {
        }.getType());
        return value;
    }

    public static String getRawTasksFromSharedPrefs(Context context, String key) {
        if (context == null) return "";
        SharedPreferences appSharedPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return appSharedPrefs.getString(key, "");
    }
}
