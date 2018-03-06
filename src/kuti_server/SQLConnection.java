/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuti_server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author hamkTeam4/deeqkko
 */
public class SQLConnection extends query {

    private Connection conn = null;

    public SQLConnection() {
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void loadDriver() throws IOException {
        login logData = new login();
        logData.getLoginValues();

        try {
            conn = DriverManager.getConnection(logData.getDB() + "user=" + logData.getUser() + "&password=" + logData.getPassword());

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }
    }

    @Override
    public void querySQL(String queryToSQL) throws IOException, SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        loadDriver();

        try {
            //Sulkuihin SQL-kysely
            stmt = conn.prepareStatement(queryToSQL);
            rs = stmt.executeQuery();

            if (queryToSQL.matches("SELECT user_ID, name, pin FROM users")) {
                while (rs.next()) {
                    responseFromSQL.append(Integer.toString(rs.getInt("user_ID")))
                            .append(",")
                            .append(Integer.toString(rs.getInt("pin")))
                            .append(",")
                            .append(rs.getString("name"))
                            .append(";");
                }
                
            }
            if (queryToSQL.matches("SELECT name FROM users")) {
                while (rs.next()){
                responseFromSQL.append(rs.getString("Name"))
                               .append(";");
                }
            } else {
                responseFromSQL.append("No results.");
            }

        } catch (SQLException ex) {
            // handle any errors
            String sqlMesg = "SQLException: " + ex.getMessage();
            String sqlState = "SQLState: " + ex.getSQLState();
            String vendorError = "VendorError: " + ex.getErrorCode();
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                //rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                //stmt = null;
            }
        }

    }

    @Override
    public void querySQL(String queryToSQL, int prepField) throws IOException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        loadDriver();
        try {
            //Sulkuihin SQL-kysely
            stmt = conn.prepareStatement(queryToSQL);
           
            stmt.setInt(1, prepField);
            rs = stmt.executeQuery();
             if (queryToSQL.contains("SELECT log_number, aika, ovi_ID, user_ID, name, event, eventtext FROM tapahtumat WHERE")) {
                while (rs.next()) {
                    responseFromSQL.append(Integer.toString(rs.getInt("log_number")))
                        .append(",")
                        .append(rs.getString("aika"))
                        .append(",")
                        .append(rs.getString("ovi_ID"))
                        .append(",")
                        .append(Integer.toString(rs.getInt("user_ID")))
                        .append(",")
                        .append(rs.getString("name"))
                        .append(",")
                        .append(Integer.toString(rs.getInt("event")))
                        .append(",")
                        .append(rs.getString("eventtext"))
                        .append(";");
                }
             } else if (queryToSQL.contains("SELECT user_ID, pin, name FROM users WHERE")) {
                 while (rs.next()){
                     responseFromSQL.append(Integer.toString(rs.getInt("user_ID")));
                     responseFromSQL.append(",");
                     responseFromSQL.append(rs.getInt("pin"));
                     responseFromSQL.append(",");
                     responseFromSQL.append(rs.getString("name"));
                 }
                
            } else {
                responseFromSQL.append("No results.");
            } 
             
            
                
                            

        } catch (SQLException ex) {
            // handle any errors
            String sqlMesg = "SQLException: " + ex.getMessage();
            String sqlState = "SQLState: " + ex.getSQLState();
            String vendorError = "VendorError: " + ex.getErrorCode();
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                //rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                //stmt = null;
            }
        }

    }

    @Override
    public void querySQL(String queryToSQL, String fieldName) throws IOException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        loadDriver();
        try {
            //Sulkuihin SQL-kysely
            stmt = conn.prepareStatement(queryToSQL);
           
            stmt.setString(1, fieldName);
            rs = stmt.executeQuery();
              
                while (rs.next()) {
                    responseFromSQL.append(Integer.toString(rs.getInt("log_number")))
                        .append(",")
                        .append(rs.getString("aika"))
                        .append(",")
                        .append(rs.getString("ovi_ID"))
                        .append(",")
                        .append(Integer.toString(rs.getInt("user_ID")))
                        .append(",")
                        .append(rs.getString("name"))
                        .append(",")
                        .append(Integer.toString(rs.getInt("event")))
                        .append(",")
                        .append(rs.getString("eventtext"))    
                        .append(";");
                }
        
            

        } catch (SQLException ex) {
            // handle any errors
            String sqlMesg = "SQLException: " + ex.getMessage();
            String sqlState = "SQLState: " + ex.getSQLState();
            String vendorError = "VendorError: " + ex.getErrorCode();
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                //rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                //stmt = null;
            }
        }
    }
    
    @Override
    public void insertSQL(String insertToSQL[]) throws IOException, SQLException{
        loadDriver();
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp eventTimestamp = new java.sql.Timestamp(now.getTime());
        String eventUpdate = "INSERT INTO tapahtumat (aika, ovi_ID, user_ID, name, event, eventtext) VALUES (?,?,?,?,?,?)";
        PreparedStatement insert = getConn().prepareStatement(eventUpdate);
        getConn().setAutoCommit(false);
        insert.setTimestamp(1,eventTimestamp);
        insert.setString(2,insertToSQL[0]); //oviID
        insert.setInt(3,Integer.parseInt(insertToSQL[1])); // userID
        insert.setString(4,insertToSQL[2]); // name
        insert.setInt(5,Integer.parseInt(insertToSQL[3])); // error
        insert.setString(6,insertToSQL[4]); //errormsg
        insert.addBatch();
        
        int[] count = insert.executeBatch(); 
        getConn().commit();
        
        
    }
}

