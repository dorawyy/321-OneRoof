package ca.oneroof.oneroof.api;

import android.net.Network;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkLiveData<R> {
    private MutableLiveData<ApiResponse<R>> data_ = new MutableLiveData<>();
    public LiveData<ApiResponse<R>> data = data_;

    protected LiveData<Call<R>> calls;

    public NetworkLiveData(LiveData<Call<R>> calls) {
        this.calls = calls;

        calls.observeForever(call -> refresh());

        refresh();
    }

    public void refresh() {
        Call<R> call = calls.getValue();
        if (call != null) {
            call = call.clone();
            call.enqueue(new Callback<R>() {
                @Override
                public void onResponse(Call<R> call, Response<R> response) {
                    if (response.isSuccessful()) {
                        data_.setValue(new ApiResponse<>(response.body()));
                    } else {
                        Log.d("OneRoof", "Unsuccessful request: " + response.message());
                        data_.setValue(new ApiResponse<>(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<R> call, Throwable t) {
                    Log.d("OneRoof", "Failed request: " + t.getMessage());
                    data_.setValue(new ApiResponse<>(t.getMessage()));
                }
            });
        }
    }
}
