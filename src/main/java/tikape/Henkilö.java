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
    private int id;

    public Henkilö(int id, String nimi, String kuvaus, String ammatti) {
        this.id = id;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.ammatti = ammatti;
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
 
    public String getAmmatti() {
        return this.ammatti;
    }
}
