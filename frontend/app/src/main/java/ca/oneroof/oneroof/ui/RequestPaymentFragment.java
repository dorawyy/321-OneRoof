package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ca.oneroof.oneroof.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestPaymentFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestPaymentFragment newInstance() {
        RequestPaymentFragment fragment = new RequestPaymentFragment();
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
        return inflater.inflate(R.layout.fragment_request_payment, container, false);
    }
}