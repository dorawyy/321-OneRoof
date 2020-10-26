package ca.oneroof.oneroof.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ca.oneroof.oneroof.api.ApiResponse;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.api.OneRoofAPI;

public class HouseViewModel extends ViewModel {
    private final OneRoofAPI api;

    public MutableLiveData<Integer> houseId;
    public LiveData<ApiResponse<House>> house;

    public HouseViewModel(OneRoofAPI api) {
        this.api = api;
        houseId = new MutableLiveData<>();
        house = api.getHouse(1);
    }
}
