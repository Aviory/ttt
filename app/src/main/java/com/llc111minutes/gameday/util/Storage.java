package com.llc111minutes.gameday.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.model.Stadias;
import com.llc111minutes.gameday.model.StickChangeData;
import com.llc111minutes.gameday.model.TeamInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Storage {

    private final static String FIRST_OPENING_APP = "first_opening_app";
    private final static String FIRST_OPENING_FRAGMENT = "first_opening_fragment1";

    private static Storage sStorage;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Deprecated
    private Storage() {
    }

    private Storage(SharedPreferences mPref) {
        this.mPref = mPref;
        this.mEditor = mPref.edit();
    }

    public static Storage instance(Context context) {
        if (sStorage != null) return sStorage;
        sStorage = new Storage(PreferenceManager.getDefaultSharedPreferences(context));
        return sStorage;
    }

    public <T extends StickChangeData> List<T> getListItemInfo(Class<T> data, String field) {
        ArrayList<T> list = new ArrayList<>();
        String jsonPreferences = mPref.getString(field, "");
        if (TextUtils.isEmpty(jsonPreferences)) return list;
        LogUtil.info(this, "String: " + jsonPreferences);
        if (data.equals(TeamInfo.class)) {
            Type type = new TypeToken<List<TeamInfo>>() {
            }.getType();
            list = new Gson().fromJson(jsonPreferences, type);
        } else {
            Type type = new TypeToken<List<Stadias>>() {
            }.getType();
            list = new Gson().fromJson(jsonPreferences, type);
        }
        return list;
    }

    public <T extends StickChangeData> void setListTeamInfo(String field, List<T> stickChangeDataList) {
        mEditor = mPref.edit();
        String str = new Gson().toJson(stickChangeDataList);
        LogUtil.info(this, "String: " + str);
        mEditor.putString(field, str);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(mEditor);
    }

    public <T extends StickChangeData> void addStickChangeData(String type, T stickChangeData) {
        ArrayList<T> list = new ArrayList<T>(getListItemInfo((Class<T>) stickChangeData.getClass(), type));
        LogUtil.info(this, "Type: " + type);
        LogUtil.info(this, "Sticker: " + stickChangeData);
        LogUtil.info(this, "List: " + list);

        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            T t = (T) listIterator.next();
            if (t.getId().equals(stickChangeData.getId())) /*listIterator.remove();*/ return;
        }
        if (list.size() >= 4) {
            list = new ArrayList<>(list.subList(0, 3));
        }
        list.add(0, stickChangeData);
        setListTeamInfo(type, list);
    }

    public void setPurchaseState(boolean state) {
        mEditor.putBoolean(Const.SKU_PURCHASE_BUY, state);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(mEditor);
    }

    public boolean getPurchaseState() {
        return mPref.getBoolean(Const.SKU_PURCHASE_BUY, false);
    }

    public boolean isFirstOpeningApp() {
        long timeFirstStart = mPref.getLong(FIRST_OPENING_APP, -1);
        return timeFirstStart < 1;
    }

    public void setFirstOpeningApp() {
        mEditor.putLong(FIRST_OPENING_APP, System.currentTimeMillis()).apply();
    }

    public boolean isFirstOpeningFragment() {
        long timeFirstStart = mPref.getLong(FIRST_OPENING_FRAGMENT, -1);
        return timeFirstStart < 1;
    }

    public void setFirstOpeningFragment() {
        mEditor.putLong(FIRST_OPENING_FRAGMENT, System.currentTimeMillis()).apply();
    }
}