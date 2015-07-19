/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riot_api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Mark
 */
public class Riot_api {

    private static int count = 0;
    private static final int limit = 3000;
    private static final int mId = 1885303956;
    private static final int sleep_timer = 1250;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MatchId matchid = new MatchId(mId);
        String response;
        
        try {
            while (count < limit) {
                response = matchid.getNextMatch();
                if (!response.equals("")) {
                    parseMatchInfo(response);
                }
                Thread.sleep(sleep_timer);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Riot_api.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//    public static void getMatchInfo() {
//            for (int i = 0; i < limit; i++) {
//                String response = matchid.getNextMatch();
//                if (!response.equals("")) {
//                }
//            }
//    }
    
    public static void parseMatchInfo(String json) {
        String output = "";
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        if (!(obj.get("queueType").toString().replace("\"", "").equals("NORMAL_3x3") ||
            obj.get("queueType").toString().replace("\"", "").equals("NORMAL_5x5_BLIND") ||
            obj.get("queueType").toString().replace("\"", "").equals("NORMAL_5x5_DRAFT") ||
            obj.get("queueType").toString().replace("\"", "").equals("RANKED_SOLO_5x5"))) {
            return;
        }
        
        // Increment the counter only for ranked solo games (these are our targets
        // Everything else is bonus
        if (obj.get("queueType").toString().replace("\"", "").equals("RANKED_SOLO_5x5")) {
            count++;
        }
        
        JsonArray participants = obj.getAsJsonArray("participants");
        for (JsonElement part : participants) {
            JsonObject participant = part.getAsJsonObject();

            output += participant.get("championId") + ",";
        }
        JsonArray teams = obj.getAsJsonArray("teams");
        for (JsonElement t : teams) {
            JsonObject team = t.getAsJsonObject();
            //System.out.println(team.get("winner"));
            if (team.get("winner").toString().equals("true")) {
                output += team.get("teamId") + "\n";
            }
        }

        try {
            String name = obj.get("queueType").toString().replace("\"", "") + ".csv";
            File file = new File(name);

            //if file doesnt exists, then create it
            if(!file.exists()){
                    file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWritter);
            bufferWriter.write(output);
            bufferWriter.close();

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}