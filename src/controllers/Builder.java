/*
* Controller class which builds the game (creates and places rooms and items)
*/

package controllers;


import entities.Armour;
import entities.Item;
import entities.Monster;
import entities.Player;
import entities.Potion;
import entities.Portal;
import entities.Room;
import entities.Spell;
import entities.Weapon;
import entities.furniture.TreasureChest;
import exceptions.InitiationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Builder {

    private ArrayList<Room> roomList = new ArrayList<Room>();
    private ArrayList<Item> itemList = new ArrayList<Item>();
    private ArrayList<Monster> monsterList = new ArrayList<Monster>();

    public ArrayList<Room> buildAdventure(Player player) {
        
        //Creates all the rooms and sets room-connections: 
        initRooms(player);
        
        //Creates a roomList, check for identical names and sort list by name.
        createRoomListFromPlayer(player, roomList);
        checkRoomNamesElseThrowExcept(roomList);
        sortRoomListByName(roomList);
        
        //creates a monsterList, check for identical names.
        initMonster();
        this.createMonsterListFromRooms(roomList);
        this.checkMonsterNamesElseThrowExcept(monsterList);
        
        //Creates and distribues Items, Traps and NonPlayableCaracters into rooms and monster:
        initItems();
        
        //Creates an itemList from the roomList 
        //Check itemList for identical names: (mostly for debugging purposes).
        createItemListFromRoomsAndMonster(roomList);
        checkItemNamesElseThrowExcept(this.itemList);  
        
        //Removes doors to secret rooms after roomList has been builded:
        findRoomInList("Bedroom").setDoors(findRoomInList("Hallway"), null, null, null);

        return roomList;
    }

    //Finds a new room for NonPlayableCharacter(NPC) when sneaking away.
    public Room newRandomRoomForNPC() {
        Room destinationRoom;
        ArrayList<Room> tempRoomList = getUniqueRandomRooms(1, roomList);
        while (!this.roomWithNoPeople(tempRoomList.get(0))) {
            tempRoomList = getUniqueRandomRooms(1, roomList);
        }
        destinationRoom = tempRoomList.get(0);
        return destinationRoom;
    }

    //Makes sure that NPC goes to a room with no other NPC.
    private boolean roomWithNoPeople(Room r) {
        boolean output = false;
        boolean hasThief = r.isThief();
        boolean hasTaxcollector = r.isTaxcollector();
        if (hasThief == false && hasTaxcollector == false) {
            output = true;
        }
        return output;
    }

    //Returns an ArrayList of random empty rooms. 
    private ArrayList<Room> getUniqueRandomRooms(int numOfRandomRooms, ArrayList<Room> rooms) {
        ArrayList<Room> output = new ArrayList<Room>();
        Random r = new Random();

        while (output.size() < numOfRandomRooms) {
            int ranInt = r.nextInt(rooms.size());
            if (!output.contains(rooms.get(ranInt))
                    && !rooms.get(ranInt).getName().equals("Blocked Tunnel")
                    && !rooms.get(ranInt).getName().equals("Exit Point")) {
                output.add(rooms.get(ranInt));
            }
        }

        return output;
    }

    private void sortRoomListByName(ArrayList<Room> rooms) {
        Collections.sort(rooms, (Room r1, Room r2)
                -> r1.getName().compareTo(r2.getName()));
    }

    //Uses the start room in player to make a list of all rooms to which the player has access.
    private void createRoomListFromPlayer(Player p, ArrayList<Room> rooms) {
        if (rooms.size() < 1) {
            Room startRoom = p.getCurrentRoom();
            int roomlistsize = rooms.size();
            rooms.add(startRoom);

            Room N = null;
            Room S = null;
            Room E = null;
            Room W = null;

            while (rooms.size() > roomlistsize) {
                roomlistsize = rooms.size();
                for (int i = 0; i < roomlistsize; i++) {
                    try {
                        N = rooms.get(i).getNorth();
                    } catch (Exception e) {
                    }
                    try {
                        S = rooms.get(i).getSouth();
                    } catch (Exception e) {
                    }
                    try {
                        E = rooms.get(i).getEast();
                    } catch (Exception e) {
                    }
                    try {
                        W = rooms.get(i).getWest();
                    } catch (Exception e) {
                    }

                    if (!rooms.contains(N) && N != null) {
                        rooms.add(N);
                    }
                    if (!rooms.contains(S) && S != null) {
                        rooms.add(S);
                    }
                    if (!rooms.contains(E) && E != null) {
                        rooms.add(E);
                    }
                    if (!rooms.contains(W) && W != null) {
                        rooms.add(W);
                    }
                }
            }
            
        }
    }
    
    private void checkRoomNamesElseThrowExcept(ArrayList<Room> rooms){
        ArrayList<String> tempArrayList = new ArrayList<String>();
        for (Room r: rooms) {
            tempArrayList.add(r.getName());
        }
        
        checkListOfStrings(tempArrayList, 
                "class: Builder: checkRoomNamesElseThrowExcept: "
                    + "some Rooms have the same name!");
    }
    
    
    private void checkItemNamesElseThrowExcept(ArrayList<Item> itemList){        
        ArrayList<String> tempArrayList = new ArrayList<String>();
        for (Item i: itemList) {
            tempArrayList.add(i.getName());
        }
        
        checkListOfStrings(tempArrayList, 
                "class: Builder: checkItemNamesElseThrowExcept: "
                    + "some Items have the same name!");
    }
    
    private void checkMonsterNamesElseThrowExcept(ArrayList<Monster> monsterList){        
        ArrayList<String> tempArrayList = new ArrayList<String>();
        for (Monster m: monsterList) {
            tempArrayList.add(m.getName());
        }
        
        checkListOfStrings(tempArrayList, 
                "class: Builder: checkMonsterNamesElseThrowExcept: "
                    + "some Monsters have the same name!");
    }
    
    private void checkListOfStrings(ArrayList<String> itemList, String errorString){
        boolean mistakesFound = false;
        for (String s: itemList) {
            int a = itemList.lastIndexOf(s);
            int b = itemList.indexOf(s);
            if (b!=a) {
                System.out.println(itemList.get(a));
                mistakesFound = true;
            }

        }
        if (mistakesFound == true){
            throw new InitiationException(errorString);
        }
    }
    
    private Monster findMonsterInList(String monsterName){
        
        Monster output;
        int findIndex = 0;
        Monster testMonster = monsterList.get(findIndex);
        while (!testMonster.getName().equals(monsterName)) {
            findIndex++;
            if (findIndex == monsterList.size()){
                throw new InitiationException("class: Builder: findMonsterInList: Room " 
                        + monsterName + " does not exist!");
            }
            testMonster = monsterList.get(findIndex);
        }
        
            output = monsterList.get(findIndex);
        
        return output;
    }
    
    //Finds a specific room in roomList
    private Room findRoomInList(String roomName) {

        Room output;
        int findIndex = 0;
        Room testRoom = roomList.get(findIndex);
        while (!testRoom.getName().equals(roomName)) {
            findIndex++;
            if (findIndex == roomList.size()){
                throw new InitiationException("class: Builder: findRoomInList: Room " 
                        + roomName + " does not exist!");
            }
            testRoom = roomList.get(findIndex);
        }
        
            output = roomList.get(findIndex);
        
        return output;
    }
    
    private void createMonsterListFromRooms(ArrayList<Room> roomList){
        for (Room r: roomList) {
            for (Monster m: r.getMonsterList()) {
                this.monsterList.add(m);
            }
        }
    }
    
    private void createItemListFromRoomsAndMonster(ArrayList<Room> roomList){
        //ArrayList<Item> tempItemList = new ArrayList<Item>();
        for (Room r: roomList) {
            for (Item i: r.getInventory()) {
                this.itemList.add(i);
            }
            for (Monster m: r.getMonsterList()) {
                for (Item it: m.getInventory()) {
                    this.itemList.add(it);
                }
            }
        }      
        
    }
    
    private void initMonster(){
                
        Monster rat = new Monster("Mr.Rat", "Squeeeeeq!!!", 20,4,4);
        rat.setHealth(10);
        Monster goblin = new Monster("Skarsnik", "An evil goblin",30,5,5);
        Monster scoundrel = new Monster("Blackbeard", "Arrgh!",50,7,7);
        Monster troll = new Monster("Troll", "Troll...",80,20,20);
        Monster halfgiant = new Monster("Gorgorbey", "Gorgorbey.....",100,3,3);
        Monster dragon = new Monster("Smaug", "Smaug....",120,10,30);
        
        findRoomInList("Mining Tunnel").addMonster(rat);
        findRoomInList("Guards Quarter").addMonster(goblin);
        findRoomInList("Dwarfs Quarter").addMonster(scoundrel);
        findRoomInList("Prison Dungeon").addMonster(troll);
        findRoomInList("Gallery").addMonster(halfgiant);
        findRoomInList("Kings Tomb").addMonster(dragon);
        
    }
    
    //Initialize and place items in rooms.
    private void initItems() {

        Weapon stick = new Weapon("Wooden Stick", 5,
                "A wooden stick, a bit heavy and have a good swing. "
                + "\nIt is a simple weapon, but can offer more than the bare hands.");
        Weapon greatsword = new Weapon("Great Sword", 20,
                "This is a great sword worthy of a king. The handle offers the hands "
                + "\ngreat protection, and the sword is very well balanced. "
                + "\nThe handle is jeweled with precious stones and is gold plated");
        Weapon pickaxe = new Weapon("Axe", 10,
                "A simple pick axe, more a tool than a weapon, but can easily "
                + "\nbe used as such.");
        Armour leather = new Armour("Leather Shirt", 5,
                "An old shirt of thick leather, offers protection from basic hits, "
                + "\nand is comfortable to wear");
        Armour chainmail = new Armour("Chainmail", 10,
                "A fine quality chainmail made in a silvercoloured metal. "
                + "\nLight and flexible.");
        Armour heavy = new Armour("Heavy Armour", 20,
                "A very heavy but effective armour, with great protection. "
                + "The metal is dark, almost black, and probably used to belong to "
                + "\na great warrior");
        Potion poison = new Potion("Pink bottle", -20, "Drinkable potion in a glassbottle");

        Portal portal1 = new Portal("Pirate Portal", findRoomInList("Lovers Den"), 
                                    "The pirate flag on the wall seems to function as a magical portal to another place");
        Portal portal2 = new Portal("Mirror Portal", findRoomInList("Narrow Path"), 
                                    "It seems that the mirror on the wall is a magical portal to another place");
        
        Potion potion01 = new Potion("Green Bottle", 10, "Drinkable potion in a glassbottle");
        Potion potion02 = new Potion("Yellow Bottle", 20, "Drinkable potion in a glassbottle");
        Potion potion03 = new Potion("Red Bottle", -30, "Drinkable potion in a glassbottle");
        Potion mpotion1 = new Potion("Black bottle",20,"Drinkable potion in a glassbottle");
        Potion mpotion2 = new Potion("Small bottle",25,"Drinkable potion in a glassbottle");
        Potion mpotion3 = new Potion("Golden bottle",50,"Drinkable potion in a glassbottle");
        Potion mpotion4 = new Potion("White bottle",100,"Drinkable potion in a glassbottle");
        
        Spell fightSpell = new Spell("Fighting Spell","Improves your meelee skills and makes you stronger",5,10,0);
        Spell protection = new Spell("Protection Spell","Increases your protection against attacks",0,0,10);
        Spell badspell = new Spell("Captains Spell","Scroll with scriplings in an ancient language",-5,-15,0);
        
        TreasureChest chest = new TreasureChest("Luggage", "A large chest made of Sapient Pearwood. "
                + "\nThere seems to be hundreds of little legs protruding from its underside.", 200);
        
        findRoomInList("Mining Tunnel").addToInventory(stick);
        findRoomInList("Mining Tunnel").addToInventory(chest);
        findRoomInList("Guards Quarter").addToInventory(fightSpell);
        //findRoomInList("Dwarfs Quarter").addToInventory(pickaxe);
        //findRoomInList("Prison Dungeon").addToInventory(leather);
        //findRoomInList("Gallery").addToInventory(chainmail);
        //findRoomInList("Kings Tomb").addToInventory(greatsword);
        findRoomInList("Lovers Den").addToInventory(poison);
        findRoomInList("Cathedral South").addToInventory(heavy);
        findRoomInList("Bedroom").addToInventory(portal2);
        findRoomInList("Pirates Hideout").addToInventory(portal1);
        findRoomInList("Priests Room").addToInventory(protection);
        findRoomInList("Pirates Hideout").addToInventory(badspell);
        
        /*
        Monster rat = new Monster("Mr.Rat", "Squeeeeeq!!!");
        Monster goblin = new Monster("Skarsnik", "An evil goblin");
        Monster scoundrel = new Monster("Blackbeard", "Arrgh!");
        Monster troll = new Monster("Troll", "Troll...");
        Monster halfgiant = new Monster("Gorgorbey", "Gorgorbey.....");
        Monster dragon = new Monster("Smaug", "Smaug....");
        */
        
        this.findMonsterInList("Skarsnik").setWeapon(pickaxe);
        this.findMonsterInList("Skarsnik").setArmour(leather);
        this.findMonsterInList("Blackbeard").setWeapon(greatsword);
        this.findMonsterInList("Blackbeard").setArmour(chainmail);
        this.findMonsterInList("Blackbeard").addToInventory(mpotion1);
        this.findMonsterInList("Troll").addToInventory(mpotion2);
        this.findMonsterInList("Gorgorbey").addToInventory(mpotion3);
        this.findMonsterInList("Smaug").addToInventory(mpotion4);
        
        
        ArrayList<Room> tempRoomList = getUniqueRandomRooms(5, roomList);

        tempRoomList.get(0).setTaxcollector(true);
        tempRoomList.get(0).setGold(0);
        tempRoomList.get(1).setThief(true);
        tempRoomList.get(1).setGold(0);
        tempRoomList.get(2).setBoobytrap(true);
        tempRoomList.get(3).setBoobytrap(true);
        tempRoomList.get(4).setBoobytrap(true);
        
        tempRoomList = getUniqueRandomRooms(3, roomList);

        tempRoomList.get(0).addToInventory(potion01);
        tempRoomList.get(1).addToInventory(potion02);
        tempRoomList.get(2).addToInventory(potion03);
    }   
    
    
    //Initialize rooms and set room-connections.
    private void initRooms(Player p) {

        Room roomStart = new Room("Blocked Tunnel",
                "\nYou are in a tunnel with stone walls. It is dark, cold and moist."
                + "\nBehind you to the north the tunnel is collapsed and blocked by pieces of rocks."
                + "\nAhead towards south, you see a weak light coming from further down the tunnel.",
                1
        );

        Room room1 = new Room("Mining Tunnel",
                "\nYou are in a mining tunnel with stone walls. The air is cold and moist."
                + "\nTorches are located along the tunnel lighting up the surroundings "
                + "\nand creating dancing shadows on the walls."
                + "\nThe tunnel splits up going east and west and to the north the blocked tunnel.",
                3
        );

        Room room2 = new Room("Tool Storage",
                "\nYou have entered a storage room with lots of shelves and lockers."
                + "\nAlong the walls you see a lot of worktables and on the walls "
                + "\nyou see a lot of tools hanging. There is a distinct smell of oil and wood."
                + "\nTowards west is the tunnel and a door leads towards south.",
                0);

        Room room3 = new Room("Dwarfs Quarter",
                "\nThis is the living space for the dwarfs working in the mine."
                + "\nIn the room you see four short beds and in the middle of the room is a "
                + "\nlow table with four small chairs around. The room smells of beer and sweat."
                + "\nA door leads north back to the storage and to the east is another door.",
                4);

        Room room4 = new Room("Weapons Room",
                "\nYou find yourself inside a small weapons depot with a lot of lockers."
                + "\nThere are locks on all the lockers so you cannot open them and look inside. "
                + "\nBut through a small glass window in the lockers you see a lot of weapons."
                + "\nThe room is cold and dark and smells a bit metallic."
                + "\nA door leads west back to the Dwarf Quater.",
                4);

        Room room5 = new Room("Mining Area",
                "\nYou have entered a gold mine with rough rock walls."
                + "\nAround the area lies picks for mining and small helmets for protection. "
                + "\nIn the rocky walls you see small pieces of gold glittering in the light."
                + "\nThe air is filled with dust and you can taste the dust in your mouth."
                + "\nThere is a exit to the south and east is leading back to the tunnel.",
                10);

        Room room6 = new Room("Staircase",
                "\nYou are standing on a long and twisted staircase going up."
                + "\nThe steps have been carved out of the rock. a heavy rope is running "
                + "\nalong the wall as a railing. Your steps echoes up the staircase."
                + "\nYou can move south up the stairs or north down the stairs.",
                0);

        Room room7 = new Room("Guards Quarter",
                "\nYou are now in a room that seems to belong to a prison guard."
                + "\nIn the room you find a small table and a chair and on the table lies"
                + "\na key ring with several big keys. There is a smell of leather and mold."
                + "\nThere is a heavy wooden door to the west and another door to the east."
                + "\nTo the north is a staircase going downwards.",
                1);

        Room room8 = new Room("Prison Dungeon",
                "\nYou have entered a prison cell carved out of the rocks."
                + "\nIt is very dark but you can vaguely see a wooden bunker bed."
                + "\nOn the floor lies a shabby blanket and a tin plate."
                + "\nYou can nearly smell the fear from the prisoners staying here."
                + "\nThere is only one way out through the wooden door to the east.",
                0);

        Room room9 = new Room("Gallery",
                "\nYou find yourelf in a room filled with pictures."
                + "\nPortraits of what seems like important people are hanging"
                + "\non all four walls and fitted into gilted wooden frames."
                + "\nFrom the ceiling hangs a chandelier lighting up the room."
                + "\nThere is a door to the west and a double-door to the south.",
                0);

        Room room10 = new Room("Cathedral North",
                "\nThis is the north end of a large cathedral."
                + "\nIn this end is a large statue of Virgin Mary for worship"
                + "\nand next to it is a small table full of small candles burning."
                + "\nAlso you can see a confessional made of wood but noone is inside."
                + "\nThere is a double-door to the north and the cathedral continues south."
                + "\nNext to the confissional to the east is a small wooden door.",
                10);

        Room room11 = new Room("Kings Tomb",
                "\nYou are inside a small grave chamber."
                + "\nIn the middle of the room is a big sarcophagus with a kings"
                + "\ncrown on top of it. Also there is a large picture on the"
                + "\nwall showing an old King on his horse during a hunting session."
                + "\nThe room smell like nobody been here for quite a while."
                + "\nThere is a door to the west back into the Cathedral."
                + "\nAlso you see a small secret entrance in the wall to the east.",
                20);

        Room room12 = new Room("Secret Passage",
                "\nYou are inside a very narrow secret passage with low ceiling height."
                + "\nIt is filled with spider web and surely noone passed here in ages"
                + "\nThere is nearly no light and it smells of dust and mold."
                + "\nAt the end of the passage to the south you vagely see a narrow door."
                + "\nYou can go west in order to go back to the kings tomb."
                + "\nBut you can also enter down a small hole to the north.",
                2);

        Room room13 = new Room("Lovers Den",
                "\nYou have entered what seems to be the Kings love nest."
                + "\nIn the middle of the room is a big canopy bed."
                + "\nNext to the bed you see a large dressing table."
                + "\nThe walls are decorated with red carpets and the walls"
                + "\nare full of pictures of people kissing and making love."
                + "\nThere is only one door going back to the north.",
                15);

        Room room14 = new Room("Cathedral Middle",
                "\nYou are now in the middle of the large cathedral."
                + "\nAround you are benches for the people attending worship"
                + "\nNext to the benches are some shelves with hymnals."
                + "\nTowards south the cathedral continues further on."
                + "\nbesides the hymnals are a door leading west.",
                10);

        Room room15 = new Room("Cathedral South",
                "\nYou are in the south end of the large cathedral."
                + "\nThis is where the priests are conducting the ceremony."
                + "\nIn the middle is a big altar with two big candles."
                + "\nBehind the altar is a huge crucifix with Jesus on it."
                + "\nYou can only go back north to the middle of the cathedral.",
                0);

        Room room16 = new Room("Priests Room",
                "\nYou are now in the priests private chamber."
                + "\nThis is where the priest relaxes before worship."
                + "\nBesides from a small writing desk and a chair there is"
                + "\na large and comfortable divan for priest to rest on."
                + "\nOn the other side a door leads east back to the cathedral."
                + "\nAlso another door leads west and finally a door leads south.",
                15);

        Room room17 = new Room("Queens Tomb",
                "\nYou have now entered the queens grave chamber."
                + "\nAlong one of the sides a sarcophagus is embedded in the wall."
                + "\nIt is beautifully decorated with roses carved into the lid."
                + "\nA portrait hanging on the other wall shows a beautiful woman."
                + "\nShe exudes dignity and grace and surely is of royal blood."
                + "\nThere is a door to the east leading back to the priest room."
                + "\nFurthermore a secret door behind the sarcophagus leads north.",
                20);

        Room room18 = new Room("Library",
                "\nYou find yourself located in a large room full of books."
                + "\nAll the walls are covered with bookcases filled with all"
                + "\nkind of different books written by all kind of authors."
                + "\nYou can even smell the scent of old paper and ink."
                + "\nTo the north the door leads back to the priests room."
                + "\nBut additionally another door leads south.",
                0);

        Room room19 = new Room("Hallway",
                "\nYou have moved into a hallway with wooden panels on the walls."
                + "\nThe floor is decorated with tiles that forms a compas shape."
                + "\nSince the hallway is pretty much empty, you can hear your steps"
                + "\necho around in between the walls as you passes down the hallway."
                + "\nAt the end of the hallway a fine decorated door leads south."
                + "\nAlso you can enter west through another more rough wooden door.",
                2);

        Room room20 = new Room("Bedroom",
                "\nYou are now in a small bedroom with a small wooden bed inside."
                + "\nNext to the bed is a small nightstand with a bible on it."
                + "\nOn the other side of the bed is a window with a small curtain."
                + "\nThere is a big mirror on the wall in front of the bed."
                + "\nLooking through the window you can see the freedom outside."
                + "\nBut the windows is too narrow for you to escape through."
                + "\nYou can go back north through the decorated wooden door.",
                5);

        Room room21 = new Room("Food Storage",
                "\nThis is the pantry full of different kind of food."
                + "\nYou have lots of sausages hanging from the ceiling and on the."
                + "\nwalls you see shelves full of jars with pickled vegetables."
                + "\nUnderneath the shelves you see a couple of barrels full of whine."
                + "\nEastwards is the door leading back to the hallway."
                + "\nAnother door next to the barrels leads to the south.",
                0);

        Room room22 = new Room("Narrow Path",
                "\nYou have entered a very narrow and dark rock passage."
                + "\nThere is only very little space around and above you."
                + "\nIt is so dark you nearly have to feel your way around."
                + "\nAhead of you to the east you see a vage light.",
                0);

        Room room23 = new Room("Cave Lake",
                "\nYou stand in front of a large underground lake."
                + "\nOn the lake shore skeleton bones are scattered all around."
                + "\nThere is nothing to be seen in the water, and yet"
                + "\nyou sense that there is a creature somewhere down there."
                + "\nEastwards is a suspension bridge across the lake."
                + "\nThrough a crack in the rock you can go west back into the passage.",
                5);

        Room room24 = new Room("Treasure Room",
                "\nLeaving the bridge, you are now inside a small treasure room."
                + "\nInside the room you see chests full of gold."
                + "\nOn one of the walls you see a pirate flag hanging."
                + "\nUnderneath it lies a couple of oars."
                + "\nTo the north is an archway leading to another room."
                + "\nIf you go west you will enter the bridge again.",
                40);

        Room room25 = new Room("Pirates Hideout",
                "\nArrgh. You are now in a hideout for pirates."
                + "\nThere is a couple of tables and some chairs."
                + "\nThe room is characterized by a lot of fights."
                + "\nThe tables are full of marks from daggers, and a "
                + "\ncouple of female names is scratched into one of them."
                + "\nSouth is back into the Treasure Room.",
                10);
        
        Room room26 = new Room("Wizards Vault",
                "You have entered the Wizards Vault."
                + "\nYou entering the room makes the ceiling collapse."
                + "\nRubble from the ceiling blocks your way back."
                + "\nThis is where the wizard designs and codes his spells."
                + "\nAll over the wall different UML scrolls are attached."
                + "\nThe room is cold and gloomy. And you shiver a little. "
                + "\nOnly way out of the vault is south.",
                10);
        
        Room roomEnd = new Room("Exit Point", "", 0);

        roomStart.setDoors(null, room1, null, null);

        room1.setDoors(roomStart, null, room2, room5);

        room2.setDoors(null, room3, null, room1);

        room3.setDoors(room2, null, room4, null);

        room4.setDoors(null, null, null, room3);

        room5.setDoors(null, room6, room1, null);

        room6.setDoors(room5, room7, null, null);

        room7.setDoors(room6, null, room9, room8);

        room8.setDoors(null, null, room7, null);

        room9.setDoors(null, room10, null, room7);

        room10.setDoors(room9, room14, room11, null);

        room11.setDoors(null, null, room12, room10);

        room12.setDoors(room4, room13, null, room11);

        room13.setDoors(room12, null, null, null);

        room14.setDoors(room10, room15, null, room16);

        room15.setDoors(room14, null, null, null);

        room16.setDoors(null, room18, room14, room17);

        room17.setDoors(room8, null, room16, null);

        room18.setDoors(room16, room19, null, null);

        room19.setDoors(room18, room20, null, room21);

        room20.setDoors(room19, null, null, room22);

        room21.setDoors(null, room26, room19, null);

        room22.setDoors(null, null, room23, null);

        room23.setDoors(null, null, room24, room22);

        room24.setDoors(room25, null, null, room23);

        room25.setDoors(null, room24, null, null);

        room26.setDoors(null, roomEnd, null, null);

        roomEnd.setDoors(room21, null, null, null);

        p.setCurrentRoom(roomStart);
        
        
    }

}
