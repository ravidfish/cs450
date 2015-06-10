/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class Item {
    
    ArrayList<Integer> itemType = new ArrayList<>();
    
    Item(String itemName) {
       
        //arbituary number and name, use possibly database query to populate this
        if (itemName.equals("hands")) {
            
            itemType.add(1);
        } else {
            
            System.out.println("ERROR: ITEMTYPE NOT FOUND");
            System.exit(0);
        }
    }
}
