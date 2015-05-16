/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id3classifiersuper;

import id3classifier.Main;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */

public class ID3ClassifierSuper extends Application {
    
    /**
     * @param primaryStage
     * @throws java.lang.Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // javaFX start function, necessary only because this has to be FX
        // couldn't import javaFX classes without this being FX for some reason
        // call the shell's main, and stop when done, so that FX terminates
        String[] args = null;
        Main.main(args);
        stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // javaFX launch function, necessary only because this has to be FX
        launch(args);
    }
}
