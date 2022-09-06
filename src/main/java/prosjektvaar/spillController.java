package prosjektvaar;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class spillController extends AbController {

    private Brett spill;

    @FXML
    private Pane spillbrett;

    @FXML
    private Text lostText = new Text();

    @FXML
    private Text wonText = new Text();

    @FXML
    private Button opp, ned, høyre, venstre;

    @FXML
    private Label score1, tap;

    @FXML
    private Label score;

    @FXML
    private Label spillerNavn;

    // setter opp spillet
    @FXML
    public void initialize() {
        lagSpill();
        lagBrett();
        drawBoard();
        spill.isGameOver();
        setName();

    }

    private void setName() {
        spillerNavn.setText(spill.getName());

    }

    private void lagSpill() {
        spill = new Brett(false);
        tap.setOpacity(0);
        getScore();
        spill.isGameOver();
    }

    @FXML
    public void bevegOpp() {
        spill.BevegOpp();
        getScore();
        spill.nyBrikke();
        drawBoard();

    }

    @FXML
    public void bevegNed() {
        spill.BevegNed();
        getScore();
        spill.nyBrikke();
        drawBoard();
    }

    @FXML
    public void bevegVenstre() {
        spill.BevegVenstre();
        getScore();
        spill.nyBrikke();
        drawBoard();
    }

    @FXML
    public void bevegHøyre() {
        spill.BevegHøyre();
        getScore();
        spill.nyBrikke();
        drawBoard();

    }

    @FXML
    private void getScore() {
        // må gjøre om tall til String
        score1.setText(Integer.toString(spill.getScore()));
    }

    // trykker på "nytt spill" i appen
    @FXML
    private void nyttSpill() {
        if (spillbrett.getChildren().contains(wonText)) {
            spillbrett.getChildren().remove(wonText);
        }
        if (spillbrett.getChildren().contains(lostText)) {
            spillbrett.getChildren().remove(lostText);
        }

        spill.nyttSpill();
        tap.setOpacity(0);
        score1.setText(Integer.toString(spill.setScore(0)));
        setName(); //setter navnet til spiller 
        drawBoard();

    }

    // setter fargen på brikkene
    private String brikkeFarge(int n) {
        if (n == 2) {
            return "#7bcaf2";
        }
        if (n == 4) {
            return "#f27b99";
        }
        if (n == 8) {
            return "#f6ff1f";
        }
        if (n == 16) {
            return "#ff4d14";
        }
        if (n == 32) {
            return "#ffa0fd";
        }
        if (n == 64) {
            return "#89ffb6";
        }
        if (n == 128) {
            return "#ffb10b";
        }
        if (n == 256) {
            return "#5f66ff";
        }
        if (n == 512) {
            return "#ebfff6";
        }
        if (n == 1024) {
            return "#ffa0a8";
        }
        if (n == 2048) {
            return "#ff03f8";
        } else {
            return "#9bd2ad";

        }
    }

    private void lagBrett() {
        spillbrett.getChildren().clear();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Pane brikke = new Pane();
                brikke.setTranslateX(x * 50);
                brikke.setTranslateY(y * 50);
                brikke.setPrefHeight(50);
                brikke.setPrefWidth(50);
                brikke.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                spillbrett.getChildren().add(brikke);

            }

        }
    }

    private void drawBoard() {
        for (int y = 0; y < spill.getBrett().length; y++) {
            for (int x = 0; x < spill.getBrett()[0].length; x++) {
                spillbrett.getChildren().get(y * spill.getBrett()[0].length + x)
                        .setStyle("-fx-background-color: " + brikkeFarge(spill.getBrett()[x][y].getVerdi())
                                + "; -fx-border-color: black; -fx-border-width: 1px;");
                Label text1 = new Label();
                HBox box = new HBox();

                box.setPrefWidth(50);
                box.setPrefHeight(50);
                box.getChildren().addAll(text1);
                box.setAlignment(Pos.CENTER);

                if (spill.getBrikke(x, y) != 0) {
                    text1.setText(Integer.toString(spill.getBrikke(x, y)));

                }
                ((Pane) spillbrett.getChildren().get(y * spill.getBrett()[0].length + x)).getChildren().setAll(box);

            }

            if (spill.isGameOver()) {
                tap.setOpacity(1);
            }
        }

        if (spill.isVertikalFullt()) {
            opp.setDisable(true);
            ned.setDisable(true);
        } else {
            opp.setDisable(false);
            ned.setDisable(false);
        }
        if (spill.isHorisontalFullt()) {
            venstre.setDisable(true);
            høyre.setDisable(true);
        } else {
            venstre.setDisable(false);
            høyre.setDisable(false);
        }

        if (spill.isGameWon()) {
            tap.setText("DU VANT");
            tap.setTextFill(Color.GREEN);
            tap.setOpacity(1);

        }
    }
}
