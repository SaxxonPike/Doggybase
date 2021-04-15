package com.saxxon;

import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DogCeoService {

    private final DogCeoApi _api;
    private final Gson _gson;

    public DogCeoService(URL url) throws MalformedURLException {
        _gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        _api = retrofit.create(DogCeoApi.class);
    }

    private <T> T Execute(Call<DogCeoResponse> c, Class<T> messageClass) throws DogCeoException, IOException {
        /* What a lovely bit of code we have here. Because the property type of "message" can be
           different depending on whether or not there was an error state, we have to check the
           error state and return that message if we didn't have a successful request. If instead of
           using "message" for both the API data *and* error messages, they had separate fields such
           as "error"/"message", we would be able to avoid this problem entirely. Alas..
         */
        var response = c.execute();
        var responseBody = response.body();
        if (responseBody == null)
            throw new DogCeoException("No response body.", null);
        if (!responseBody.status.equals("success"))
            throw new DogCeoException(_gson.fromJson(responseBody.message, String.class), responseBody);
        return _gson.fromJson(responseBody.message, messageClass);
    }

    public String getRandom() throws DogCeoException, IOException {
        return Execute(_api.getRandom(), String.class);
    }

    public List<String> getRandomForBreed(String breed, int count) throws DogCeoException, IOException {
        /* There is a code smell here, but I didn't find a better solution. */
        return Execute(_api.getRandomForBreed(breed, count), List.class);
    }
}
