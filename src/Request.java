public class Request<R> {

    public enum RequestType {
        ADD_ENTITY,
        REMOVE_ENTITY,
        ADD_THROWN_TRASH,
        ADD_SPRITE,
        REMOVE_SPRITE,
        UPDATE_POLLUTION,
        UPDATE_SCORE,
        UPDATE_THROW_ANGLE,
        TOGGLE_DISPLAY,
        PLAY_SOUND;
    }

    //any extra information that a RequestListener
    //might need to fulfill the Request
    private R specifics;

    //lets us know what should happen
    //when the Request is fulfilled
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
