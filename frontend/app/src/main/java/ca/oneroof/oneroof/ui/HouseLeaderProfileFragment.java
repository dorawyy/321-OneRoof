package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        // display roommate list
        RecyclerView roommate_list = view.findViewById(R.id.roommate_list);
        RoommateNameAdapter adapter = new RoommateNameAdapter(viewmodel.house.data.getValue().data.roommate_names, viewmodel.roommateName.getValue());
        roommate_list.setAdapter(adapter);
        roommate_list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // only allow house settings to be accessible if user is the house leader
        if (viewmodel.isHouseLeader) {
            Button houseSettingsBtn = view.findViewById(R.id.house_settings_btn);
            houseSettingsBtn.setVisibility(View.VISIBLE);
        }

        // display type of roommate: either house leader or house member
        TextView type = view.findViewById(R.id.house_member_type);
        type.setText(viewmodel.isHouseLeader ? "House Leader" : "House Member");

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
