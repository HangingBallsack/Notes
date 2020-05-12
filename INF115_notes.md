# Ukesoppgaver 1
1. The total number of films in the table   
   ```SQL
    SELECT count(Fnr) FROM film 
    ```
2. All information on films produced in 1982
   ```SQL
   SELECT * FROM film WHERE Year = 1982
   ```
3. The title of American films produced in the 1980s
   ```SQL
    SELECT Title FROM film WHERE Country = "USA" AND Year BETWEEN 1980 AND 1989;
   ```
4. All films less than 40 years old with a run time under 130 minutes   
   ```SQL
    SELECT Title FROM film WHERE Year > (YEAR(current_timestamp())-40) AND Run_time < 130;
    ```
5. The title of all films directed by James Cameron or George Lucas   
   ```SQL
    SELECT Title From film WHERE Director = "James Cameron" OR Director = "George Lucas";
    ```
6. All manufacturing countries, sorted and without repitition   
   ```SQL
    SELECT distinct Country FROM film ORDER BY Country ASC;
    ```
7. The number of films that are not for sale   
   ```SQL
    SELECT count(Price) FROM film;
    ```
8. The number of films under 100 kroner   
   ```SQL
    SELECT count(Price) FROM film;
    ```
9.  Films with a title beginning with "The"
    ```SQL
    SELECT Title FROM film WHERE Title LIKE "The%";
    ```
10. The average price for films belonging to directors with more than 2 movies
    ```SQL
    SELECT AVG(Price) FROM film GROUP BY Director having count(*) >= 2;
    ```
11. The difference between the most expensive and cheapest film from each country   
    ```SQL
    SELECT MAX(Price)-MIN(price) FROM film GROUP BY Country;
    ```
12. The number of films for sale, broken down by country of production
    ```SQL
    SELECT country, count(Title) Antall FROM film GROUP BY Country;
    ```
13. The shortest and longest runtime from each decade   
    ```SQL
    SELECT floor(year/10)*10 Decade, MIN(Run_time) Minst, MAX(Run_Time) Lengst FROM film GROUP BY floor(Year/10);
    ```

# Generelt
|Begrep | Forklaring|
|:----|:---|
|Database |En logisk samling av data **(opplysninger)** |
|Databasesystem | = DataBaseHåndteringsSystem + database. |
|Databasehåndteringssystem| MySQL, MariaDB, Access, Oracle, PostgreSQL, SQL Server..| 

## Programmeringspråk
- Kristen Nygaard og Ole-Johan Dahl har fått Turing Award for arbeid med objektorientert programmering, uttrykt i Simula
- Objekt-orientert programmeringsspråk 
  - Simula (Regnes som det første)
  - Smalltalk
  - Java
  - Javascript 
  - ...
- Ikke objeckt-orienterte programmeringsspråk 
  - C
  - BASIC
  - PASCAL
  - FORTRAN
- Forskjellen på en **interpreter** og en **kompilator**: 
  - En kompilator oversetter kildespråk om til objecktspråk (konverterer hele programmet til kjørbar kode på *en* gang)
  - En Interpreter er et program som imiterer kjøringen av programmet skrevet i kildespråket (konverterer programmet linje for linje til kjørbar kode)

## Data representations
 - *ASCII* er en 7-bit kode, som betyr at den inneholder **128** karakterer
 - 2 bytes kan representere $2^{16} bits = 65536-1 = 65535$
 - For å representere $3$ heksadesimale tall så trenger vi $3(tall)*4(biter)$.

# SQL
- Primærnøkkel er identifikatoren til en rad i en database
- Fremmednøkkel brukes til å linke to tabeller sammen, refererer til primærnøkkelen i en annen tabell
- Siden fremmednøkler kobler flere tabeller sammen så hjelper de ved å koble flere tabeller sammen, som ``` Member.name ``` og ```Person.name``` er samme person i to forskjellige tabeller.
- Nullmerker er *ikke* verdier, men *tom*rom som kan skyldes: 
  - Vi har glemt å registrere data
  - vi k jenner ikke til den korrekte verdien
  - Det gir ikke mening å registrere data
