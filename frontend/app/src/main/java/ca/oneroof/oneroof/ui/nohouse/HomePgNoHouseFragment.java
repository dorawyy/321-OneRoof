package ca.oneroof.oneroof.ui.nohouse;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.CreateHouseRequest;
import ca.oneroof.oneroof.api.IdResponse;
import ca.oneroof.oneroof.databinding.FragmentHomePgNoHouseBinding;
import ca.oneroof.oneroof.ui.LoginFragmentDirections;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePgNoHouseFragment extends Fragment {
    private HouseViewModel viewmodel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        viewmodel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                /* Do nothing: disable back button for this page.
                 * If a house leader deletes a house, they shouldn't be able to go back to
                 * the settings page of the now deleted house.
                 */
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomePgNoHouseBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home_pg_no_house, container, false);
        binding.setFragment(this);
        binding.setViewmodel(viewmodel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    public void clickCreateHouse(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Create house");

        final EditText editText = new EditText(getContext());
        editText.setId(R.id.house_name);
        editText.setHint("House name");
        editText.setSingleLine();
        builder.setView(editText);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                viewmodel.api.postCreateHouse(new CreateHouseRequest(name)).enqueue(new Callback<IdResponse>() {
                    @Override
                    public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                        if (response.isSuccessful()) {
                            viewmodel.houseId.setValue(response.body().id);
                            NavController nav = Navigation.findNavController(v);
                            nav.popBackStack();
                            nav.navigate(LoginFragmentDirections.actionLoginFragmentToHomePgHasHouseFragment());
                        }
                    }

                    @Override
                    public void onFailure(Call<IdResponse> call, Throwable t) {
                        // nothing
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}