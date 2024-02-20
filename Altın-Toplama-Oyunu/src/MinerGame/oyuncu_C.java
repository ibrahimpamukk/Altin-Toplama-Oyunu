/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinerGame;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;

/**
 *
 * @author ziya
 */
public class oyuncu_C {

    anaEkran ekran1 = new anaEkran();
    public static int konumx_C = 0;
    public static int konumy_C;
    private int maliyetC;

    public static int guncel_hedef_x = 0;
    public static int guncel_hedef_y = 0;
    public static int altin_miktari = 0;
    private int hareket_maaliyeti;
    private int adim_sayisi;
    public JButton[][] butonlar;      //yan classdan haritayı alıcaz

    private int boy;
    private int genislik;
    private anaEkran ekran;
    public boolean elendi_mi = false;
    public boolean hedef_var_mı = false;
    public static int atilan_adim_sayisi = 0;
    public static int toplanan_altin_miktari = 0;
    public static int harcanan_altin_miktari = 0;
    private int hedefleme_maliyeti;

    private int acilacak_altin = 0;
    public FileWriter fw;

    public oyuncu_C(anaEkran ekran1) throws IOException {
        this.konumy_C = ekran1.board_boy - 1;
        this.ekran = ekran1;
        this.altin_miktari = ekran1.oyuncu_altin;
        this.hareket_maaliyeti = ekran1.aMaliyet;
        this.adim_sayisi = ekran1.oyuncu_adim_sayisi;
        this.boy = ekran1.board_boy;
        this.genislik = ekran1.board_genislik;
        this.butonlar = new JButton[this.boy][this.genislik];
        this.butonlar = ekran1.butonlar;
        this.maliyetC = ekran1.cMaliyet;
        this.acilacak_altin = ekran1.cGizli_altin_say;
        this.hedefleme_maliyeti = ekran1.cHedef;
        FileWriter output = new FileWriter("outputC.txt");
        this.fw = output;
    }

    public int hedef_bul(JButton[][] jb1, int boy, int genislik) throws InterruptedException {

        jb1 = this.butonlar;
        if (this.elendi_mi) {      //elendiyse hedef bul çalışmadan biter
            return 0;
        }
        int indis = 0;
        int temp;
        int gecicix = 0;
        int geciciy = 0;
        int farkx;
        int farky;
        int geciciM = 9999;
        int maliyet = 0;
        boolean kontrol = false;
        int acilan_altin = 0;

        while (acilan_altin < this.acilacak_altin) {
            geciciM = 9999;
            for (int i = 0; i < boy; i++) {
                for (int j = 0; j < genislik; j++) {
                    if (jb1[i][j].getBackground() == Color.GRAY) {
                        farkx = Math.abs(i - this.konumy_C);
                        farky = Math.abs(j - this.konumx_C);
                        maliyet = farkx + farky;
                        kontrol = true;
                        if (geciciM > (maliyet)) {
                            geciciM = maliyet;
                            geciciy = i;
                            gecicix = j;

                        }
                    }

                }
            }
            if (kontrol && gecicix != 0 && geciciy != 0) {
                jb1[geciciy][gecicix].setBackground(Color.YELLOW);
                kontrol = false;
            }

            // Thread.sleep(200);
            acilan_altin++;
        }

        int geciciy2 = 0;
        int gecicix2 = 0;
        int maliyet2 = 0;
        int geciciM2 = 9999;

        if (jb1[this.guncel_hedef_y][this.guncel_hedef_x].getBackground() != Color.yellow) {    //hedef altın hala mevcut mu kontrolü
            this.hedef_var_mı = false;

        }
        if (this.hedef_var_mı == false) {
            for (int i = 0; i < boy; i++) {
                for (int j = 0; j < genislik; j++) {
                    if (jb1[i][j].getBackground() == Color.YELLOW) {
                        farkx = Math.abs(i - this.konumy_C);
                        farky = Math.abs(j - this.konumx_C);
                        this.hedef_var_mı = true;
                        try {
                            maliyet2 = ((farkx + farky) * maliyetC) - (Integer.parseInt(jb1[i][j].getText()));
                        } catch (Exception e) {
                        }

                        if (geciciM2 > (maliyet2)) {
                            geciciM2 = maliyet2;
                            geciciy2 = i;
                            gecicix2 = j;
                        }
                    }

                }
            }
            if (gecicix2 == 0 && geciciy2 == 0) {
                this.elendi_mi = true;
                jb1[this.konumy_C][this.konumx_C].setBackground(Color.white);
                jb1[this.konumy_C][this.konumx_C].setText("C");
            } else {
                this.harcanan_altin_miktari = this.harcanan_altin_miktari + this.hedefleme_maliyeti;
                this.altin_miktari = this.altin_miktari - this.hedefleme_maliyeti;
                this.guncel_hedef_x = gecicix2;
                this.guncel_hedef_y = geciciy2;
            }
        }

        System.out.println("--------hedef x:" + gecicix2 + "  hedef y:" + geciciy2);

        return 0;

    }

