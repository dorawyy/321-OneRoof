package ca.oneroof.oneroof;

import android.app.Application;

import ca.oneroof.oneroof.api.OneRoofAPI;
import retrofit2.Retrofit;

public class OneRoofApplication extends Application {
    private OneRoofAPI api;
    // TODO: Replace this basic user token with Google Signin
    private String user;


    public OneRoofApplication() {
        super();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .build();
        api = retrofit.create(OneRoofAPI.class);
    }

    public OneRoofAPI getApi() {
        return api;
    }
}
