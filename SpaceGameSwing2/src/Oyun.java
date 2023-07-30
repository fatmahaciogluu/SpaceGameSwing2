import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Objects;

class Ates{

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int x;
    private int y;

}
public class Oyun extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(5, this); // timer her çalıştığında actionPerformed çalışmış olacak
    private int gecenSure = 0;
    private int harcananAtes = 0;
    private final BufferedImage image;

    private final ArrayList<Ates> atesler = new ArrayList<Ates>();

    private final int atesdirY = 1; //Her actionperformed olduğunda atesler y koordinatlarına eklenecek, böylece atesler hareket etmiş olacak
    private int topX= 0; // Sağa sola gitme için başlangıç 0, sonra arttırılacak böylece hareket etmiş olacak
    private int topdirX = 2; // bu topdirX sağda belli bir limite çarptıktan sonra sola dönecek
    private int uzayGemisiX =0; // başlangıç noktası
    private final int dirUzayX = 20; //klavyede sağ tuşa basılınca kaç birim sağa kayacağını bu değişken belirliyor veya sola..
    public boolean kontrolEt(){
        for (Ates ates : atesler){
            if(new Rectangle(ates.getX(), ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20)))
            {
                //top ve ates farklı şekiller olsalar da ikisini de rectangle yapıp çarpışma oldu mu test edilebilir
                return true; // en az bir ateş çarpsa bile return ile program sonlandırılır
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gecenSure+=5;

        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);

        g.drawImage(image, uzayGemisiX, 490 , image.getWidth()/10, image.getHeight() / 10, this);

        for(Ates ates : atesler){
            if(ates.getY() < 0){
                atesler.remove(ates); //JFrame de çıkan ateşi ateşler arraylistinden sil
            }

        }

        g.setColor(Color.BLUE);

        for(Ates ates : atesler){
            g.fillRect(ates.getX(), ates.getY(),10,20);
        }

        if(kontrolEt()){
            timer.stop();
            String message = "Kazandınız! \n"+
                    "\n Harcanan Ateş = " + harcananAtes +
                    "\n Geçen Süre = " + gecenSure / 1000.0;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() { //repaint çağrılarak paint işlemlerinin tekrar yapılması sağlanacak
        super.repaint();
    }

    public Oyun() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setBackground(Color.BLACK);

        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ates ates : atesler){

            ates.setY(ates.getY() - atesdirY);
        }

        topX += topdirX;

        if (topX>= 750){
            topdirX= -topdirX; //sınıra geldiğinde tersi yönde hareket etsin diye önüne - işareti geldi
        }
        if(topX <=0){
            topdirX = -topdirX;
        }
        repaint(); //her top hareketinde yeniden paint olsun diye çağılına metot

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if(c == KeyEvent.VK_LEFT){
            if(uzayGemisiX <= 0){
                uzayGemisiX =0;
            }
            else {
                uzayGemisiX -= dirUzayX;
            }
        }
        if(c==KeyEvent.VK_RIGHT){
            if(uzayGemisiX >= 750){

                uzayGemisiX=750;
            }
            else {
                uzayGemisiX += dirUzayX;
            }
        }
        else if (c == KeyEvent.VK_CONTROL){

            atesler.add(new Ates(uzayGemisiX + 15,490));

            harcananAtes++;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
