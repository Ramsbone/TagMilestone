package entities;

import java.util.ArrayList;

public class Portal extends Item {

    private final Room roomToEnter;

    public Portal(String name, Room room, String description) {
        super(name, description);
        this.roomToEnter = room;
        this.setTakeable(false);
    }

    public Room getRoomToEnter() {
        return roomToEnter;
    }

    public ArrayList<Boolean> useItem(Character c) {
        Room newRoom = (this).getRoomToEnter();
        ((Player) c).setCurrentRoom(newRoom);
        ArrayList<Boolean> output = new ArrayList<Boolean>();
        output.add(true);//moveOn in commandcontrol is set to true
        return output;
    }
}
