package teamvulcan.refokus.authenticator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import teamvulcan.refokus.helpers.Portal;
import teamvulcan.refokus.helpers.Utilities;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class Register {
    private Context context;
    private Portal portal;

    public Register(Context context) {
        this.context = context;
        portal = new Portal(this.context);
    }

    public int user(String name,String email , String password , int age , String gender){
        int app_user_id = -1 ;
        try {
            password = Utilities.sha1(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Future<JsonObject> register_task ;
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email", email);
        json.addProperty("pass", password);// don't put the key as "password"  , gson gives json parse error
        json.addProperty("age", age);
        json.addProperty("gender", gender);
        Log.i("json",json.toString());

        register_task = Ion.with(context)
                .load(portal.api_url() + "/user/register/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Log.e("network_error", e.getMessage());
                            Toast.makeText(context, "network connection error", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

        try {
            app_user_id = register_task.get().get("app_user_id").getAsInt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return app_user_id;
    }

}
