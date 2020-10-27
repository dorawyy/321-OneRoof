package ca.oneroof.oneroof.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import ca.oneroof.oneroof.api.Purchase;
import ca.oneroof.oneroof.databinding.ItemPurchaseBinding;

public class PurchaseAdapter extends ArrayAdapter<Purchase> {
    int resource;
    List<Purchase> list;
    private Context context;

    public PurchaseAdapter(Context context, int resource, List<Purchase> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, null, false);
        ItemPurchaseBinding binding = DataBindingUtil.bind(convertView);
        convertView.setTag(binding);
        binding.setPurchase(list.get(position));
        return binding.getRoot();
    }

    public void setList(ArrayList<Purchase> data) {
        this.list.clear();
        this.list.addAll(data);
        notifyDataSetChanged();
    }
}
