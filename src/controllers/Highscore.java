/*
* Highscore Controller class of the program. 
*/
package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Highscore {
    
    private ArrayList<String[]> highscore = new ArrayList<String[]>();
    
    
            // read info from highscore document, add player info, return ArrayList of Strings
    public ArrayList<String> endGameHighscore(String name, int gold) throws IOException{
        ArrayList<String> output = new ArrayList<String>();        
        ArrayList<String> completeHighscoreList = new ArrayList<String>();
        boolean playerIsInTop10;
        int playerIndex;
        String playerLine;
        
        playerIndex = playerIndex(name, gold);
        if (playerIndex > 9) {
            playerIsInTop10 = false;
        }else{
            playerIsInTop10 = true;
        }
        
        addLineToHighscore(name, gold);
        createHighscore();
        adjustHighscore();
        
        completeHighscoreList = convertNestedListToArrayList(this.highscore);
        playerLine = completeHighscoreList.get(playerIndex);
        highscore = new ArrayList<String[]>();
        
        output = returnTop10Lines(completeHighscoreList);
        if (playerIsInTop10 == false) {
            output.add(playerLine);
        }
        
        return output;
    }
    
    public void writeToHighscoreDocument(String name, int gold) throws FileNotFoundException, IOException{
        addLineToHighscore(name, gold);
        createHighscore();
        adjustHighscore();
        
        PrintWriter out = new PrintWriter("./src/files/highscore.txt");
  
        String temp;
        for (String[] item: this.highscore){ 
            temp = item[0] + ", " + item[1] + ", " + item[2];
            out.println(temp);
        
        }
        out.close();
        highscore = new ArrayList<String[]>();
    }
    
    
    private int playerIndex(String name, int gold) throws IOException{
        int playerIndex = -1;
        
        addLineToHighscore(name, gold);
        createHighscore();
        addSpaceToString(2, true);
        sortHighscoreByIndex(2);
        for (int i = 0; i < this.highscore.size(); i++) {
            if (this.highscore.get(i)[0] == "0") {
                playerIndex = i;
            }
        }
        this.highscore = new ArrayList<String[]>();
        return playerIndex;           
    }
    
    private ArrayList<String> returnTop10Lines(ArrayList<String> input){
        ArrayList<String> output = new ArrayList<String>();
        
        if (input.size() < 11) {
            output = input;
        }else{
            for (int i = 0; i < 10; i++) {
                output.add(input.get(i));
            }
        }
        
        return output;
    }
    
    
    private void adjustHighscore() throws IOException{
        
        addSpaceToString(2, true); //unify length of gold string (index 2)
        sortHighscoreByIndex(2);   //sort Arraylist by gold string 
        
        addSpaceToString(1, false); //unify length of name string (index 1)
        correctRankNumbers();      // add ranks and unify length of ranknumbers (index 0)
        
    }
    
    private void createHighscore() throws FileNotFoundException, IOException{
        FileReader fr = new FileReader("./src/files/highscore.txt");
        
        BufferedReader br = new BufferedReader(fr);
        String s;
        while((s = br.readLine()) != null) {
            String[] r = s.split(", ");
            highscore.add(r);
            
        }
        fr.close();
    }
    
    
    private ArrayList<String> convertNestedListToArrayList(ArrayList<String[]> nestedList){
        ArrayList<String> output = new ArrayList<String>();
        String separator = "\t\t";        
        String lineString = "";
        for (String[] s: nestedList) {
            for (int i = 0; i < s.length; i++) {
                lineString = lineString + s[i] + separator;
            }
            output.add(lineString);
            lineString = "";
        }
        
        return output;    
    }
    
    
    private void addLineToHighscore(String name, int goldCount){
        String[] temp = new String[3];
        temp[0] = "0";
        temp[1] = name;
        temp[2] = ""+goldCount;
        highscore.add(temp);
        
    }
        
    private void sortHighscoreByIndex(int index){
        
        Collections.sort(highscore,new Comparator<String[]>() {
            @Override
            public int compare(String[] strings, String[] otherStrings) {
                return strings[index].compareTo(otherStrings[index]);
            }
        });
        
        Collections.reverse(highscore);
    }
    
    
    private void correctRankNumbers(){
        int increaseRankNumber = 1;
        for (String[] ar: highscore) {
            ar[0] = ""+increaseRankNumber;
            increaseRankNumber++;
        }
        addSpaceToString(0, true);
        
    }
    
    private void addSpaceToString(int indexnumber, boolean front){
        int maxlength = 0;
        int tooshort = 0;
        for (String[] ar: highscore) {
            if (maxlength < ar[indexnumber].length()) {
                maxlength = ar[indexnumber].length();
            }
        }
        for (String[] ar: highscore) {
            tooshort = maxlength - ar[indexnumber].length();
            if (front == true) {
                ar[indexnumber] = repeat(tooshort, " ")+ar[indexnumber];
            }else{
                ar[indexnumber] = ar[indexnumber] + repeat(tooshort, " ");
            }
            
        }
    }
    
    private String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }
   
}


