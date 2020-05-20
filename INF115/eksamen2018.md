# Oppgave1

## a)
```SQL 
SELECT * FROM Tur 
WHERE Pris < 8000
AND MONTH(StartDato) = 7;
```

## b)
``` SQL
SELECT * FROM Medlem, Påmelding, Tur
WHERE Medlem.MNr = Påmelding.MNr
AND Påmelding.TNr = Tur.Tnr
AND Tur.StartHytte = 2;
```
## c)
``` SQL
SELECT TNr, Beskrivelse, StartDato, count(*) FROM Tur
WHERE Tur.TNR = Påmelding.TNr
GROUP BY Tur.TNr;
```

## d)
``` SQL
CREATE TABLE Hytte(
    HNr INT AUTO_INCREMENT,
    Navn VARCHAR NOT NULL,
    AntSenger INT,
    HytteType VARCHAR,
    CONTRAINT HyttePK PRIMARY KEY (HNr)
)
```

## e)
``` SQL
INSERT INTO Medlem(Fornavn, Etternavn, TLF)
VALUES("Ola", "Nordmann", "81520030");

INSERT INTO Påmelding(TNr, MNr)
VALUES(3, LAST_INSERT_ID());
```

## f)
``` SQL
SELECT * FROM Medlem
WHERE Medlem.MNr NOT IN(SELECT MNr FROM Påmelding);
```