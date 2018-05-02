package controller.requests;

/**
 * A controller.requests.Request is a semantic type used to represent an action
 * that SHOULD occur, but has not necessarily taken place yet
 *
 * @param <R> The type used for the specifics of the request
 */
public class Request<R> {

    /**
     * The possible types of actions that can be represented by a controller.requests.Request
     */
    public enum RequestType {
        ADD_TO_MODEL,
        REMOVE_FROM_MODEL,
        ADD_THROWN_TRASH,
        ADD_TO_VIEW,
        REMOVE_FROM_VIEW,
        UPDATE_POLLUTION,
        UPDATE_SCORE,
        UPDATE_THROW_ANGLE,
        TOGGLE_DISPLAY,
        PLAY_SOUND;
    }

    //any extra information that a controller.requests.RequestListener
    //might need to fulfill the controller.requests.Request
    private R specifics;

    //lets us know what should happen
    //when the controller.requests.Request is fulfilled
    private RequestType requestedAction;

    public Request(R specifics, RequestType requestedAction){
        this.specifics = specifics;
        this.requestedAction = requestedAction;
    }

    /**
     * @return Any specific information needed to actually fulfill the request
     * ex: the model.entities.Entity to be added to the model.Model for an ADD_TO_MODEL request
     */
    public R getSpecifics() {
        return specifics;
    }

    /**
     * @return The type of action requested
     */
    public RequestType getRequestedAction() {
        return requestedAction;
    }
}