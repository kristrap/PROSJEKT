package prosjektvaar;

public class BrettFunksjoner {

    // sjekker om spillet er over eller ikke
    // dette kan bare skje hvis det ikke er mulig å bevege noen brikker hverken
    // vertikalt eller horisontalt
    protected boolean isGameOver(Brett brett) {
        // sjekker om det er et gyldig brett
        if (brett == null) {
            throw new IllegalArgumentException("ikke gyldig brett");
        }
        // returnerer true hvis det ikke er noen gyldige trekk igjen (etter å ha sjekket
        // både vertiklat og horisontalt)
        return isVertikalFullt(brett) && isHorisontalFullt(brett);
    }

    // sjekker om man har vunnet spillet
    // dette kan bare skje hvis noen av brikkene har 2046 eller høyere som verdi
    protected boolean isGameWon(Brett brett) {
        // sjekker om det er et gyldig brett
        if (brett == null) {
            throw new IllegalArgumentException("ikke gyldig brett");
        }
        for (int x = 0; x < brett.getBrett().length; x++) {
            for (int y = 0; y < brett.getBrett()[x].length; y++) {
                if (brett.getBrett()[x][y].getVerdi() >= 2048) {
                    return true;
                }

            }

        }

        return false;
    }

    // sjekker om det er noe gyldige trekk vertikalt
    // retunrerer true, hvis det ikke er noe gyldige trekk, hvis det er noen gyldige
    // trekk returneres false, dette kan bare skje hvis det ikk er noen ledige
    // brikker igjen vertikalt
    protected boolean isVertikalFullt(Brett brett) {
        // sjekker om det er et gyldig brett
        if (brett == null) {
            throw new IllegalArgumentException("ikke gyldig brett");
        }
        int count = 0;
        // det er kun mulig at spillet er over, hvis brettet er fullt
        if (brett.isBrettFullt()) {
            // men selvom brettet er fullt er det mulig at to brikker kan slå seg sammen
            // vertikalt
            // iterere gjennom brikkene for å sjekke dette
            for (int x = 0; x < brett.getBrett().length; x++) {
                for (int y = 0; y < brett.getBrett()[x].length; y++) {
                    if (x != 3) {
                        if (brett.getBrett()[x][y].getVerdi() != brett.getBrett()[x + 1][y].getVerdi()) {
                            count++;
                        }
                    }
                }
            }
        }

        if (count == 12) { // hvis ingen av stegene over er mulig, betyr det at det ikke går å slå sammen
                           // noen brikker vertikalt
                           // for hver brikke som det ikke går an å slå sammen med, plusses det på 1 i
                           // count
                           // når den har gått igjennom alle 12 brikker som det er mulig å kunne slå sammen
                           // med, sjekker vi om count = 12
                           // hvis count= 12, betyr det at det ikke er mulig at noen brikker kan slå seg
                           // sammen og spillet er over

            return true; // det betyr at det ikke er noen gyldige trekk igjen og spiller er dermed over
        }
        return false;
    }

    // sjekker om det er noe gyldige trekk horisontalt
    // retunrerer true, hvis det ikke er noe gyldige trekk, hvis det er noen gyldige
    // trekk returneres false, dette kan bare skje hvis det ikk er noen ledige
    // brikker igjen horisontalt
    protected boolean isHorisontalFullt(Brett brett) {
        // sjekker om det er et gyldig brett
        if (brett == null) {
            throw new IllegalArgumentException("ikke gyldig brett");
        }
        int count = 0;
        // det er kun mulig at spillet er over, hvis brettet er fullt
        if (brett.isBrettFullt()) {
            // men selvom brrettet er fullt er det mulig at to brikke rkan slå seg sammen
            // horisontalt
            // iterere gjennom brikkene for å sjekke dette
            for (int x = 0; x < brett.getBrett().length; x++) {
                for (int y = 0; y < brett.getBrett()[x].length; y++) {

                    if (y != 3) {
                        if (brett.getBrett()[x][y].getVerdi() != brett.getBrett()[x][y + 1].getVerdi()) {
                            count++;
                        }
                    }

                }
            }
        }

        if (count == 12) { // hvis ingen av stegene over er mulig, betyr det at det ikke går for noen
                           // brikker og count == 12
            return true; // det betyr at det ikke er noen gyldige trekk igjen og spiller er dermed over
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
