package tikape;

/**
 *
 * @author User
 */
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author User
 */
public class HenkiloDao {

    private String database;

    public HenkiloDao(String database) {
        this.database = database;

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(database);
    }

    public List<Henkilo> haeKaikkiHenkilot() throws SQLException {
        ArrayList<Henkilo> henkilot = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT*FROM Henkilö");
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            henkilot.add(new Henkilo(result.getString("nimi"), result.getString("kuvaus"), result.getString("ammatti"), result.getDate("syntymäaika")));
        }
        stmt.close();
        result.close();
        connection.close();
        return henkilot;
    }

    public Henkilo haeHenkilo(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT*FROM Henkilö WHERE Henkilö.id=?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
              stmt.close();
        connection.close();
            return null;
        }

        Henkilo h = new Henkilo(rs.getString("nimi"), rs.getString("kuvaus"), rs.getString("ammatti"), rs.getDate("syntymäaika"));
        stmt.close();

        connection.close();
        return h;
    }

    public List<Henkilo> haeHenkilonYstavat(int id) throws SQLException {
        ArrayList<Henkilo> ystavat = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT*FROM Henkilö WHERE id IN(SELECT b_id FROM Ystävyys,Henkilö WHERE a_id=id and id=?);");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ystavat.add(new Henkilo(rs.getString("nimi"), rs.getString("kuvaus"), rs.getString("ammatti"), rs.getDate("syntymäaika")));
        }
        stmt.close();
        rs.close();
        connection.close();
        return ystavat;
    }

    public List<Integer> haeHenkilonYstavienId(int henkilonId) throws SQLException {
        ArrayList<Integer> idt = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT id FROM Henkilö WHERE id IN(SELECT b_id FROM Ystävyys,Henkilö WHERE a_id=id and id=?)");
        stmt.setInt(1, henkilonId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            idt.add(rs.getInt("id"));
        }
        return idt;
    }

    public void luoUusiHenkilo(String nimi, Timestamp syntymaaika, String ammatti, String kuvaus) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Henkilö(nimi,syntymäaika,ammatti,kuvaus) VALUES (?,?,?,?)");
        stmt.setString(1, nimi);
        stmt.setTimestamp(2, syntymaaika);
        stmt.setString(3, ammatti);
        stmt.setString(4, kuvaus);
        stmt.execute();
        stmt.close();
        connection.close();
    }

    public void tuhoaHenkiloJaYstavyysSuhteet(int henkilonId) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Ystavyys WHERE a_id=? OR b_id=?");
        stmt.setInt(1, henkilonId);
        stmt.setInt(2, henkilonId);
        stmt.execute();
        stmt = connection.prepareStatement("DELETE FROM Henkilö WHERE id=?");
        stmt.setInt(1, henkilonId);
        stmt.execute();
        stmt.close();
        connection.close();
    }

    public void luoYstävyysSuhde(int a_id, int b_id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Ystävyys (a_id,b_id) VALUES(?,?)");
        stmt.setInt(1, a_id);
        stmt.setInt(2, b_id);
        stmt.execute();
        stmt.close();
        connection.close();
    }

    public void poistaYstavyys(int a_id, int b_id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Ystävyys WHERE a_id=? AND b_id=?");
        stmt.setInt(1, a_id);
        stmt.setInt(2, b_id);
        stmt.execute();
        stmt.close();
        connection.close();
    }

}
