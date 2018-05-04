package controller.requests;

import model.entities.Entity;
import model.entities.Player;
import model.entities.Trash;
import view.sprites.Sprite;

import javax.lang.model.type.NullType;

/**
 * controller.requests.RequestFactory is utility for producing different types of controller.requests
 */

public class RequestFactory {
    public static Request<Player> createStartGameRequest(Player player){
        return new Request<>(
                player,
                Request.RequestType.START_GAME
        );
    }

    public static Request<NullType> createPauseGameRequest(){
        return new Request<>(
                null,
                Request.RequestType.PAUSE_GAME
        );
    }

    /**
     * @param sprite A Sprite not currently in the View
     * @return A controller.requests.Request for the given Sprite to be added to the View
     */
    public static Request<Sprite> createAddToViewRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.ADD_TO_VIEW
        );
    }

    /**
     * @param sprite A Sprite currently in the View
     * @return A controller.requests.Request for the given Sprite to be removed from the View
     */
    public static Request<Sprite> createRemoveFromViewRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.REMOVE_FROM_VIEW
        );
    }

    /**
     * @param entity An model.entities.Entity not currently in the model.Model
     * @return A controller.requests.Request for the given model.entities.Entity to be added to the model.Model
     */
    public static Request<Entity> createAddToModelRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.ADD_TO_MODEL
        );
    }

    /**
     * @param entity An model.entities.Entity currently in the model.Model
     * @return A controller.requests.Request for the given model.entities.Entity to be removed from the model.Model
     */
    public static Request<Entity> createRemoveFromModelRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.REMOVE_FROM_MODEL
        );
    }

    /**
     * @param trash A piece of model.entities.Trash not currently thrown
     * @return A controller.requests.Request for the given model.entities.Trash to be thrown
     */
    public static Request<Trash> createAddThrownTrashRequest(Trash trash){
        return new Request<>(
                trash,
                Request.RequestType.ADD_THROWN_TRASH
        );
    }

    /**
     * @param dPollution The change in pollution level
     * @return A controller.requests.Request for the pollution level to be changed by
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
     * @return A controller.requests.Request for the score to be changed by the specified amount
     */
    public static Request<Integer> createUpdateScoreRequest(int dScore){
        return new Request<>(
                dScore,
                Request.RequestType.UPDATE_SCORE
        );
    }

    /**
     * @param sound The name of the view.soundeffects.SoundEffect to be played
     * @return A controller.requests.Request for the view.soundeffects.SoundEffect represented by sound
     * to be played
     */
    public static Request<String> createPlaySoundRequest(String sound){
        return new Request<>(
          sound,
          Request.RequestType.PLAY_SOUND
        );
    }
}
