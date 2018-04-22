public class EstuaryEvent {

    private final EstuaryEventType type;
    private final Object source;

    public enum EstuaryEventType{
        REMOVE,
        ADD,
        POLLUTION_CHANGE,
        SCORE_CHANGE;
    }

    public EstuaryEvent(EstuaryEventType type, Object source){
        this.type = type;
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    public EstuaryEventType getType() {
        return type;
    }
}
