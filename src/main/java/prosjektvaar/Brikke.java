package prosjektvaar;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.List;

public class Brikke {

    //settes som final static siden dette er de neste verdiene som det er mulig å ha på brettet 
    public final static List<Integer> gyldigVerdi = Arrays.asList(0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048);
    private int verdi;

    public Brikke() { // lager en brikke som har 0 som verdi
        verdi = 0;
    }

    public Brikke(int verdi) { // lager en brikke som får en satt verdi, så lenge den er gyldig 
        if (!gyldigVerdi.contains(verdi)) {
            throw new IllegalArgumentException("ikke en gyldig verdi");
        }

        this.verdi = verdi;
    }

    public int getVerdi() {
        return verdi;
    }

    public void setVerdi(int verdi) { // setter verdi på en brikke, så lenge verdien er gyldig 
        if (!gyldigVerdi.contains(verdi)) {
            throw new IllegalArgumentException("ikke en gyldig verdi");
        }
        this.verdi = verdi;
    }


    public boolean isGyldigVerdi() { // sjekker om brikken har en gyldig verdi
        return gyldigVerdi.contains(verdi);
    }
}