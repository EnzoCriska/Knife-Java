
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
    private Thread thread;
    private boolean ingame = true;
    private static int radian;
    private int score, app, player;
    private  static ArrayList<Knife> list;
    private int kdao = 0, play = 1;
    public static int  level = 0;
    private String notic;
    private SQLiteJDBC sqLiteJDBC;
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

        if (level == 1){
            list.add(new Knife(100,160));
        }
        if (level == 4){
            list.add(new Knife(100,160));

        }
        System.out.println(list.size() + ": level " + level);
        if (thread == null) {
            thread = new Thread(this);
            thread.start();

        }

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
            g.drawImage(knife.getImage(), (int)knife.getX()-Knife_Width/2, (int)knife.getY(), null);
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
            //sqLiteJDBC.createTable();

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
//            sqLiteJDBC.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    public synchronized void startGame(){
        if (level >9 ){
            notic = "You are Boss";
            ingame = false;
        }

        if (kdao == NUM_OF_WIN){
            knife.setDestroy(true);
            trunk.setFireDown(true);
            new Thread(trunk).start();

                for (int i = 0; i <list.size(); i++) {
                    list.remove(0);
                }
                if (!trunk.isDestroy()) {
                    kdao = 0;
                    level++;
                    boardInit();
                }
    }

        if (!trunk.isFireDown()) {
            trunk.setBx_str((int) (Trunk_X + r_trunk * Math.cos(Math.toRadians(radian))));
            trunk.setBy_str((int) (Trunk_Y + r_trunk * Math.sin(Math.toRadians(radian))));
        }

        if (!apple.isFire()) {
            apple.setX((int) (Trunk_X + r_trunk * Math.cos(Math.toRadians(radian))));
            apple.setY((int) (Trunk_Y + r_trunk * Math.sin(Math.toRadians(radian))));
        }

        for (Knife k : list){

            k.setX((int) (trunk.getX()+ r_trunk*Math.cos(Math.toRadians(k.getRad()))));
            k.setY((int) (trunk.getY() + r_trunk*Math.sin(Math.toRadians(k.getRad()))));
        }


        if (!knife.isVisiable()){
            knife = new Knife();
        }

        double knife_x = knife.getX();
        double knife_y = knife.getY();
        double trunk_x = trunk.getX();
        double trunk_y = trunk.getY();

        for (Knife kni : list){
            double knix = kni.getX();
            double kniy = kni.getY();

            if (knife_x >= knix -5 && knife_x <= knix +5 && knife_y > kniy && knife_y < kniy+10){
                knife.a = -knife.a;

                notic = "Game Over";
                ingame = false;
            }

        }

        if (knife_x >= (trunk_x - r_trunk) && knife_x <= (trunk_x + r_trunk) &&
                knife_y >= (trunk_y - r_trunk) && knife_y <= (trunk_y + r_trunk)){
            score++;
            kdao++;
            {
                Knife tpmk = new Knife();
                tpmk.setX(155);
                tpmk.setY(160 );
                tpmk.setImage(knife.getImage());

                tpmk.setRad(90);
                if (!apple.isFire()){
                    if (knife_x - 5 >= apple.getX() -28 && knife_x -5 <= apple.getX()+28 && knife_y <= apple.getY()+28){
                        app+=10;
                        score+=5;
                        apple.setFire(true);
                        Thread threadq = new Thread(apple);
                        threadq.start();
                    }
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
        connectSQL();
        gameOver();


    }

    private void gameOver(){
        Graphics g = this.getGraphics();
        this.setBackground(Color.BLACK);
        g.setColor(Color.GREEN);

        g.drawString(notic, 130, 280);
        g.setColor(Color.red);
        int ax = 0;
        g.drawString("Top Score", 130, 300);
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            g.drawString(entry.getKey() + " \t:\t " + entry.getValue(), 130, 320+ax*10);
        }
        g.setColor(Color.YELLOW);
        g.drawString("Your score: " + score, 120, 400);
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
