package com.example.locationpointsapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView lisPointUbication;
    private EditText etLatitude;
    private EditText etLength;
    private Button btnAddPoints;
    private Button btnCrearFigura;
    private ArrayAdapter adapterListPointUbication;
    private ArrayList<String> listPointUbicationString;
    private PolylineOptions polygonOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);
        initViews();
        initOnClickListener();
        initMap();
    }

    private void initMap() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } else {

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }
    }

    private void initViews() {
        lisPointUbication = findViewById(R.id.lisPointUbication);
        etLatitude = findViewById(R.id.etLatitude);
        etLength = findViewById(R.id.etLength);
        btnAddPoints = findViewById(R.id.btnAddPoints);
        btnCrearFigura = findViewById(R.id.btnCrearFigura);
        listPointUbicationString = new ArrayList<>();
        polygonOptions = new PolylineOptions();
        adapterListPointUbication = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listPointUbicationString);
        lisPointUbication.setAdapter(adapterListPointUbication);
    }

    private void initOnClickListener() {
        btnAddPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPointToMap();
            }
        });
        btnCrearFigura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCameraToPointInitial();
            }
        });
    }

    private void addPointToMap() {
        String latitude = etLatitude.getText().toString();
        String length = etLength.getText().toString();
        if (!latitude.equals("") && !length.equals("")) {
            LatLng point = new LatLng(Float.parseFloat(latitude), Float.parseFloat(length));
            MarkerOptions markerUbication = new MarkerOptions();
            markerUbication.position(point).title("Esta ubicado en Bogota D.C")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            float zoomLevel = 5;
            mMap.addMarker(markerUbication);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, zoomLevel));
            polygonOptions.add(point);
            mMap.addPolyline(polygonOptions);
            listPointUbicationString.add("Latitud: " + latitude +
                    " Longitud: " + length);
            etLatitude.setText("");
            etLength.setText("");
            adapterListPointUbication.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No ha agregado ningun punto", Toast.LENGTH_LONG);
        }
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
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        moveCameraToPointInitial();
    }

    private void moveCameraToPointInitial() {
        mMap.clear();
        LatLng bogota = new LatLng(4.736, -74.023);
        MarkerOptions markerUbication = new MarkerOptions();
        markerUbication.position(bogota).title("Esta ubicado en Bogota D.C")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        float zoomLevel = 16;
        mMap.addMarker(markerUbication);
        polygonOptions.add(bogota);
        mMap.addPolyline(polygonOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, zoomLevel));
    }

}
