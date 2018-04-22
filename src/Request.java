public class Request<D> {

    public enum ActionType{
        ADD,
        REMOVE,
        UPDATE_POLLUTION,
        UPDATE_SCORE;
    }

    private D specifics;
    private ActionType requestedAction;

    public Request(D specifics, ActionType requestedAction){
        this.specifics = specifics;
        this.requestedAction = requestedAction;
    }

    public D getSpecifics() {
        return specifics;
    }

    public ActionType getRequestedAction() {
        return requestedAction;
    }
}
