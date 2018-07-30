import javax.swing.*;
import java.awt.*;

public class Trunk extends Circle implements Commons, Runnable {
    private int Bx_str =Trunk_X+r_trunk;
    private int By_str =Trunk_Y;
    Apple apple;

    public Trunk(){
        this.x = Trunk_X;
        this.y = Trunk_Y;
        ImageIcon ic = new ImageIcon("res/res/trunk/trunk"+ Board.level+".png");
        this.setImage(ic.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        apple = new Apple(Bx_str,By_str);

    }


    public Apple getApple(){
        return apple;
    }


    public int getBx_str() {
        return Bx_str;
    }

    public void setBx_str(int bx_str) {
        Bx_str = bx_str;
    }

    public int getBy_str() {
        return By_str;
    }

    public void setBy_str(int by_str) {
        By_str = by_str;
    }

    @Override
    public void run() {

    }

    public class Apple extends Circle{
        public Apple(int x, int y){
            this.x = x;
            this.y = y;

            ImageIcon ic = new ImageIcon("res/Apple.png");
            this.image = ic.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
        }
    }
}
