
package controllers;

import entities.Player;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveAndLoad {
    
    public void saveGame(Player p) throws FileNotFoundException, IOException{
        ArrayList<String> gameinfo = new ArrayList<String>();
        String RoomString = "";
        String RoomItemString = "";
        String RoomItemStatString = "";
        String RoomMonsterString = "";
        String RoomMonsterStatString = "";
        String RoomMonsterItemString = "";
        
//        
//        PrintWriter out = new PrintWriter("./src/files/savegame01.txt");
//  
//        String temp;
//        for (String[] item: this.highscore){ 
//            temp = item[0] + ", " + item[1] + ", " + item[2];
//            out.println(temp);
//        
//        }
//        out.close();
//        highscore = new ArrayList<String[]>();
    }
    
}