- ```Pattern Matching``` er når du ikke vet riktig ord eller lignende når du søker. **Eksempel:** Du søker etter ```WHERE last_name Like 'C%' ``` når du vet det begynner med ```C```.
- Datatype: ```INT(x), CHAR(x), DECIMAL(x), VARCHAR(x), DATE, BOOLEAN, INTERVAL ...```
- ```VARCHAR``` er dynamisk og ```CHAR``` er statisk lengdemessig, ```CHAR``` er raskere enn ```VARCHAR``` men kan holde betraktelig mindre informasjon ($255$ mot $65,535$)
- ```SMALLINT```: $-2^{15}(-32,768)$ til $2^{15-1}(32,768)$\
```INTEGER```: $-2^{31} (-2,147,483,648)$ til $2^{31-1} (2,147,483,647)$
- Å bruke prefix i kolonnenavn 'trenger' vi for å differensiere mellom f.eks kolliderende tabeller som begge inneholder f.eks ```person_name, company_name```
- Forretningsregel: Er en form for "contraint" som at f.eks ```Fødselsdato < AnsattDato``` og at ```Lønn < MaksLønn```
- Utsnitt (views):
  - Er en "virtuell" tabell
    - Bruker ikke aksess til visse tabeller
    - oppdeling ikke synlig for bruker
    - kan endre tabell definisjoner uten at bruker blir berørt
    - ```CREATE VIEW <utsnittsnavn> (<kolonner>) AS <utvalgsspørring uten ORDER BY> ```
  - **Eksempel:** 
  ``` SQL
  CREATE VIEW Keramikk( Varekode, Navn ) AS
      SELECT VNr, Varenavn
      FROM Vare
      WHERE KategoriNr= 5
  ```
  - Spørring mot utsnitt:
    - ```SELECT * FROM Keramikk WHERE Antall < 5 ```

# Relasjonsmodellen og relasjonsalgebra
TODO: fyll inn

# Datamodellering og databasedesign
## E/R diagram
- En "svak identitet" er en entitet som ikke kan bli identifisert av kun attributtene, og må bruke en fremmednøkkel sammen med attributene for å skape en primærnøkkel.
- E/R diagrammer bør ikke ha fremmednøkler fordi ER er ikke det samme som relations-diagram hvor de som oftest er representert. E/R diagram har streker med egenskaper mellom seg som presenterer nøkler
- ```Subtype```: spesialisering av en annen entitet.
  - Arver egenskapene til supertypen, kan ha ekstra attributter
  - ```Kjøretøy = Personbil og/eller Lastebil ```
    - Personbil og lastebil er subtyper av kjøretøy

## Normalisering
|Begrep |Betydning  |
|:---   |:--        |
| Funksjonell avhengighet |X bestemmer Y; ```AnsNR bestemmmer Etternavn, AnsNr bestemmer PostNr``` |
|Supernøkkel              |En eller flere data som definerer en rad |
|Kandidatnøkkel           |Minimal supernøkkel; hvis man fjerner noen attributter er det ikke en supernøkkel |
|Primærnøkkel             |Spesielt utvalgt kandidatnøkkel, alle relasjoner skal ha en og bare en primærnøkkel
|Oppdateringsanomali      |Anomali som oppstår når man endrer opplysninger om noe som er lagret flere steder
|Normaliseringssteg       |Et steg i å designe en tabell slik at man minimerer duplisering av informasjon, som gjør det lettere å gjøre databasen konsistent |
|Tapsfri dekomposisjon    |Natural join vil aldri gi opphav til falske tupler|
|Partielle avhengighet    |Ett eller flere attributter er avhengig av kun deler av primærnøkkelen |
|Transitiv avhengighet    |Et attributt er avhengig av et annet ikke-primærnøkkel-attributt|

#### 1NF:
|Name |Address |Movies rented | Salutation |
|:--|:--|:--|:--|
|Janet jones |First street |pirates of the |Ms.|
|Janet jones |First street |clash of the titans|Ms.|
|Robert phil |3rd street 34 |Forgetting sarah |Mr.|
|Robert phil |3rd street 34 |Daddys little girls |Mr.|
|Robert phil |5th avenue | clash of the titans|Mr.|

