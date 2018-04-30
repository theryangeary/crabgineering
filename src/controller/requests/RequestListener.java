package controller.requests;

/**
 * controller.requests.RequestListener should be implemented by classes which wish to
 * be notified by a controller.requests.RequestQueue when Requests are being fulfilled
 */
public interface RequestListener {
    /**
     * A handle for controller.requests.RequestQueue to pass a controller.requests.Request to the implementing
     * class. Should define how the controller.requests.RequestListener reacts to the controller.requests.Request.
     * @param request The controller.requests.Request to be handled
     */
    void handleRequest(Request request);
}
