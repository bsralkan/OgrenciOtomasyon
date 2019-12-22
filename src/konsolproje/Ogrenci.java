package konsolproje;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import static konsolproje.KonsolProje.scanner;

/**
 *
 * @author Busra
 */
public class Ogrenci extends OrtakIslemler implements CrudIslemleri, DersIslemleri, NotIslemleri {
    Scanner scan = new Scanner(System.in);
    
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
                System.out.println("Bilgilerine sahip ogrencinin yalnizca email ve sifre bilgilerini guncelleme yetkiniz var! ");

            }
            System.out.println("Email bilgisini degistirmek icin 1, sifre degistirmek icin 2 tuslayınız: ");
            int islem = scanner.nextInt();
            PreparedStatement preparedStmt;
            switch (islem) {
                case 1:
                    System.out.println("Yeni Email: ");
                    String mail = scanner.next();

                    sql = "UPDATE ogrenci SET email='" + mail + "' where no=" + no;
                    preparedStmt = con.prepareStatement(sql);
                    preparedStmt.executeUpdate();
                    con.close();

                    break;
                case 2:
                    System.out.println("Yeni sifre: ");
                    String sif = scanner.next();
                    sql = "UPDATE ogrenci SET sifre='" + sif + "' where no=" + no;
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

            String sql = "SELECT id FROM ogrenci WHERE no=" + no + " and sifre='" + sifre + "'";
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
        //ogrenci ekleme yapamaz
    }

    @Override
    public void dersListele(int no) {
        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "SELECT * FROM derssecim WHERE ogrNo=" + no;
            ResultSet rs = stt.executeQuery(sql);

            System.out.println("ogrencinin aldigi dersler: ");
            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz
                int id = rs.getInt("id");
                int noo = rs.getInt("ogrNo");
                String adi = rs.getString("dersAdi");

                // Verileri görüntüle - yaz
                System.out.print("ID: " + id);
                System.out.print(", no: " + noo);
                System.out.print(", ders adi: " + adi);
                System.out.println("\n **************************************");

            }
        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*---------- numaraya göre ogrenciye ait bütün notları görüntüler -----------*/
    @Override
    public void notGoruntule(int no) {
        try {
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "SELECT * FROM notlar  WHERE ogrNo=" + no;
            ResultSet rs = stt.executeQuery(sql);

            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz
                int not = rs.getInt("notu");
                int noo = rs.getInt("ogrNo");
                String adi = rs.getString("ad");
                String soyadi = rs.getString("soyad");
                String ders = rs.getString("ders");
                // Verileri görüntüle - yaz

                System.out.print(" || no: " + noo);
                System.out.print(" || Adı: " + adi);
                System.out.print(" || Soyadı: " + soyadi);
                System.out.print(" || Ders: " + ders);
                System.out.print(" || Notu: " + not);
                System.out.println("\n");
            }
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

    public void dersSecim(int no) {
        try {
            String bolum = null;
            String ders = null;
            Connection con = baglanti();
            Statement stt = con.createStatement();
            String sql = "SELECT * FROM dersler WHERE  bolum = (select bolum from ogrenci WHERE no = '" + no + "')";
            ResultSet rs = stt.executeQuery(sql);
            //---------------------------------------------

            //---------------------------------------------
            System.out.println("ogrencinin alabilecegi dersler: ");
            while (rs.next()) {
                // Sutunlara göre degerlerı alıyoruz

                String adi = rs.getString("dersAdi");
                bolum = rs.getString("bolum");
                // Verileri görüntüle - yaz

                System.out.print(", ders adi: " + adi);
                System.out.println("\n **************************************");

            }
            System.out.println("almak istediginiz dersi giriniz(ogrenci menuye donmek icin cikis yaziniz): ");
            ders = scan.nextLine();
            if (!ders.equals("cikis")) {                
                Statement stmt = con.createStatement();
                String sql1 = "INSERT INTO derssecim values(NULL,'" + bolum + "','" + ders + "'," + no + ")";
                stmt.executeUpdate(sql1);
                System.out.println("ders secimi basarili");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CrudIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notEkle(int no) {
        //ogrenci not ekleyemez!
    }
}
