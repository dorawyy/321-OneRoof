package ca.oneroof.oneroof.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import ca.oneroof.oneroof.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button updateBudgetBtn;

    private int monthlyBudgetCents = -1; // indicator val

    // get the purchase total input by the user
    TextInputLayout monthlyBudgetInput;
    TextInputEditText monthlyBudgetText;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        monthlyBudgetText = view.findViewById(R.id.monthly_budget_text_input);
        monthlyBudgetInput = view.findViewById(R.id.monthly_budget);

        monthlyBudgetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                double newBudget;
                try {
                    newBudget = Double.parseDouble(editable.toString());
                } catch(Exception e) {
                    newBudget = -1;
                }

                if(newBudget < 0) {
                    monthlyBudgetCents = -1;
                    return;
                }

                // convert to cents
                monthlyBudgetCents = (int) Math.round(newBudget * 100);
            }
        });

        updateBudgetBtn = view.findViewById(R.id.update_budget_btn);
        updateBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change the budget both locally and on the backend

                // if it is the same as before, don't update anything
                // if(monthlyBudgetCents == oldBudgetCents) {return;}

                // don't update if we have an invalid value
                if(monthlyBudgetCents < 0) {
                    return;
                }

                // create JSON object
                JSONObject jsonBudget = new JSONObject();
                try {
                    jsonBudget.put("limit", monthlyBudgetCents);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // TODO: update hint to reflect budget change

            }
        });

        return view;
    }
}