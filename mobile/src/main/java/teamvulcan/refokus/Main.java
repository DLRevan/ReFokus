package teamvulcan.refokus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import teamvulcan.refokus.authenticator.LoginActivity;
import teamvulcan.refokus.authenticator.RegisterActivity;
import teamvulcan.refokus.helpers.User;
import teamvulcan.refokus.user.Summary;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class Main extends Activity {

    // ui elements
    private Button login ;
    private Button register;

    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false); // saving the default preferences for the first time
        user = new User(getApplicationContext());

        if(user.is_logged_in()){
            if(savedInstanceState == null) {
                startActivity(new Intent(this , Summary.class));
                finish();
            }
        } else {
            if(savedInstanceState == null) {
                setContentView(R.layout.activity_main);
                login = (Button)findViewById(R.id.main_login);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(user.isOnline()){
                            startActivity(new Intent(getApplication(), LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Internet connectivity required",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                register = (Button)findViewById(R.id.main_register);
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(user.isOnline()){
                            startActivity(new Intent(getApplication(), RegisterActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Internet connectivity required",Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        }
    }

}

