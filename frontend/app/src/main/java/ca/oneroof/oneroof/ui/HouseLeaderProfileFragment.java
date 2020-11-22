package ca.oneroof.oneroof.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.Resource;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.databinding.FragmentHomePgHasHouseBinding;
import ca.oneroof.oneroof.databinding.FragmentHouseLeaderProfileBinding;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

public class HouseLeaderProfileFragment extends Fragment {
    private TextView name;
    private TextView houseName;

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

        Button houseSettingsBtn = view.findViewById(R.id.house_settings_btn);
        houseSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_houseLeaderProfileFragment_to_houseSettingsFragment);
            }
        });

        Button budgetBtn = view.findViewById(R.id.budget_btn);
        budgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_houseLeaderProfileFragment_to_budgetFragment);
            }
        });

        // roommate list stuff
        RecyclerView roommate_list = (RecyclerView) view.findViewById(R.id.roommate_list);
        // Create adapter passing in the sample user data
        RoommateNameAdapter adapter = new RoommateNameAdapter(viewmodel.house.data.getValue().data.roommate_names, viewmodel.roommateName.getValue());
        // Attach the adapter to the recyclerview to populate items
        roommate_list.setAdapter(adapter);
        // Set layout manager to position the items
        roommate_list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}
