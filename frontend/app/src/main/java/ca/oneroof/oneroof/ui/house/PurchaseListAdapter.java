package ca.oneroof.oneroof.ui.house;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.function.Consumer;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.Purchase;
import ca.oneroof.oneroof.databinding.ItemPurchaseBinding;
import ca.oneroof.oneroof.ui.common.ClickCallback;
import ca.oneroof.oneroof.ui.common.DataBoundListAdapter;

public class PurchaseListAdapter extends DataBoundListAdapter<Purchase, ItemPurchaseBinding> {
    private final Consumer<Purchase> callback;

    public PurchaseListAdapter(Consumer<Purchase> callback) {
        this.callback = callback;
    }

    @Override
    protected ItemPurchaseBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_purchase,
                        parent, false);
    }

    @Override
    protected void bind(ItemPurchaseBinding binding, Purchase item) {
        binding.setPurchase(item);
        binding.setCallback(new ClickCallback() {
            @Override
            public void click(View v) {
                callback.accept(item);
            }
        });
    }
}
