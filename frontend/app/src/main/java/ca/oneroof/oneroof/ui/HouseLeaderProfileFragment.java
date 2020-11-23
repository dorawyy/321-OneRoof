package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.databinding.FragmentHouseLeaderProfileBinding;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

public class HouseLeaderProfileFragment extends Fragment {

    private HouseViewModel viewmodel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentHouseLeaderProfileBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_house_leader_profile, container, false);
        binding.setViewmodel(viewmodel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();

        // TODO: make this update right away after adding new roommate
        // roommate list stuff
        RecyclerView roommate_list = view.findViewById(R.id.roommate_list);
        // Create adapter passing in the sample user data
        RoommateNameAdapter adapter = new RoommateNameAdapter(viewmodel.house.data.getValue().data.roommate_names, viewmodel.roommateName.getValue());
        // Attach the adapter to the recyclerview to populate items
        roommate_list.setAdapter(adapter);
        // Set layout manager to position the items
        roommate_list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // only allow house settings to be accessible if user is the house leader
        if (viewmodel.isHouseLeader) {
            Button houseSettingsBtn = view.findViewById(R.id.house_settings_btn);
            houseSettingsBtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void clickBudget(View v) {
        Navigation.findNavController(v)
                .navigate(HouseLeaderProfileFragmentDirections.actionHouseLeaderProfileFragmentToBudgetFragment());
    }

    public void clickSettings(View v) {
        Navigation.findNavController(v)
                .navigate(HouseLeaderProfileFragmentDirections.actionHouseLeaderProfileFragmentToHouseSettingsFragment());
    }
}
