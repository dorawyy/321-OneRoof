package ca.oneroof.oneroof.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.House;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPurchaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPurchaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // get the purchase total input by the user
    TextInputLayout purchaseTotalInput;
    TextInputEditText purchaseEditText;

    // button instantiations
    private Button addDivisionBtn;
    private Button addPurchaseBtn;

    // purchase data fields
    int purchaseTotalCents = -1;
    PurchaseDivision[] divisions;
    LocalDate purchaseDate; // use localDate.now(), then
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //dateString = date.format(formatter);


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPurchaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPurchaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPurchaseFragment newInstance(String param1, String param2) {
        AddPurchaseFragment fragment = new AddPurchaseFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_purchase, container, false);

        purchaseEditText = view.findViewById(R.id.enter_purchase_total);
        purchaseTotalInput = view.findViewById(R.id.purchase_total);

        purchaseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable st) {
                double total;
                try {
                    total = Double.parseDouble(st.toString());
                } catch(Exception e) {
                    total = -1;
                }


                // TODO: error handlingggggg
                // can't have a negative purchase amount, also could be an error from above
                if(total < 0) {
                    // error handling
                    purchaseTotalCents = -1;
                    return;
                }

                // convert to cents
                purchaseTotalCents = (int) Math.round(total * 100);

                Log.d("purchase", Integer.toString(purchaseTotalCents));
            }
        });

        addDivisionBtn = view.findViewById(R.id.add_division_btn);
        addDivisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if we don't have a total, can't make a division of it
                //if(purchaseTotal)
            }
        });

        addPurchaseBtn = view.findViewById(R.id.save_purchase_btn);
        addPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check that a valid total has been entered
                if(purchaseTotalCents < 0) {
                    return;
                }

                // create JSON object for the purchase
                JSONObject jsonPurchase = new JSONObject();

                // TODO: get memo from user
                try {
                    jsonPurchase.put("memo", "tmp_memo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // TODO: get user's id
                try {
                    jsonPurchase.put("purchaser", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // TODO: change this once we implement divisions
                // automatically create a division for the total with all roommates

                // TODO: get list of roommates in house

                //division[0] = new PurchaseDivision(purchaseTotalCents, house.roommates, "");

                // add division
                //jsonPurchase.put()

            }
        });
        return view;
    }
}