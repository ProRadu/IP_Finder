package com.example.ipfinder;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPFinderService {
    @GET("api/json/{ip}")
    Call<IPLocation> getLocationData(@Path("ip") String ipAddress);

}



