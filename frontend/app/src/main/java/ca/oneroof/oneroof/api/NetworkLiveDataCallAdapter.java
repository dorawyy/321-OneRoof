package ca.oneroof.oneroof.api;

import android.net.Network;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

// Similar to the one in the architecture component samples.
public class NetworkLiveDataCallAdapter<R> implements CallAdapter<R, NetworkLiveData<R>> {
    private Type responseType;

    public NetworkLiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public NetworkLiveData<R> adapt(Call<R> call) {
//        return new NetworkLiveData<R>(call);
        return null;
    }
}
