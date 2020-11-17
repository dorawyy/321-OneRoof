package ca.oneroof.oneroof.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ca.oneroof.oneroof.api.BudgetStats;
import ca.oneroof.oneroof.api.BudgetUpdate;
import ca.oneroof.oneroof.api.DebtSummary;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.api.IdResponse;
import ca.oneroof.oneroof.api.NetworkLiveData;
import ca.oneroof.oneroof.api.OneRoofAPI;
import ca.oneroof.oneroof.api.OneRoofAPIUtils;
import ca.oneroof.oneroof.api.Purchase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseViewModel extends ViewModel {
    public final OneRoofAPI api;

    public MutableLiveData<Integer> houseId = new MutableLiveData<>();
    public MutableLiveData<Integer> roommateId = new MutableLiveData<>();
    public NetworkLiveData<House> house;
    public NetworkLiveData<ArrayList<Purchase>> purchases;
    public String permissions; // TODO: change this and the hardcoding below
    public NetworkLiveData<BudgetStats> budgetStats;
    public NetworkLiveData<DebtSummary> debtStats;

    public HouseViewModel(OneRoofAPI api) {
        this.api = api;

        house = new NetworkLiveData<>(Transformations.map(houseId, api::getHouse));
        purchases = new NetworkLiveData<>(Transformations.map(houseId, api::getPurchases));
        debtStats = new NetworkLiveData<>(OneRoofAPIUtils.doubleTransform(houseId, roommateId, api::getDebtSummary));

// TODO: put this in its own viewmodel
//        budgetStats = new NetworkLiveData<>(Transformations.map(roommateId, api::getBudgetStats));
//        debtStats = new NetworkLiveData<>(Transformations.map(roommateId, id -> {
//            return api.getDebtSummary(houseId.getValue(), id);
//        }));

        permissions = "owner";
    }

    public void postPurchase(Purchase purchase) {
        api.postPurchase(houseId.getValue(), purchase).enqueue(new Callback<IdResponse>() {
            @Override
            public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                purchases.refresh();
            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {
                // Empty
            }
        });
    }

    public void postBudget(BudgetUpdate update) {
        api.postBudget(roommateId.getValue(), update).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                budgetStats.refresh();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // Empty
            }
        });
    }
}
