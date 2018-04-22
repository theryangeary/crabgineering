public interface RequestListener {
    void handleRequest(Request request);
    //should return true iff the request was handled
}