    public int hamle_yap(JButton[][] map, int yapilacak_hamle_sayisi, int konum_x, int konum_y) throws InterruptedException, IOException {

        if (this.elendi_mi) {
            System.out.println("C ELENDİ C ELENDİ");
            return 0;
        }

        if (this.altin_miktari < this.hareket_maaliyeti) {
            this.elendi_mi = true;
            map[konum_y][konum_x].setBackground(Color.white);

            return 0;
        }
        System.out.println("\n altin:" + this.altin_miktari);
        this.altin_miktari = this.altin_miktari - this.hareket_maaliyeti;
        this.altin_miktari = this.altin_miktari - this.hareket_maaliyeti;
        this.harcanan_altin_miktari = this.harcanan_altin_miktari + this.hareket_maaliyeti;

        int hedef_x = this.guncel_hedef_x;
        int hedef_y = this.guncel_hedef_y;
        konum_x = this.konumx_C;
        konum_y = this.konumy_C;
        int yapilan_hamle_sayisi = 0;
        int temp_gizli = 0, temp_gizli_yardimci = 0;
        int temp_x, temp_y;
        System.out.println("CCCCCCC");
        while (yapilan_hamle_sayisi < this.adim_sayisi) {
            Thread.sleep(500);

            if (hedef_y > konum_y) {   // aşağı hareket
                // System.out.println("1. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y + 1][konum_x].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve aşağıda altın varsa
                    // System.out.println("\nif 1 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());//altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C + 1) + "," + oyuncu_C.konumx_C + "\n");
                    this.konumy_C = this.konumy_C + 1;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //   System.out.println("\n 1 2");
                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C + 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = this.konumy_C + 1;
                    this.atilan_adim_sayisi++;
                    konum_y = konum_y + 1;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y + 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //  System.out.println("\n 1 3");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y + 1][konum_x].getText());    //sonraki karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setText("C");                                     //aşağıda gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C + 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = konum_y + 1;                                                       //konumu güncelledik
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y + 1][konum_x].getBackground() == Color.GRAY) {       //sonraki adımda gizli altın varsa 
                    //   System.out.println("\n 1 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y + 1][konum_x].getText()); //gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setText("C");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C + 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = konum_y + 1;                                                       //konumu güncelledik
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y + 1][konum_x].getBackground() == Color.YELLOW) {  //sonraki adımda altın varsa
                    //  System.out.println("\n 1 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());

                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("C");
                    map[konum_y][konum_x].setBackground(Color.WHITE);

                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C + 1) + "," + oyuncu_C.konumx_C + "\n");
                    this.konumy_C = this.konumy_C + 1;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //    System.out.println("\n 1 6");
                    map[konum_y + 1][konum_x].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("C");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C + 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = this.konumy_C + 1;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                }

                yapilan_hamle_sayisi++;

            } else if (hedef_y < konum_y) {    // yukarı hareket
                // System.out.println("2. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y - 1][konum_x].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve aşağıda altın varsa
                    System.out.println("\n 2 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C - 1) + "," + oyuncu_C.konumx_C + "\n");
                    this.konumy_C = this.konumy_C - 1;
                    konum_y = konum_y - 1;
                    temp_gizli = 0;
                    this.atilan_adim_sayisi++;
                    this.hedef_var_mı = false;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //   System.out.println("\n 2 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());    //yukarıda bulunan karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setText("C");                                     //yukarıdaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye hareket etmeye yardımcı temp_gizliyi güncelledik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C - 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = konum_y - 1;                                                       //konumu güncelledik
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //   System.out.println("\n 2 3");
                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C - 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = this.konumy_C - 1;
                    konum_y = konum_y - 1;
                    temp_gizli = 0;                            // temp'i temizledik
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //üstümüzde  gizli altın varsa 
                    //  System.out.println("\n 2 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y - 1][konum_x].getText());      //gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setText("C");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C - 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = konum_y - 1;                                                       //konumu güncelledik
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y - 1][konum_x].getBackground() == Color.YELLOW) {  //üstümüzde altın varsa
                    //    System.out.println("\n 2 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());//altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("C");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C - 1) + "," + oyuncu_C.konumx_C + "\n");
                    this.konumy_C = konum_y - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //   System.out.println("\n 2 6");
                    map[konum_y - 1][konum_x].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("C");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C - 1) + "," + oyuncu_C.konumx_C + " ");
                    this.konumy_C = konum_y - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_x < konum_x) {      // sola hareket
                // System.out.println("3. if");
                if (map[konum_y][konum_y].getBackground() == Color.gray && map[konum_y][konum_x - 1].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve solda altın varsa
                    System.out.println("\n 3 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C - 1) + "\n");
                    this.konumx_C = this.konumx_C - 1;
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;

                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y][konum_x - 1].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //   System.out.println("\n 3 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());  //sol karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setText("C");                                     //soldaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C - 1) + " ");
                    this.konumx_C = konum_x - 1;                                                       //konumu güncelledik
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //  System.out.println("\n 3 3");
                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C - 1) + " ");
                    this.konumx_C = this.konumx_C - 1;
                    konum_x = konum_x - 1;
                    temp_gizli = 0;
                    this.atilan_adim_sayisi++;// temp'i temizledik

                } else if (map[konum_y][konum_x - 1].getBackground() == Color.GRAY) {  //sonraki adımda gizli altın varsa 
                    //  System.out.println("\n 3 4");
                    temp_gizli = Integer.parseInt(map[konum_y][konum_x - 1].getText());     //gizli altın miktarını temp'e koyduk
                    map[konum_y][konum_x - 1].setText("C");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C - 1) + " ");
                    this.konumx_C = konum_x - 1;                                                       //konumu güncelledik
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y][konum_x - 1].getBackground() == Color.YELLOW) {  //sol karede altın varsa
                    //   System.out.println("\n 3 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());  //altın miktarı güncelledik  
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("C");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C - 1) + "\n");
                    this.konumx_C = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    konum_x = konum_x - 1;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //  System.out.println("\n 3 6");
                    map[konum_y][konum_x - 1].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("C");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C - 1) + " ");
                    this.konumx_C = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    konum_x = konum_x - 1;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_x > konum_x) {       //sağa  hareket
                //  System.out.println("4. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y][konum_x + 1].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve sağa  altın varsa
                    //    System.out.println("\n 4 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C + 1) + "\n");
                    this.konumx_C = this.konumx_C + 1;
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y][konum_x + 1].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //  System.out.println("\n 4 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());  //solda karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x + 1].setText("C");                                     //soldaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C + 1) + " ");
                    this.konumx_C = konum_x + 1;
                    this.atilan_adim_sayisi++;//konumu güncelledik
                    konum_x = konum_x + 1;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //   System.out.println("\n 4 3");
                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("C");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C + 1) + " ");
                    this.konumx_C = this.konumx_C + 1;
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y][konum_x + 1].getBackground() == Color.GRAY) {  //sonraki adımda gizli altın varsa 
                    //   System.out.println("\n 4 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y][konum_x + 1].getText());     //gizli altın miktarını temp'e koyduk
                    } catch (NumberFormatException e) {
                    }
                    map[konum_y][konum_x + 1].setText("C");
                    //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C + 1) + " ");
                    this.konumx_C = konum_x + 1;                                                       //konumu güncelledik
                    this.atilan_adim_sayisi++;
                    konum_x = konum_x + 1;

                } else if (map[konum_y][konum_x + 1].getBackground() == Color.YELLOW) {  //sol karede altın varsa
                    System.out.println("\n 4 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());  //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());
                    } catch (NumberFormatException e) {
                    }

                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("C");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C + 1) + "\n");
                    this.konumx_C = this.konumx_C + 1;
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;

                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //  System.out.println("\n 4 6");
                    map[konum_y][konum_x + 1].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("C");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_C.konumy_C + "," + oyuncu_C.konumx_C + "-->" + (oyuncu_C.konumy_C) + "," + (oyuncu_C.konumx_C + 1) + "\n");
                    this.konumx_C = this.konumx_C + 1;
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;
                }

                yapilan_hamle_sayisi++;

            } else {
                this.hedef_var_mı = false;

                return 0;
            }

        }
        this.fw.write("\n");
        return 0;
    }

}
