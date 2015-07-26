package teamvulcan.refokus.podcast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import teamvulcan.refokus.R;

/**
 * Created by kcheng.2013 on 26/7/2015.
 */
public class Podcast extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.summary, menu);
        return true;
    }
}
