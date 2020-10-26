package ca.oneroof.oneroof.viewmodel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ca.oneroof.oneroof.api.ApiResponse;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.api.LoginRequest;
import ca.oneroof.oneroof.api.OneRoofAPI;

public class HouseViewModel extends ViewModel {
    private final OneRoofAPI api;

    public MutableLiveData<Integer> houseId = new MutableLiveData<>();
    public MutableLiveData<String> fcmToken = new MutableLiveData<>();
    public LiveData<Integer> roommateId;
    public LiveData<ApiResponse<House>> house;
    public LiveData<String> name;

    public HouseViewModel(OneRoofAPI api) {
        this.api = api;

        house = Transformations.switchMap(houseId, api::getHouse);

        roommateId = Transformations.switchMap(fcmToken, fcm ->
                Transformations.map(api.postLogin(new LoginRequest(fcm)), r -> r.data.id)
        );

        name = Transformations.map(house, h -> h.data == null ? "Loading" : h.data.name);
        roommateId.observeForever(x -> Log.d("OneRoof", String.valueOf(x)));
    }
}
