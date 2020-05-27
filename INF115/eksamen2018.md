# Oppgave 1
## a)
``` SQL 
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
    CONSTRAINT HyttePK PRIMARY KEY (HNr)
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

# Oppgave 2
``` 
person(PNr, Fornavn, Etternavn, e-post, passord)
oppdrag(Oppdragnummer, makspris, minpris)
oppdragType(Type, beskrivelse)
bud(BNr, Pris)
kommune(KNr, Navn)
fylke(FNr, Navn)
```
<img src="../assets/Untitled.png">

# Oppgave 3
## a)
``` SQL
SELECT Pris FROM Hytte, Tur
WHERE HNr=StartHytte
AND HytteType="Selvbetjent" 
AND Beskrivelse="Brevandring";
```

## b)
Original:

$π Pris (σHytteType='Selvbetjent',Beskrivelse='Brevandring' Hytte⊗HNr=StartHytte Tur))$

Min versjon:

$π Pris (σHytteType='Selvbetjent'(σBeskrivelse='Brevandring'(Hytte⊗HNr=StartHytte Tur)))$

## c)
``` 
π Pris
|
σ HytteType='Selvbetjent',Beskrivelse='Brevandring'
|
⊗ HNr=StartHytte
|   \
|    \
|     \
Hytte, Tur
```

```
π Pris 
|
σ HytteType='Selvbetjent'
|
σ Beskrivelse='Brevandring'
|
⊗ HNr=StartHytte
|  \
|   \
|    \
Hytte, Tur
```

# Oppgave 4
``` SQL
TurUtvidet(TNr, Beskrivelse, Pris, Dato, Hytte)
TurUtvidet(1, "Krevende topptur", 7500, "2018-04-28", 1)
```
Hva som er uheldig med denne tabellen:

Den viser repetitiv informasjon, pris og beskrivelse forekommer flere ganger og fører til dobbeltlagring.

**Funksjonelle avhengigheter:**
- TNr &rarr; Beskrivelse
- TNr &rarr; Pris
- TNr+dat &rarr; Hytte

**Kandidatnøkkel:**
- TNr +  Dato

**Normalisering:**
- TurUtvidet(#TNr, Beskrivelse, Pris)
- Beskrivelse(#TNr, Dato*, Hytte)

# Oppgave 5
- Indeks: 
  - En indeks er lik stikkordregisrert i en bok: Den består av søkenøkler og pekere
  - En indeks er en datastruktur som bidrar til å effektivisere søk
  - Kolonner som er med i mange søkebetingelser
  - Kolonner som inngår i koblingsbetingelser, spesielt fremmednøkler.
- Hva er en visning (utsnitt)?
  - En virtuell tabell som kan være skjult for sluttbruker
  - Blir definert som et utvalgsspørring
- Hva er en lås i sammenheng med databaser?
  - Ventemekanisme som blir styrt av en transaksjon
  - Vranglås er når en prosess venter på en annen prosess som venter på den andre (Dining philosophers)
- Fire egenskaper en transaksjon bør ha
  1.  Atomicity
  2.  Consistency
  3.  Isolation
  4.  Durability
- Hvorfor kalles PHP et skriptspråk?
  - Fordi det samhandler med andre programmer
  - produserer deler av programmer
- Hva har "regelbasert optimalisering" og XSLT til felles?
  - Begge jobber med tre strukturer
  - Begge bygger på regler som beskriver transformasjoner av tre strukturer

# Oppgave 6
- baba, aba og abb
  - %ab_
- Fremmednøkkel
  - Den må enten være null, eller inneholde en verdi som finnes i tilhørende primærnøkkel
- ``` SQL
  SELECT MAX(Pris) FROM Tur WHERE StartHytte=1
  ```
  - En kolonne med 4 verdien 6500
- ``` SQL
  SELECT * FROM Tur, Hytte WHERE Tur.StartHytte = Hytte.HNr
  ```
  - 4
- ``` SQL
  SELECT * FROM Medlem LEFT OUTER JOIN Påmelding 
  ON Medlem.MNr = Påmelding.MNr
  ```
  - 8
- Hvordan blir et en-til-mange forhold mellom entitetene A og B replresentert i databasen?
  - Det blir lagt til en fremmednøkkel i B
- Hva er korrekt?
  - En primærnøkkel er også en kandidatnøkkel
- Hva er redundans?
  - Informasjon som blir gjentatt
- Hva er en svak entitet?
  - En entitet som arver identifikator (primærnøkkel) fra en annen entitet
- Hva menes med at PHP brukes til å lage dynamiske nettsider?
  - Nettsidene blir generert av et program for hver forespørsel