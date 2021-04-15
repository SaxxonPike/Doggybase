package com.saxxon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogCeoApi {
    @GET("api/breeds/image/random")
    Call<DogCeoResponse> getRandom();

    @GET("api/breed/{breed}/images/random/{count}")
    Call<DogCeoResponse> getRandomForBreed(@Path("breed") String breed, @Path("count") int count);
}

