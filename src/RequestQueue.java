import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class RequestQueue extends ArrayDeque<Request> {

    private List<RequestListener> listeners;

    public RequestQueue (){
        this.listeners = new ArrayList<>();
    }

    public void addListener(RequestListener listener){
        listeners.add(listener);
    }

    public void postRequest(Request request){
        add(request);
    }

    public void fulfillAllRequests(){
        while (peek() != null) { //while there's still Requests in the queue
            Request request = poll();
            //get the next request

            for (RequestListener listener : listeners) {
                listener.handleRequest(request);
            }
        }
    }
}
