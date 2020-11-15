package ca.oneroof.oneroof.ui.house;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.Purchase;
import ca.oneroof.oneroof.databinding.ItemPurchaseBinding;
import ca.oneroof.oneroof.ui.common.DataBoundListAdapter;

public class PurchaseListAdapter extends DataBoundListAdapter<Purchase, ItemPurchaseBinding> {
    @Override
    protected ItemPurchaseBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_purchase,
                        parent, false);
    }

    @Override
    protected void bind(ItemPurchaseBinding binding, Purchase item) {
        binding.setPurchase(item);
    }
}
