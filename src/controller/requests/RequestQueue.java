package controller.requests;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for collecting Requests and distributing
 * them to RequestListeners. Intended to be used to maintain
 * control flow in an organised fashion for
 *  (1) horizontal updates (eg model.Model to view.View)
 *  (2) vertical updates (eg model.entities.Entity to model.Model)
 *  (3) asynchronous events (eg removing model.entities.Trash outside of update loop)
 */
public class RequestQueue extends ArrayDeque<Request> {

    //anything that might fulfill a request from this queue
    private List<RequestListener> listeners;

    /**
     * Initialises an empty controller.requests.RequestQueue with no Requests or RequestListeners
     */
    public RequestQueue (){
        this.listeners = new ArrayList<>();
    }

    /**
     * Sets this queue to inform listener whenever it fulfills Requests
     * @param listener A controller.requests.RequestListener not already listening to this queue
     */
    public void addListener(RequestListener listener){
        listeners.add(listener);
    }

    /**
     * Adds the given request to the queue
     * @param request A controller.requests.Request to be added to the queue
     */
    public void postRequest(Request request){
        add(request);
    }

    /**
     * Hands the controller.requests on the queue over to the listeners to fulfill them,
     * removing each from the queue in the process
     */
    public void fulfillAllRequests(){
        while (peek() != null) { //while there's still Requests in the queue
            //get the next request
            Request request = poll();

            //inform all listeners about the request
            for (RequestListener listener : listeners) {
                listener.handleRequest(request);
            }
        }
    }
}
