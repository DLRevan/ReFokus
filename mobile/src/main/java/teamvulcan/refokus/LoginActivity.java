package teamvulcan.refokus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.graphics.Color;
import android.widget.EditText;
import android.content.res.Resources;
import android.util.TypedValue;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            layout
        */
        RelativeLayout loginLayout = new RelativeLayout(this);
        loginLayout.setBackgroundColor(Color.rgb(84, 228, 120));

        /*
            sign in button
        */
        Button loginButton = new Button(this);
        loginButton.setText("LOGIN");
        loginButton.setTextSize(16);
        loginButton.setTextColor(Color.WHITE);
        loginButton.setBackgroundColor(Color.rgb(84, 120, 228));
        loginButton.setWidth(pixelsToDIP(250));
        loginButton.setHeight(pixelsToDIP(5));
        loginButton.setId(3);

        RelativeLayout.LayoutParams loginButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        loginButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        loginButtonDetails.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        loginButtonDetails.setMargins(0, 0, 0, pixelsToDIP(175));

        /*
            password field
        */
        EditText passwordField = new EditText(this);
        passwordField.setWidth(pixelsToDIP(250));
        passwordField.setPadding(pixelsToDIP(5), pixelsToDIP(5), pixelsToDIP(5), pixelsToDIP(5));
        passwordField.setId(2);

        RelativeLayout.LayoutParams passwordFieldDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        passwordFieldDetails.addRule(RelativeLayout.ABOVE, loginButton.getId());
        passwordFieldDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        passwordFieldDetails.setMargins(0, 0, 0, pixelsToDIP(25));

        /*
            username field
        */
        EditText usernameField = new EditText(this);
        usernameField.setWidth(pixelsToDIP(250));
        usernameField.setPadding(pixelsToDIP(5), pixelsToDIP(5), pixelsToDIP(5), pixelsToDIP(5));
        usernameField.setId(1);

        RelativeLayout.LayoutParams usernameFieldDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        usernameFieldDetails.addRule(RelativeLayout.ABOVE, passwordField.getId());
        usernameFieldDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usernameFieldDetails.setMargins(0, 0, 0, pixelsToDIP(15));

        loginLayout.addView(loginButton, loginButtonDetails);
        loginLayout.addView(passwordField, passwordFieldDetails);
        loginLayout.addView(usernameField, usernameFieldDetails);

        setContentView(loginLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // method to convert pixels to dip
    public int pixelsToDIP(int pixels) {
        Resources r = getResources();
        int dip = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels,
                r.getDisplayMetrics()
        );
        return dip;
    }
}
