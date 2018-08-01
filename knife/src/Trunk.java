import javax.swing.*;
import java.awt.*;

public class Trunk extends Circle implements Commons, Runnable {
    private double Bx_str =Trunk_X+r_trunk;
    private double By_str =Trunk_Y;
    Apple apple;
    private boolean fireDown = false;
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


    public double getBx_str() {
        return Bx_str;
    }

    public void setBx_str(int bx_str) {
        Bx_str = bx_str;
    }

    public double getBy_str() {
        return By_str;
    }

    public void setBy_str(int by_str) {
        By_str = by_str;
    }

    public boolean isFireDown() {
        return fireDown;
    }

    public void setFireDown(boolean fireDown) {
        this.fireDown = fireDown;
    }

    @Override
    public void run() {
        while (this.getY() < 500){
            this.setX(this.getX() + 0.5);

            this.setY(this.getY() + 2.5);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setDestroy(true);

        }
    }

    public class Apple extends Circle implements Runnable{
        boolean fire = false;
        public Apple(double x, double y){
            this.x = x;
            this.y = y;

            ImageIcon ic = new ImageIcon("res/Apple.png");
            this.image = ic.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
        }

        public boolean isFire() {
            return fire;
        }

        public void setFire(boolean fire) {
            this.fire = fire;
        }

        @Override
        public void run() {
            while (this.getY() < 500) {
                this.setX(apple.getX() + 0.3);

                this.setY(apple.getY() + 2);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.setDestroy(true);
        }
    }
}
