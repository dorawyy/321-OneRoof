package ca.oneroof.oneroof.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class FirebaseAuthInterceptor implements Interceptor {
    private String idToken;

    public FirebaseAuthInterceptor(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + idToken)
                .build();
        return chain.proceed(request);
    }
}
