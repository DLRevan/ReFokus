package teamvulcan.refokus.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.concurrent.ExecutionException;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class User {
    Context context;
    Portal portal;

    public User (Context context){
        this.context = context;
        portal = new Portal(context);
    }

    public boolean is_logged_in(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getBoolean("logged_in",false);
    }

    public int id(String email){
        Future<JsonObject> user_task ;
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        int id = -1 ;
        user_task = Ion.with(context)
                .load(portal.api_url() + "/user/id/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(context, "network connection error", Toast.LENGTH_LONG).show();
                            Log.e("network_error", e.getMessage());
                            return;
                        }
                    }
                });

        try {
            id = user_task.get().get("app_user_id").getAsInt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return id ;
    }

    public int app_user_id(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("app_user_id",-1);
    }

    public boolean save_info(int app_user_id){
        Future<JsonObject> user_task ;
        if(app_user_id == -1){
            Toast.makeText(context, "error updating user info", Toast.LENGTH_LONG).show();
            return false;
        }
        JsonObject json = new JsonObject();
        json.addProperty("app_user_id", app_user_id);
        user_task = Ion.with(context)
                .load(portal.api_url() + "/user/info/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(context, "network connection error", Toast.LENGTH_LONG).show();
                            Log.e("network_error", e.getMessage());
                            return;
                        }
                    }
                });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            boolean error = user_task.get().get("error").getAsBoolean();
            if(!error){
                editor.putString("name",user_task.get().get("name").getAsString());
                editor.putString("email",user_task.get().get("email").getAsString());
                editor.putString("gender",user_task.get().get("gender").getAsString());
                editor.putInt("age" ,user_task.get().get("age").getAsInt() );
                editor.putInt("current_study" , user_task.get().get("cur_study").getAsInt());
                editor.commit();
                return true;

            }else {
                Toast.makeText(context, "error updating user info", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
