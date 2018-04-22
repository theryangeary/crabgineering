import java.util.Queue;

public abstract class RequestFulfiller {
    protected Queue<Request> requestQueue;

    public void postRequest(Request request){
        requestQueue.add(request);
    }

    public void fulfillAllRequests(){
        for (Request request: requestQueue)
            fulfillRequest(request);
    }

    abstract void fulfillRequest(Request request);
}
