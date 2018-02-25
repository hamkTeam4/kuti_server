Kuti Server Java Application

Versio 0.5

Tarvittavat kirjastot ja ajurit:
-MySQL Java JDBC Driver

Classpathiin asetettava resources/login.properties

Muuntaa asiakasohjelmalta saamansa String-muotoisen kyselyn SQL Queryksi
ja palauttaa asiakasohjelmalle String-muotoisen vastauksen.

Clientilta tulleen merkkijonon perustella haetaan hashmapista (queryHash) oikea sql-kysely

Syntaksi:           Esimerkki:
["string"]          "kyselyUsers"
["string,int"]      "kyselyTapahtumatByID,1001" 
["string,string"]   "kyselyTapahtumatByNimi, Jormakka Jorma"

Pääohjelma tulostaa clientilta saamansa kyselyn, käyttämänsä SQL-kyselyn ja clientille
lähettämänsä vastauksen näytölle.

!!VARMISTA, ETTÄ PÄÄOHJELMASSA OLEVAT SQL-KYSELYT TOIMIVAT!!

query.java

Abstrakti luokka 
+String queryToSQL              -> SQL-kysely
+String queryInTCP              -> Clientin lähettämä merkkijonokysely (esim kyselyUsers)
+int    prepField               -> Clientin lähettämä numeromuotoinen lisämääre.
                                   Käytetään PreparedStatementissa WHERE -muuttujana
+String fieldName               -> Kuten edellinen. WHERE -muuttuja (sarakkeen nimi)
+StringBuffer responseFromSQL   -> SQL-palvelimen vastaus.

loadDriver()                    -> Muodostaa yhteyden SQL-palvelimeen ja hakee 
                                   tietokannan osoitteen, käyttäjätunnuksen ja salasanan
                                   login.java:n metodilla login.propertiesilta

querySQL()                      -> Lähettää muuttujaa vastaavan SQL-kyselyn tietokannalle
                                   ja tallettaa vastauksen responseFromSQL-muuttujalle


SQLConnection.java

Perii query.javan

                                ->Query.javan metodit kirjoitettu auki.

login.java
                                ->Metodi tietokantayhteyden, käyttäjätunnuksen ja salasanan
                                  lukuun login.properties -tiedostosta

login.properties                ->Tähän tiedostoon asetetaan tietokantayhteys, SQL-käyttäjä
                                  ja salasana. Tarkemmat ohjeet tiedostossa.
