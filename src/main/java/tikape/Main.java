package tikape;

import spark.Spark;
import java.sql.*;

import tikape.*;

public class Main {
    public static void main(String[] args) {
	HenkilöDao dao = new HenkilöDao("jdbc:sqlite:db/Ystavia.db");
	Spark.get("/", (req, res) -> {
		res.redirect("/persons/list");
	    });
	Spark.get("/persons/list", (req, res) -> {
		List<Henkilö> henkilöt = dao.haeKaikkiHenkilöt();
		// html
	    });
	Spark.get("/persons/:id", (req, res) -> {
		Henkilö h = dao.haeHenkilö(Integer.parseInt(req.params("id")));
		// html
	    });
	Spark.post("/persons/new", (req, res) -> {
		try {
		    String nimi = req.queryParams("nimi");
		    String ammatti = req.queryParams("ammatti");
		    String kuvaus = req.queryParams("kuvaus");
		    Timestamp syntynyt = Timestamp.valueOf(req.queryParams("syntynyt"));
		    dao.luoUusiHenkilö(nimi, syntynyt, ammatti, kuvaus);
		    res.redirect("/persons/list");
		} catch (Exception e) {
		    e.printStackTrace();
		    // virhe
		}
	    });
	Spark.post("/persons/new_friend", (req, res) -> {
		try {
		    int a_id = Integer.parseInt(req.queryParams("a_id"));
		    int b_id = Integer.parseInt(req.queryParams("b_id"));
		    dao.luoYstävyyssuhde(a_id, b_id);
		    res.redirect("/persons/" + a_id);
		} catch (Exception e) {
		    e.printStackTrace();
		    // virhe
		}
	    });
	Spark.post("/persons/delete", (req, res) -> {
		try {
		    int id = Integer.parseInt(req.queryParams("id"));
		    dao.tuhoaHenkilöJaYstävyyssuhteet(id);
		    res.redirect("/persons/list");
		} catch (Exception e) {
		    e.printStackTrace();
		    // virhe
		}
	    });
	Spark.post("/persons/remove_friend", (req, res) -> {
		try {
		    int a_id = Integer.parseInt(req.queryParams("a_id"));
		    int b_id = Integer.parseInt(req.queryParams("b_id"));
		    dao.poistaYstävyys(a_id, b_id);
		    res.redirect("/persons/"+a_id);
		} catch (Exception e) {
		    e.printStackTrace();
		    // virhe
		}
	    });
    }
}
