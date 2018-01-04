package events;

/**
 * Created by Armaan on 15-12-2017.
 */

public class TripCanceledEvent {
    public Integer position;
    public String id;

    public TripCanceledEvent(Integer position, String id) {
        this.id = id;
        this.position = position;
    }
}
