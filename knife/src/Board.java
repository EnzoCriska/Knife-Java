
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board extends JPanel implements Commons, Runnable{
    private Knife knife;
    private Trunk trunk;
    private Trunk.Apple apple;
    private Thread thread, thread2;
    boolean ingame = true;
    static int radian;
    private int score, app, player;
    private  static ArrayList<Knife> list;
    int kdao = 0, play = 1;
    public static int  level = 1;
    String notic;
    SQLiteJDBC sqLiteJDBC;
    private Map<String, Integer> map = new HashMap<String, Integer>();

    public Board(){
        addKeyListener(new  AdapterKey());
        setFocusable(true);
        setSize(BOARD_WIDTH,BOARD_HEIGHT);
        setBackground(Color.BLACK);


    }

    public void boardInit(){
        radian = 180;
        knife = new Knife();
        trunk = new Trunk();
        apple = trunk.getApple();
        list = new ArrayList<>();

        thread = new Thread(this);
        thread2 = new Thread(trunk);
        thread.start();
        thread2.start();

    }

    public void addNotify(){
        super.addNotify();
        boardInit();
    }

    public void paint(Graphics g){
        super.paint(g);
        radian++;

        g.setColor(Color.cyan);
        g.drawString("Score: " + score, 30, 50);
        g.drawString("Level: " + level, 120, 70);
        g.drawString("Apple: " + app, 200, 50);


//        for(Knife knif : list){
        int j = 0;
        while (j < list.size()){
            Knife knif = list.get(j);
//            System.out.println("-----------" + list.get(list.size()-1).getX() + ":" +list.get(list.size()-1).getY());

            if (knif.isVisiable){
                BufferedImage knifStr = loadImage("res/res/knifes/Layer "+level+".png");
                AffineTransform af = AffineTransform.getTranslateInstance(159, 198);
                knif.setRad(knif.getRad()+1);
                af.rotate(Math.toRadians(knif.getRad()-90), 0, -40);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(knifStr,af, null);
            }
            j++;
        }

        // Ve dich xoay goc radian
        if (trunk.isVisiable){
            BufferedImage trunkStr = loadImage("res/res/trunk/trunk"+level+".png");
            AffineTransform af = AffineTransform.getTranslateInstance(trunk.getX()-r_trunk, trunk.getY()-r_trunk);
            af.rotate(Math.toRadians(radian), Center_X, Center_Y);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(trunkStr,af, null);
        }
        if (trunk.isDestroy()){
            trunk.setVisiable(false);
        }


        //Ve qua tao xoay goc radian
        if (apple.isVisiable){
            BufferedImage appleStr = loadImage("res/Apple.png");
            AffineTransform af = AffineTransform.getTranslateInstance(apple.getX()-Apple_Width/2, apple.getY()-Apple_Height/2);
            af.rotate(Math.toRadians(radian+90),  Apple_Width/2, Apple_Height/2);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(appleStr, af, null);
        }
        if (apple.isDestroy()){
            apple.setVisiable(false);
        }


        if (knife.isVisiable){
            g.drawImage(knife.getImage(), knife.getX()-Knife_Width/2, knife.getY(), null);
        }
        if (knife.isDestroy()){
            knife.setVisiable(false);
        }

        //Ve day dao nem
        ImageIcon ic = new ImageIcon("res/res/ui/knife_icon.png");
        for (int i = 0; i<NUM_OF_WIN; i++){
            if (kdao > i){
                g.drawImage(ic.getImage(), 20, 300+i*25, null);
            }else {
                BufferedImage knirStr = loadImage("res/res/knifes/Layer " + level + ".png");

                AffineTransform af = AffineTransform.getTranslateInstance(20, 300+i*25);
                af.rotate(Math.toRadians(15));
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(knirStr, af, null);
            }
        }



    }

    public BufferedImage loadImage(String FileName){
        BufferedImage img = null;
        try {
            File file = new File(FileName);
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public void connectSQL(){
        try {
            sqLiteJDBC = new SQLiteJDBC();
            sqLiteJDBC.createTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public synchronized void startGame(){


        if (kdao == NUM_OF_WIN){
            for (int i = 0; i <list.size(); i++) {
                list.remove(0);
            }
            boardInit();
            kdao = 0;
            level++;
        }

        trunk.setBx_str((int) (Trunk_X + r_trunk*Math.cos(Math.toRadians(radian))));
        trunk.setBy_str((int) (Trunk_Y + r_trunk*Math.sin(Math.toRadians(radian))));

        apple.setX((int) (Trunk_X + r_trunk*Math.cos(Math.toRadians(radian))));
        apple.setY((int) (Trunk_Y + r_trunk*Math.sin(Math.toRadians(radian))));

        for (Knife k : list){

            k.setX((int) (trunk.getX()+ r_trunk*Math.cos(Math.toRadians(k.getRad()))));
            k.setY((int) (trunk.getY() + r_trunk*Math.sin(Math.toRadians(k.getRad()))));
        }


        if (!knife.isVisiable()){
            knife = new Knife();
        }

        int knife_x = knife.getX();
        int knife_y = knife.getY();
        int trunk_x = trunk.getX();
        int trunk_y = trunk.getY();

        for (Knife kni : list){
            int knix = kni.getX();
            int kniy = kni.getY();

            if (knife_x >= knix -5 && knife_x <= knix +5 && knife_y > kniy && knife_y < kniy+10){
                knife.a = -knife.a;

                notic = "Game Over";
                ingame = false;
            }

        }

        if (knife_x >= (trunk_x - 50) && knife_x <= (trunk_x + 50) &&
                knife_y >= (trunk_y - 50) && knife_y <= (trunk_y + 50)){
            score++;
            kdao++;
//            System.out.println(apple.getX() + "  : "+apple.getY());
//            System.out.println(knife_x + ":" + knife_y);
            {
                Knife tpmk = new Knife();
                tpmk.setX(155);
                tpmk.setY(160 );
                tpmk.setImage(knife.getImage());

                tpmk.setRad(90);
                if (knife_x - 5 >= apple.getX() -28 && knife_x -5 <= apple.getX()+28 && knife_y <= apple.getY()+28){
                    app+=10;
                    score+=5;
                    apple.setDestroy(true);
                    apple.setVisiable(true);
                }
                list.add(tpmk);
                knife.setDestroy(true);
                knife.setVisiable(false);

            }
         knife = new Knife();
        }


        if (knife_y > 500 || knife_y <= 0){
            knife.setDestroy(true);
        }
    }

    @Override
    public void run() {

        while (ingame){
            repaint();
            startGame();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gameOver();
        connectSQL();
        try {
            map = sqLiteJDBC.queryTop5();

            int topMin = score;
            String topMinN = "null";
            if (map.size() < 5){
                map.put(topMinN,topMin);
                sqLiteJDBC.insertScore(map.size()+1, topMinN, score);
            }
            else {

                sqLiteJDBC.updateScore(sqLiteJDBC.queryMinScore(), topMinN, topMin);
                map = sqLiteJDBC.queryTop5();

            }

            for (Map.Entry<String, Integer> entry : map.entrySet()){
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
            sqLiteJDBC.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void gameOver(){
        Graphics g = this.getGraphics();
        this.setBackground(Color.BLACK);
        g.setColor(Color.GREEN);

        g.drawString(notic, 130, 280);
        g.setColor(Color.red);

        g.drawString("Your score: " + score, 120, 300);
    }


    public class AdapterKey extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == KeyEvent.VK_SPACE) {
                Thread thread3 = new Thread(knife);
                thread3.start();
            }



        }
    }
}