package ca.oneroof.oneroof.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OneRoofAPI {
    @GET("houses/{house}")
    Call<House> getHouse(@Path("house") int houseId);
}
