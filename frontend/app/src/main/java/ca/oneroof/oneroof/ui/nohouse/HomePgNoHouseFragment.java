package ca.oneroof.oneroof.ui.nohouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.databinding.FragmentHomePgHasHouseBinding;
import ca.oneroof.oneroof.databinding.FragmentHomePgNoHouseBinding;
import ca.oneroof.oneroof.ui.house.HomePgHasHouseFragmentDirections;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

public class HomePgNoHouseFragment extends Fragment {
    private HouseViewModel viewmodel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomePgNoHouseBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home_pg_no_house, container, false);
        binding.setFragment(this);
        binding.setViewmodel(viewmodel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    public void createHouse(View v) {
    }
}