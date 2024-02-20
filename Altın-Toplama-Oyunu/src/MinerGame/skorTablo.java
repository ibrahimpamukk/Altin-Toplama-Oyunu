/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinerGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author foryoul3aby
 */
public class skorTablo {

    public skorTablo() {

    }

    public void skor_tablosu_goruntule() {

        JFrame frame = new JFrame("Skor Tablosu");
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(5, 5));

        JLabel frm[][] = new JLabel[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                frm[i][j] = new JLabel();
                panel.add(frm[i][j]);
            }
        }

        frm[1][0].setText("A");
        frm[1][0].setForeground(Color.YELLOW);
        frm[2][0].setText("B");
        frm[2][0].setForeground(Color.YELLOW);
        frm[3][0].setText("C");
        frm[3][0].setForeground(Color.YELLOW);
        frm[4][0].setText("D");
        frm[4][0].setForeground(Color.YELLOW);

        frm[0][1].setText("Adım Sayısı");
        frm[0][1].setForeground(Color.YELLOW);
        frm[0][2].setText("Harcanan Altın Miktarı");
        frm[0][2].setForeground(Color.YELLOW);
        frm[0][3].setText("Kasadaki Altın Miktarı");
        frm[0][3].setForeground(Color.YELLOW);
        frm[0][4].setText("Toplanan Altın Miktarı");
        frm[0][4].setForeground(Color.YELLOW);

        frm[1][1].setText(String.valueOf(oyuncu_A.atilan_adim_sayisi)); // A nın Adim Sayisi
        frm[1][1].setForeground(Color.YELLOW);
        frm[2][1].setText(String.valueOf(oyuncu_B.atilan_adim_sayisi)); // B nin Adim Sayisi
        frm[2][1].setForeground(Color.YELLOW);
        frm[3][1].setText(String.valueOf(oyuncu_C.atilan_adim_sayisi)); // C nin Adim Sayisi
        frm[3][1].setForeground(Color.YELLOW);
        frm[4][1].setText(String.valueOf(oyuncu_D.atilan_adim_sayisi)); // D nin Adim Sayisi
        frm[4][1].setForeground(Color.YELLOW);

        frm[1][2].setText(String.valueOf(oyuncu_A.harcanan_altin_miktari)); // A nın Harcanan Altın Miktarı
        frm[1][2].setForeground(Color.YELLOW);
        frm[2][2].setText(String.valueOf(oyuncu_B.harcanan_altin_miktari)); // B nin Harcanan Altın Miktarı
        frm[2][2].setForeground(Color.YELLOW);
        frm[3][2].setText(String.valueOf(oyuncu_C.harcanan_altin_miktari)); // C nin Harcanan Altın Miktarı
        frm[3][2].setForeground(Color.YELLOW);
        frm[4][2].setText(String.valueOf(oyuncu_D.harcanan_altin_miktari)); // D nin Harcanan Altın Miktarı
        frm[4][2].setForeground(Color.YELLOW);

        frm[1][3].setText(String.valueOf(oyuncu_A.altin_miktari)); // A nın Kasadaki Altın Miktarı
        frm[1][3].setForeground(Color.YELLOW);
        frm[2][3].setText(String.valueOf(oyuncu_B.altin_miktari)); // B nin Kasadaki Altın Miktarı
        frm[2][3].setForeground(Color.YELLOW);
        frm[3][3].setText(String.valueOf(oyuncu_C.altin_miktari)); // C nin Kasadaki Altın Miktarı
        frm[3][3].setForeground(Color.YELLOW);
        frm[4][3].setText(String.valueOf(oyuncu_D.atilan_adim_sayisi)); // D nin Kasadaki Altın Miktarı
        frm[4][3].setForeground(Color.YELLOW);

        frm[1][4].setText(String.valueOf(oyuncu_A.toplanan_altin_miktari)); // A nın Toplanan Altın Miktarı
        frm[1][4].setForeground(Color.YELLOW);
        frm[2][4].setText(String.valueOf(oyuncu_B.toplanan_altin_miktari)); // A nın Toplanan Altın Miktarı
        frm[2][4].setForeground(Color.YELLOW);
        frm[3][4].setText(String.valueOf(oyuncu_C.toplanan_altin_miktari)); // A nın Toplanan Altın Miktarı
        frm[3][4].setForeground(Color.YELLOW);
        frm[4][4].setText(String.valueOf(oyuncu_D.toplanan_altin_miktari)); // A nın Toplanan Altın Miktarı
        frm[4][4].setForeground(Color.YELLOW);

        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
