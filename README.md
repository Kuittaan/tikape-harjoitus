# tikape-harjoitus

Repo Helsingin yliopiston Tietokantojen perusteet -kurssin ryhmätyötä varten

Tietokannan schema:

    CREATE TABLE Henkilö (id integer PRIMARY KEY NOT NULL, nimi varchar(50) NOT NULL, syntymäaika date NOT NULL, ammatti varchar(100) NOT NULL, kuvaus text NOT NULL);
    CREATE TABLE Ystävyys (a_id integer, b_id integer, FOREIGN KEY (a_id) REFERENCES Henkilö(id), FOREIGN KEY (b_id) REFERENCES Henkilö(id));

