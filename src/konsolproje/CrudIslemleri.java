/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konsolproje;

/**
 *
 * @author Busra
 */
public interface CrudIslemleri  {
    
     /*-------veritabanından bilgileri goruntuleyen fonksiyon-------*/
    public void listele(String tablo, int no);
    
     /*-------veritabanına ekleme yapan fonksiyon-------*/
    public void ekle(int no); 
    
     /*-------veritabanından email ve sifre guncelleyen fonksiyon-------*/
    public void guncelle(String tablo, int no);
    
     /*-------veritabanından giris bilgilerinin dogrulugunu kontrol eden fonksiyon-------*/
    public boolean sorgula(int no, String sifre);
    
    
}
