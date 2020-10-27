package ca.oneroof.oneroof.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.ApiResponse;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.databinding.FragmentHomePgHasHouseBinding;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePgHasHouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePgHasHouseFragment extends Fragment {
    HouseViewModel viewmodel;
    FragmentHomePgHasHouseBinding binding;
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
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home_pg_has_house, container, false);
        binding.setViewmodel(viewmodel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        return view;
    }

    public void clickViewPurchases(View v) {
        Navigation.findNavController(v)
                .navigate(HomePgHasHouseFragmentDirections.actionHomePgHasHouseFragmentToPurchaseHistoryFragment());
    }

    public void clickProfile(View v) {
        Navigation.findNavController(v)
                .navigate(HomePgHasHouseFragmentDirections.actionHomePgHasHouseFragmentToBasicProfileFragment());
    }

    public void clickAddPurchase(View v) {
        Navigation.findNavController(v)
                .navigate(HomePgHasHouseFragmentDirections.actionHomePgHasHouseFragmentToAddPurchaseFragment());
    }

    public void clickIOUS(View v) {
        Navigation.findNavController(v)
                .navigate(HomePgHasHouseFragmentDirections.actionHomePgHasHouseFragmentToDebtSummaryFragment());
    }
}