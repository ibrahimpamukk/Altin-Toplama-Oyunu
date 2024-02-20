/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinerGame;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;

public class oyuncu_A {

    public static int konumx_A = 0;
    public static int konumy_A = 0;
    public static int guncel_hedef_x = 0;
    public static int guncel_hedef_y = 0;
    public static int altin_miktari = 0;
    private int hareket_maaliyeti;
    private int adim_sayisi;
    public static int atilan_adim_sayisi = 0;
    public static int harcanan_altin_miktari = 0;
    public static int toplanan_altin_miktari = 0;
    private int hedefleme_maliyeti;

    public FileWriter fw;

    public JButton[][] butonlar;      //yan classdan haritayı alıcaz
    //private JButton hedef_kare;                     //hedef kareyi tüm classda kullanabilmek için yarattığımız değişken
    private int boy;
    private int genislik;
    private anaEkran ekran;
    public boolean elendi_mi = false;
    boolean hedef_var_mı = false;

    public oyuncu_A(anaEkran ekran1) throws IOException {         //ana classdan argüman olarak oyunu başlatma ayarlarını aldık

        // this.butonlar[5][5] = ekran1.butonlar[5][5];
        // anaEkran ekran2 = new anaEkran();
        butonlar = ekran1.butonlar;
        this.ekran = ekran1;
        this.altin_miktari = ekran.oyuncu_altin;
        this.hareket_maaliyeti = ekran.aMaliyet;
        this.adim_sayisi = ekran.oyuncu_adim_sayisi;
        this.boy = ekran.board_boy;
        this.genislik = ekran.board_genislik;
        this.butonlar = new JButton[this.boy][this.genislik];
        this.butonlar = ekran.butonlar;
        this.hedefleme_maliyeti = ekran1.aHedef;
        FileWriter output = new FileWriter("outputA.txt");
        this.fw = output;

        //fw = new BufferedWriter(fw);
        // System.out.println("\naltın miktarı:" + this.altin_miktari + "  hareket maaliyeti:" + this.hareket_maaliyeti + "  adim sayisi:" + this.adim_sayisi + "  boy:" + this.boy + "  genislik:" + this.genislik);
        //System.out.println("\n renk: " + this.butonlar[5][5].getText());
        //  System.out.println("\n renk2:" + ekran.butonlar[5][5].getBackground());
    }

    public int hedef_bul(JButton[][] jb1, int boy, int genislik) {

        
        if (this.elendi_mi) {      //elendiyse hedef bul çalışmadan biter
            return 0;
        }
        //System.out.println("lkıjegdrgşolrthşltryhşty");
        jb1 = this.butonlar;
        int indis = 0;
        int temp;
        int gecicix = 0;
        int geciciy = 0;
        int farkx;
        int farky;
        int geciciM = 9999;
        int maliyet;
        //System.out.println(jb1[5][5].getBackground() + "ooooooo");

        if (jb1[this.guncel_hedef_y][this.guncel_hedef_x].getBackground() != Color.yellow) {    //hedef altın başka oyuncu tarafından alındı mı kontrolu

            this.hedef_var_mı = false;

        }

        if (this.hedef_var_mı == false) {      //hedef yoksa hedef arama işlemi gerçekleşir
            for (int i = 0; i < boy; i++) {
                for (int j = 0; j < genislik; j++) {

                    if (jb1[i][j].getBackground() == Color.YELLOW) {
                        farkx = Math.abs(j - this.konumx_A);
                        farky = Math.abs(i - this.konumy_A);
                        maliyet = farkx + farky;
                        this.hedef_var_mı = true;

                        if (geciciM > (maliyet)) {
                            geciciM = maliyet;
                            geciciy = i;
                            gecicix = j;
                            // System.out.println("\n döngü içi hedef x:" + gecicix + " döngü içi hedef y:" + geciciy);

                        }
                    }

                }
            }
            if (gecicix == 0 && geciciy == 0) {
                this.elendi_mi = true;
                jb1[this.konumy_A][this.konumx_A].setBackground(Color.white);
                jb1[this.konumy_A][this.konumx_A].setText("A");
            } else {

                this.harcanan_altin_miktari = this.harcanan_altin_miktari + this.hedefleme_maliyeti;
                this.altin_miktari = this.altin_miktari - this.hedefleme_maliyeti;
                this.guncel_hedef_x = gecicix;
                this.guncel_hedef_y = geciciy;
            }

        }

        System.out.println("\nhedef x :" + this.guncel_hedef_x + " hedef y:" + this.guncel_hedef_y);

        return 0;
    }

