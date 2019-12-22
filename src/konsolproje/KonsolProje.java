/*
 * projeye mysql i dahil etmek için mysql-connector-java.jar dosyasını libraries'e ekledim
 * xampp içindeki my.ini dosyasındaki bind-adres kısmının başındaki # i sildim.
 */
package konsolproje;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Busra
 */
public class KonsolProje {

    /* ---------- Konsolda giriş ve hatalı girişlerde renk kullanımı için-------------*/
    public static final String GREEN = "\033[0;32m";
    public static final String RED = "\033[0;31m";
    public static final String RESET = "\033[0m";
    /* -------------------------------------------------------------------------------*/

    static Scanner scanner = new Scanner(System.in);
    Scanner scan = new Scanner(System.in);
    static Ogretmen ogretmen = new Ogretmen();
    static Ogrenci ogr = new Ogrenci();
    static Memur memur = new Memur();

    public static void main(String[] args) {

        anaMenu();

    }

    public static void anaMenu() {

        int islem = 0;
        System.out.println("Lutfen Seciminizi Yapınız \n "
                + "1->ogrenci \n "
                + "2->ogretim uyesi \n "
                + "3->memur \n "
                + "4->Cikis");
        islem = scanner.nextInt();
        System.out.println(" ");
        if (islem != 1 && islem != 2 && islem != 3 && islem != 4) {
            System.out.println("Girilen Kod Geçersiz !! ");
            anaMenu();
        } else {
            switch (islem) {
                case 1:
                    /* ---------------------- Giris ---------------------*/
                    System.out.print("Lutfen Ogrenci Numaranizi Giriniz: ");
                    int no = scanner.nextInt();
                    System.out.print("\n");
                    System.out.print("Lutfen Sifrenizi Giriniz: ");
                    String sifre = scanner.next();
                    System.out.println("\n");

                    /* ---------------------- Giris Kontrolü ---------------------*/
                    if (ogr.sorgula(no, sifre)) {
                        System.out.println(KonsolProje.GREEN + "Giris Basarili!!\n" + KonsolProje.RESET);
                        ogrenciMenu(no);
                    } else {
                        /* ---------------------- hatalıysa tekrar giriş yap ---------------------*/
                        System.out.println(KonsolProje.RED + "Hatali giris yaptiniz! \n" + KonsolProje.RESET);
                        anaMenu();
                    }
                    break;
                case 2:
                    /* ---------------------- Giris ---------------------*/
                    System.out.print("Lutfen Calisan Numaranizi Giriniz: ");
                    no = scanner.nextInt();
                    System.out.print("\n");
                    System.out.print("Lutfen Sifrenizi Giriniz: ");
                    sifre = scanner.next();
                    System.out.println("\n");


                    /* ---------------------- Giris kontrolü ---------------------*/
                    if (ogretmen.sorgula(no, sifre)) {
                        System.out.println(KonsolProje.GREEN + "Giris Basarili!!\n" + KonsolProje.RESET);
                        ogretmenMenu(no);
                    } else {
                        /* ---------------------- bilgiler hatalıysa tekrar giriş sayfasına yönlendir ---------------------*/
                        System.out.println(KonsolProje.RED + "Hatali giris yaptiniz! \n" + KonsolProje.RESET);
                        anaMenu();
                    }
                    break;
                case 3:
                    /* ---------------------- Giris ---------------------*/
                    System.out.print("Lutfen Calisan Numaranizi Giriniz: ");
                    no = scanner.nextInt();
                    System.out.print("\n");
                    System.out.print("Lutfen Sifrenizi Giriniz: ");
                    sifre = scanner.next();
                    System.out.println("\n");

                    /* ---------------------- sifre ve no kontrolu ---------------------*/
                    if (memur.sorgula(no, sifre)) {
                        System.out.println(KonsolProje.GREEN + "Giris Basarili!!\n" + KonsolProje.RESET);
                        memurMenu(no);
                    } else {
                        /* ---------------------- dogru degil ise tekrar girise yonlendir ---------------------*/

                        System.out.println(KonsolProje.RED + "Hatali giris yaptiniz! \n" + KonsolProje.RESET);
                        anaMenu();
                    }
                    break;
                case 4:
                    break;
            }
        }
    }

