package ca.oneroof.oneroof.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OneRoofAPIUtils {
    public static OneRoofAPI buildAPI(String baseUrl, String idToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FirebaseAuthInterceptor(idToken))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(new NetworkLiveDataCallAdapterFactory())
                .build();
        return retrofit.create(OneRoofAPI.class);
    }
}
