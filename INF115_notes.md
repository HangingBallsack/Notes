# Ukesoppgaver 1
1. The total number of filkms in the table   
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