#### 2NF: 
|Membership ID |Full name |address | Salutation |
|:--|:--|:--|:--|
|1 |Janet jones |First street  |Ms.|
|2 |Robert phil |3rd street 34|Mr.|
|3 |Robert phil |5th avenue |Mr.|

|Membership ID |Movies rented |
|:--|:--|
|1 |Pirates |
|1 |Clash of the titans |
|2 |Forgetting sarah |
|2 | Daddys little girls|
|3 | Clash of the titants|

#### 3NF: 
|Membership ID |Full name |address | Salutation |
|:--|:--|:--|:--|
|1 |Janet jones |First street  |2|
|2 |Robert phil |3rd street 34|1|
|3 |Robert phil |5th avenue |1|

|Membership ID |Movies rented |
|:--|:--|
|1 |Pirates |
|1 |Clash of the titans |
|2 |Forgetting sarah |
|2 | Daddys little girls|
|3 | Clash of the titants|

|Salutation ID |Salutation |
|:--|:--|
|1 |Mr. |
|2 |Ms. |
|3 |Mrs. |
|4 |Dr. |

## Fysiske databasedesign
- En ```blokk``` er den minste enheten for overføring av data mellom minnet og ytre lager.
  - Typisk blokkstørrelse på 4KB (4096 bytes)
  - inneholder som regel mange *poster* (rader)
- En *post* består av en mengde bytes som lagres på en blokk
  - Har Posthode, postskjemaer
<img src=img/post.png width=500>

- Hashing er å kartlegge data til verdier som er fikserte
- Hashing er ikke til hjelp når det kommer til å f.eks søke etter URL strenger i databaser
- Primærindeks er en indeks på primærnøkkelen til en tabell, som at Ansatt ID kan være et eksempel på
- "Tett" indeks: Bare ett indeksfelt pr. unike søkenøkkel, har et oppslag for hver verdi av søkenøkkelen
- "Denormalisering" er omvednt prosess i normaliseringsprosessen. Fungerer ved å legge til overflødig data for å *optimalisere* ytelsen


## Databaser i produksjon
- En transaksjon er enlogisk operasjon mot databasen
  - an bestå av en eller flere SQL-setninger
  - programmereren bestemmer hva som er en transaksjon
- Samtidige brukere kan ødelegge for hverandre - kollisjon
- En transaksjonslogg er en fil på ytre lager som inneholder data om alle operasjoner som er utført mot databasen

|Akronym      |Betydning|
|:--          |:--|
|Atomicity    | Alle eller ingen av deloperasjonene i en transaksjon må fullføres |
|Consistency  | En transaksjon fører databasen fra en lovlig tilstand til en annen lovlig tilstand |
|Isolation    | Effekt av transaksjoner under utførelse skal ikke kunne observeres av andre transaksjoner |
|Durability   | Effekt av fullførte (comitted) transaksjoner lagres i databasen og skal ikke gå tapt på grunn av feil |

- En *lås* er en *ventemekanisme* som blir styrt av en transaksjon
  - skrivelåser (exclusive locks)
  - leselåser (shared locks)
- En transaksjon følger reglene for *tofaselåsing* hvis låseoperasjoner gjøes før første frigivelse
  - Ingen låsing etter første opplåsing
- Vranglås
  - Typ dining philosophers fra INF214 hvor alle venter på neste i en sirkel og vil aldri låse opp
- *Horisontal fragmentering:* Tabell delt opp etter rader
- *Vertikal fragmentering:* Tabell delt opp etter kolonner
  - Primærnøkkel må være med i *alle* fragmenter
- *Metadata* er "data om data" og beskriver databasen; 
  - Hvilke tabeller finnes
  - Hvordan tabellene er bygget opp
  - Hvilke indekser er definert
- En *metadatabase* er en database model for metadata
- Integritetskontroll er en modul(?) som legger ved regler som verifiserer operasjoner og passer på at data ikke blir korrupt
- En SQL-parser er på en måte en "oversetter" fra spørring til *faktisk* spørring; f.eks ```ignore.case``` o.l.
- 