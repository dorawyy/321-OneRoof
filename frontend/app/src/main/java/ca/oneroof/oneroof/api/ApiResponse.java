package ca.oneroof.oneroof.api;

/**
 * A holder type for responses from the OneRoof API.  It can be in one of two states:
 * - a successful response from the API, holding a T (the type of the result of the particular API
 *   call
 * - an unsuccessful response from the API, holding a message that we can display for debugging.
 *
 * This could be implemented in a safer way by using a pair of subclasses and the visitor pattern,
 * but this is unsuitable in our case for a few reasons: We would have to add boilerplate visitors
 * for every use of an API call in the UI, since we can't do it inline using anonymous classes in
 * a databinding.
 *
 * To use this class, you must check for data == null.  If so, the loading was unsuccessful and you
 * may display the message, which is guaranteed to not be null.  If it is not null, then loading the
 * data went well and you can use it.
 *
 * @param <T> The type of a successful response from the API (parsed by GSON).
 */
public class ApiResponse<T> {
    public final T data;
    public final String message;

    public ApiResponse(T data) {
        this.data = data;
        this.message = null;
    }

    public ApiResponse(String message) {
        this.data = null;
        this.message = message;
    }
}
