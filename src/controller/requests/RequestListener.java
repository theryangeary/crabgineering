package controller.requests;

/**
 * RequestListener should be implemented by classes which wish to
 * be notified by a RequestQueue when Requests are being fulfilled
 */
public interface RequestListener {
    /**
     * A handle for RequestQueue to pass a Request to the implementing
     * class. Should define how the RequestListener reacts to the Request.
     * @param request The Request to be handled
     */
    void handleRequest(Request request);
}
