package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.Debt;
import ca.oneroof.oneroof.databinding.FragmentDebtSummaryBinding;
import ca.oneroof.oneroof.ui.house.PurchaseListAdapter;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

public class DebtSummaryFragment extends Fragment {
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
        FragmentDebtSummaryBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_debt_summary, container, false);
        binding.setViewmodel(viewmodel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        DebtListAdapter debtListAdapter = new DebtListAdapter(p -> {

        });
        RecyclerView debtRecycler = (RecyclerView) binding.getRoot().findViewById(R.id.debt_list);
        debtRecycler.setAdapter(debtListAdapter);
        debtRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        debtListAdapter.observe(getViewLifecycleOwner(), viewmodel.detailDebts.data);

        View view = binding.getRoot();

        return view;
    }
}