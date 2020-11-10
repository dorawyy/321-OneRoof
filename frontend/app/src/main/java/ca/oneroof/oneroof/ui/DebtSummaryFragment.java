package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.databinding.FragmentDebtSummaryBinding;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebtSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebtSummaryFragment extends Fragment {
    private HouseViewModel viewmodel;

    public DebtSummaryFragment() {
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
        FragmentDebtSummaryBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_debt_summary, container, false);
        binding.setViewmodel(viewmodel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();

        return view;
    }
}