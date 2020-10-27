package ca.oneroof.oneroof.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.Division;
import ca.oneroof.oneroof.api.Purchase;
import ca.oneroof.oneroof.databinding.ItemDivisionEditBinding;
import ca.oneroof.oneroof.databinding.ItemPurchaseBinding;

// danger: superfund cleanup site

public class DivisionEditAdapter extends ArrayAdapter<DivisionEdit> {
    int resource;
    List<DivisionEdit> list;
    private Context context;
    MutableLiveData<Integer> total;

    public DivisionEditAdapter(Context context, int resource, List<DivisionEdit> list, MutableLiveData<Integer> total) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.total = total;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, null, false);
        ItemDivisionEditBinding binding = DataBindingUtil.bind(convertView);

        DivisionEdit divisionEdit = list.get(position);


        EditText amount = binding.getRoot().findViewById(R.id.division_amount);
        amount.setText(AddPurchaseFragment.formatDollars(divisionEdit.amount));
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                CharSequence first = null;
                CharSequence second = null;
                int i;
                for (i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '.') {
                        first = s.subSequence(0, i);
                        break;
                    }
                }
                if (first == null) {
                    first = s;
                } else {
                    if (s.length() > i+1) {
                        second = s.subSequence(i+1, s.length());
                    }
                }


                try {
                    int dollars = Integer.parseInt(first.toString());
                    int cents = second != null ? Integer.parseInt(second.toString()) : 0;
                    cents += dollars * 100;

                    divisionEdit.amount = cents;
                    computeTotal();
                } catch (NumberFormatException ignored) {}
            }
        });

        LinearLayout roommateLayout = binding.getRoot().findViewById(R.id.division_roommates);

        for (int i = 0; i < divisionEdit.roommateNames.size(); i++) {
            String roommate = divisionEdit.roommateNames.get(i);
            View child = LayoutInflater.from(context).inflate(R.layout.item_roommate_toggle,
                    null, false);
            Switch toggle = child.findViewById(R.id.roommate_toggle);
            toggle.setText(roommate);
            toggle.setChecked(divisionEdit.roommateEnables.get(i));

            final int j = i; // lol i hate java so much

            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    divisionEdit.roommateEnables.set(j, isChecked);
                }
            });

            roommateLayout.addView(child);
        }

        convertView.setTag(binding);
        binding.setDivisionEdit(list.get(position));
        return binding.getRoot();
    }

    public void addEdit(DivisionEdit edit) {
        this.list.add(edit);
        notifyDataSetChanged();
    }

    private void computeTotal() {
        int t = 0;
        for (DivisionEdit edit : list) {
            t += edit.amount;
        }

        total.setValue(t);
    }

}
