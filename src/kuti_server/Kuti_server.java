/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuti_server;

/**
 *
 * @author hamkTeam4/deeqkko
 */
public class Kuti_server {

    public static void main(String argv[]) throws Exception {
        TCPclass listen = new TCPclass();
        
        while (true) {
            listen.TCPConn();
        }

}
}