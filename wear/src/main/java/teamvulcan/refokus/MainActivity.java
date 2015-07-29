
package teamvulcan.refokus;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity {

   private TextView mTextView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
       stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
           @Override
           public void onLayoutInflated(WatchViewStub stub) {
               mTextView = (TextView) stub.findViewById(R.id.text);
           }
       });

       Context context = this;

       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sound_file_1);
       mediaPlayer.start(); // no need to call prepare(); create() does that for you

       try {

       } catch(Exception e) {
           System.out.println("Exception");
       }

       final String TAG = "KCKCKC";
       GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
               .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                   @Override
                   public void onConnected(Bundle connectionHint) {
                       Log.d(TAG, "onConnected: " + connectionHint);
                       System.out.println("KCKCKCKC");
                       // Now you can use the Data Layer API
                   }
                   @Override
                   public void onConnectionSuspended(int cause) {
                       Log.d(TAG, "onConnectionSuspended: " + cause);
                       System.out.println("KCKCKCKCE");
                   }
               })
               .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                   @Override
                   public void onConnectionFailed(ConnectionResult result) {
                       Log.d(TAG, "onConnectionFailed: " + result);
                       System.out.println("KCKCKCKCF");
                   }
               })
                       // Request access only to the Wearable API
               .addApi(Wearable.API)
               .build();
       mGoogleApiClient.connect();
//       Uri myUri = ....; // initialize Uri here
//       MediaPlayer mediaPlayer = new MediaPlayer();
//       mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//       mediaPlayer.setDataSource(getApplicationContext(), myUri);
//       mediaPlayer.prepare();
//       mediaPlayer.start();

//       String url = "http://........"; // your URL here
//       MediaPlayer mediaPlayer = new MediaPlayer();
//       mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//       mediaPlayer.setDataSource(url);
//       mediaPlayer.prepare(); // might take long! (for buffering, etc)
//       mediaPlayer.start();
   }
}
