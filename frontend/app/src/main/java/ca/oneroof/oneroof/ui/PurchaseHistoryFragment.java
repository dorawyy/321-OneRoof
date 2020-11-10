package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.ApiResponse;
import ca.oneroof.oneroof.api.Purchase;
import ca.oneroof.oneroof.databinding.FragmentPurchaseHistoryBinding;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseHistoryFragment extends Fragment {
    private HouseViewModel viewmodel;
    public PurchaseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentPurchaseHistoryBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_purchase_history, container, false);
        binding.setViewmodel(viewmodel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        adapter = new PurchaseAdapter(getContext(), R.layout.item_purchase, new ArrayList<>());

        viewmodel.purchases.data.observe(getViewLifecycleOwner(), new Observer<ApiResponse<ArrayList<Purchase>>>() {
            @Override
            public void onChanged(ApiResponse<ArrayList<Purchase>> arrayListApiResponse) {
                adapter.setList(arrayListApiResponse.data);
            }
        });

        viewmodel.purchases.refresh();

        View view = binding.getRoot();
        return view;
    }
}