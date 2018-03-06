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
        ServerSocket welcomeSocket = new ServerSocket(6789);

        HashMap<String, String> queryHash = new HashMap<>();
        //Laita tähän 1. Kysely asiakkaalta avaimeksi ja 2. vastaava SQL-kysely stringinä
        //Jos käyttää XAMPP tai MAMP, en tiedä pitääkö taulun nimi korvata (esim users = kuti.users)
        //Muista muuttaa vastaavat asiat SQLConnectioniin!
        queryHash.put("kyselyUsers", "SELECT user_ID, name, pin FROM users");
        queryHash.put("kyselyTapahtumatByID", "SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE user_ID=?");
        //queryHash.put("kyselyTapahtumatByID", "SELECT event, aika, ovi_ID, user_ID, name, error FROM tapahtumat WHERE user_ID=?");
        queryHash.put("kyselyNimet", "SELECT name FROM users");
        queryHash.put("kyselyTapahtumatByNimi", "SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE name=?");
        queryHash.put("kyselyTapahtumatByTapahtuma", "SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE event=?");
        queryHash.put("kyselyRfidPin", "SELECT user_ID, pin, name FROM users WHERE user_ID=?");
        System.out.println("Kuti Server v.0.5 \n");
        
        while (true) {
            System.out.println("Listening port " +welcomeSocket.getLocalPort());
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient
                    = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            data.responseFromSQL.delete(0, data.responseFromSQL.length());
            sqlStatement = inFromClient.readLine();
            System.out.println("Received: " + sqlStatement);
            separateFields = sqlStatement.split(",");
            if (separateFields[0].contains("kysely") && separateFields.length <= 1) {
                data.setQueryInTCP(separateFields[0]);
                data.querySQL(queryHash.get(data.getQueryInTCP()));
                System.out.println("Using SQL-query: "+queryHash.get(data.getQueryInTCP()));
            }
            else if (separateFields[0].contains("kysely") && isInteger(separateFields[1])) {
                data.setQueryInTCP(separateFields[0]);
                data.setPrepField(Integer.parseInt(separateFields[1]));
                data.querySQL(queryHash.get(data.getQueryInTCP()), data.getPrepField());
                data.querySQL(queryHash.get(data.getQueryInTCP()));
                System.out.println("Using SQL-query: "+queryHash.get(data.getQueryInTCP())+ "\nSetting PreparedStatement argument to ["  + data.getPrepField()+"]");
            } else if (separateFields[0].contains("kysely")) {
                data.setQueryInTCP(separateFields[0]);
                data.setFieldName(separateFields[1]);
                data.querySQL(queryHash.get(data.getQueryInTCP()), data.getFieldName());
                data.querySQL(queryHash.get(data.getQueryInTCP()));
                System.out.println("Using SQL-query: " +queryHash.get(data.getQueryInTCP())+ "\nSetting PreparedStatement argument to [" + data.getFieldName()+"]");
            } else {
                data.insertSQL(separateFields);
            }

            
            outToClient.writeBytes(data.responseFromSQL.toString() + '\n');
            System.out.println("Sent: " + data.responseFromSQL.toString() + '\n');

        }

    }

    static boolean isInteger(String isInteger) {
        try {
            Integer.parseInt(isInteger);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
