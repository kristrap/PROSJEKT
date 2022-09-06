package prosjektvaar;

import java.io.FileNotFoundException;

public interface ISaveBrettHandler {

    public void save(String filename, Brett brett) throws FileNotFoundException;

    public int loadScore(String filename) throws FileNotFoundException;

    public Brikke[][] load(String filename) throws FileNotFoundException;

    public boolean brukerHarSpill(String filename) throws FileNotFoundException;

    public void saveName(String filename, String navn) throws FileNotFoundException;

    public String loadName(String filename) throws FileNotFoundException;

    public String loadSaveName(String filename)throws FileNotFoundException;





}
