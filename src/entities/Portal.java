package entities;

public class Portal extends Item {
    
    private final Room roomToEnter;

    public Portal(String name, Room room, String description) {
        super(name, description);
        this.roomToEnter = room;
    }

    public Room getRoomToEnter() {
        return roomToEnter;
    }
      
    
}