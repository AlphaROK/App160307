package my.cookandroid.com.app160307;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView txLat, txLong, txAlt, txState;
    public Button btLoc, btMap;
    public LocationManager locationMan;
    public MyLocationListener myLocationListener;
    public double latitude, longitude, altitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txLat = (TextView) findViewById(R.id.txLat);
        txLong = (TextView) findViewById(R.id.txLong);
        txAlt = (TextView) findViewById(R.id.txAlt);
        txState = (TextView) findViewById(R.id.txState);
        btLoc = (Button) findViewById(R.id.btLoc);
        btMap = (Button) findViewById(R.id.btMap);

        btLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txState.setText("Getting location...");
                latitude = myLocationListener.latitude;
                longitude = myLocationListener.longitude;
                altitude = myLocationListener.altitude;

                txLat.setText(String.format("%g", latitude));
                txLong.setText(String.format("%g", longitude));
                txAlt.setText(String.format("%g", altitude));
                txState.setText("Done retreiving.");

            }
        });

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sLoc = String.format("geo:%g,%g?z=17", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sLoc));
                startActivity(intent);
            }
        });

        locationMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocationListener = new MyLocationListener();
        long minTime = 1000;
        float minDistance = 0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocationListener);
        locationMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationListener);
    }
}