    public static void ogrenciMenu(int no) {

        /* ---------------------- Girisyapıldıysa kullanıcı işlemleri ---------------------*/
        int islem = 0;

        System.out.println("\nLutfen Seciminizi Yapınız \n "
                + "1->Bilgileri Gorme \n "
                + "2->Bilgileri Guncelleme \n "
                + "3->Not Gorme \n "
                + "4->Ders Secme \n "
                + "5->Ders Listele \n "
                + "6->Ana Menu \n");
        islem = scanner.nextInt();
        if (islem < 1 || islem > 6) {
            System.out.println("Girilen Kod Geçersiz !! ");
            ogrenciMenu(no);
        }
        switch (islem) {
            case 1:
                ogr.listele("ogrenci", no);
                ogrenciMenu(no);
                break;
            case 2:
                ogr.guncelle("ogrenci", no);
                ogrenciMenu(no);
                break;
            case 3:
                ogr.notGoruntule(no);
                ogrenciMenu(no);
                break;
            case 4:
                ogr.dersSecim(no);
                ogrenciMenu(no);
                break;
            case 5:
                ogr.dersListele(no);
                ogrenciMenu(no);
                break;
            case 6:
                anaMenu();
        }

    }

    public static void ogretmenMenu(int no) {
        int islem = 0;

        /* ---------------------- Giris yapıldıysa kullanıcı işlemleri  ---------------------*/
        System.out.println(
                "\n Lutfen Seciminizi Yapınız \n "
                + "1->Bilgileri Gorme \n "
                + "2->Bilgileri Guncelleme \n "
                + "3->Not Girme \n "
                + "4->Not Görüntüle \n"
                + " 5->Harf Notu Belirleme\n"
                + " 6->Yoklama Girme \n"
                + " 7->Ana Menu\n");
        islem = scanner.nextInt();
        if (islem < 1 || islem > 7) {
            System.out.println("Girilen Kod Geçersiz !! ");
            ogretmenMenu(no);
        }

        switch (islem) {
            case 1:
                ogretmen.listele("ogretimuyesi", no);
                System.out.println("\nOgretmen menuye yonlendiriliyorsunuz");
                ogretmenMenu(no);
                break;
            case 2:
                ogretmen.guncelle("ogretimuyesi", no);
                System.out.println("\nOgretmen menuye yonlendiriliyorsunuz");
                ogretmenMenu(no);
                break;
            case 3:
                ogretmen.notEkle(no);
                ogretmenMenu(no);
                break;
            case 4:
                ogretmen.notGoruntule(no);
                ogretmenMenu(no);
                break;
            case 5:
                ogretmen.harfNotu(no);
                ogretmenMenu(no);
                break;
            case 6:
                ogretmen.yoklama(no);
                ogretmenMenu(no);
                break;
            case 7:
                anaMenu();
                break;
        }

    }

    public static void memurMenu(int no) {
        int islem = 0;

        /* ---------------------- Giris yapildi ise kullanici islemleri ---------------------*/
        System.out.println("Lutfen Seciminizi Yapınız \n "
                + "1->Bilgileri Gorme \n "
                + "2->Bilgileri Guncelleme  \n "
                + "3->Ders Programi Hazirlama \n"
                + "4->Ekle \n"
                + "5->Ana Menu\n");
        islem = scanner.nextInt();
        if (islem < 1 || islem > 5) {
            System.out.println("Girilen Kod Gecersiz !! ");
            memurMenu(no);
        }

        switch (islem) {
            case 1:
                memur.listele("memur", no);
                memurMenu(no);
                break;
            case 2:
                memur.guncelle("memur", no);
                memurMenu(no);
                break;
            case 3:

                memur.dersProgramiHazirla(no);

                break;
            case 4:

                memur.ekle(no);
                memurMenu(no);
                break;
            case 5:
                anaMenu();
                break;
        }

    }

}
