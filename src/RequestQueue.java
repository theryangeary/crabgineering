import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class RequestQueue extends ArrayDeque<Request> {

    //anything that might fulfill a request from this queue
    private List<RequestListener> listeners;

    public RequestQueue (){
        this.listeners = new ArrayList<>();
    }

    public void addListener(RequestListener listener){
        listeners.add(listener);
    }

    //adds a new request to the queue
    public void postRequest(Request request){
        add(request);
    }

    //hands the requests on the queue over to the listeners to fulfill them
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
