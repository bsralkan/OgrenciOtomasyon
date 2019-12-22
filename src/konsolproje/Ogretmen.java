/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konsolproje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static konsolproje.KonsolProje.scanner;

/**
 *
 * @author Busra
 */
public class Ogretmen extends OrtakIslemler implements CrudIslemleri, DersIslemleri, NotIslemleri {
    
    Scanner scan = new Scanner(System.in);
    String bolum;
    public static String ders;

    @Override
    public void listele(String tablo, int no) {
        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "SELECT * FROM " + tablo + " WHERE no=" + no;
            ResultSet rs = stt.executeQuery(sql);

            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz
                int id = rs.getInt("id");
                int noo = rs.getInt("no");
                String adi = rs.getString("ad");
                String soyadi = rs.getString("soyad");
                ders = rs.getString("ders");
                // Verileri görüntüle - yaz
                System.out.print("ID: " + id);
                System.out.print(", no: " + noo);
                System.out.print(", Adı: " + adi);
                System.out.println(", Soyadı: " + soyadi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void guncelle(String tablo, int no) {
        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();

            String sql = "SELECT * FROM " + tablo + " WHERE no=" + no;
            ResultSet rs = stt.executeQuery(sql);

            while (rs.next()) {
                // Verileri görüntüle - yaz
                System.out.print(", no: " + rs.getInt("no"));
                System.out.print(", Adı: " + rs.getString("ad"));
                System.out.println(", Soyadı: " + rs.getString("soyad"));
                System.out.println(", Email: " + rs.getString("email"));
                System.out.println(", Sifre: " + rs.getString("sifre"));
                System.out.println("Bilgilerine sahip ogretim uyesinin yalnizca email ve sifre bilgilerini guncelleme yetkiniz var! ");

            }
            System.out.println("Email bilgisini degistirmek icin 1, sifre degistirmek icin 2 tuslayınız: ");
            int islem = scanner.nextInt();
            PreparedStatement preparedStmt;
            switch (islem) {
                case 1:
                    System.out.println("Yeni Email: ");
                    String mail = scanner.next();

                    sql = "UPDATE ogretimuyesi SET email='" + mail + "' where no=" + no;
                    preparedStmt = con.prepareStatement(sql);
                    preparedStmt.executeUpdate();
                    con.close();

                    break;
                case 2:
                    System.out.println("Yeni sifre: ");
                    String sif = scanner.next();
                    sql = "UPDATE ogretimuyesi SET sifre='" + sif + "' where no=" + no;
                    preparedStmt = con.prepareStatement(sql);
                    preparedStmt.executeUpdate();
                    con.close();
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean sorgula(int no, String sifre) {
        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();

            String sql = "SELECT id FROM ogretimuyesi WHERE no=" + no + " and sifre='" + sifre + "'";
            ResultSet rs = stt.executeQuery(sql);
            if (rs.first()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void ekle(int no) {

    }

    @Override
    public void dersListele(int no) {

    }

    @Override
    public void notGoruntule(int no) { // bu fonksiyon hatalı!!!
        try {

            Connection con = baglanti();
            Statement st = con.createStatement();
            String sqlSorgusu = "SELECT ders FROM ogretimuyesi Where no=" + no;
            ResultSet rs1 = st.executeQuery(sqlSorgusu);

            while (rs1.next()) {
                ders = rs1.getString("ders");
            }

            Statement stt = con.createStatement();
            String sql = "SELECT * FROM notlar WHERE ders='" + ders + "'";
            ResultSet rs = stt.executeQuery(sql);
            System.out.println(ders);

            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz
                int not = rs.getInt("notu");
                int noo = rs.getInt("ogrNo");
                String adi = rs.getString("ad");
                String soyadi = rs.getString("soyad");

                // Verileri görüntüle - yaz
                System.out.print("Ders: " + ders);
                System.out.print(" Ogrenci no: " + noo);
                System.out.print(" Adı: " + adi);
                System.out.print(" Soyadı: " + soyadi);
                System.out.println(" Notu: " + not);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void notEkle(int no) {
        try {

            Connection con = baglanti();
            Statement st = con.createStatement();
            String sqlSorgusu = "SELECT ders FROM ogretimuyesi Where no=" + no;
            ResultSet rs1 = st.executeQuery(sqlSorgusu);

            while (rs1.next()) {
                ders = rs1.getString("ders");
            }
            System.out.println("Ogrencinin Adi: ");
            String ad = scan.nextLine();
            System.out.println("Soyadi: ");
            String soyad = scanner.next();
            System.out.println("Ogrenci Numarasi: ");
            String ogrNo = scanner.next();
            System.out.println("Bolumu: ");
            String bolum = scan.nextLine();
            System.out.println("Notu: ");
            int not = scan.nextInt();

            Statement stt = con.createStatement();
            String sql = "INSERT INTO notlar VALUES(NULL,'" + bolum + "','" + ogrNo + "','" + ad + "','" + soyad + "','" + ders + "','" + not + "')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void harfNotu(int no){
        System.out.println("Dersin Adi: ");
        String ad = scan.nextLine();
        System.out.println("En dusuk: ");
        int endusuk = scan.nextInt();
        System.out.println("En yuksek: ");
        int enyuksek = scanner.nextInt();
        System.out.println("harf: ");
        String harf = scanner.next();

        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "INSERT INTO harfnotu values(NULL,'" +  ad + "','" + endusuk + "','" + enyuksek + "','" + harf + "')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void dersProgramiGoruntule() {
        String bolum = null;
        System.out.println("Bolum Giriniz: ");
        bolum=scan.nextLine();
        
        super.dersPrograminiGor(bolum);
    }

        
    public void yoklama(int no){
        try {

            Connection con = baglanti();
            Statement st = con.createStatement();
            String sqlSorgusu = "SELECT ders FROM ogretimuyesi Where no=" + no;
            ResultSet rs1 = st.executeQuery(sqlSorgusu);

            while (rs1.next()) {
                ders = rs1.getString("ders");
            }
            
            System.out.println("Ogrenci Numarasi: ");
            String ogrNo = scanner.next();
            System.out.println("Bolumu: ");
            String bolum = scan.nextLine();
            System.out.println("Tarih (lutfen yil-ay-gun formatinda yaziniz): ");
            String tarih = scan.nextLine();
            System.out.println("Ogrenci derse girdi mi? (evet icin 1 hayir icin 0 girinz)");
            int kontrol = scan.nextInt();

            Statement stt = con.createStatement();
            String sql = "INSERT INTO yoklama VALUES(NULL,'"+ ogrNo + "','"  + bolum + "','" + ders + "','" +tarih + "','" + kontrol +"')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

    
