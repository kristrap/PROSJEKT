package prosjektvaar;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//lager en abstarkt controller, som de andre controllerne skal arve fra 
//lager denne for at jeg kan switche fra den ene fxml-filen til den andre
public abstract class AbController {
    protected void switchScene(String fxmlFile, ActionEvent event) {
       
        try {
          final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          Parent parent = FXMLLoader.load(AbController.class.getResource(fxmlFile));
          Scene scene = new Scene(parent);
          
    
          stage.setScene(scene);
          stage.show();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
//kilde: https://stackoverflow.com/questions/37200845/how-to-switch-scenes-in-javafx
    
}
