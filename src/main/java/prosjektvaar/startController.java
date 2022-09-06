package prosjektvaar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class startController extends AbController {

    SaveBrettHandler name = new SaveBrettHandler();
    @FXML
    private Label spillNavn;

    @FXML
    private Label navn;

    @FXML
    private Text ugyldignavn;

    @FXML
    private TextField SkrivInn;

    @FXML
    private Button GåVidere;

    @FXML
    private void start(ActionEvent event) {
        if (!SkrivInn.getText().equals("")) {
            //navnet må være mer enn 3 bokstaver og ikke inneholde visse tegn
            if (SkrivInn.getText().matches("[A-z|Æ|Ø|Å|æ|ø|å]{3,}") && !SkrivInn.getText().matches("^(.|-|;|:)")) {
                name.saveName("Navn.txt", SkrivInn.getText()); //navnet blir lagret til filen "Navn.txt"
                switchScene("spill2048.fxml", event); //bytter vindu til spillet hvis man har skrevet inn gyldig navn
            }
        } 
        ugyldignavn.setVisible(true);
        

        

    }
}
