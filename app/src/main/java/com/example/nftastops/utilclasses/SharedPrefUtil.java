package com.example.nftastops.utilclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class SharedPrefUtil {

   /* public static void saveTasksToSharedPrefs(Context context, Map<String, T> favEvents) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favEvents); //tasks is an ArrayList instance variable
        prefsEditor.putString("fav", json);
        prefsEditor.commit();
    }

    public static Map<String, T> getTasksFromSharedPrefs(Context context) {
        Gson gson = new Gson();
        String json = getRawTasksFromSharedPrefs(context);
        Map<String, T> favEvents = gson.fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
        return favEvents;
    }

    public static String getRawTasksFromSharedPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return appSharedPrefs.getString("fav", "");
    }*/
}
