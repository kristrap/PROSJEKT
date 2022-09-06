package prosjektvaar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrikkeTest {

    private Brikke brikke;
    

    @BeforeEach
    public void setup() {
        brikke = new Brikke();
    }

    @Test
    @DisplayName("Test om konstruktør er riktig")
	public void testBrikke() {
        //når man lager en ny brikke uten noe paramater og valgt verdi, skal brikken få verdi 0 som betyr at den er tom
		brikke = new Brikke();
		Assertions.assertEquals(0, brikke.getVerdi(), "fikk ikke rikitg verdi på brikke, når konstruktør er tom betyr det at brikken får verdi 0");
        //når man lager en ny brikke med paramater og valgt verdi, skal brikken få verdi som blir gitt, hvis det er en gyldig verdi
        brikke = new Brikke(2);
        //når man lager en ny brikke med paramater og valgt verdi, skal brikken få verdi som blir gitt, hvis det er en gyldig verdi
		Assertions.assertEquals(2, brikke.getVerdi(), "fikk ikke rikitg verdi på brikke, brikken skulle fått verdi 2");
        //når man lager en ny brikke med paramater og valgt verdi, skal brikken få verdi som blir gitt, hvis det er en gyldig verdi
        brikke = new Brikke(4);
		Assertions.assertEquals(4, brikke.getVerdi(),"fikk ikke rikitg verdi på brikke, brikken skulle fått verdi 4");
         //når man lager en ny brikke med paramater og valgt verdi, skal brikken få verdi som blir gitt, hvis det er en gyldig verdi
         brikke = new Brikke(1024);
         Assertions.assertEquals(1024, brikke.getVerdi(),"fikk ikke rikitg verdi på brikke, brikken skulle fått verdi 4");
 

        //det er ikke mulig å lage en ny brikke med en ugylidg verdi 
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brikke = new Brikke(-1);
            brikke = new Brikke(-100);
		}, "Kan ikke være et negatvit tall i brikke");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brikke = new Brikke(3);
            brikke = new Brikke(200);
		}, "Ugydlig verdi til brikke, brikkene kan bare ha verdi: 0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 eller 2048");
	}


    @Test
    @DisplayName("Test ved å sette gyldig verdi på brikke")
    public void testSetGyldigVerdi() {
        //man kan bare sette en brikke til en gydlig verdi 
        brikke.setVerdi(0);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(2);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(4);
        assertTrue(brikke.isGyldigVerdi(),"feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(8);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(16);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(32);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(64);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(128);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(256);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(512);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(1024);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");
        brikke.setVerdi(2048);
        assertTrue(brikke.isGyldigVerdi(), "feil, brikken har ikke fått verdien som ble satt");

        //det er ikke mulig å sette en brikke til en ugylldig verdi 
        assertThrows(
                IllegalArgumentException.class,
                () -> brikke.setVerdi(-1),
                "IllegalArgument skal kastes når man setter en brikke til en ugyldig verdi, kan ikke sette verdien til et negativt tall");
        assertThrows(
                IllegalArgumentException.class,
                () -> brikke.setVerdi(22),
                "IllegalArgument skal kastes når man setter en brikke til en ugyldig verdi, brikken kan bare ha verdiene: 0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 eller 2048"); 
    }
            

   
    @Test
    @DisplayName("Test ved å sette ugyldig verdi på brikke")
    public void testSetUgyldigVerdi() {
        //litt forskjellige eksempler på verdier som ikke er gyldig 
        assertThrows(IllegalArgumentException.class, () -> {
            brikke.setVerdi(-1);
        }, "IllegalArgument skal kastes når man setter en brikke til en ugyldig verdi, kan ikke sette negatvit tall");

        assertThrows(IllegalArgumentException.class, () -> {
            brikke.setVerdi(22);
        }, "IllegalArgument skal kastes når man setter en brikke til en ugyldig verdi, brikken kan bare ha verdiene: 0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 eller 2048");

        assertThrows(IllegalArgumentException.class, () -> {
            brikke.setVerdi(3000);
        }, "IllegalArgument skal kastes når man setter en brikke til en ugyldig verdi, brikken kan bare ha verdiene: 0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 eller 2048");

        assertThrows(IllegalArgumentException.class, () -> {
            brikke.setVerdi(87);
        }, "IllegalArgument skal kastes når man setter en brikke til en ugyldig verdi, brikken kan bare ha verdiene: 0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 eller 2048");

    }
}
