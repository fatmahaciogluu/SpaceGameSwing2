import javax.swing.*;
import java.awt.*;

public class OyunEkrani extends JFrame {

    public OyunEkrani(String title) throws HeadlessException{
        super(title);
    }
    public static void main(String[] args) {
        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");

        ekran.setResizable(false);
        ekran.setFocusable(false); //Oyun ekranından focus alındı

        ekran.setSize(800, 600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Oyun oyun =new Oyun();
        oyun.requestFocus(); // klavye işlemlerini anlamasi için
        oyun.addKeyListener(oyun); //klavye işlemlerini anlaması için

        oyun.setFocusable(true); //Oyun PANELine focus verildi
        oyun.setFocusTraversalKeysEnabled(false);

        ekran.add(oyun);

        ekran.setVisible(true);

    }
}
