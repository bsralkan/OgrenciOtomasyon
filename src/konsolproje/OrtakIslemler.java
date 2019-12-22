/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konsolproje;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Busra
 */
public abstract class OrtakIslemler extends Baglanti {
    
    public void dersPrograminiGor(String bolum){
        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "SELECT * FROM dersprogrami WHERE bolum=" + bolum;
            ResultSet rs = stt.executeQuery(sql);

            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz
                
                String dersadi = rs.getString("dersAdi");
                String gun = rs.getString("gun");
                
                // Verileri görüntüle - yaz
                System.out.print(", ders adi: " + dersadi );
                System.out.print(", gun: " + gun);
                System.out.println(", bolum: " + bolum);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
