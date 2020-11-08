package ca.oneroof.oneroof.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ca.oneroof.oneroof.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HouseSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HouseSettingsFragment extends Fragment {

    public HouseSettingsFragment() {
        // Required empty public constructor
    }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_house_settings, container, false);
    }
}