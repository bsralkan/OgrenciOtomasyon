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
public class Memur extends OrtakIslemler implements CrudIslemleri {

    String alan;
    Scanner scan = new Scanner(System.in);

    public void dersProgramiHazirla(int no) {
        try {
            System.out.print("bolum giriniz: ");
            String bolum = scan.nextLine();

            String ders = null;
            String gun = null;

            Connection con = baglanti();
            Statement stt = con.createStatement();

            String sql = "SELECT * FROM dersler WHERE  bolum = '" + bolum + "'";
            ResultSet rs = stt.executeQuery(sql);
            //---------------------------------------------

            //---------------------------------------------
            System.out.println("Ders programina eklenecek dersler: ");
            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz

                String adi = rs.getString("dersAdi");
                bolum = rs.getString("bolum");
                // Verileri görüntüle - yaz

                System.out.print(", ders adi: " + adi);
                System.out.println("\n **************************************");

            }
            while (true) {
                System.out.println("Ders Adi giriniz:(cikmak icin cikis yazmalisiniz) ");
                ders = scan.nextLine();

                if (ders.equals("cikis")) {
                    KonsolProje.memurMenu(no);
                    break;
                } else {
                    System.out.println("Dersin eklenecegi gunu giriniz: ");
                    gun = scan.nextLine();
                    Statement stmt = con.createStatement();
                    String sql1 = "INSERT INTO dersprogrami values(NULL,'" + ders + "','" + gun + "','" + bolum + "')";
                    stmt.executeUpdate(sql1);
                    System.out.println("ders ekleme basarili");
                }

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
                System.out.println("Bilgilerine sahip gorevlinin yalnizca email ve sifre bilgilerini guncelleme yetkiniz var! ");

            }
            System.out.println("Email bilgisini degistirmek icin 1, sifre degistirmek icin 2 tuslayınız: ");
            int islem = scanner.nextInt();
            PreparedStatement preparedStmt;
            switch (islem) {
                case 1:
                    System.out.println("Yeni Email: ");
                    String mail = scanner.next();

                    sql = "UPDATE memur SET email='" + mail + "' where no=" + no;
                    preparedStmt = con.prepareStatement(sql);
                    preparedStmt.executeUpdate();
                    con.close();

                    break;
                case 2:
                    System.out.println("Yeni sifre: ");
                    String sif = scanner.next();
                    sql = "UPDATE memur SET sifre='" + sif + "' where no=" + no;
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

            String sql = "SELECT id FROM memur WHERE no=" + no + " and sifre='" + sifre + "'";
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
    public void ekle(int no) {
        System.out.println("1->Ogrenci ekle\n"
                + "2->Ogretim Uyesi Ekle\n"
                + "3->Personel Ekle\n"
                + "4->Ders Ekle\n"
                + "5->Personel Menuye don");
        int islem = scanner.nextInt();

        if (islem < 1 || islem > 5) {
            System.out.println("Girilen kod Gecersiz!!");
            ekle(no);

        } else {
            switch (islem) {
                case 1:
                    ogrenciEkle();
                    ekle(no);
                    break;
                case 2:
                    ogretmenEkle();
                    ekle(no);
                    break;
                case 3:
                    memurEkle();
                    ekle(no);

                    break;
                case 4:
                    dersEkle();
                    ekle(no);
                    break;
                case 5:
                    KonsolProje.memurMenu(no);
                    break;
            }
        }

    }

    public void ogrenciEkle() {
        System.out.println("Ogrencinin numarasi: ");
        int no = scanner.nextInt();
        System.out.println("Adi: ");
        String ad = scan.nextLine();
        System.out.println("Soyadi: ");
        String soyad = scanner.next();
        System.out.println("Bolumu: ");
        String bolum = scan.nextLine();
        System.out.println("Email: ");
        String mail = scanner.next();
        System.out.println("Sifre: ");
        String sifre = scanner.next();

        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "INSERT INTO ogrenci values(NULL," + no + ",'" + ad + "','" + soyad + "','" + mail + "','" + sifre + "','" + bolum + "')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void memurEkle() {
        System.out.println("Personel numarasi: ");
        int no = scanner.nextInt();
        System.out.println("Adi: ");
        String ad = scan.nextLine();
        System.out.println("Soyadi: ");
        String soyad = scanner.next();
        System.out.println("Alani: ");
        String alan = scan.nextLine();
        System.out.println("Email: ");
        String mail = scanner.next();
        System.out.println("Sifre: ");
        String sifre = scanner.next();

        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "INSERT INTO memur values(NULL," + no + ",'" + ad + "','" + soyad + "','" + mail + "','" + sifre + "','" + alan + "')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ogretmenEkle() {
        System.out.println("Ogretim uyesinin numarasi: ");
        int no = scanner.nextInt();
        System.out.println("Adi: ");
        String ad = scanner.next();
        System.out.println("Soyadi: ");
        String soyad = scanner.next();
        System.out.println("Bolumu: ");
        String bolum = scan.nextLine();
        System.out.println("Verdigi ders: ");
        String ders = scan.nextLine();
        System.out.println("Email: ");
        String mail = scanner.next();
        System.out.println("Sifre: ");
        String sifre = scanner.next();

        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "INSERT INTO ogretimuyesi values(NULL," + no + ",'" + ad + "','" + soyad + "','" + mail + "','" + sifre + "','" + bolum + "'," + ders + "')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void dersEkle() {

        System.out.println("Dersin Adi: ");
        String ad = scan.nextLine();
        System.out.println("Bolumu: ");
        String bolum = scan.nextLine();
        System.out.println("Ders Kodu: ");
        String derskod = scanner.next();
        System.out.println("Yariyil: ");
        String yariyil = scanner.next();

        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "INSERT INTO dersler values(NULL,'" + bolum + "','" + ad + "','" + derskod + "','" + yariyil + "')";
            stt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
