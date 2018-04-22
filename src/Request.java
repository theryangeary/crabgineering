public class Request<D> {
    protected D specifics;

    public Request(D specifics){
        this.specifics = specifics;
    }

    public D getSpecifics() {
        return specifics;
    }
}
