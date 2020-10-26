package ca.oneroof.oneroof.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ca.oneroof.oneroof.api.OneRoofAPI;

public class HouseViewModelFactory implements ViewModelProvider.Factory {
    private final OneRoofAPI api;

    public HouseViewModelFactory(OneRoofAPI api) {
        this.api = api;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HouseViewModel(api);
    }
}
