package requests;

/**
 * requests.RequestListener should be implemented by classes which wish to
 * be notified by a requests.RequestQueue when Requests are being fulfilled
 */
public interface RequestListener {
    /**
     * A handle for requests.RequestQueue to pass a requests.Request to the implementing
     * class. Should define how the requests.RequestListener reacts to the requests.Request.
     * @param request The requests.Request to be handled
     */
    void handleRequest(Request request);
}
