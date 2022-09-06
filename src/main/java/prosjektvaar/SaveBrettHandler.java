package prosjektvaar;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SaveBrettHandler implements ISaveBrettHandler {

    // skal alltid hente og lagre i denne folderen
    private final static String SAVE_FOLDER = "./src/main/resources/prosjektvaar/";

    //hjelpemetode: gjør det lettere å hente ut brettet
    // tar inn tilstanden til et brett som er gitt på fomatet i filen den blir
    // lagret/hentet fra
    private SaveBrett stringToSaveSpill(String tilstand) {
        String[] resultat = tilstand.split("\\."); // splitter tilstanden slik at vi får score, navn, og brett-verdiene
                                                   // hver for seg
        SaveBrett sb = new SaveBrett(); // brukes for å kunne sette tilstandene

        sb.setScore((int) Integer.parseInt(resultat[0].split(":")[1]));

        sb.setNavn(resultat[1].split(":")[1]);

        sb.setBrett(resultat[2].split(";"));

        return sb; // returnerer det lagrede brettet/tilstanden til brettet

    }

    //hjelpemetode: gjør det lettere å lagre til fil 
    // tar inn et brett og gjør det om til en String, lik formatet som blir lagret
    // til fil
    private String spillToString(SaveBrett brett) {
        String resultat = ""; // starter med en tom String der alt bli rlagt til
        resultat += "Score:" + Integer.toString(brett.getScore()) + "."; // henter score til brettet
        resultat += "Name:" + brett.getNavn() + "."; // henter navnet på spilleren
        resultat += Arrays.asList(brett.getBrett()).stream().collect(Collectors.joining(";")); // sorterer alle
                                                                                               // brikkeverdiene og
                                                                                               // formerer til rikitg
                                                                                               // format mellom hver
                                                                                               // verdi

        return resultat;

    }

    //hjelpemetode: gjør det lettere å lagre brett
    // gjør om brettet til Array, som gjr det lettere å lagre brettet på rikitg
    // format
    private String[] brettToArray(Brett brett) {
        List<String> brikker = new ArrayList<>(); // lager en liste som inneholder alle brikke-verdiene til brettet
        for (int x = 0; x < 4; x++) { // går igjennom hver brikke og legger til i listen som skal inneholde verdiene
            for (int y = 0; y < 4; y++) {
                brikker.add(Integer.toString(brett.getBrikke(x, y)));
            }
        }
        return brikker.toArray(new String[0]); // gjør om til en array, siden det er slik vi skal bruke den senere
    }

    //hjelpemetode: gjør det lettere å hente ut brett
    // gjør om posisjonene i array, og setter de som brikker
    private Brikke[][] arrayToBrikke(String[] array) {
        Brikke[][] brett = new Brikke[4][4]; // lager nytt brett

        // henter verdiene fra array og gjør det om til en liste med tall
        List<Integer> brikkeverdier = Arrays.asList(array).stream().map(a -> Integer.parseInt(a)).toList();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                // brikkene får de gitte verdiene ut i fra posisjonen i listen som inneholder
                // verdiene
                brett[y][x] = new Brikke(brikkeverdier.get(y * 4 + x));

            }
        }
        return brett; // returnerer brettet bestående av brikker med riktige verider

    }

    // henter alle de lagrede spill-tilstandene fra filen, der alle tilstander for
    // alle brett blir lagret
    private List<SaveBrett> loadBoards(String filename) {
        try (Scanner scanner = new Scanner(new FileReader(SAVE_FOLDER + filename))) {
            String file = "";
            while (scanner.hasNext()) {
                file += scanner.next();
            }
            return Arrays.asList(file.split("-")).stream().map(s -> stringToSaveSpill(s)).collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Filen ble ikke funnet");
        }

    }

    //henter navnet i filen
    @Override
    public String loadName(String filename) {
        try (Scanner scanner = new Scanner(new FileReader(SAVE_FOLDER + filename))) {

            //det første som blir hentet 
            String navn = scanner.next();

            return navn;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Filen ble ikke funnet");
        }
    }

    // sjekker om brukeren har spill fra før
    // det betyr at vi sjekker om navnet som blir skrevet inn når spillet starter
    // allerde har en lagret spilltilstand fra tidligere
    @Override
    public boolean brukerHarSpill(String filename) {
        String name = loadName("Navn.txt"); // henter navnet som blir skrevet inn da man åpner spillet
        return loadBoards(filename).stream().anyMatch(sb -> sb.getNavn().equals(name)); // går igjennom alle lagrede
                                                                                        // brett-tilstander i filen og
                                                                                        // sjekker om navnet som blir
                                                                                        // skrevet inn er likt med et av
                                                                                        // de lagrede navnene i filen
    }

    // Henter rikitg spill-tilstand i forhold til navnet som blir skrevet inn da mn
    // starter spillet
    private SaveBrett getCurrentSpill(String filename) {
        String name = loadName("Navn.txt"); // henter navnet som blir skrevet inn da man starter spillet

        // går igjennom alle lagrede spill og sjekker om navnet som blir skrevet inn
        // matcher med et av de lagrede spillene
        // hvis dette ikke er tilfellet blir det kastet et unntak
        return loadBoards(filename).stream().filter(sb -> sb.getNavn().equals(name)).findAny().orElseThrow();
    }

    // lagrer tille brett til fil
    @Override
    public void save(String filename, Brett brett) {
        List<SaveBrett> saveBoards = loadBoards(filename); // starter med å lage en liste med alle
                                                                   // brett-tilstander som allerde er lagret i filen

        if (saveBoards.stream().anyMatch(sb -> sb.getNavn().equals(brett.getName()))) {// sjekker om navnet på brettet
                                                                                       // som skal lagres allerde finnes
                                                                                       // i filen

            // hvis dette stemmer henter man ut navnet
            SaveBrett saveBoard = saveBoards.stream().filter(sb -> sb.getNavn().equals(brett.getName()))
                    .findAny()
                    .get();

            SaveBrett original = null;

            // deretter går man igjennom alle tilstandene og finner spillerens tildigre
            // spill-tilstand
            for (SaveBrett sb : saveBoards) {
                if (sb.getNavn() == saveBoard.getNavn()) {
                    original = sb;
                }
            }
            // til slutt lagrer man det nye tilstandne til brette, siden den har endret seg
            // det vil si at både score og brikkeverdier blir endret, siden spiller har
            // gjort nye trekke siden forrige spill
            original.setBrett(brettToArray(brett));
            original.setScore(brett.getScore());

            // hvis spilleren ikke har spilt noe tildigre, blir det laget en ny
            // spill-tilstand, med nytt navn, score og brett som blir lagt til i listen med
            // alle spill-tilstander
        } else {

            SaveBrett saveBoard = new SaveBrett();
            saveBoard.setScore(brett.getScore());
            saveBoard.setNavn(brett.getName());
            saveBoard.setBrett(brettToArray(brett));
            saveBoards.add(saveBoard);

        }

        //til slutt lagres alle brett tilstandene til filen , enten de er oppdatert eller det er lagt til en ny 
        try (PrintWriter writer = new PrintWriter(SAVE_FOLDER + filename)) {

            //for å skille mellom hver spill-tilstand/spiller, skiller vi dette med "-" når vi lagrer til filen 
            //alle tilstandene blir lagret som String 
            writer.print(saveBoards.stream().map(sb -> spillToString(sb)).collect(Collectors.joining("-")));

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Filen ble ikke funnet");

        }

    }

    //lagrer navnet til spiller i filen
    //skal være på det første som blir lagret i filen
    @Override
    public void saveName(String filename, String navn) {
        try (PrintWriter writer = new PrintWriter(SAVE_FOLDER + filename)) {
            writer.println(String.format(navn)); 

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File ble ikke funnet");

        }

    }


    //henter bare navnet i filen 
    @Override
    public String loadSaveName(String filename) {
        return getCurrentSpill(filename).getNavn();
    }

    //hneter bare score i filen 
    @Override
    public int loadScore(String filename) {
        return getCurrentSpill(filename).getScore();
    }

    //henter bare brettet i filen 
    @Override
    public Brikke[][] load(String filename) {
        return arrayToBrikke(getCurrentSpill(filename).getBrett());
    }

}
