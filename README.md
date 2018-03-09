Kuti Server Java Application

Versio 1.0

hamkTeam4

Palvelinohjelma, joka muuntaa kuti-sovellusten lähettämät string-muotoiset kyseylt SQL-kyselyiksi sekä palauttaa tietokannalta saamansa tiedon string-muodossa lähteeseen.

Asennusohje:

Ohje olettaa että käytössä on Netbeans 8.2, Java 8 ja SQL-palvelin johon on asennettuna kutidb-tietokanta. Yksi tarpeeksi laajoilla oikeuksilla oleva SQL-käyttäjä on oltava luotuna (käyttäjällä oltava oikeus tehdä inserttejä tauluihin).

1. Valitse File - Import project -> From ZIP...
2. Aseta ZIP file (kuti_server.zip)
3. Aseta Folder-riville kansio johon ohjelma asennetaan 
4. Valitse Import
5. Avaa Project navigatorista Projects -> kuti_server -> resources -> <default packages> -> login.properties
6. Aseta tiedostoon riville 6 tietokantayhteys jdbc:mysql://[SQL-palvelimen ip-osoite/tietokannan nimi]? (Esim. jdbc:mysql//localhost/kutidb?
7. Aseta riville 7 SQL-käyttäjänimi
8. Aseta riville 8 SQL-käyttäjän salasana
9. Tallenna (Ctrl + S)
10. Avaa Project navigatorista kuti_server -> Source packages -> kuti_server -> Kuti_server.java
11. Aseta riville 29 (ServerSocket(TCP-port)) TCP-portti (Oletus 6789)
12. Tallenna (Ctrl + S)
13. Käännä ohjelma. Run -> Clean and Build project
14. Aja juurikansiosta kuti_server.cmd
  
Ohjelma käynnistyy.
Kuti Server v1.0

Listening port (TCP-portnumber)

Konsoliviestit:

Kuti-ovilukija testaa TCP-yhteyttä:
Received: pollConn
Sent: ok

Kuti-ovilukija pyytää palvelimelta RFID, PIN ja USERNAME -tietoa:
Received: kyselyRfidPin,[RFID]
Using SQL-query: [SQL-kysely]
Setting PreparedStatement argument to [RFID]
Sent: [RFID,PIN,USERNAME]

Kuti-ovilukija lähettää palvelimelle tapahtumatiedon vietäväksi SQL-kantaan:
Received: [oviID,RFID,USERNAME,EVENT,EVENTMSG]
Sent: Event from [oviID] sent to database.

Palvelin pysäytetään Ctrl + C:llä


TESTIOHJE:

a) Ohjelman perustoiminta:
    1. Käynnistä ohjelma
    2. Odotettu lopputulos: Ohjelma käynnistyy.
       Konsoliviesti:
       Kuti Server v1.0
       Listening port [TCP-port]
   
b) Yhteys ovilukijaan:
    1. Käynnistä ovilukijasovellus
    2. Odotettu lopputulos:
       Konsoliviesti:
       Received: pollConn
       Sent: ok
   
c) Tietokantayhteys:
    1. Syötä RFID ovilukijasovellukselle.
    2. Odotettu lopputulos:
       Received: kyselyRfidPin,[RFID]
       Using SQL-query: [SQL-kysely]
       Setting PreparedStatement argument to [RFID]
       Sent: [RFID,PIN,USERNAME]
   
4. Tietokantayhteyden katkeaminen:
    1. Katkaise yhteys SQL-tietokantaan.
    2. Syötä RFID ovilukijasovellukselle
    3. Odotettu lopputulos:
       SQLException: Communications link failure
       SQLState: 08S01
       VendorError: 0
       Sent: No SQL-connection
       
Konsolissa näkyy myös palvelimen yritys lähettää tietokantaan ovilukijalta saamansa tapahtumatieto. Samat SQL-virheet   toistuu myös siinä. Palvelin jää odottamaan uusia syötteitä (Listening port [TCP-port]

    4. Palauta SQL-yhteys ja tee testi c.
    
 Ongelmatilanteet:
 
 a) Ohjelma ei saa yhteyttä tietokantaan.
    1. Tarkasta että SQL-palvelin on toiminnassa.
    2. Tarkasta SQL-palvelimen asetukset (tietokantaskeema on käytössä, sql-käyttäjällä on oikeudet tehdä inserttejä)
    3. Tarkasta yhteysnimi, SQL-käyttäjänimi ja salasana login.properties:ta
    4. Tee testi c
    
 b) Ohjelma ei saa yhteyttä ovilukijasovellukseen
    1. Tarkasta, että ohjelma käyttää samaa TCP-porttia ovilukijasovelluksen kanssa (Asennusohje kohta 11)
    2. Tarkasta, että palomuuri sallii liikenteen käyttämälläsi TCP-portilla
    3. Tee testi b
    
   
