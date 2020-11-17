package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.AddRoommate;
import ca.oneroof.oneroof.api.BudgetUpdate;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HouseSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HouseSettingsFragment extends Fragment {

    private int inviteCode = -1; // indicator val

    private HouseViewModel viewmodel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HouseSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HouseSettingsFragment newInstance(String param1, String param2) {
        HouseSettingsFragment fragment = new HouseSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_settings, container, false);

        // for invite code input
        TextInputEditText inviteCodeInput = view.findViewById(R.id.invite_code);

        inviteCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Empty
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int inviteCodeInput;
                try {
                    inviteCodeInput = Integer.parseInt(editable.toString());
                } catch(Exception e) {
                    inviteCodeInput = -1;
                }

                // shouldn't have -ive invite code
                if(inviteCodeInput < 0) {
                    inviteCode = -1;
                    return;
                }

            }
        });

        Button addRoommateBtn = view.findViewById(R.id.add_roommate_btn);
        addRoommateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add a new roommate to the house using the entered invite code

                // don't update if we have an invalid value
                if(inviteCode < 0) {
                    return;
                }

                AddRoommate addRoommate = new AddRoommate();
                addRoommate.invite_code = inviteCode;

                viewmodel.patchRoommates(addRoommate);
            }
        });

        return view;
    }
}