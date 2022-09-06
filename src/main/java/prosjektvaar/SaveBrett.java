package prosjektvaar;

//hjelpe-klasse som gjør det lettere å endre og hente tilstand til spill
//brukes for lagring og skriving til fil

public class SaveBrett {

    private int score; //score til spiller
    private String navn; //navn på spiller 
    private String[] brett; //inneholder verdiene til brettet 



    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getNavn() {
        return navn;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }
    public String[] getBrett() {
        return brett;
    }
    public void setBrett(String[] brett) {
        this.brett = brett;
    }

    
    


    

    
    
    
}
