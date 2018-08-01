
import javax.swing.JFrame;
import java.awt.*;

public class Homeses extends javax.swing.JPanel {
    Board board;
    public Homeses() {
        initComponents();
    }


    private void initComponents() {
        setBackground(Color.BLACK);
        play = new javax.swing.JButton();
        hightScore = new javax.swing.JButton();
        quit = new javax.swing.JButton();

        play.setText("Play");
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });

        hightScore.setText("Hight Score");
        hightScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hightScoreActionPerformed(evt);
            }
        });

        quit.setText("Quit");
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(quit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hightScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(166 ,166, 166)
                .addComponent(play)
                .addGap(30, 30, 30)
                .addComponent(hightScore)
                .addGap(45, 45, 45)
                .addComponent(quit)
                .addContainerGap(289, Short.MAX_VALUE))
        );
    }

    private void playActionPerformed(java.awt.event.ActionEvent evt) {
        Container parent = this.getParent();
        parent.remove(this);
        board = new Board();
        parent.add(board);
        board.requestFocus();
        parent.repaint();
        parent.revalidate();
    }

    private void hightScoreActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void quitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(WIDTH);
    }



    private javax.swing.JButton hightScore;
    private javax.swing.JButton play;
    private javax.swing.JButton quit;

}
