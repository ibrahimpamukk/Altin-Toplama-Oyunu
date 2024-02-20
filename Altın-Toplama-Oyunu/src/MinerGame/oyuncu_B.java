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
public class oyuncu_B {

    anaEkran ekran1 = new anaEkran();
    public static int konumx_B;
    public static int konumy_B = 0;
    private int maliyetB;

    public static int guncel_hedef_x = 0;
    public static int guncel_hedef_y = 0;
    public static int altin_miktari = 0;
    private int hareket_maaliyeti;
    private int adim_sayisi;
    public JButton[][] butonlar;      //yan classdan haritayı alıcaz
    //private JButton hedef_kare;                     //hedef kareyi tüm classda kullanabilmek için yarattığımız değişken
    private int boy;
    private int genislik;
    private anaEkran ekran;
    public static int atilan_adim_sayisi = 0;
    public static int toplanan_altin_miktari = 0;
    public boolean elendi_mi = false;
    boolean hedef_var_mı = false;
    public static int harcanan_altin_miktari = 0;
    private int hedefleme_maliyeti;
    public FileWriter fw;

    public oyuncu_B(anaEkran ekran1) throws IOException {
        //anaEkran ekran2 = new anaEkran();

        // butonlar = ekran2.butonlar;
        this.konumx_B = ekran1.board_genislik - 1;
        this.ekran = ekran1;
        this.altin_miktari = ekran.oyuncu_altin;
        this.hareket_maaliyeti = ekran.aMaliyet;
        this.adim_sayisi = ekran.oyuncu_adim_sayisi;
        this.boy = ekran.board_boy;
        this.genislik = ekran.board_genislik;
        this.butonlar = new JButton[this.boy][this.genislik];
        this.butonlar = ekran.butonlar;
        this.maliyetB = ekran.bMaliyet;
        this.hedefleme_maliyeti = ekran.bHedef;
        FileWriter output = new FileWriter("outputB.txt");
        this.fw = output;
    }

    public int hedef_bul(JButton[][] jb1, int boy, int genislik) {

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
        jb1 = this.butonlar;

        if (jb1[oyuncu_B.guncel_hedef_y][oyuncu_B.guncel_hedef_x].getBackground() != Color.yellow) {    //hedef altın başka oyuncu tarafından alındı mı kontrolu
            this.hedef_var_mı = false;

        }
        if (this.hedef_var_mı == false) {
            for (int i = 0; i < boy; i++) {
                for (int j = 0; j < genislik; j++) {
                    if (jb1[i][j].getBackground() == Color.YELLOW) {
                        farkx = Math.abs(i - this.konumy_B);
                        farky = Math.abs(j - this.konumx_B);
                        this.hedef_var_mı = true;
                        try {
                            maliyet = ((farkx + farky) * maliyetB) - (Integer.parseInt(jb1[i][j].getText()));
                        } catch (Exception e) {
                        }

                        if (geciciM > (maliyet)) {
                            geciciM = maliyet;
                            geciciy = i;
                            gecicix = j;
                        }
                    }

                }
            }
            if (geciciy == 0 && gecicix == 0) {
                this.elendi_mi = true;
                jb1[this.konumy_B][this.konumx_B].setBackground(Color.white);
                jb1[this.konumy_B][this.konumx_B].setText("B");
            } else {
                this.harcanan_altin_miktari = this.harcanan_altin_miktari + this.hedefleme_maliyeti;
                this.altin_miktari = this.altin_miktari - this.hedefleme_maliyeti;
                this.guncel_hedef_x = gecicix;
                this.guncel_hedef_y = geciciy;
            }

        }

        System.out.println("hedef x:" + gecicix + "  hedef y:" + geciciy);

        return 0;

    }

