package teamvulcan.refokus.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class Prefs {
    Context context;

    public Prefs(Context context){
        this.context = context ;
    }

    public void create_pref(String key ,String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key ,value);

    }

    public void update_pref(String key ,String value){

    }
}
