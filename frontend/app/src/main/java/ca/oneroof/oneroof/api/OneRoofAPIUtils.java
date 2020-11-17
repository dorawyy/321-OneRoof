package ca.oneroof.oneroof.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OneRoofAPIUtils {
    public static <A, B, R> LiveData<R> doubleTransform(LiveData<A> l1, LiveData<B> l2, DoubleTransformer<A, B, R> f) {
        MutableLiveData<A> d1 = new MutableLiveData<>();
        MutableLiveData<B> d2 = new MutableLiveData<>();
        MediatorLiveData<R> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(l1, d -> {
            d1.setValue(d);
            if (d2.getValue() != null) {
                mediatorLiveData.setValue(f.transform(d1.getValue(), d2.getValue()));
            }
        });
        mediatorLiveData.addSource(l2, d -> {
            d2.setValue(d);
            if (d1.getValue() != null) {
                mediatorLiveData.setValue(f.transform(d1.getValue(), d2.getValue()));
            }
        });
        return mediatorLiveData;
    }

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

    public interface DoubleTransformer<A, B, R> {
        R transform(A d1, B d2);
    }
}
