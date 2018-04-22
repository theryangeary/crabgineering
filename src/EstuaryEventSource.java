public interface EstuaryEventSource {

    void addListener(EstuaryEventListener listener);
    void removeListener(EstuaryEventListener listener);
    void notifyListeners(EstuaryEvent event);
}
