package tikape;

import spark.*;
import java.sql.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.*;


import tikape.*;

public class Main {
    public static void main(String[] args) {
        HenkilöDao dao = new HenkilöDao("jdbc:sqlite:db/Ystavia.db");
        Spark.staticFiles.location("/public");
        Spark.get("/", (req, res) -> {
                res.redirect("/persons/list");
                return "";
            });
        Spark.get("/persons/list", (req, res) -> {
                List<Henkilö> henkilöt = dao.haeKaikkiHenkilöt();
                HashMap map = new HashMap();
                map.put("persons", henkilöt);
                return new ModelAndView(map, "list");
                
                // html
            } , new ThymeleafTemplateEngine());

        Spark.get("/persons/:id", (req, res) -> {
                List<Henkilö> henkilöt = dao.haeKaikkiHenkilöt();
                Henkilö h = dao.haeHenkilö(Integer.parseInt(req.params("id")));
                List<Henkilö> ystävät=dao.haeHenkilönYstävät(h.getId());
                List<Integer> idt=dao.haeHenkilönYstävienId(h.getId());
                HashMap map = new HashMap();
                map.put("friends",ystävät);
                map.put("friend_ids",idt);
                map.put("person", h);
                map.put("persons", henkilöt);
                return new ModelAndView(map, "person");
            }, new ThymeleafTemplateEngine());
        Spark.post("/persons/new", (req, res) -> {
                try {
                    String nimi = req.queryParams("nimi");
                    String ammatti = req.queryParams("ammatti");
                    String kuvaus = req.queryParams("kuvaus");
                    dao.luoUusiHenkilö(nimi, ammatti, kuvaus);
                    res.redirect("/persons/list");
                } catch (Exception e) {
                    e.printStackTrace();
                    // virhe
                }
                return "";
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

                return "";
            });
        Spark.post("/persons/delete", (req, res) -> {
                try {
                    int id = Integer.parseInt(req.queryParams("pid"));
                    dao.tuhoaHenkilöJaYstävyyssuhteet(id);
                    res.redirect("/persons/list");
                } catch (Exception e) {
                    e.printStackTrace();
                    // virhe
                }
                return "";
            });
        Spark.post("/persons/remove_friend", (req, res) -> {
                try {
                    int a_id = Integer.parseInt(req.queryParams("a_id"));
                    int b_id = Integer.parseInt(req.queryParams("b_id"));
                    dao.poistaYstävyys(a_id, b_id);
                    res.redirect("/persons/" + a_id);
                } catch (Exception e) {
                    e.printStackTrace();
                    // virhe
                }
                return "";
            });
    }
}