    public int hamle_yap(JButton[][] map, int yapilacak_hamle_sayisi, int konum_x, int konum_y) throws InterruptedException, IOException {

        if (this.elendi_mi) {
            System.out.println("A ELENDİ A ELENDİ");
            return 0;
        }
        if (this.altin_miktari < this.hareket_maaliyeti) {
            this.elendi_mi = true;
            map[konum_y][konum_x].setBackground(Color.white);
            return 0;
        }
        System.out.println("\n altin:" + this.altin_miktari);
        this.altin_miktari = this.altin_miktari - this.hareket_maaliyeti;
        this.harcanan_altin_miktari = this.harcanan_altin_miktari + this.hareket_maaliyeti;

        int hedef_x = this.guncel_hedef_x;
        int hedef_y = this.guncel_hedef_y;
        konum_x = this.konumx_A;
        konum_y = this.konumy_A;
        int yapilan_hamle_sayisi = 0;
        int temp_gizli = 0, temp_gizli_yardimci = 0;
        int temp_x, temp_y;
        System.out.println("AAAAAA");
        while (yapilan_hamle_sayisi < this.adim_sayisi) {
            Thread.sleep(500);

            if (hedef_y > konum_y) {   // aşağı hareket
                //  System.out.println("1. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y + 1][konum_x].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve aşağıda altın varsa
                    // System.out.println("\nif 1 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());//altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y + 1][konum_x].getText()));
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A + 1) + "," + oyuncu_A.konumx_A + "\n");
                    this.konumy_A = this.konumy_A + 1;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //    System.out.println("\n 1 2");
                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A + 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = this.konumy_A + 1;
                    this.atilan_adim_sayisi++;
                    konum_y = konum_y + 1;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y + 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //  System.out.println("\n 1 3");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y + 1][konum_x].getText());    //sonraki karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setText("A");                                     //aşağıda gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik
                                     

                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A + 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = konum_y + 1;                                                       //konumu güncelledik
                    konum_y = konum_y + 1;
                } else if (map[konum_y + 1][konum_x].getBackground() == Color.GRAY) {       //sonraki adımda gizli altın varsa 
                    //   System.out.println("\n 1 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y + 1][konum_x].getText()); //gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setText("A");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");

                    this.atilan_adim_sayisi++;
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A + 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = konum_y + 1;                                                       //konumu güncelledik
                    konum_y = konum_y + 1;

                } else if (map[konum_y + 1][konum_x].getBackground() == Color.YELLOW) {  //sonraki adımda altın varsa
                    System.out.println("\n 1 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText()); //altın miktarı güncelledik
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y + 1][konum_x].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("A");
                    map[konum_y][konum_x].setBackground(Color.WHITE);

                    map[konum_y][konum_x].setText("");
                    this.atilan_adim_sayisi++;
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A + 1) + "," + oyuncu_A.konumx_A + "\n");
                    this.konumy_A = konum_y + 1;
                    konum_y++;
                    this.hedef_var_mı = false;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    // System.out.println("\n 1 6");
                    map[konum_y + 1][konum_x].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("A");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                   

                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A + 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = this.konumy_A + 1;

                    this.atilan_adim_sayisi++;
                    konum_y = konum_y + 1;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_y < konum_y) {    // yukarı hareket
                //System.out.println("2. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y - 1][konum_x].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve aşağıda altın varsa
                    // System.out.println("\n 2 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText()); //altın miktarı güncelledik
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y - 1][konum_x].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A - 1) + "," + oyuncu_A.konumx_A + " \n");
                    this.konumy_A = this.konumy_A - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //  System.out.println("\n 2 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());    //yukarıda bulunan karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setText("A");                                     //yukarıdaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye hareket etmeye yardımcı temp_gizliyi güncelledik
                                   
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A - 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = konum_y - 1;                                                       //konumu güncelledik
                    this.atilan_adim_sayisi++;
                    konum_y = konum_y - 1;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //  System.out.println("\n 2 3");
                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A - 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = this.konumy_A - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //üstümüzde  gizli altın varsa 
                    //   System.out.println("\n 2 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y - 1][konum_x].getText());      //gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_y].setText("A");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                   
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A - 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = konum_y - 1;                                                       //konumu güncelledik
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y - 1][konum_y].getBackground() == Color.YELLOW) {  //üstümüzde altın varsa
                    //   System.out.println("\n 2 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());//altın miktarı güncelledik
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y - 1][konum_x].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("A");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A - 1) + "," + oyuncu_A.konumx_A + " \n");
                    this.konumy_A = konum_y - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //   System.out.println("\n 2 6");
                    map[konum_y - 1][konum_x].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("A");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                   
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + (oyuncu_A.konumy_A - 1) + "," + oyuncu_A.konumx_A + "  ");
                    this.konumy_A = konum_y - 1;
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
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y][konum_x - 1].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());

                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A - 1) + " \n");
                    this.konumx_A = this.konumx_A - 1;
                    konum_x = konum_x - 1;
                    temp_gizli = 0;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    this.atilan_adim_sayisi++;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    System.out.println("\n 3 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());  //sol karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setText("A");                                     //soldaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik
                                      
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A - 1) + "  ");
                    this.konumx_A = konum_x - 1;                                                       //konumu güncelledik
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_x][konum_y].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    // System.out.println("\n 3 3");
                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A - 1) + "  ");
                    this.konumx_A = konum_x - 1;
                    konum_x = konum_x - 1;
                    temp_gizli = 0;                            // temp'i temizledik
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y][konum_x - 1].getBackground() == Color.GRAY) {  //sonraki adımda gizli altın varsa 
                    //  System.out.println("\n 3 4");
                    temp_gizli = Integer.parseInt(map[konum_y][konum_x - 1].getText());     //gizli altın miktarını temp'e koyduk
                    map[konum_y][konum_x - 1].setText("A");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                    
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A - 1) + "  ");
                    this.konumx_A = konum_x - 1;                                                       //konumu güncelledik
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y][konum_x - 1].getBackground() == Color.YELLOW) {  //sol karede altın varsa
                    //   System.out.println("\n 3 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());  //altın miktarı güncelledik  
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y][konum_x - 1].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("A");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A - 1) + "\n ");
                    this.konumx_A = konum_x - 1;
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //   System.out.println("\n 3 6");
                    map[konum_y][konum_x - 1].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("A");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                    
                    this.atilan_adim_sayisi++;
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A - 1) + " \n");
                    this.konumx_A = konum_x - 1;
                    konum_x = konum_x - 1;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_x > konum_x) {       //sağa  hareket
                // System.out.println("4. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y][konum_x + 1].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve sağa  altın varsa
                    //   System.out.println("\n 4 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText()); //altın miktarı güncelledik
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y][konum_x + 1].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A + 1) + " \n");
                    this.konumx_A = this.konumx_A + 1;
                    konum_x = konum_x + 1;
                    temp_gizli = 0;
                    this.atilan_adim_sayisi++;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y][konum_x + 1].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //   System.out.println("\n 4 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());  //solda karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x + 1].setText("A");                                     //soldaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik
                                
                    this.atilan_adim_sayisi++;
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A + 1) + "  ");
                    this.konumx_A = konum_x + 1;                                                       //konumu güncelledik
                    konum_x = konum_x + 1;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //   System.out.println("\n 4 3");
                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("A");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                  
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A + 1) + "  ");
                    this.konumx_A = this.konumx_A + 1;
                    this.atilan_adim_sayisi++;
                    konum_x = konum_x + 1;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y][konum_x + 1].getBackground() == Color.GRAY) {  //sonraki adımda gizli altın varsa 
                    //   System.out.println("\n 4 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y][konum_x + 1].getText());     //gizli altın miktarını temp'e koyduk
                    } catch (NumberFormatException e) {
                    }
                    map[konum_y][konum_x + 1].setText("A");
                    //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                  
                    this.atilan_adim_sayisi++;
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A + 1) + "  ");
                    this.konumx_A = konum_x + 1;                                                       //konumu güncelledik
                    konum_x = konum_x + 1;

                } else if (map[konum_y][konum_x + 1].getBackground() == Color.YELLOW) {  //sağ karede altın varsa
                    //   System.out.println("\n 4 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());  //altın miktarı güncelledik
                        System.out.println("\naltin toplandı +" + Integer.parseInt(map[konum_y][konum_x + 1].getText()));
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());
                    } catch (NumberFormatException e) {
                    }

                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("A");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A + 1) + " \n");
                    this.konumx_A = konum_x + 1;
                    this.atilan_adim_sayisi++;
                    konum_x = konum_x + 1;
                    this.hedef_var_mı = !this.hedef_var_mı;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    // System.out.println("\n 4 6");
                    map[konum_y][konum_x + 1].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("A");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");
                   
                    this.fw.write(oyuncu_A.konumy_A + "," + oyuncu_A.konumx_A + "-->" + oyuncu_A.konumy_A + "," + (oyuncu_A.konumx_A + 1) + "  ");
                    this.atilan_adim_sayisi++;
                    this.konumx_A = this.konumx_A + 1;
                    konum_x = konum_x + 1;
                }

                yapilan_hamle_sayisi++;

            } else {
                this.hedef_var_mı = false;
                System.out.println("\n A oyuncu konum y:" + konum_x + " oyuncu konum x:" + konum_y);
              
                return 0;
            }

        }

        this.fw.write("\n");

        return 0;
    }

}
