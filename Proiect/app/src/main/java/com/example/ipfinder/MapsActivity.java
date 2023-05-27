package com.example.ipfinder;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ipfinder.databinding.ActivityMapsBinding;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final String IP_FINDER_BASE_URL = "https://freeipapi.com/";

    private IPFinderService ipFinderService;

    private EditText ipAddressEditText;
    private Button searchButton;



    private void fetchIPLocation(String ipAddress) {
        ipFinderService.getLocationData(ipAddress).enqueue(new Callback<IPLocation>() {
            @Override
            public void onResponse(Call<IPLocation> call, Response<IPLocation> response) {
                if (response.isSuccessful()) {
                    IPLocation location = response.body();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location.getCityName() + ", " + location.getCountryName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(MapsActivity.this, "Error fetching IP location: " + response.message(), Toast.LENGTH_SHORT).show();
                        Log.e("MapsActivity", "Error response: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<IPLocation> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ipAddressEditText = findViewById(R.id.ip_address_edit_text);
        searchButton = findViewById(R.id.search_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP_FINDER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ipFinderService = retrofit.create(IPFinderService.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddress = ipAddressEditText.getText().toString().trim();
                if (!ipAddress.isEmpty()) {
                    fetchIPLocation(ipAddress);
                } else {
                    Toast.makeText(MapsActivity.this, "Please enter a valid IP address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
