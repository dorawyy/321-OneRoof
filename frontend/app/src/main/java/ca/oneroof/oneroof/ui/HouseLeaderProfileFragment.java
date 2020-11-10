package ca.oneroof.oneroof.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.ApiResponse;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HouseLeaderProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HouseLeaderProfileFragment extends Fragment {
    private TextView name;
    private TextView houseName;

    private HouseViewModel viewmodel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HouseLeaderProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HouseLeaderProfileFragment newInstance() {
        HouseLeaderProfileFragment fragment = new HouseLeaderProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_leader_profile, container, false);

        name = view.findViewById(R.id.user_name);
        name.setText(String.valueOf(viewmodel.roommateId.getValue()));

        houseName = view.findViewById(R.id.house_name);
        viewmodel.house.data.observe(getViewLifecycleOwner(), new Observer<ApiResponse<House>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onChanged(ApiResponse<House> houseApiResponse) {
                houseName.setText(houseApiResponse.data.name);
            }
        });

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

        return view;
    }
}
