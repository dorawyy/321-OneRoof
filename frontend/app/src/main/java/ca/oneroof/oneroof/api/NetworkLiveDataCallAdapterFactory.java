package ca.oneroof.oneroof.api;

import android.net.Network;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class NetworkLiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != NetworkLiveData.class) {
            return null;
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new NetworkLiveDataCallAdapter<>(bodyType);
    }
}
