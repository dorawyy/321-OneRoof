package ca.oneroof.oneroof.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.databinding.FragmentHomePgHasHouseBinding;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePgHasHouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePgHasHouseFragment extends Fragment {
    HouseViewModel viewmodel;

    private Button profileBtn;
    private Button debtsBtn;
    private Button scanReceiptBtn;
    private Button enterPurchaseBtn;
    private Button viewPurchasesBtn;

    public HomePgHasHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomePgHasHouseBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home_pg_has_house, container, false);
        binding.setViewmodel(viewmodel);
        binding.setLifecycleOwner(getActivity());

        View view = binding.getRoot();
        profileBtn = view.findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // select profile based on account type: basic or house leader
//                if(isHouseLeader) {
//                    Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_houseLeaderProfileFragment);
//                }
//                else {
//                    Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_basicProfileFragment);
//                }
            }
        });

        debtsBtn = view.findViewById(R.id.debts_btn);//crashhhhhhhhhhhhhhhhhhhhh
        debtsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to debt summary fragment
                Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_debtSummaryFragment);
            }
        });

        scanReceiptBtn = view.findViewById(R.id.receipt_btn);
        scanReceiptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to receipt picture fragment
                Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_receiptFragment);
            }
        });

        enterPurchaseBtn = view.findViewById(R.id.enter_purchase_btn);
        enterPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to manual purchase entry fragment
                Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_addPurchaseFragment);
            }
        });

        viewPurchasesBtn = view.findViewById(R.id.view_purchases_btn);
        viewPurchasesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to past purchases fragment
                Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_purchaseHistoryFragment);
            }
        });




        return view;
    }
}