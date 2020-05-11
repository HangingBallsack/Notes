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