import java.util.Collection;

public class DefaultEstuaryEventSource implements EstuaryEventSource {

    private Collection<EstuaryEventListener> listeners;

    @Override
    public void addListener(EstuaryEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(EstuaryEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners(EstuaryEvent event){
        for (EstuaryEventListener listener: listeners) {
            listener.handleEstuaryEvent(event);
        }
    }
}
