package ca.oneroof.oneroof.api;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

// Similar to the one in the architecture component samples.
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            private AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();

                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            if (response.isSuccessful()) {
                                postValue(new ApiResponse<R>(response.body()));
                            } else {
                                postValue(new ApiResponse<>(response.message()));
                            }
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            postValue(new ApiResponse<>(t.getLocalizedMessage()));
                        }
                    });
                }
            }
        };
    }
}
