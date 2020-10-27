package ca.oneroof.oneroof.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.api.ApiResponse;
import ca.oneroof.oneroof.api.House;
import ca.oneroof.oneroof.api.LoginRequest;
import ca.oneroof.oneroof.api.LoginResponse;
import ca.oneroof.oneroof.api.OneRoofAPI;
import ca.oneroof.oneroof.api.OneRoofAPIBuilder;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;
import ca.oneroof.oneroof.viewmodel.HouseViewModelFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    private Button loginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySignIn();
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();

//        if (auth.getCurrentUser() != null) {
//            onLogggedIn();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("OneRoof", "firebase auth: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("OneRoof", "Google sign-in failed.", e);
            }
        }
    }

    private void trySignIn() {
        if (auth.getCurrentUser() == null) {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            onLogggedIn();
        }
    }

    private void clickLogin(View view) {
        trySignIn();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLogggedIn();
                        } else {
                            Snackbar.make(getView(), "Login failed.", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void onLogggedIn() {
        auth.getCurrentUser().getIdToken(false)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            onIdToken(idToken);
                        } else {
                            Log.d("OneRoof", task.getException().getMessage());
                            loginFail();
                        }
                    }
                });
    }

    private void onIdToken(String idToken) {
        OneRoofAPI api = OneRoofAPIBuilder.buildAPI(getString(R.string.api_url), idToken);

        HouseViewModel houseViewModel =
                new ViewModelProvider(getActivity(), new HouseViewModelFactory(api))
                    .get(HouseViewModel.class);

        // TODO: Hardcoded house id for now.
        houseViewModel.houseId.setValue(1);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        api.postLogin(new LoginRequest(task.getResult().getToken()))
                                .enqueue(new Callback<LoginResponse>() {
                                    @Override
                                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                        if (response.isSuccessful()) {
                                            houseViewModel.roommateId.setValue(response.body().id);
                                        } else {
                                            Log.d("OneRoof",
                                                    "Failure to receive roommate id: " + response.message());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                                        Log.d("OneRoof", "Failure to post FCM token: "
                                                + t.getLocalizedMessage());
                                        loginFail();
                                    }
                                });
                    }
                });

        Navigation.findNavController(getView())
                .navigate(LoginFragmentDirections.actionLoginFragmentToHomePgHasHouseFragment());
    }

    private void loginFail() {
        Snackbar.make(getView(), "Login failed.", Snackbar.LENGTH_SHORT)
                .show();
    }
}