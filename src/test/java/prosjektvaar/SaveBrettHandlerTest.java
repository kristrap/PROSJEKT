package prosjektvaar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class SaveBrettHandlerTest {

    private Brett spillbrett;
    private Brikke[][] brett;
    private SaveBrettHandler saveBrettHandler = new SaveBrettHandler();

    private void lagBrett() {
        spillbrett = new Brett(true); //skal lagre/hente fra testfil: "ResultatTest.txt"
        brett = new Brikke[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                this.brett[x][y] = new Brikke();
                spillbrett.brett = brett;

            }
        }

    }
    //lager en tilstand til et spill
    private void lagSpill(){
        lagBrett();
        brett[0][0].setVerdi(2);
        brett[1][0].setVerdi(2);
        brett[2][1].setVerdi(4);
        brett[1][2].setVerdi(16);
        brett[0][3].setVerdi(8);
        spillbrett.BevegNed();
        spillbrett.BevegHøyre();

    }



    @BeforeEach
    public void setup() {
       lagSpill();
    }

    @Test
    @DisplayName("Tester om bruker har et spill fra før")
    public void testBrukerHarSpill(){
        saveBrettHandler.saveName("Navn.txt", "Nora");
        assertTrue(saveBrettHandler.brukerHarSpill("ResultatTest.txt"), "feil, bruker har allerde spilt tidligere");
        saveBrettHandler.saveName("Navn.txt", "Berit");
        assertFalse(saveBrettHandler.brukerHarSpill("ResultatTest.txt"), "feil, bruker har allerde spilt tidligere");
        saveBrettHandler.saveName("Navn.txt", "IkkeBerit");
    }

    @Test
    @DisplayName("Test om det lagres og hentes rikitg navn")
    public void testNavn(){
        saveBrettHandler.saveName("Navn.txt", "Henrik");
        assertEquals("Henrik", saveBrettHandler.loadName("Navn.txt"), "feil, bruker har spilt og navnet skal ha blitt lagret");
        assertEquals("Henrik", saveBrettHandler.loadSaveName("ResultatTest.txt"), "feil, bruker har spilt og navnet skal ha blitt lagret");
    }
    
    @Test
    @DisplayName("Test om det lagres og hentes rikitg score")
    public void testScore(){
        lagSpill();
        //tester at scoren man får er lik scoren som er lagret og man henter ut 
        assertEquals(spillbrett.getScore(),saveBrettHandler.loadScore("ResultatTest.txt"), "feil, bruker har spilt og score skal ha blitt lagret");
        brett[3][2].setVerdi(32);
        brett[1][3].setVerdi(8);
        spillbrett.BevegVenstre();
        spillbrett.BevegHøyre();
        assertEquals(spillbrett.getScore(),saveBrettHandler.loadScore("ResultatTest.txt"), "feil, bruker har spilt og score skal ha blitt lagret");
        
    }
    @Test
    @DisplayName("Test om det lagres og hentes rikitg verdier på brikker")
    public void testVerdier(){
        Brikke[][] lagret = saveBrettHandler.load("ResultatTest.txt");
        //tester at alle verdiene til hver brikke stemmer med tilstanden til brikkene som blir lagret og hentet 
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
        assertEquals(brett[x][y].getVerdi(), lagret[x][y].getVerdi(), "feil, bruker har spilt og verdiene skal ha blitt lagret");
        lagSpill();
        //har gått igjennom alle brikker og sett at det stemmer, men tester litt mer for sikkerhetsskyld 
        assertEquals(brett[1][2].getVerdi(), lagret[1][2].getVerdi(), "feil, bruker har spilt og verdiene skal ha blitt lagret");
        assertEquals(brett[3][0].getVerdi(), lagret[3][0].getVerdi(), "feil, bruker har spilt og verdiene skal ha blitt lagret");
        assertEquals(brett[3][3].getVerdi(), lagret[3][3].getVerdi(), "feil, bruker har spilt og verdiene skal ha blitt lagret");




    }
    
   
     }}}
       
        
    

