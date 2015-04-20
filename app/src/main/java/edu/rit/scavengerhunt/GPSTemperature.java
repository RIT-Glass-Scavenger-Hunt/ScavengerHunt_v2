package edu.rit.scavengerhunt;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.util.Log;


public class GPSTemperature extends Activity implements LocationListener{
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String provider;
    double userLat;
    double userLog;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    public GPSTemperature(){
        //  setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    /*
    *  As user's location is changing, it updates the background colors.
    * */
    @Override
    public void onLocationChanged(Location location) {
           userLat = location.getLatitude();
           userLog = location.getLongitude();

    }

    public double getUserLat(){
        return userLat;
    }

    public double  getUserLog(){
        return userLog;
    }



    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}