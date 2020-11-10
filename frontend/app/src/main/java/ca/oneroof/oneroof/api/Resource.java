package ca.oneroof.oneroof.api;

import retrofit2.Call;

public class Resource<T> {
    public final T data;
    public final String message;

    public Resource(T data) {
        this.data = data;
        this.message = null;
    }

    public Resource(String message) {
        this.data = null;
        this.message = message;
    }
}
