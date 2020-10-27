package ca.oneroof.oneroof.api;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OneRoofAPI {
    @POST("login")
    Call<LoginResponse> postLogin(@Body LoginRequest body);

    @GET("houses/{house}")
    Call<House> getHouse(@Path("house") int houseId);

    @GET("houses/{house}/purchases")
    Call<ArrayList<Purchase>> getPurchases(@Path("house") int houseId);

    @POST("roommates/{roommate}/budget")
    Call postBudget(@Path("roommate") int roommateId);

    @GET("roommates/{roommate}/budget")
    Call<BudgetStats> getBudgetStats(@Path("roommate") int roommateId);
}
