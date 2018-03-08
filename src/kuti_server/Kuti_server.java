/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuti_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author hamkTeam4/deeqkko
 */
public class Kuti_server {

    public static void main(String argv[]) throws Exception {

        String sqlStatement;
        String capitalizedSentence;
        int prepField;
        String clientQuery;
        String[] separateFields;
        query data = new SQLConnection();
        ServerSocket welcomeSocket = new ServerSocket(6789); //Tässä asetetaan TCP-portti. Tarkasta, että ovisovelluksessa on sama
        
        //HashMap, josta asetetaan TCP-yhteyden yli tulleen Stringin (1 kenttä) perusteella oikea SQL-kysely SQLconnectionin querySQL-metodille.
        HashMap<String, String> queryHash = new HashMap<>();
        queryHash.put("kyselyUsers", "SELECT user_ID, name, pin FROM users");
        queryHash.put("kyselyTapahtumatByID", "SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE user_ID=?");
        queryHash.put("kyselyNimet", "SELECT name FROM users");
        queryHash.put("kyselyTapahtumatByNimi", "SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE name=?");
        queryHash.put("kyselyTapahtumatByTapahtuma", "SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE event=?");
        queryHash.put("kyselyRfidPin", "SELECT user_ID, pin, name FROM users WHERE user_ID=?");
        System.out.println("Kuti Server v1.0 \n");
        //Pääsilmukka
        while (true) {
            
            //TCP-yhteys. Saatu merkkijono tallennetaan ensin sqlStatement-muuttujaan, josta se erotellaan StringArrayksi (separateFields).
            //Hyväksyttävä string oltava muotoa [String], [String,int] tai [String,String]
            //Hyväksyttävä SQL-kantaan menevä tiedossa oltava ensimmäisenä "S2"
            System.out.println("Listening port " +welcomeSocket.getLocalPort());
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient
                    = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            data.responseFromSQL.delete(0, data.responseFromSQL.length());
            sqlStatement = inFromClient.readLine();
            System.out.println("Received: " + sqlStatement);
            separateFields = sqlStatement.split(",");
            
            //Ehtolauseet SQL-kyselyiden määrittelyksi.
            
            //1.Ehto. Ohjautuu metodille querySQL(String)
            if (separateFields[0].contains("kysely") && separateFields.length <= 1) {
                data.setQueryInTCP(separateFields[0]);
                data.querySQL(queryHash.get(data.getQueryInTCP()));
                System.out.println("Using SQL-query: "+queryHash.get(data.getQueryInTCP()));
            }
            //2.Ehto. Ohjautuu metodille querySQL(String, int)
            else if (separateFields[0].contains("kysely") && isInteger(separateFields[1])) {
                data.setQueryInTCP(separateFields[0]);
                data.setPrepField(Integer.parseInt(separateFields[1]));
                data.querySQL(queryHash.get(data.getQueryInTCP()), data.getPrepField());
                data.querySQL(queryHash.get(data.getQueryInTCP()));
                System.out.println("Using SQL-query: "+queryHash.get(data.getQueryInTCP())+ "\nSetting PreparedStatement argument to ["  + data.getPrepField()+"]");
            //3.Ehto. Ohjautuu metodille querySQL(String,String)
            } else if (separateFields[0].contains("kysely")) {
                data.setQueryInTCP(separateFields[0]);
                data.setFieldName(separateFields[1]);
                data.querySQL(queryHash.get(data.getQueryInTCP()), data.getFieldName());
                data.querySQL(queryHash.get(data.getQueryInTCP()));
                System.out.println("Using SQL-query: " +queryHash.get(data.getQueryInTCP())+ "\nSetting PreparedStatement argument to [" + data.getFieldName()+"]");
            //4.Ehto. Lähettää vastauksen saamaansa pollConn viestiin. Yhteyden tarkistusta
            } else if (separateFields[0].contains("pollConn")) { 
                data.checkConnection();
            //5.Ehto. Lähettää kaikki viestit, jossa ensimmäisessä kentässä on merkkijono "S2" SQL-palvelimelle (tämä kuvaa julkista todennusavainta, jonka ohjelma lähettäisi
            //jos oltaisiin osattu/ehditty sellainen viritys tekemään
            } else if (separateFields[0].contains("S2")){
                data.insertSQL(separateFields);
            //6.Ehto. Käsittelee muunmuotoiset saamansa TCP-viestit (eli heittää bittiroskikseen)
            } else {
                try{
                    data.rejectConnection();
                } catch (ArrayIndexOutOfBoundsException a) {
                    data.rejectConnection();
                }
            }

            //Lähettää vastauksen
            outToClient.writeBytes(data.responseFromSQL.toString() + '\n');
            System.out.println("Sent: " + data.responseFromSQL.toString() + '\n');

        }

    }
    //Tarkistaa, onko annettu arvo muotoa int
    static boolean isInteger(String isInteger) {
        try {
            Integer.parseInt(isInteger);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
