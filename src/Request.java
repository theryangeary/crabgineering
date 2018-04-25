public class Request<R> {

    public enum RequestType {
        ADD_ENTITY,
        REMOVE_ENTITY,
        ADD_THROWN_TRASH,
        ADD_SPRITE,
        REMOVE_SPRITE,
        UPDATE_POLLUTION,
        UPDATE_SCORE,
        PLAY_SOUND;
    }

    private R specifics;
    private RequestType requestedAction;

    public Request(R specifics, RequestType requestedAction){
        this.specifics = specifics;
        this.requestedAction = requestedAction;
    }

    public R getSpecifics() {
        return specifics;
    }

    public RequestType getRequestedAction() {
        return requestedAction;
    }
}
