package requests;

/**
 * requests.RequestFactory is utility for producing different types of requests
 */

public class RequestFactory {
    /**
     * @param sprite A Sprite not currently in the View
     * @return A requests.Request for the given Sprite to be added to the View
     */
    public static Request<Sprite> createAddToViewRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.ADD_TO_VIEW
        );
    }

    /**
     * @param sprite A Sprite currently in the View
     * @return A requests.Request for the given Sprite to be removed from the View
     */
    public static Request<Sprite> createRemoveFromViewRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.REMOVE_FROM_VIEW
        );
    }

    /**
     * @param entity An Entity not currently in the Model
     * @return A requests.Request for the given Entity to be added to the Model
     */
    public static Request<Entity> createAddToModelRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.ADD_TO_MODEL
        );
    }

    /**
     * @param entity An Entity currently in the Model
     * @return A requests.Request for the given Entity to be removed from the Model
     */
    public static Request<Entity> createRemoveFromModelRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.REMOVE_FROM_MODEL
        );
    }

    /**
     * @param trash A piece of Trash not currently thrown
     * @return A requests.Request for the given Trash to be thrown
     */
    public static Request<Trash> createAddThrownTrashRequest(Trash trash){
        return new Request<>(
                trash,
                Request.RequestType.ADD_THROWN_TRASH
        );
    }

    /**
     * @param dPollution The change in pollution level
     * @return A requests.Request for the pollution level to be changed by
     * the specified amount
     */
    public static Request<Integer> createUpdatePollutionRequest(int dPollution){
        return new Request<>(
                dPollution,
                Request.RequestType.UPDATE_POLLUTION
        );
    }

    /**
     * @param dScore The change in score
     * @return A requests.Request for the score to be changed by the specified amount
     */
    public static Request<Integer> createUpdateScoreRequest(int dScore){
        return new Request<>(
                dScore,
                Request.RequestType.UPDATE_SCORE
        );
    }

    /**
     * @param sound The name of the SoundEffect to be played
     * @return A requests.Request for the SoundEffect represented by sound
     * to be played
     */
    public static Request<String> createPlaySoundRequest(String sound){
        return new Request<>(
          sound,
          Request.RequestType.PLAY_SOUND
        );
    }
}
