package tikape;

/**
 *
 * @author User
 */
import java.util.Date;

/**
 *
 * @author User
 */
public class Henkilö {

    private String nimi, kuvaus, ammatti;
    private Date syntymaaika;
    private int id;

    public Henkilö(int id, String nimi, String kuvaus, String ammatti, Date syntyma) {
        this.id = id;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.ammatti = ammatti;
        this.syntymaaika = syntyma;
    }

    public int getId() {
        return this.id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
 
    public String getKuvaus() {
        return this.kuvaus;
    }
        
    @Override
    public String toString(){
        return this.nimi+", ammatti: "+ammatti+", syntynyt: "+syntymaaika+", kuvaus: "+kuvaus;
    }
    

}
