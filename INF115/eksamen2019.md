# Oppgave1
## a)
``` SQL
SELECT * FROM ContainerType 
WHERE MaxVekt > 5000 
AND dagpris < 1800;
```

## b)
``` SQL
SELECT * FROM Oppdrag 
WHERE FraDato = "2019-12-17" OR TilDato = "2019-12-19";
```

## c)
``` SQL
SELECT TypeID, TypeNavn, count(*) FROM ContainerType, Container 
WHERE ContainerType.TypeID = Container.TypeID;
```

## d)
``` SQL
CREATE TABLE Oppdrag(
    ONr INT AUTO_INCREMENT,
    Tlf VARCHAR NOT NULL, 
    CNr INT NOT NULL, 
    FraDato DATE NOT NULL,
    TilDato DATE,
    PRIMARY KEY(ONr),
    FOREIGN KEY(Tlf) REFERENCES Kunde(Tlf),
    Foreign key(CNr) REFERENCES Container(CNr));
```

## e)
``` SQL
INSERT INTO Kunde("90190190", "TotallyAnAddress 123");
INSERT INTO Oppdrag(Tlf, CNr, FraDato, TilDato) 
Values("90190190", 1337, "2020-12-12", "2020-12-30");
```

## f)
``` SQL
CREATE VIEW minView(Kunde, Pris) AS SELECT K.Tlf, SUM(*DATEDIFF(TilDato, FraDato)*Dagpris) FROM Kunde AS K, Oppdrag AS O, Container AS C, ContainerType AS T 
WHERE K.Tlf = O.Tlf
AND O.Cnr = C.Cnr
AND C.TypeId = T.TypeId
GROUP BY O.TLF;  
```

# Oppgave2
``` SQL
SKIP(Navn, MaksPass)

Passasjer(Pnr, epost, fornavn, etternavn, kjønn, fødselsdato, tlf)

Pass_has_reserv(pass_pnr, Res_nr, kabin_dekk, kabin_løpnr, kabin_skipnavn, døgnpris)

Reservasjon(Resnr, CruiseNr)

cruise(Cnr, Start_dato, Skip_navn)

kabin(dekknr, løpenr, kat, skip_navn)

kategori(katnr, antall_senger, døgnpris)

opphold(cruise_cnr, havn_hnr, ankomst_dato, avreise_dato)

havn(hnr, bynavn, telefon)
```

# Oppgave3
## a)
``` SQL
SELECT ONr 
FROM Oppdrag, Container 
WHERE Oppdrag.CNr = Container.CNr 
AND Tlf="12341234" 
AND TypeId="2";
```
## b)
$πONr(σTlf="12341234"(σTypeId="2"(Skadesak⊗Oppdrag.CNr=Container.CNrContainer)))$

$πONr(σTlf="12341234"(Oppdrag)⊗Oppdrag.CNr=Container.CNrσTypeId="2"(Container))$

## c)
**Operatortre:**   
<img src=../assets/operatortre.png>

# Oppgave4
Hvorfor løsningen er problematisk: 
- Tabellen inneholder informasjon som er unødvendig å vise flere ganger, som ```MaksVekt``` og ```RegAar``` som blir unødvendig å vise i tabellen.

``` SQL
Mitt forslag:

Modell(#Modell, MaxVekt)  
Bil(#RegNr, Modell*, ONr*)   
Aarstall(#RegNr*, RegAar)
```

``` SQL
Løsningsforslag:

Modell(#Modell, MaxVekt)
Bil(#RegNr, RegAar, Modell*)   
Oppdrag(ONr*, RegNr*)   
```

# Oppgave5
1. Regelbasert optimalisering
   1. Skyv seleksjon og projeksjoner nedover i treet hvis det er mulig
   2. Bytt om på likekoblinger for å minske størrelsen på mellomresultater
   3. Bryt ned sammensatte seleksjoner i flere enkle
2. Tapsi dekomposisjon
   1. Natural join vil aldri gi opphav til falske tupler, $2/2=1$ -> $1+1=2$
3. Horisontal og vertikal fragmentering
   1. Horisontal: Bryt opp tabellen vannrett(Rader)
   2. Vertikal: Bryt opp tabellen loddrett(Kolonner)
4. Hva bør vi være obs på ved vertikal fragmentering?
   1. Primærnøkkel må være med i *alle* fragmenteringer
5. Tre krav til "velformet" XML-dokument
   1. Kun *et* rotelement
   2. Alle elementer har et start og sluttmerke
   3. Elementer er nøstet korrekt
6. Vi stiller disse kravene fordi da kan vi representere XML-dokumentet som et tre

# Oppgave6
1. d) "a%b" matcher "abccba"
2. b) Vi velger en kandidatnøkkel som primærnøkkel
3. c) Alle verdier i fremmednøkkelen må finnes i tilhørende primærnøkkel
4. d) 12
5. c) 4
6. a) Skriptet blir utført på web-tjeneren og utdata blir sendt til nettleseren
7. b) vis telefon og antall oppdrag for samtlige kunder, også de uten oppdrag
8. d) Et utsnitt er en virtuell tabell
9. d) Flere transaksjoner kan ha leselås på samme rad samtidig
10. c) Tofaselåsing garanterer serialiserbare forløp