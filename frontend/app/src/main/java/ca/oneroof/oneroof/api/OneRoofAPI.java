package ca.oneroof.oneroof.api;

import androidx.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OneRoofAPI {
    @GET("houses/{house}")
    LiveData<ApiResponse<House>> getHouse(@Path("house") int houseId);
}
