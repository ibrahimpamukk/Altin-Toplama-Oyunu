package MinerGame;

import java.awt.Color;
import java.io.IOException;

public class mainClass {

    public static void main(String[] args) throws InterruptedException, IOException {
        String s = null;
        anaEkran ekran1 = new anaEkran();  
        ekran1.kurulum();
        Thread.sleep(10000);   // ayar seçilmesi için  verilen 15 saniye
        oyuncu_A a = new oyuncu_A(ekran1);
        oyuncu_B b = new oyuncu_B(ekran1);
        oyuncu_C c = new oyuncu_C(ekran1);
        oyuncu_D d = new oyuncu_D(ekran1);
        int sayac = 0;
        while (!a.elendi_mi || !b.elendi_mi || !c.elendi_mi) {
            Thread.sleep(1000);
            a.hedef_bul(a.butonlar, ekran1.board_boy, ekran1.board_genislik);
            Thread.sleep(100);
            b.hedef_bul(b.butonlar, ekran1.board_boy, ekran1.board_genislik);
            Thread.sleep(100);
            c.hedef_bul(c.butonlar, ekran1.board_boy, ekran1.board_genislik);
            Thread.sleep(100);
            d.hedef_bul(d.butonlar, ekran1.board_boy, ekran1.board_genislik, a, b, c);
            a.hamle_yap(a.butonlar, ekran1.oyuncu_adim_sayisi, a.konumx_A, a.konumy_A);
            Thread.sleep(200);

            b.hamle_yap(b.butonlar, ekran1.oyuncu_adim_sayisi, b.konumx_B, b.konumy_B);
            Thread.sleep(200);
            c.hamle_yap(c.butonlar, ekran1.oyuncu_adim_sayisi, c.konumx_C, c.konumy_C);
            Thread.sleep(200);

            d.hamle_yap(d.butonlar, ekran1.oyuncu_adim_sayisi, d.konumx_D, d.konumy_D);
            
        }
        a.fw.flush();
        a.fw.close();
        b.fw.flush();
        b.fw.close();
        c.fw.flush();
        c.fw.close();

        skorTablo tablo = new skorTablo();
        tablo.skor_tablosu_goruntule();
        System.out.println("\n Bitti.");
    }
}
