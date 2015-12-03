package cs258.emerapp;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Z370 on 11/4/2015.
 */
public class LocationMap extends FragmentActivity implements OnMapReadyCallback {
     GoogleMap myMap;
     GPSTracker gps;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          gps=MainActivity.gps;
          setContentView(R.layout.activity_maps);
          SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.map);
          mapFragment.getMapAsync(this);
     }

     @Override
     public void onMapReady(GoogleMap map) {
          //retrieve lat and lang values of the locations and put markers
          myMap=map;
          myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
          Location loc= gps.getLocation();
          LatLng currLoc = new LatLng(loc.getLatitude(), loc.getLongitude());
          myMap.addMarker(new MarkerOptions().position(currLoc).title("Current Location"));
          myMap.moveCamera(CameraUpdateFactory.newLatLng(currLoc));
          myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

               @Override
               public void onMapClick(LatLng point) {
                    Log.d("Map","Map clicked");
                    myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 40));
                    Toast.makeText(getApplicationContext(), point.toString(),
                            Toast.LENGTH_LONG).show();
               }
          });

          myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

               @Override
               public void onMapLongClick(LatLng point) {
                    Log.d("Map","Map clicked");
                    myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
                    Toast.makeText(getApplicationContext(),"LONGGG CLICK",
                            Toast.LENGTH_LONG).show();
               }
          });
     }

  /*   @Override
    public void onMapLongClick(LatLng point) {

          myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
          myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 20));
          Toast.makeText(getApplicationContext(), point.toString(),
                  Toast.LENGTH_LONG).show();
          Log.e("MOUSE ","longclick");
     }

     @Override
     public void onMapClick(LatLng latLng) {
          Log.e("MOUSE ","click");
          Toast.makeText(getApplicationContext(),latLng.toString(),
                  Toast.LENGTH_LONG).show();
     }*/


}
