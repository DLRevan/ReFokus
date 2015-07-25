package teamvulcan.refokus.authenticator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import teamvulcan.refokus.R;
import teamvulcan.refokus.helpers.User;
import teamvulcan.refokus.helpers.Utilities;
import teamvulcan.refokus.user.Summary;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class RegisterActivity extends Activity {
    Register register ;
    Verify verify;
    User user ;
    // UI elements

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText age;
    private RadioGroup gender_radio_group;
    private Button register_button ;

    // variables
    private String gender = "unset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialise the helpers
        register = new Register(getApplicationContext());
        verify = new Verify(getApplicationContext());
        user = new User(getApplicationContext());

        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.name_register_form);
        email = (EditText)findViewById(R.id.email_register_form);
        password = (EditText)findViewById(R.id.password_register_form);
        age = (EditText)findViewById(R.id.age_register_form);
        register_button = (Button)findViewById(R.id.register_button);
        gender_radio_group = (RadioGroup)findViewById(R.id.gender_register_form);
        gender_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int choice = gender_radio_group.getCheckedRadioButtonId();
                switch(choice) {
                    case R.id.gender_radio_male_register_form:
                        gender = "male";
                        break;
                    case R.id.gender_radio_female_register_form:
                        gender = "female";
                        break;
                }
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   if (user.isOnline()) {
                                                       attemptRegister();
                                                   } else {
                                                       Toast.makeText(getApplicationContext(), "Internet connectivity required", Toast.LENGTH_LONG).show();
                                                   }
                                               }
                                           }
        );

    }

    public void attemptRegister() {
        int age_num;
        // Reset errors.
        name.setError(null);
        email.setError(null);
        password.setError(null);
        age.setError(null);

        // Store values at the time of the login attempt.
        String name_string = name.getText().toString();
        String email_string = email.getText().toString();
        String password_string = password.getText().toString();
        if(age.getText().toString().trim().equals("")){
            age_num = -1;
        }else {
            age_num = Integer.parseInt(age.getText().toString());
        }


        boolean cancel = false;
        View focusView = null;



        if (TextUtils.isEmpty(name_string)) {
            name.setError(getString(R.string.error_field_required));
            focusView = name;
            cancel = true;
        }

        if (TextUtils.isEmpty(password_string)){
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        } else if (!isPasswordValid(password_string)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email_string)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else {
            if (!email_string.contains("@")) {
                email.setError(getString(R.string.error_invalid_email));
                focusView = email;
                cancel = true;
            } else if (isEmailDuplicate(email_string)) {
                email.setError(getString(R.string.error_email_exists));
                focusView = email;
                cancel = true;
            }
        }

        if (age_num == -1) {
            age.setError(getString(R.string.error_field_required));
            focusView = age;
            cancel = true;
        }


        if(gender == "unset"){
            Toast.makeText(getApplicationContext(),"please select your gender",Toast.LENGTH_LONG).show();
            focusView = gender_radio_group;
            cancel = true;

        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            String pass_encrypt = null;
            Log.i("reached","registration window");

            int app_user_id = register.user(name_string,email_string,password_string,age_num,gender);
            if(app_user_id == -1){
                focusView = name;
                Toast.makeText(this, "Registration error", Toast.LENGTH_LONG).show();
                focusView.requestFocus();
            } else {
                if(user.save_info(app_user_id)) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logged_in", true);
                    editor.commit();
                    Intent intent = new Intent(this, Summary.class);
                    //intent.putExtra("id",id);
                    Toast.makeText(this, "user registered", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }
        }
        return ;
    }
    private boolean isEmailDuplicate(String email) {

        boolean duplicate =  verify.email(email);
        if(duplicate){
            return true;
        } else {
            return false ;
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}
