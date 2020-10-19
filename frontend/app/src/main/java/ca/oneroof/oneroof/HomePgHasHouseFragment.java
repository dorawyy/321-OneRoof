package ca.oneroof.oneroof;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePgHasHouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePgHasHouseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button profileBtn;
    private Button debtsBtn;
    private Button scanReceiptBtn;
    private Button enterPurchaseBtn;
    private Button viewPurchasesBtn;

    private boolean isHouseLeader;

    public HomePgHasHouseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePgHasHouseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePgHasHouseFragment newInstance(String param1, String param2) {
        HomePgHasHouseFragment fragment = new HomePgHasHouseFragment();
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

        isHouseLeader = isHouseLeader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_pg_has_house, container, false);

        profileBtn = view.findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // select profile based on account type: basic or house leader
                if(isHouseLeader) {
                    Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_houseLeaderProfileFragment);
                }
                else {
                    Navigation.findNavController(view).navigate(R.id.action_homePgHasHouseFragment_to_basicProfileFragment);
                }
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

    private boolean isHouseLeader() {
        return true;
    }
}