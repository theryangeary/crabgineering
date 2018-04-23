public class RequestFactory {
    public static Request<Sprite> createAddSpriteRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.ADD_SPRITE
        );
    }

    public static Request<Sprite> createRemoveSpriteRequest(Sprite sprite){
        return new Request<>(
                sprite,
                Request.RequestType.REMOVE_SPRITE
        );
    }

    public static Request<Entity> createAddEntityRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.ADD_ENTITY
        );
    }

    public static Request<Entity> createRemoveEntityRequest(Entity entity){
        return new Request<>(
                entity,
                Request.RequestType.REMOVE_ENTITY
        );
    }

    public static Request<Trash> createAddThrownTrashRequest(Trash trash){
        return new Request<>(
                trash,
                Request.RequestType.ADD_THROWN_TRASH
        );
    }

    public static Request<Integer> createUpdatePollutionRequest(int pollutionLevel){
        return new Request<>(
                pollutionLevel,
                Request.RequestType.UPDATE_POLLUTION
        );
    }

    public static Request<Integer> createUpdateScoreRequest(int score){
        return new Request<>(
                score,
                Request.RequestType.UPDATE_SCORE
        );
    }
}
