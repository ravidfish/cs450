/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mark
 */
public class Player {
    
    int maxHandSize = 7;
    ArrayList<Card> hand = new ArrayList<>(maxHandSize);
    Map<Integer,Item> inventory = new HashMap<>();
    
    public void addInventoryItem() {
        
        //crate a new inventory item
        //inventory.put(, );
    }    
}
