package prosjektvaar;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Brett {

    private List<String> gyldigRetning = Arrays.asList("opp", "ned", "venstre", "høyre");
    private BrettFunksjoner brettFunksjoner = new BrettFunksjoner(); // henter funksjonene fra denne klassen
    public Brikke[][] brett; // brettet skal bli laget av flere brikker
    private String filename = "Resultat.txt"; // filen jeg skal bruke
    private int ny = 0;
    private int score = 0;
    private SaveBrettHandler save = new SaveBrettHandler();
    private String name; // navn til spiller


    public Brett(boolean isTest) {
        if (isTest) { // bestemmer om dette er for å teste eller ikke
            filename = "ResultatTest.txt";// hvis det er en test skal jeg heller lagre/hente fra denne filen
        }
        // Hvis brukeren allerde har spilt før hentes den tildigre spill-tilstanden,
        // altså den gamle scoren og brikke-verdiene

        if (save.brukerHarSpill(filename)) {
            score = save.loadScore(filename);
            brett = save.load(filename);
            name = save.loadSaveName(filename);

            if (brett == null) {
                brett = new Brikke[4][4]; // setter opp et brett som en 4x4-matrise som består av (brikker)
                for (int x = 0; x < 4; x++) { // x er rader på brettet
                    for (int y = 0; y < 4; y++) { // y er kolonner på brettet

                        this.brett[x][y] = new Brikke(); // hver brikke på brettet får 0 som verdi

                    }
                }

            }
        } else {
            // hvis spilleren ikke har spilt tidligre, lages det et nytt spill
            nyttSpill();
        }

    }

    public void nyttSpill() { // starter et nytt når vi ikke allerde har et spill som er lagret
        brett = new Brikke[4][4]; // lager en 4x4-matrise
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                this.brett[x][y] = new Brikke();
            }
        }
        // Når man starter et nytt spill, skal man alltid starte med to brikker på
        // brettet
        // disse brikkene har enten verdi 2 eller 4
        nyBrikke();
        nyBrikke();
        // når man starter et spill, må score alltid starte på 0 siden det ikke er gjort
        // noen trekke enda
        setScore(0);
        setName(save.loadName("Navn.txt")); // spilleren får navnet som blir skrevet inn da appen åpnes

    }

    // det lages alltid en ny brikke som enten inneholder verdi 2 eller 4
    // på en tilfelidg brikke som er tom
    public void nyBrikke() {
        boolean tomBrikke = true; // varibalen som sier om brikken er tom eller ikke
        while (tomBrikke)// sjekker om det er en tom brikke
        {
            Random randomTall = new Random();
            int x = randomTall.nextInt(4); // tar et tilfeldig tall fra x-veriden til spillbrettet
            int y = randomTall.nextInt(4); // tar et tilfeldig tall i y-verdien på spillbrettet
            Double r = (double) Math.random(); // henter et tall mellom 0 og 1

            // dersom brikken har 0 som verdi betyr det at brikken er ledig og brikken kan
            // få verdi 2 eller 4
            if (this.brett[x][y].getVerdi() == 0) {

                int n = (r < 0.9) ? 2 : 4; // det er 90% sjanse for at verdien som den nye brikken får verdi 2
                                           // og 10% for å at den nye brikken får verdi 4

                brett[x][y] = new Brikke(n);// setter brikken til enten 2 eller 4 avhengig av det tilfeldige tallet
                tomBrikke = false;// når brikken får en verdi er den ikke lenger tom (ikke ledig lenger) og
                                  // "tomBrikke" må settes til false

            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // sjekker om brikken er gyldig
    private boolean isBrikke(int x, int y) {
        return (4 >= x || x >= 0) && (4 >= y || y >= 0);
    }

    // sjekker om brettet er gyldig
    private boolean isBrett() {
        if (this.brett == null) {
            return false;
        }
        return true;
    }

    // henter brikken, hvis man gir gyldig input
    public int getBrikke(int x, int y) {
        if (!isBrikke(x, y)) {
            throw new IllegalArgumentException("Ikke en gyldig brikke");
        }
        return this.brett[x][y].getVerdi(); // henter verdien til valgt brikke
    }

    public void setBrett(Brikke[][] brett) {
        this.brett = brett;
    }

    // henter brett hvis det er gyldig
    public Brikke[][] getBrett() {
        if (!isBrett()) {
            throw new IllegalArgumentException("Ikke en gyldig brikke");
        }
        return this.brett; // henter brettet
    }

    public int getScore() {
        return score;
    }

    public int setScore(int score) {
        return this.score = score;
    }

    // sjekker om det er noen ledige brikker igjen på brettet, hvis ikke er brettet
    // fullt (alle brikker har en verdi)
    public boolean isBrettFullt() {

        if (!isBrett()) { // starter med å sjekke om det er et gyldig brett
            throw new IllegalArgumentException("ikke gyldig brett");
        } else {
            int count = 0;

            // starter med å iterere gjennom brettet og sjekker om verdien er større enn 0
            // hvis brikken som blir iterert igjennom er større enn 0 betyr det at den
            // allerede har en verdi
            // varibalen count blir plusset på hver gang det er en brikke som har en verdi
            // for å finne ut om det til slutt blir 16, og at det dermed gjelder alle
            // brikkene på brettet
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {

                    if (brett[x][y].getVerdi() > 0) {
                        count++;
                    }
                }
            }
            return count == 16; // returnerer true hvis count = 16, da er brettet fullt og alle 16 brikker har
                                // en verdi

        }
    }

    // henter metoden som sjekker om spillet er over
    public boolean isGameOver() {
        return this.brettFunksjoner.isGameOver(this);
    }

    // henter metoden som sjekker om man har vunnet
    public boolean isGameWon() {
        return this.brettFunksjoner.isGameWon(this);
    }

    // henter metoden som sjekker om det er mulig å flytte noen brikker vertikalt
    public boolean isVertikalFullt() {
        return this.brettFunksjoner.isVertikalFullt(this);
    }

    // henter metoden som sjekker om det er mulig å flytte noen brikker horisontalt
    public boolean isHorisontalFullt() {
        return this.brettFunksjoner.isHorisontalFullt(this);
    }

    // beveger alle brikker oppover i brettet hvis, man trykker "opp" i appen og
    // dermed vil skyve alle brikker oppover
    // da kaller man til slutt på metoden som beveger alle brikker opp
    public void BevegOpp() {
        // må først sjekke om det er et gydlig trekk
        if (isVertikalFullt() || isGameWon() || isGameOver()) {
            throw new IllegalArgumentException("ikke mulig å flytte oppover");
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                ny = 0; // ny x-verdi, denne verdien øker
                if (ny <= x) // hvis den nye x-verdien er mindre enn den opprinnelige x-verdien betyr det at
                             // ny-x er høyere opp på brettet
                             // da er det altså mulig å flytte brikken lenger opp på brettet

                {

                    BevegeBrikkerVertikalt(x, y, "opp");

                }
            }
        }
    }

    // hvis man trykker på "ned" i spillet, vil denne metodne bli kalt på
    // den går igjennom alle brikkene på brettet og beveger brikkene nedover
    public void BevegNed() {
        // må først sjekke om det er et gydlig trekk
        if (isVertikalFullt() || isGameWon() || isGameOver()) {
            throw new IllegalArgumentException("ikke mulig å flytte nedover");
        }
        for (int x = 3; x >= 0; x--) {
            for (int y = 0; y < 4; y++) {
                ny = 3; // ny x- verdi
                if (ny >= x) // hvis den nye x-veriden er større enn den opprinnelige x-verdien, betyr det at
                             // plassen er lenger ned på brette
                             // da kan man altså flytte brikkene lenger ned
                {

                    BevegeBrikkerVertikalt(x, y, "ned");
                }
            }
        }
    }

    // denne metoden endrer verdien på brikker ut ifra hvilken retning som er valgt.
    // Hvis den nye brikken er 0, eller har lik verdi som brikken man skal bevege
    // betyr det at man kan flytte den opprinnelige brikken til den nye plassen
    // hvis ny brikke er 0, vil den få samme verdi som den opprinnelige brikken
    // hvis den nye brikken og den brikken man flytter har lik verdi, vil de to
    // veridene til brikken slå seg sammen
    private void BevegeBrikkerVertikalt(int x, int y, String retning) {
        // må først sjekke om det er et gydlig trekk
        if (!isBrikke(x, y) || !gyldigRetning.contains(retning)) {
            throw new IllegalArgumentException("ikke gyldig bevegelse");
        }

        // sjekker om brikke som flytts mot har 0 eller lik verdi som den opprinnelige
        // brikken
        if (brett[ny][y].getVerdi() == 0 || brett[ny][y].getVerdi() == brett[x][y].getVerdi()) {
            // dette kan bare skje hvis man trykker opp eller ned i appen
            if (x > ny && (retning.equals("opp")) || (retning.equals("ned") && (x < ny))) {

                boolean Zero = (retning.equals("opp"))
                        // sjekker om brikkene som er mellom ny og gammel brikke har 0 som verdi eller
                        // ikke
                        ? IntStream.range(ny + 1, x).boxed().allMatch(p -> brett[p][y].getVerdi() == 0) // sjekker for
                                                                                                        // bevegelse opp
                        : IntStream.range(x + 1, ny).boxed().allMatch(p -> brett[p][y].getVerdi() == 0); // sjekker for
                                                                                                         // bevegelse
                                                                                                         // ned
                if (Zero) {
                    // hvis det bare er 0 mellom brikkene slår vi sammen den nye og gamle brikken
                    // sin verdi
                    int Score = brett[ny][y].getVerdi() + brett[x][y].getVerdi();
                    if (brett[ny][y].getVerdi() != 0) {
                        score += Score;
                    }
                    brett[ny][y].setVerdi(Score);
                    brett[x][y].setVerdi(0); // gammel brikke får 0 som verdi

                    // hvis ikke alle brikker mellom har 0 som verdi, må man gå igjennom brikke
                    // for brikke for å sjekke hvor langt opp det er mulig å flytte en brikke
                    // oppover i brettet
                } else {
                    ny = (retning.equals("opp")) ? ny + 1 : ny - 1;
                    BevegeBrikkerVertikalt(x, y, retning);
                }
            }

            // går igjennom alle brikker enten opppver eller nedover på brettet ved å trekke
            // fra eller plusse på variabel "ny" i metodekallene, slik at man får gått
            // igjennom alle brikkene på brettet for å eneten bevege de opp eller ned
        } else {
            ny = (retning.equals("opp")) ? ny + 1 : ny - 1;
            BevegeBrikkerVertikalt(x, y, retning);
        }

        // lagrer bretett sitt tilstand etter hver gang man har gjort en handling
        // (flyttet på brikker)
        save.save(filename, this);

    }

    // hvis man trykker på venstre i spillet, vil denne metoden bli kalt på
    // den går igjennom alle brikkene på brettet og fører de til venstre
    public void BevegVenstre() {
        // må først sjekke om det er et gydlig trekk
        if (isHorisontalFullt() || isGameWon() || isGameOver()) {
            throw new IllegalArgumentException("ikke mulig å flytte til venstre");
        }
        for (int x = 0; x < brett.length; x++) {
            for (int y = 0; y < 4; y++) {
                ny = 0;// ny y-verdi
                if (ny <= y) // hvis den nye y-veriden er mindre enn den opprinnelige y-verdien, betyr det at
                             // plassen er lenger til venstre på brette
                             // da er det altså mulig å flytte brikken til venstre
                {

                    BevegeBrikkerHorisontalt(x, y, "venstre");

                }
            }
        }
    }

    // hvis man trykker på høyre i spillet, vil denne metoden bli kalt på
    // den går igjennom alle brikkene på brettet og fører de til høyre
    public void BevegHøyre() {
        // må først sjekke om det er et gydlig trekk
        if (isHorisontalFullt() || isGameWon() || isGameOver()) {
            throw new IllegalArgumentException("ikke mulig å flytte til høyre");
        }
        for (int x = 0; x < brett.length; x++) {
            for (int y = 3; y >= 0; y--) {
                ny = 3;// ny y-verdi
                if (ny >= y) // hvis den nye y-veriden er større enn den opprinnelige y-verdien, betyr det at
                             // plassen er lenger til høyre
                             // da er det altså mulig ¨å flytte brikken til høyre
                {

                    BevegeBrikkerHorisontalt(x, y, "høyre");

                }
            }
        }
    }

    // denne metoden endrer verdien på brikker ut ifra hvilken retning som er valgt.
    // Hvis den nye brikken er 0, eller har lik verdi som brikken man skal bevege
    // betyr det at man kan flytte den opprinnelige brikken til den nye plassen
    // hvis ny brikke er 0, vil den få samme verdi som den opprinnelige brikken
    // hvis den nye brikken og den brikken man flytter har lik verdi, vil de to
    // veridene til brikken slå seg sammen
    private void BevegeBrikkerHorisontalt(int x, int y, String retning) {
        // må først sjekke om det er et gydlig trekk
        if (!isBrikke(x, y) || !gyldigRetning.contains(retning)) {
            throw new IllegalArgumentException("ikke gyldig bevegelse");
        }
        // sjekker om brikke som flytts mot har 0 eller lik verdi som den opprinnelige
        // brikken
        if ((brett[x][ny].getVerdi() == 0 || brett[x][ny].getVerdi() == brett[x][y].getVerdi())) {

            if (y > ny && (retning.equals("venstre")) || (retning.equals("høyre") && (y < ny))) {

                boolean Zero = (retning.equals("venstre"))
                        ? IntStream.range(ny + 1, y).boxed().allMatch(p -> brett[x][p].getVerdi() == 0) // sjekker for
                                                                                                        // bevegelse mot
                                                                                                        // venstre
                        : IntStream.range(y + 1, ny).boxed().allMatch(p -> brett[x][p].getVerdi() == 0); // sjekker for
                                                                                                         // beveglse mot
                                                                                                         // høyre
                if (Zero) {
                    // hvis det bare er 0 mellom brikkene slår vi sammen den nye og gamle brikken
                    // sin verdi
                    int Score = brett[x][ny].getVerdi() + brett[x][y].getVerdi();
                    if (brett[x][ny].getVerdi() != 0) {
                        score += Score;
                    }
                    brett[x][ny].setVerdi(Score);
                    brett[x][y].setVerdi(0);// gammel brikke får 0 som verdi

                    // hvis det ikke alle brikker mellom har 0 som verdi, må man gå igjennom brikke
                    // for brikke for å sjekke hvor langt opp det er mulig å flytte en brikke
                    // oppover i brettet
                } else {
                    ny = (retning.equals("venstre")) ? ny + 1 : ny - 1;
                    BevegeBrikkerHorisontalt(x, y, retning);
                }
            }
            // går igjennom alle brikker enten opppver eller nedover på brettet ved å trekke
            // fra eller plusse på variabel "ny" i metodekallene, slik at man får gått
            // igjennom alle brikkene på brettet for å eneten bevege de til venstre eller
            // høyre
        } else {
            ny = (retning.equals("venstre")) ? ny + 1 : ny - 1;

            BevegeBrikkerHorisontalt(x, y, retning);
        }

        // lagrer bretett sitt tilstand etter hver gang man har gjort en handling
        // (flyttet på brikker)
        save.save(filename, this);

    }

}