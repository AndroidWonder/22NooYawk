package com.course.example.nooyawk;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //next two variables are part of a test for longPress event
    private long lastTouchTimeDown = -1;
    private long lastTouchTimeUp = -1;
    private static final float zoom = 14.0f;
    private String TAG = "Map";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //center map and set zoom level
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.76793169992044, -73.98180484771729), zoom));

        //set Google Map styles
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_styles));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //set markers
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.748963847316034,
                        -73.96807193756104))
                .title("UN")
                .snippet("United Nations")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.76866299974387,
                        -73.98268461227417))
                .title("Lincoln Center")
                .snippet("Home of NY Jazz")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.765136435316755,
                        -73.97989511489868))
                .title("Carnegie Hall")
                .snippet("Where you go with practice, practice, practice")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.70686417491799,
                        -74.01572942733765))
                .title("The Downtown Club")
                .snippet("Original home of the Heisman Trophy")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        //set listeners
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    public boolean onMarkerClick(Marker m) {
                        String title = m.getTitle();
                        String snip = m.getSnippet();
                        Toast.makeText(getApplicationContext(), title + "\n" + snip, Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
        );

        mMap.setOnMapLongClickListener(
                new GoogleMap.OnMapLongClickListener() {
                    public void onMapLongClick(LatLng point) {
                        Toast.makeText(getApplicationContext(), "Long Tap",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        mMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    public void onMapClick(LatLng point) {
                        Toast.makeText(getApplicationContext(), "Short Tap", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_S) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return (true);
        }
        else if (keyCode == KeyEvent.KEYCODE_N) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return (true);
        }
        return (super.onKeyDown(keyCode, event));
    }
}
