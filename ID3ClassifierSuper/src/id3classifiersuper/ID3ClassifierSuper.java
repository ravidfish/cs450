/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id3classifiersuper;

import id3classifier.ID3Shells;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */

public class ID3ClassifierSuper extends Application {
    
    /**
     *
     * @param primaryStage
     * @throws java.lang.Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] args = null;
        ID3Shells.main(args);
        stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
}
