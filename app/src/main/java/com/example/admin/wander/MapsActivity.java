package com.example.admin.wander;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    // Aster getting KEY run map activity and then Change extends MapActivity to AppCompatActivity, then it will shop activity name
    // otherwise whole screen will fill with map
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);// step 20  remove the above 2 lines
        // step 21 created a dynamic support map fragment
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    // step 2 create a new menu XML file and override to fill menu with options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        //  step 3 inflate with menu item
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    // step 4 select item from the menu and set the map accordingly
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the my type on the User's selection
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;

            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;

            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;

            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // step 5 remove the code for Sydney and replace it with
        // location of your home without marker Add a marker in Monmouth and move the camera
        LatLng home = new LatLng(40.914355, -90.638366);
        //mMap.addMarker(new MarkerOptions().position(home).title("Monmouth College campus"));
        // Create a camera update with appropriate zoom level ( 15 for street level
        // and 20 for building level)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 16));


        // step 8
        setMapLongClick(mMap);

        // step 13 call this method to show poi
        setPoiClick(mMap);
        // call street view
        setInfoWindowClickToPanorama(mMap);

        // step 16b check permission for location
        enableMyLocation();
        // mapStyle(mMap); // night style changed it in last step.

        // step 15 Add an Overlay [TileOverlay vs GroundOverlay] drawings on top of maps
        LatLng monmouth = new LatLng(40.912480, 90.60859); // app stopped crashing after using monmouth instead of home
        GroundOverlayOptions homeOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.imgandroid))
                .position(monmouth,100);

        mMap.addGroundOverlay(homeOverlay); // having trouble with this code of line app crashes

    }

    // step 14 Style your map we have created a map style online and copy the jason code
    // and saved it in a new folder raw with name map_style.jason , now we are importing it
    // into our map via onMapReady() in step last i made it a method
    private void mapStyle(GoogleMap map) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find styles. Error: ", e);
        }
    }
    // step 6 add a marker (tap at a location for long and marker will appear there) and
    // extend markers to show contextual information in info window

    private void setMapLongClick(final GoogleMap map) {
        // set google map on click listener
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // step 7 call addMarker() and call the method at the end of the onMapReady()
                // map.addMarker(new MarkerOptions().position(latLng));
                // with step 7 code Navigation buttons appear at the bottom-left side of the screen,
                // allowing the user to use the Google Maps app to navigate to the marked position.

                // step 9 to add info window for the marker (compare with step 7)
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                // step 15 above line change the color of the default google balloon from red
                // to HUE_BLUE.
            }
        });
    }

    // step 10 Add POI point of interest, we will use GoogleMap.OnPoiClickListener that places a marker
    // on the map immediately, instead for waiting for a touch and hold.
    private void setPoiClick(final GoogleMap map) {
        //  step 11 set google map on click listener
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                // step 12 place a marker at the Poi
                Marker poiMarker = mMap.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();
                poiMarker.setTag("poi"); // step 18 and 19 change xml file to FrameLayout

            }
        });
    }

    // step 16 ( 15 failed to put overlay)Tracking Location of the device create method enableMyLocation

    /**
     * Checks for location permissions, and requests them if they are missing.
     * Otherwise, enables the location layer.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    // step 17 ( I am not sure if we need this method below) every thing is working till step 17 (-15)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }
    // step 18 enabling street view

    /**
     * Starts a Street View panorama when an info window containing the poi tag
     * is clicked.
     *
     * @param map The GoogleMap to set the listener to.
     */
    private void setInfoWindowClickToPanorama(GoogleMap map) {
        map.setOnInfoWindowClickListener(
                new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                    // check the tag
                        if(marker.getTag() == "poi"){
                            //set the position to the position of the marker
                            StreetViewPanoramaOptions options =
                                    new StreetViewPanoramaOptions().position(
                                            marker.getPosition());
                            // create a new instance
                            SupportStreetViewPanoramaFragment streetViewFragment =
                                    SupportStreetViewPanoramaFragment.newInstance(options);

                            // replace the fragment and add it to the back stack
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, streetViewFragment)
                                    .addToBackStack(null).commit();
                        }

                    }
                });
    }


}
