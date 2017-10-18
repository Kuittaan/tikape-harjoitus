/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import java.util.Date;

/**
 *
 * @author User
 */
public class Henkilo {

    private String nimi, kuvaus, ammatti;
    private Date syntymaaika;

    public Henkilo(String nimi, String kuvaus, String ammatti, Date syntyma) {
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.ammatti = ammatti;
        this.syntymaaika = syntyma;
    }
    
    @Override
    public String toString(){
        return this.nimi+", ammatti: "+ammatti+", syntynyt: "+syntymaaika+", kuvaus: "+kuvaus;
    }
    

}