    public int hamle_yap(JButton[][] map, int yapilacak_hamle_sayisi, int konum_x, int konum_y) throws InterruptedException, IOException {

        // Thread.sleep(300);
        if (this.elendi_mi) {
            System.out.println("B ELENDİ B ELENDi");
            return 0;
        }
        if (this.altin_miktari < this.hareket_maaliyeti) {
            this.elendi_mi = true;
            map[konum_y][konum_x].setBackground(Color.white);
        }
        this.altin_miktari = this.altin_miktari - this.hareket_maaliyeti;
        this.harcanan_altin_miktari = this.harcanan_altin_miktari + this.hareket_maaliyeti;
        System.out.println("\n altin:" + this.altin_miktari);

        int hedef_x = this.guncel_hedef_x;
        int hedef_y = this.guncel_hedef_y;
        konum_x = this.konumx_B;
        konum_y = this.konumy_B;
        int yapilan_hamle_sayisi = 0;
        int temp_gizli = 0, temp_gizli_yardimci = 0;
        int temp_x, temp_y;
        System.out.println("BBBBBB");
        System.out.println("konum y:" + konum_x + "  konum x:" + konum_y);
        while (yapilan_hamle_sayisi < this.adim_sayisi) {
            Thread.sleep(500);

            if (hedef_y > konum_y) {   // aşağı hareket
                //  System.out.println("1. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y + 1][konum_x].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve aşağıda altın varsa
                    //    System.out.println("\nif 1 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());//altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B + 1) + "," + oyuncu_B.konumx_B + "\n");
                    this.konumy_B = this.konumy_B + 1;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;
                    this.hedef_var_mı = !this.hedef_var_mı;

                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //    System.out.println("\n 1 2");
                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B + 1) + "," + oyuncu_B.konumx_B + " ");
                    this.konumy_B = this.konumy_B + 1;
                    this.atilan_adim_sayisi++;
                    konum_y = konum_y + 1;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y + 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //   System.out.println("\n 1 3");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y + 1][konum_x].getText());    //sonraki karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setText("B");                                     //aşağıda gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B + 1) + "," + oyuncu_B.konumx_B + " ");
                    this.konumy_B = konum_y + 1;                                                       //konumu güncelledik
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y + 1][konum_x].getBackground() == Color.GRAY) {       //sonraki adımda gizli altın varsa 
                    //    System.out.println("\n 1 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y + 1][konum_x].getText()); //gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setText("B");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B + 1) + "," + oyuncu_B.konumx_B + "  ");

                    this.konumy_B = konum_y + 1;                                                       //konumu güncelledik
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y + 1][konum_x].getBackground() == Color.YELLOW) {  //sonraki adımda altın varsa
                    //    System.out.println("\n 1 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y + 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y + 1][konum_x].setBackground(Color.blue);
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("B");
                    map[konum_y][konum_x].setBackground(Color.WHITE);

                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B + 1) + "," + oyuncu_B.konumx_B + "\n");
                    this.konumy_B = this.konumy_B + 1;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //   System.out.println("\n 1 6");
                    map[konum_y + 1][konum_x].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y + 1][konum_x].setForeground(Color.white);
                    map[konum_y + 1][konum_x].setText("B");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B + 1) + "," + oyuncu_B.konumx_B + " ");
                    this.konumy_B = this.konumy_B + 1;
                    konum_y = konum_y + 1;
                    this.atilan_adim_sayisi++;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_y < konum_y) {    // yukarı hareket
                //   System.out.println("2. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y - 1][konum_x].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve aşağıda altın varsa
                    //  System.out.println("\n 2 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.konumy_B = this.konumy_B - 1;
                    konum_y = konum_y - 1;
                    temp_gizli = 0;
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B - 1) + "," + oyuncu_B.konumx_B + "\n");
                    this.atilan_adim_sayisi++;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //    System.out.println("\n 2 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());    //yukarıda bulunan karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setText("B");                                     //yukarıdaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye hareket etmeye yardımcı temp_gizliyi güncelledik

                    this.konumy_B = konum_y - 1;
                    this.atilan_adim_sayisi++;//konumu güncelledik
                    konum_y = konum_y - 1;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //   System.out.println("\n 2 3");
                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B - 1) + "," + oyuncu_B.konumx_B + " ");
                    this.konumy_B = this.konumy_B - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;                            // temp'i temizledik

                } else if (map[konum_y - 1][konum_x].getBackground() == Color.GRAY) {  //üstümüzde  gizli altın varsa 
                    //   System.out.println("\n 2 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y - 1][konum_x].getText());      //gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setText("B");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");

                    this.konumy_B = konum_y - 1;
                    this.atilan_adim_sayisi++;//konumu güncelledik
                    konum_y = konum_y - 1;

                } else if (map[konum_y - 1][konum_x].getBackground() == Color.YELLOW) {  //üstümüzde altın varsa
                    //   System.out.println("\n 2 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());//altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y - 1][konum_x].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y - 1][konum_x].setBackground(Color.blue);
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("B");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B - 1) + "," + oyuncu_B.konumx_B + "\n");
                    this.konumy_B = konum_y - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //  System.out.println("\n 2 6");
                    map[konum_y - 1][konum_x].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y - 1][konum_x].setForeground(Color.white);
                    map[konum_y - 1][konum_x].setText("B");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B - 1) + "," + oyuncu_B.konumx_B + " ");
                    this.konumy_B = konum_y - 1;
                    konum_y = konum_y - 1;
                    this.atilan_adim_sayisi++;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_x < konum_x) {      // sola hareket
                //  System.out.println("3. if");
                if (map[konum_y][konum_y].getBackground() == Color.gray && map[konum_y][konum_x - 1].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve solda altın varsa
                    //    System.out.println("\n 3 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B - 1) + "\n");
                    this.konumx_B = this.konumx_B - 1;
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    temp_gizli = 0;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y][konum_x - 1].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //  System.out.println("\n 3 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());  //sol karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setText("B");                                     //soldaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B - 1) + " ");
                    this.konumx_B = konum_x - 1;                                                       //konumu güncelledik
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //    System.out.println("\n 3 3");
                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B - 1) + " ");
                    this.konumx_B = this.konumx_B - 1;
                    konum_x = konum_x - 1;
                    temp_gizli = 0;                            // temp'i temizledik
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y][konum_x - 1].getBackground() == Color.GRAY) {  //sonraki adımda gizli altın varsa 
                    //   System.out.println("\n 3 4");
                    temp_gizli = Integer.parseInt(map[konum_y][konum_x - 1].getText());     //gizli altın miktarını temp'e koyduk
                    map[konum_y][konum_x - 1].setText("B");                                   //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B - 1) + " ");
                    this.konumx_B = konum_x - 1;                                                       //konumu güncelledik
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    // return 0;
                } else if (map[konum_y][konum_x - 1].getBackground() == Color.YELLOW) {  //sol karede altın varsa
                    //    System.out.println("\n 3 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());  //altın miktarı güncelledik  
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x - 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x - 1].setBackground(Color.blue);
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("B");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B - 1) + "\n");
                    this.konumx_B = konum_x - 1;
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    //   System.out.println("\n 3 6");
                    map[konum_y][konum_x - 1].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y][konum_x - 1].setForeground(Color.white);
                    map[konum_y][konum_x - 1].setText("B");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B - 1) + " ");
                    this.konumx_B = konum_x - 1;
                    konum_x = konum_x - 1;
                    this.atilan_adim_sayisi++;

                }

                yapilan_hamle_sayisi++;

            } else if (hedef_x > konum_x) {       //sağa  hareket
                // System.out.println("4. if");
                if (map[konum_y][konum_x].getBackground() == Color.gray && map[konum_y][konum_x + 1].getBackground() == Color.yellow) {  //gizli altından harekete geçicek ve sağa  altın varsa
                    //   System.out.println("\n 4 1");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText()); //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B + 1) + "\n");
                    this.konumx_B = this.konumx_B + 1;
                    konum_x = konum_x + 1;
                    temp_gizli = 0;
                    this.atilan_adim_sayisi++;
                    return 0;
                } else if (map[konum_y][konum_x].getBackground() == Color.GRAY && map[konum_y][konum_x + 1].getBackground() == Color.GRAY) {  //gizli altın üstünden gizli altına hareket edicekse
                    //  System.out.println("\n 4 2");
                    try {
                        temp_gizli_yardimci = Integer.parseInt(map[konum_y - 1][konum_x].getText());  //solda karedeki gizli altın miktarını temp'e koyduk
                    } catch (Exception e) {
                    }

                    map[konum_y][konum_x + 1].setText("B");                                     //soldaki gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.yellow);                           //şuanki karemize altın rengi koyduk
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));                //şuanki gizli altın karemize altın miktarını geri koyduk
                    temp_gizli = temp_gizli_yardimci;                                      //griden başka kareye harekete yardımcı temp_gizliyi güncelledik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B + 1) + " ");
                    this.konumx_B = konum_x + 1;                                                       //konumu güncelledik
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;
                } else if (map[konum_y][konum_x].getBackground() == Color.gray) {  //eğer oyuncu gizli altının üstünden boş kareye harekete geçicekse
                    //   System.out.println("\n 4 3");
                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("B");
                    map[konum_y][konum_x].setText(String.valueOf(temp_gizli));     //gizli altından çıkarken eski değerini yerine koyduk
                    map[konum_y][konum_x].setBackground(Color.yellow);                 //gizliyi açık altın haline getirdik

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B + 1) + " ");
                    this.konumx_B = this.konumx_B + 1;
                    konum_x = konum_x + 1;
                    temp_gizli = 0;                            // temp'i temizledik
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y][konum_x + 1].getBackground() == Color.GRAY) {  //sonraki adımda gizli altın varsa 
                    //   System.out.println("\n 4 4");
                    try {
                        temp_gizli = Integer.parseInt(map[konum_y][konum_x + 1].getText());     //gizli altın miktarını temp'e koyduk
                    } catch (NumberFormatException e) {
                    }
                    map[konum_y][konum_x + 1].setText("B");
                    //gizli altın karesine oyuncu adını yazdık
                    map[konum_y][konum_x].setBackground(Color.WHITE);                        //şuanki karemizi boşa çıkardık
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B + 1) + " ");
                    this.konumx_B = konum_x + 1;                                                       //konumu güncelledik
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;

                } else if (map[konum_y][konum_x + 1].getBackground() == Color.YELLOW) {  //sol karede altın varsa
                    //   System.out.println("\n 4 5");
                    try {
                        this.altin_miktari = this.altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());  //altın miktarı güncelledik
                        this.toplanan_altin_miktari = this.toplanan_altin_miktari + Integer.parseInt(map[konum_y][konum_x + 1].getText());
                    } catch (NumberFormatException e) {
                    }

                    map[konum_y][konum_x + 1].setBackground(Color.blue);
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("B");
                    map[konum_y][konum_x].setBackground(Color.WHITE);
                    map[konum_y][konum_x].setText("");
                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B + 1) + "\n");
                    this.konumx_B = this.konumx_B + 1;
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;
                    return 0;               //altını bulduk başka hareket yapmıyoruz

                } else {
                    // System.out.println("\n 4 6");
                    map[konum_y][konum_x + 1].setBackground(Color.blue);  //boş kareden boş kareye hareket
                    map[konum_y][konum_x + 1].setForeground(Color.white);
                    map[konum_y][konum_x + 1].setText("B");
                    map[konum_y][konum_x].setBackground(Color.white);
                    map[konum_y][konum_x].setText("");

                    this.fw.write(oyuncu_B.konumy_B + "," + oyuncu_B.konumx_B + "-->" + (oyuncu_B.konumy_B) + "," + (oyuncu_B.konumx_B + 1) + " ");
                    this.konumx_B = this.konumx_B + 1;
                    konum_x = konum_x + 1;
                    this.atilan_adim_sayisi++;
                }

                yapilan_hamle_sayisi++;

            } else {

                this.hedef_var_mı = false;
                System.out.println("\n B oyuncu konum y:" + konum_x + " oyuncu konum x:" + konum_y);
                System.out.println("ehherhe");
                return 0;
            }

        }

        this.fw.write("\n");
        return 0;
    }

}
