package ca.oneroof.oneroof.api;

import androidx.annotation.IdRes;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OneRoofAPI {
    @POST("login")
    Call<LoginResponse> postLogin(@Body LoginRequest body);

    @POST("houses")
    Call<IdResponse> postCreateHouse(@Body CreateHouseRequest request);

    @GET("houses/{house}")
    Call<House> getHouse(@Path("house") int houseId);

    @GET("houses/{house}/purchases")
    Call<ArrayList<Purchase>> getPurchases(@Path("house") int houseId);

    @POST("houses/{house}/purchases")
    Call<IdResponse> postPurchase(@Path("house") int houseId, @Body Purchase purchase);

    @GET("houses/{house}/statistics/{roommate}")
    Call<DebtSummary> getDebtSummary(@Path("house") int houseId, @Path("roommate") int roommateId);

    @GET("houses/{house}/debts_detailed/{roommate}")
    Call<ArrayList<Debt>> getDebtsDetailed(@Path("house") int houseId, @Path("roommate") int roommateId);

    @POST("roommates/{roommate}/budget")
    Call<Void> postBudget(@Path("roommate") int roommateId, @Body BudgetUpdate update);

    @GET("roommates/{roommate}/budget")
    Call<BudgetStats> getBudgetStats(@Path("roommate") int roommateId);
}
