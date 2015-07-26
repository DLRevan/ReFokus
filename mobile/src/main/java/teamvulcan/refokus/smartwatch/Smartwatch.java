package teamvulcan.refokus.smartwatch;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;

import teamvulcan.refokus.R;
import teamvulcan.refokus.user.Summary;
import teamvulcan.refokus.podcast.Podcast;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class Smartwatch extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartwatch);

        final Resources res = getResources();

        Button viewGraphButton = (Button)findViewById(R.id.viewGraph);
        viewGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), ViewGraphActivity.class));
            }
        });

        Button mButton = (Button) findViewById(R.id.sendNotification);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summaryIntent = new Intent(getBaseContext(), Summary.class);
                PendingIntent summaryPendingIntent =
                        PendingIntent.getActivity(getBaseContext(), 0, summaryIntent, 0);

                Intent podcastIntent = new Intent(getBaseContext(), Podcast.class);
                PendingIntent podcastPendingIntent =
                        PendingIntent.getActivity(getBaseContext(), 0, podcastIntent, 0);

                Notification notification = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setPriority(1)
                        .setContentTitle("ReFokus")
                        .setContentText("Time to meditate!")
                        .extend(new NotificationCompat.WearableExtender().setBackground(BitmapFactory.decodeResource(res,
                                R.drawable.scenary)))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(summaryPendingIntent)
                        .addAction(R.drawable.podcast, getString(R.string.podcast), podcastPendingIntent)
                        .build();

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

                int notificationId = 1;
                notificationManager.notify(notificationId, notification);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.summary, menu);
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
}
