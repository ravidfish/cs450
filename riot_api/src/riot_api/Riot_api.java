/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riot_api;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark
 */
public class Riot_api {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MatchId matchid = new MatchId(1885303956);
        int limit = 3000;
        try {
            PrintWriter out = new PrintWriter("MatchJson.txt");
 
            for (int i = 0; i < limit; i++) {
                String response = matchid.getNextMatch();
                if (response.equals("")) {
                    limit++;
                } else {
                    out.println(response);
                }
                Thread.sleep(1500);  
            }
            
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Riot_api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Riot_api.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        /*for (int i = 0; i < 1000; i++) {
            try {
               
                //set variables by calling the setters
                //call matchid 's submit
                //save off the object data, prob json to csv
                
                String matchId = "1885303956";    //MatchId.setMatchId();
                
                
                
                
               
                
                
                
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Riot_api.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
