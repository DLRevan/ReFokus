package teamvulcan.refokus.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import teamvulcan.refokus.Main;
import teamvulcan.refokus.R;
import teamvulcan.refokus.authenticator.LoginActivity;
import teamvulcan.refokus.helpers.User;
import teamvulcan.refokus.preferences.SettingsActivity;
import teamvulcan.refokus.smartwatch.Smartwatch;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class Summary extends Activity {
    private TextView name;
    private TextView email;
    private TextView gender;
    private TextView age;
    private TextView cur_study;
    private Button smartwatchButton;

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!new User(getApplicationContext()).is_logged_in()){
            startActivity(new Intent(this, Main.class));
            finish();
        } else {
            setContentView(R.layout.activity_summary);
            populate_info();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //
        }

        setContentView(R.layout.activity_summary);
        populate_info();

        smartwatchButton = (Button)findViewById(R.id.smartwatch);
        smartwatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), Smartwatch.class));
            }
        });

    }

    public void populate_info(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name = (TextView)findViewById(R.id.user_name);
        name.setText("Name : "+prefs.getString("name" ,"unset"));

        email = (TextView)findViewById(R.id.user_email);
        email.setText("Email : "+prefs.getString("email" ,"unset"));

        gender = (TextView)findViewById(R.id.user_gender);
        gender.setText("Gender : "+prefs.getString("gender" ,"unset"));

        age = (TextView)findViewById(R.id.user_age);

        age.setText("Age : "+Integer.toString(prefs.getInt("age" ,-1)));

        cur_study = (TextView)findViewById(R.id.user_cur_study);
        cur_study.setText("Current Study : "+Integer.toString(prefs.getInt("cur_study" ,-1)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            this.startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

