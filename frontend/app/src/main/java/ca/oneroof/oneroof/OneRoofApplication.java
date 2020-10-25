package ca.oneroof.oneroof;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import ca.oneroof.oneroof.api.OneRoofAPI;
import retrofit2.Retrofit;

public class OneRoofApplication extends Application {
    private OneRoofAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getString(R.string.api_url))
//                .build();
//        api = retrofit.create(OneRoofAPI.class);
    }

    public OneRoofAPI getApi() {
        return api;
    }
}
