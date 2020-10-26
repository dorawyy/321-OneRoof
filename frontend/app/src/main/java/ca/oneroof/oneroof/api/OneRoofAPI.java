package ca.oneroof.oneroof.api;

import androidx.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OneRoofAPI {
    @POST("login")
    LiveData<ApiResponse<LoginResponse>> postLogin(@Body LoginRequest body);

    @GET("houses/{house}")
    LiveData<ApiResponse<House>> getHouse(@Path("house") int houseId);
}
