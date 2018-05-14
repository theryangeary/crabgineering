package controller.requests;

import model.entities.Entity;
import model.entities.Player;
import model.entities.Trash;
import view.View;
import view.sprites.Sprite;

import javax.lang.model.type.NullType;

/**
 * RequestFactory is utility for producing different types of controller.requests
 */

public class RequestFactory {

    /**
     * @param playerType Indicates which type of Player the Model should use
     * @return A Request to start the game with the given type of Player
     */
    public static Request<Entity.EntityType> createStartGameRequest(Entity.EntityType playerType){
        return new Request<>(
                playerType,
                Request.RequestType.START_GAME
        );
    }

    /**
     * @param playerType Indicates which type of Player the Model should use
     * @return A Request to start the boss fight
     */
    public static Request<Entity.EntityType> createStartBossRequest(Entity.EntityType playerType){
        return new Request<>(
                playerType,
                Request.RequestType.START_BOSS
        );
    }

    /**
     * @param playerType Indicates which type of Player the Model should use
     * @return A Request to start the game with the given type of Player
     */
    public static Request<Entity.EntityType> createStartTutorialRequest(Entity.EntityType playerType){
        return new Request<>(
                playerType,
                Request.RequestType.START_TUTORIAL
        );
    }

    public static Request<View.PopupType> createShowPopupRequest(View.PopupType popupType){
        return new Request<>(
                popupType,
                Request.RequestType.SHOW_POPUP_REQUEST
        );
    }

    /**
     * @param shouldBePaused indicates whether or not the game should be paused
     * @return A Request for the game to be paused
     */
    public static Request<Boolean> createTogglePausedRequest(boolean shouldBePaused){
        return new Request<>(
                shouldBePaused,
                Request.RequestType.TOGGLE_PAUSED
        );
    }

    /**
     * @param sprite A Sprite not currently in the View
     * @return A Request for the given Sprite to be added to the View
     */
    public static Request<Sprite> createAddToViewRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.ADD_TO_VIEW
        );
    }

    /**
     * @param sprite A Sprite currently in the View
     * @return A Request for the given Sprite to be removed from the View
     */
    public static Request<Sprite> createRemoveFromViewRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.REMOVE_FROM_VIEW
        );
    }

    /**
     * @param entity An Entity not currently in the Model
     * @return A Request for the given Entity to be added to the Model
     */
    public static Request<Entity> createAddToModelRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.ADD_TO_MODEL
        );
    }

    /**
     * @param entity An Entity currently in the Model
     * @return A Request for the given Entity to be removed from the Model
     */
    public static Request<Entity> createRemoveFromModelRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.REMOVE_FROM_MODEL
        );
    }

    /**
     * @param trash A piece of Trash not currently thrown
     * @return A Request for the given Trash to be thrown
     */
    public static Request<Trash> createAddThrownTrashRequest(Trash trash){
        return new Request<>(
                trash,
                Request.RequestType.ADD_THROWN_TRASH
        );
    }

    /**
     * @param dPollution The change in pollution level
     * @return A Request for the pollution level to be changed by
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
     * @return A Request for the score to be changed by the specified amount
     */
    public static Request<Integer> createUpdateScoreRequest(int dScore){
        return new Request<>(
                dScore,
                Request.RequestType.UPDATE_SCORE
        );
    }

    /**
     * @param sound The name of the EstuarySound to be played
     * @return A Request for the EstuarySound represented by sound
     * to be played
     */
    public static Request<String> createPlaySoundRequest(String sound){
        return new Request<>(
          sound,
          Request.RequestType.PLAY_SOUND
        );
    }
}
