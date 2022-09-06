package prosjektvaar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrettTest {

    private Brett spillbrett;
    private Brikke[][] brett;
    private SaveBrettHandler save = new SaveBrettHandler();

    // setter opp brett der alle brikker får verdi 0
    private void lagBrett() {

        spillbrett = new Brett(true); // bruker testfilen: "ResultatTest.txt"
        brett = new Brikke[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                this.brett[x][y] = new Brikke();
                spillbrett.brett = brett;

            }
        }

    }

    // lager tilstand til et spill
    private void lagSpill() {
        lagBrett();
        brett[0][0].setVerdi(128);
        brett[1][0].setVerdi(32);
        brett[2][0].setVerdi(4);
        brett[3][0].setVerdi(16);
        brett[0][1].setVerdi(32);
        brett[1][1].setVerdi(8);
        brett[2][1].setVerdi(2);
        brett[3][1].setVerdi(8);
        brett[0][2].setVerdi(0);
        brett[1][2].setVerdi(4);
        brett[2][2].setVerdi(16);
        brett[3][2].setVerdi(2);
        brett[0][3].setVerdi(0);
        brett[1][3].setVerdi(0);
        brett[2][3].setVerdi(8);
        brett[3][3].setVerdi(4);
        spillbrett.brett = brett;

    }

    // gjør slik at ResultatTest.txt blir tom og ikke inneholder noe
    @BeforeEach
    private void setup() {
        Brett brettet = new Brett(true);
        brettet.nyttSpill();
        save.save("ResultatTest.txt", brettet);
        lagBrett();

    }

    @Test
    @DisplayName("Tester om konstruktøren er riktig satt opp")
    public void testKonstruktør() {
        // sjekker størrelsen på brettet
        assertEquals(4, brett.length, "feil mengde på rader, skal ha 4 rader");
        assertEquals(4, brett[3].length, "feil mengde på kolonner, skal ha 4 kolonner");

        // tester at alle brikkene har verdi 0 når brettet blir satt opp
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                assertEquals(0, brett[x][y].getVerdi(),
                        "feil, når brettet blir satt opp skal ha verdiene starte med å ha null som verdi");
            }
        }

    }

    @Test
    @DisplayName("Tester om riktig verdi blir satt på en brikke som er ledig")
    public void testNyBrikke() {
        lagSpill();
        // setter verdier på to brikker til slik at det bare er en brikke som ikke har
        // noen verdi
        // da vet vi at dene brikken enten må få verdi 2 eller 4 for at denne metoden
        // skal fungere
        brett[0][2].setVerdi(2);
        brett[0][3].setVerdi(8);
        spillbrett.nyBrikke();
        if (brett[1][3].getVerdi() != 4) {
            assertEquals(2, brett[1][3].getVerdi(),
                    "det er bare en ledig plass på brettet, hvis brikken ikke får verdi 4 skal den ha verdi 2");
        } else {
            assertEquals(4, brett[1][3].getVerdi(),
                    "det er bare en ledig plass på brettet, hvis brikken ikke får verdi 4 skal den ha verdi 2");
        }
        lagSpill();
        // samme som over men på en litt annen måte:)
        if (brett[1][3].getVerdi() != 0 & brett[0][3].getVerdi() != 0) {
            if (brett[0][2].getVerdi() != 4) {
                assertEquals(2, brett[0][2].getVerdi(),
                        "det er bare en ledig plass på brettet, hvis brikken ikke får verdi 4 skal den ha verdi 2");
            } else {
                assertEquals(4, brett[0][2].getVerdi(),
                        "det er bare en ledig plass på brettet, hvis brikken ikke får verdi 4 skal den ha verdi 2");
            }
        }

        spillbrett.nyBrikke();
        // når man kaller på denne metoden er det ingen av de andre brikkene som
        // alleredd hadde en verdi, som kan få en ny verdi
        // sjekker at de har samme verdi som før man kalte på denne metoden
        assertEquals(8, brett[1][1].getVerdi(), "brikken har allerede en verdi, og kan derfor ikke bli en ny brikke!");
        assertEquals(16, brett[2][2].getVerdi(), "brikken har allerede en verdi, og kan derfor ikke bli en ny brikke!");
        assertEquals(2, brett[3][2].getVerdi(), "brikken har allerede en verdi, og kan derfor ikke bli en ny brikke!");
        assertEquals(32, brett[0][1].getVerdi(), "brikken har allerede en verdi, og kan derfor ikke bli en ny brikke!");

    }

    @Test
    @DisplayName("Test ved å starte nytt spill")
    public void testNyttSpill() {
        // når man lager et nytt spill skal det alltid starte med to brikker som enten
        // har verdi 2 eller 4
        int count = 0;
        spillbrett.nyttSpill();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (spillbrett.brett[x][y].getVerdi() != 0) {
                    count++;
                }

            }

        }
        // for at metoden skal stemme er det kun to brikker på bretett som ikke har
        // verdi 0
        assertEquals(2, count);

        // nesten samme test bare litt annerledes:)
        int n = 0;
        spillbrett.nyttSpill();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (spillbrett.brett[x][y].getVerdi() == 2 || spillbrett.brett[x][y].getVerdi() == 4) {
                    n++;
                }

            }

        }
        // for at metoden skal stemme er det kun to brikker på bretett som ikke har
        // verdi 0
        assertEquals(2, n);

    }

    @Test
    @DisplayName("Tester om man får rikitg verdi på score etter å ha beveget brikker")
    public void testGetScore() {
        assertEquals(0, spillbrett.getScore(), "har ikke beveget på noen briker enda, derfor skal score være lik 0");
        brett[0][0].setVerdi(4);
        brett[3][0].setVerdi(4);
        brett[1][2].setVerdi(8);
        // det er bare når brikker beveger seg at scorene kan endres, hvis ikke er det
        // ingen brikker som slår seg sammen
        spillbrett.BevegNed();
        assertEquals(8, spillbrett.getScore(), "feil score");
        spillbrett.BevegVenstre();
        assertEquals(24, spillbrett.getScore(), "feil score");
        brett[3][3].setVerdi(4);
        spillbrett.BevegHøyre();
        assertEquals(24, spillbrett.getScore(), "feil score");
        brett[0][2].setVerdi(16);
        spillbrett.BevegOpp();
        assertEquals(56, spillbrett.getScore(), "feil score");

    }

    @Test
    public void testGetBrikke() {
        // kan bare hente en brikke som har gyldig tilsatnd
        assertEquals(0, spillbrett.getBrikke(1, 2), "hentet ikke rikitg brikke, denne har feil verdi");
        lagSpill();
        assertEquals(4, spillbrett.getBrikke(1, 2), "hentet ikke rikitg brikke, denne har feil verdi");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> spillbrett.getBrikke(5, 2),
                "IllegalArgument skal kastes når man setter brikke til ugyldig verdi!");
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> spillbrett.getBrikke(-1, 3),
                "IllegalArgument skal kastes når man setter brikke til ugyldig verdi!");

    }

    @Test
    @DisplayName("Test om brettet er fullt")
    public void testIsBrettFullt() {
        // for at brettet skal være fullt må alle brikkene ha fått en verdi
        assertEquals(false, spillbrett.isBrettFullt(), "brettet er ikke fullt");
        lagSpill();
        // ikke alle brikker har en verdi enda
        assertEquals(false, spillbrett.isBrettFullt(), "brettet er ikke fullt");
        brett[0][2].setVerdi(4);
        brett[0][3].setVerdi(8);
        brett[1][3].setVerdi(4);
        // alle brikker har fått verdi og brettet er fullt
        assertEquals(true, spillbrett.isBrettFullt(), "brettet er fullt!");

    }

    @Test
    @DisplayName("Test å bevege brikker nedover på brettet")
    public void testBevegeNed() {
        // starter med å gi noen brikker verdier slik at man kan teste bevegelse nedover
        // på brettet
        brett[0][3].setVerdi(4);
        brett[1][3].setVerdi(8);
        brett[2][3].setVerdi(4);
        brett[3][3].setVerdi(16);
        // alle brikkene over er i samme kolonne og alle har ulik verdi
        // det betyr at ingen av brikkene kan slå seg sammen ved bevegelse ned og de
        // skal ha samme verdi som før valg av bevegelse ned
        spillbrett.BevegNed();
        assertEquals(4, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");
        assertEquals(8, brett[1][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");
        assertEquals(4, brett[2][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");
        assertEquals(16, brett[3][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");
        brett[0][2].setVerdi(4);

        spillbrett.BevegHøyre();
        assertEquals(8, brett[0][3].getVerdi(), "feil verdi på brikke");
        // nå har brikkene beveget seg og én har fått ny verdi
        // to av brikkene har derfor lik verdi og vil slå seg sammen, tillegg vil den
        // ene brikken bli tom siden den har slått seg sammen med en annen brikke
        spillbrett.BevegNed();
        assertEquals(0, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");// blir tom
        assertEquals(16, brett[1][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");// har slått seg sammen
        assertEquals(4, brett[2][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");
        assertEquals(16, brett[3][3].getVerdi(), "feil verdi på brikke etter bevegelse ned");

        lagSpill();
        brett[0][2].setVerdi(4);
        spillbrett.BevegNed();
        assertEquals(brett[1][2].getVerdi(), 8, "feil verdi på brikke etter bevegelse ned");

        // tester hva som skjer hvis man prøver å bevege når spillet er vunnet
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter vunnet spill
        brett[0][3].setVerdi(1024);
        brett[1][3].setVerdi(1024);
        spillbrett.BevegNed();
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegNed();
            ;
        }, "spillet er vunnet, det er ikke mulig å flytte brikker etter dette.");

        // tester hva som skjer hvis det ikke er noen like brikker vertikalt, da skal
        // det ikke være mulig å beveg noen brikker nedover
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(4);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegNed();
            ;
        }, "det er ingen like brikker vertikalt, og det er derfor ikke mulig å bevege nedover. Du kan bare bevege deg horisontalt");

        // tester hva som skjer hvis man prøver å bevege ned når spillet er tapt
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter tapt spill
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(64);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegNed();
            ;
        }, "du har tapt, og det er ikke mulig å bevege seg lenger");

    }

    @Test
    @DisplayName("Test å bevege brikker oppover på brettet")
    public void testBevegeOpp() {
        // starter med å gi noen brikker verdier slik at man kan teste bevegelse oppover
        // på brettet
        brett[0][3].setVerdi(4);
        brett[1][3].setVerdi(8);
        brett[2][3].setVerdi(4);
        brett[3][3].setVerdi(16);
        // alle brikkene over er i samme kolonne og alle har ulik verdi
        // det betyr at ingen av brikkene kan slå seg sammen ved bevegelse opp og de
        // skal ha samme verdi som før valg av bevegelse opp
        spillbrett.BevegOpp();
        assertEquals(4, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");
        assertEquals(8, brett[1][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");
        assertEquals(4, brett[2][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");
        assertEquals(16, brett[3][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");
        brett[0][2].setVerdi(4);

        spillbrett.BevegHøyre();
        assertEquals(8, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse");
        // nå har brikkene beveget seg og én har fått ny verdi
        // to av brikkene har derfor lik verdi og vil slå seg sammen, tillegg vil den
        // ene brikken bli tom siden den har slått seg sammen med en annen brikke
        spillbrett.BevegOpp();
        assertEquals(16, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");// har slått seg sammen
        assertEquals(4, brett[1][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");
        assertEquals(16, brett[2][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");
        assertEquals(0, brett[3][3].getVerdi(), "feil verdi på brikke etter bevegelse opp");// blir tom

        lagSpill();
        brett[0][2].setVerdi(4);
        spillbrett.BevegOpp();
        assertEquals(brett[0][2].getVerdi(), 8, "feil verdi på brikke etter bevegelse opp");

        // tester hva som skjer hvis man prøver å bevege opp når spillet er vunnet
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter vunnet spill
        brett[0][3].setVerdi(1024);
        brett[1][3].setVerdi(1024);
        spillbrett.BevegNed();
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegOpp();
            ;
        }, "spillet er vunnet, det er ikke mulig å flytte brikker etter dette.");

        // tester hva som skjer hvis det ikke er noen like brikker vertikalt, da skal
        // det ikke være mulig å bevege noen brikker oppover
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(4);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegOpp();
            ;
        }, "det er ingen like brikker vertikalt, og det er derfor ikke mulig å bevege til oppover. Du kan bare bevege deg horisontalt");

        // tester hva som skjer hvis man prøver å bevege ned når spillet er tapt
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter tapt spill
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(64);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegOpp();
            ;
        }, "du har tapt, og det er ikke mulig å bevege seg lenger");

    }

    @Test
    @DisplayName("Test å bevege brikker til høyre på brettet")
    public void testBevegeHøyre() {
        // starter med å gi noen brikker verdier slik at man kan teste bevegelse til
        // høyre
        // på brettet
        brett[0][0].setVerdi(4);
        brett[0][1].setVerdi(8);
        brett[0][2].setVerdi(4);
        brett[0][3].setVerdi(16);
        // alle brikkene over er i samme rad og alle har ulik verdi
        // det betyr at ingen av brikkene kan slå seg sammen ved bevegelse til høyre og
        // de
        // skal ha samme verdi som før valg av bevegelse til høyre
        spillbrett.BevegHøyre();
        assertEquals(4, brett[0][0].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");
        assertEquals(8, brett[0][1].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");
        assertEquals(4, brett[0][2].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");
        assertEquals(16, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");
        brett[1][0].setVerdi(4);

        spillbrett.BevegOpp();
        assertEquals(8, brett[0][0].getVerdi(), "feil verdi på brikke etter bevegelse");
        // nå har brikkene beveget seg og én har fått ny verdi
        // to av brikkene har derfor lik verdi og vil slå seg sammen, tillegg vil den
        // ene brikken bli tom siden den har slått seg sammen med en annen brikke
        spillbrett.BevegHøyre();

        assertEquals(0, brett[0][0].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");// blir tom
        assertEquals(16, brett[0][1].getVerdi(), "feil verdi på brikke etter bevegelse til høyre"); // har slått seg
                                                                                                    // sammen
        assertEquals(4, brett[0][2].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");
        assertEquals(16, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse til høyre");

        lagSpill();
        spillbrett.BevegHøyre();
        assertEquals(brett[0][3].getVerdi(), 32, "feil verdi på brikke etter bevegelse til høyre");

        // tester hva som skjer hvis man prøver å bevege til høyre når spillet er vunnet
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter vunnet spill
        brett[0][3].setVerdi(1024);
        brett[1][3].setVerdi(1024);
        spillbrett.BevegNed();
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegHøyre();
            ;
        }, "spillet er vunnet, det er ikke mulig å flytte brikker etter dette.");

        // tester hva som skjer hvis det ikke er noen like brikker horisontalt, da skal
        // det ikke være mulig å bevege noen brikker til høyre
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(8);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegHøyre();
            ;
        }, "det er ingen like brikker horisontalt, og det er derfor ikke mulig å bevege til høyre. Du kan bare bevege deg vertikalt");

        // tester hva som skjer hvis man prøver å bevege til høyre når spillet er tapt
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter tapt spill
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(64);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegHøyre();
            ;
        }, "du har tapt, og det er ikke mulig å bevege seg lenger");

    }

    @Test
    @DisplayName("Test å bevege brikker til venstre på brettet")
    public void testBevegeVenstre() {
        // starter med å gi noen brikker verdier slik at man kan teste bevegelse til
        // venstre
        // på brettet
        brett[0][0].setVerdi(4);
        brett[0][1].setVerdi(8);
        brett[0][2].setVerdi(4);
        brett[0][3].setVerdi(16);
        // alle brikkene over er i samme rad og alle har ulik verdi
        // det betyr at ingen av brikkene kan slå seg sammen ved bevegelse til venstre
        // og de
        // skal ha samme verdi som før valg av bevegelse til venstre
        spillbrett.BevegVenstre();
        assertEquals(4, brett[0][0].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");
        assertEquals(8, brett[0][1].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");
        assertEquals(4, brett[0][2].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");
        assertEquals(16, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");
        brett[1][0].setVerdi(4);

        spillbrett.BevegOpp();
        assertEquals(8, brett[0][0].getVerdi(), "feil verdi på brikke");
        // nå har brikkene beveget seg og én har fått ny verdi
        // to av brikkene har derfor lik verdi og vil slå seg sammen, tillegg vil den
        // ene brikken bli tom siden den har slått seg sammen med en annen brikke
        spillbrett.BevegVenstre();

        assertEquals(16, brett[0][0].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");// har slått seg
                                                                                                     // sammen
        assertEquals(4, brett[0][1].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");
        assertEquals(16, brett[0][2].getVerdi(), "feil verdi på brikke etter bevegelse til venstre");
        assertEquals(0, brett[0][3].getVerdi(), "feil verdi på brikke etter bevegelse til venstre"); // blir tom

        lagSpill();
        brett[0][3].setVerdi(32);
        spillbrett.BevegVenstre();
        assertEquals(brett[0][1].getVerdi(), 64, "feil verdi på brikke etter bevegelse til venstre");

        // tester hva som skjer hvis man prøver å bevege til venstre når spillet er
        // vunnet
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter vunnet spill
        brett[0][3].setVerdi(1024);
        brett[1][3].setVerdi(1024);
        spillbrett.BevegNed();
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegVenstre();
            ;
        }, "spillet er vunnet, det er ikke mulig å flytte brikker etter dette.");

        // tester hva som skjer hvis det ikke er noen like brikker horisontalt, da skal
        // det ikke være mulig å bevege noen brikker til venstre
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(8);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegVenstre();
            ;
        }, "det er ingen like brikker horisontalt, og det er derfor ikke mulig å bevege til venstre. Du kan bare bevege deg vertikalt");

        // tester hva som skjer hvis man prøver å bevege til venstre når spillet er tapt
        // dette skal ikke være mulig da man ikke skal kunne gjøre noen flere handlinger
        // etter tapt spill
        lagSpill();
        brett[0][2].setVerdi(1024);
        brett[0][3].setVerdi(2);
        brett[1][3].setVerdi(64);
        assertThrows(IllegalArgumentException.class, () -> {
            spillbrett.BevegVenstre();
            ;
        }, "du har tapt, og det er ikke mulig å bevege seg lenger");

    }

    @Test
    @DisplayName("Test om det er mulig å flytte vertiklat")
    public void testIsVertikalFullt() {
        // det skal alltid være mulig å bevege seg vertikalt hvis to brikker over eller
        // under hverandre har lik verdi
        // eller hvis det er en tom brikke

        // mange ledige brikker
        Assertions.assertFalse(spillbrett.isVertikalFullt(), "det er fortsatt mulig å bevege seg opp og ned");
        lagSpill();
        brett[0][2].setVerdi(8);
        // fortsatt ledige brikker
        Assertions.assertFalse(spillbrett.isVertikalFullt(), "det er fortsatt mulig å bevege seg opp og ned");
        brett[0][3].setVerdi(1024);
        // forsatt en ledig brikke på brettet
        Assertions.assertFalse(spillbrett.isVertikalFullt(), "det er fortsatt mulig å bevege seg opp og ned");
        brett[1][3].setVerdi(64);
        // nå er brettet fullt og ingen brikker til over eller under hverandre har lik
        // verdi
        // det er dermed ikke mulig å bevege seg vertikalt
        Assertions.assertTrue(spillbrett.isVertikalFullt(), "det er ikke mulig å gjøre noe trekk opp eller ned");

    }

    @Test
    @DisplayName("Test om det er mulig å flytte horisontalt")
    public void testIsHorisontal() {
        // det skal alltid være mulig å bevege seg horisontalt hvis to brikker som er
        // til høyre eller venstre for hverandre har lik verdi
        // eller hvis det er en tom brikke på brettet

        // mange ledige brikker
        Assertions.assertFalse(spillbrett.isHorisontalFullt(),
                "det er fortsatt mulig å bevege seg til høyre eller venstre");
        lagSpill();
        brett[0][2].setVerdi(8);
        // fortsatt ledige brikker
        Assertions.assertFalse(spillbrett.isHorisontalFullt(),
                "det er fortsatt mulig å bevege seg til høyre eller venstre");
        brett[0][3].setVerdi(1024);
        // forsatt en ledig brikke på brettet
        Assertions.assertFalse(spillbrett.isHorisontalFullt(),
                "det er fortsatt mulig å bevege seg til høyre eller venstre");
        brett[1][3].setVerdi(64);
        // nå er brettet fullt og ingen brikker til høyre eller venstre for hverandre
        // har lik verdi
        // det er dermed ikke mulig å bevege seg horisontalt
        Assertions.assertTrue(spillbrett.isHorisontalFullt(),
                "det er ikke mulig å gjøre noen trekk til høyre eller venstre");

    }

    @Test
    @DisplayName("Test om man har tapt")
    public void testIsGameOver() {
        lagSpill();
        // det er fortsatt ledige brikker på brettet og dermed kan ikke spillet være
        // over
        Assertions.assertFalse(spillbrett.isGameOver(),
                "spillet er ikke over enda, brettet er ikke fullt og brikker kan slå seg sammen");
        brett[0][3].setVerdi(16);
        brett[1][3].setVerdi(256);
        brett[0][2].setVerdi(2);
        // det er ingen ledige brikker på brettet og ingen brikker ved siden av
        // hverandre har lik verdi og kan slå seg sammen
        // dermed må spillet være over siden det ikke er mulig å utføre noen
        // trekk/handlinger
        Assertions.assertTrue(spillbrett.isGameOver(), "spillet er over, det er ikke mulig å gjøre flere trekk");

    }

    @Test
    @DisplayName("Test om man har vunnet")
    public void testIsGameWon() {
        // helt nytt spill uten noen handlinger gjort, dermed kan man ikke ha nådd
        // verdien 2048 enda og spillet er ikke vunnet
        Assertions.assertFalse(spillbrett.isGameWon(), "ingen brikker har verdi 2048, du kan ikke ha vunnet!");
        lagSpill();
        // ingen brikker har verdien 2048
        Assertions.assertFalse(spillbrett.isGameWon(), "ingen brikker har verdi 2048, du kan ikke ha vunnet!");
        brett[0][3].setVerdi(1024);
        brett[1][3].setVerdi(1024);
        spillbrett.BevegNed();
        // en brikke har fått verdien 2048 og spillet er vunnet
        Assertions.assertTrue(spillbrett.isGameWon(), "du har vunnet, det er en brikke på brettet som har verdi 2048!");

    }

}
