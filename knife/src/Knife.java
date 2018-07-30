import javax.swing.*;
import java.awt.*;

public class Knife extends Circle implements Commons, Runnable{
    private double rad;
    private boolean inTrucnk = false;


    public Knife() {
        this.x = START_Kife_X;
        this.y = START_Kife_Y;
        a  = -3;
        ImageIcon ic = new ImageIcon("res/res/knifes/Layer " + Board.level + ".png");
        this.image = ic.getImage().getScaledInstance(10, 30, Image.SCALE_DEFAULT);
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
    }


    public boolean isInTrucnk() {
        return inTrucnk;
    }

    public void setInTrucnk(boolean inTrucnk) {
        this.inTrucnk = inTrucnk;
    }

    @Override
    public void run() {
        while (true){
            if (!isInTrucnk()){
                this.setY(this.getY()+ a);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
