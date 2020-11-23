package ca.oneroof.oneroof.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.function.Consumer;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.Debt;
import ca.oneroof.oneroof.databinding.ItemDebtBinding;
import ca.oneroof.oneroof.ui.common.ClickCallback;
import ca.oneroof.oneroof.ui.common.DataBoundListAdapter;

public class DebtListAdapter extends DataBoundListAdapter<Debt, ItemDebtBinding> {
    private final Consumer<Debt> callback;

    public DebtListAdapter(Consumer<Debt> callback) {
        this.callback = callback;
    }

    @Override
    protected ItemDebtBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_debt,
                        parent, false);
    }

    @Override
    protected void bind(ItemDebtBinding binding, Debt item) {
        binding.setDebt(item);
        binding.setCallback(new ClickCallback() {
            @Override
            public void click(View v) {
                callback.accept(item);
            }
        });
    }
}
