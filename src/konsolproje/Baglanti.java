/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konsolproje;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Busra
 */
public abstract class Baglanti {

    
    public Connection baglanti() {
        //url kısmında /okul dan sonra yazılanlar türkçe karakter kullaımıyla alakalı.
        String url = "jdbc:mysql://localhost:3306/Okul?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
        String user = "root";
        String password = "";
        Connection con = null;
        try {
            // com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
        
    }
    
    
    
    
}
