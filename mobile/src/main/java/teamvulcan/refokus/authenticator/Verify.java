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
public class Verify {
    private Context context;
    private boolean email_found;
    private boolean user_authenticated;
    private Portal portal;

    public Verify(Context context) {
        this.context = context;
        portal = new Portal(this.context);
    }

    public boolean email(String email) {
        Future<JsonObject> email_task ;
        JsonObject json = new JsonObject();
        json.addProperty("email", email);

        email_task = Ion.with(context)
                .load(portal.api_url() + "/verify/email/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(context, "network connection error", Toast.LENGTH_LONG).show();
                            Log.e("network_error", e.getMessage());
                            email_found = false;
                            return;
                        } else {
                            email_found = result.get("exists").getAsBoolean();
                        }
                    }
                });

        try {
            email_task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (email_found) {
            return true;
        } else {
            return false;
        }


    }


    public boolean user(String email , String pass){
        user_authenticated = false;
        try {
            pass = Utilities.sha1(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        JsonObject json = new JsonObject();
        json.addProperty("email",email);
        json.addProperty("pass",pass);
        Future<JsonObject> user_task ;

        user_task = Ion.with(context)
                .load(portal.api_url()+"/verify/user/")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(context, "network connection error", Toast.LENGTH_LONG).show();
                            Log.e("network_error", e.getMessage());
                            user_authenticated = false;
                        } else {
                            user_authenticated = result.get("authenticated").getAsBoolean();
                        }
                    }
                });
        try {
            user_task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(user_authenticated){
            return true;
        } else {
            return false ;
        }
    }
}
