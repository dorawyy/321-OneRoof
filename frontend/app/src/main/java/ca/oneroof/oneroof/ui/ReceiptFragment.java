package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ca.oneroof.oneroof.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiptFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReceiptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiptFragment newInstance() {
        ReceiptFragment fragment = new ReceiptFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

        Button confirmTotalBtn = view.findViewById(R.id.confirm_total_btn);
        confirmTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make sure we pass in the total for this one, and fill in the total box on the purchase entry page
                Navigation.findNavController(view).navigate(R.id.action_receiptFragment_to_addPurchaseFragment);
            }
        });

        Button wrongTotalBtn = view.findViewById(R.id.wrong_total_btn);
        wrongTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_receiptFragment_to_addPurchaseFragment);
            }
        });

        return view;
    }
}