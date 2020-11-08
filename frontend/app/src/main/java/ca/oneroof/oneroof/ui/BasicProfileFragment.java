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
 * Use the {@link BasicProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicProfileFragment extends Fragment {
    private TextView name;
    private TextView houseName;

    private HouseViewModel viewmodel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button budgetBtn;

    public BasicProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicProfileFragment newInstance(String param1, String param2) {
        BasicProfileFragment fragment = new BasicProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_profile, container, false);

        name = view.findViewById(R.id.user_name);
        //viewmodel.roommateId.data.observe(getViewLifecycleOwner(), new Observer<ApiResponse<House>>() {
        //    @SuppressLint("DefaultLocale")
        //    @Override
        //    public void onChanged(ApiResponse<House> houseApiResponse) {
        //        houseName.setText(houseApiResponse.data.name);
        //    }
        //});

        houseName = view.findViewById(R.id.house_name);
        viewmodel.house.data.observe(getViewLifecycleOwner(), new Observer<ApiResponse<House>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onChanged(ApiResponse<House> houseApiResponse) {
                houseName.setText(houseApiResponse.data.name);
            }
        });

        budgetBtn = view.findViewById(R.id.basic_budget_btn);
        budgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_houseLeaderProfileFragment_to_budgetFragment);
            }
        });

        return view;
    }
}