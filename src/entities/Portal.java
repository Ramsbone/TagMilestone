package entities;

import java.util.ArrayList;

public class Portal extends Item {

    private final Room roomToEnter;
    private final String moveDirection;

    public Portal(String name, Room room, String description, String moveDirection) {
        super(name, description);
        this.roomToEnter = room;
        this.setTakeable(false);
        this.moveDirection = moveDirection;
    }

    public Room getRoomToEnter() {
        return roomToEnter;
    }

    public String getMoveDirection() {
        return moveDirection;
    }
    
    public ArrayList<Boolean> useItem(Character c, String moveDirection) {
        Room newRoom = (this).getRoomToEnter();
        ((Player) c).setCurrentRoom(newRoom);
        ((Player) c).setMoveDirection(moveDirection);
        ArrayList<Boolean> output = new ArrayList<Boolean>();
        output.add(true);//moveOn in commandcontrol is set to true
        return output;
    }
}
