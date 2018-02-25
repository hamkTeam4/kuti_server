/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuti_server;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author hamkTeam4/deeqkko
 */
public abstract class query {
    
    protected String queryToSQL;  //SQL-kysely palvelimelle
    protected String queryInTCP;  //Kysely client-softalta
    protected int prepField;
    protected String fieldName;
    protected StringBuffer responseFromSQL = new StringBuffer();

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    
    public int getPrepField() {
        return prepField;
    }

    public void setPrepField(int prepField){
        this.prepField = prepField;
    }


    public String getQueryToSQL() {
        return queryToSQL;
    }

    public void setQueryToSQL(String queryToSQL) {
        this.queryToSQL = queryToSQL;
    }

    public String getQueryInTCP() {
        return queryInTCP;
    }

    public void setQueryInTCP(String queryInTCP) {
        this.queryInTCP = queryInTCP;
    }

    public StringBuffer getResponseFromSQL() {
        return responseFromSQL;
    }

    public void setResponseFromSQL(StringBuffer responseFromSQL) {
        this.responseFromSQL = responseFromSQL;
    }

    

    
    
    
    
    public abstract void loadDriver() throws IOException; //Muodostaa SQL-yhteyden
    
    public abstract void querySQL(String queryInTCP) throws IOException, SQLException;
    
    public abstract void querySQL(String queryInTCP, int prepField) throws IOException;
    
    public abstract void querySQL(String queryInTCP, String fieldName) throws IOException;
}
