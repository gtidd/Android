package greg.phoenix.com.mapapplicationgregtidd;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private UiSettings mUiSettings;

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
     * <p/>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GoogleMapOptions options = new GoogleMapOptions();
        mUiSettings = mMap.getUiSettings();

        LatLng denverColorado = new LatLng(39.55, 255); // Google states lat long for Denver was 39 and 104. However
                                                     // I believe there is a bug that places the marker on the wrong spot
                                                     // in the emulator. Tried to compensate.
        mMap.addMarker(new MarkerOptions().position(denverColorado).title("Marker in Denver, Colorado"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(denverColorado, 8));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mUiSettings.setZoomControlsEnabled(true);
    }
}