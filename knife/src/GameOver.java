
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class GameOver extends javax.swing.JPanel {
    Board board;
    Homeses homeses;
    DefaultTableModel model;
    ArrayList<HightScore> top = new ArrayList<>();
    SQLiteJDBC sqLiteJDBC;


    public GameOver() {
        initComponents();
        String[] colum = {"STT", "Họ tên", "Điểm", "Táo"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(colum);
        table.setModel(model);
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        connectSQL();

        int i = 0;
        for (HightScore hsc : top){
            String[] value = new String[4];
            value[0] = String.valueOf(++i);
            value[1] = String.valueOf(hsc.getName());
            value[2] = String.valueOf(hsc.getScore());
            value[3] = String.valueOf(hsc.getAppleScore());
            model.addRow(value);
            }
        }

   

    private void initComponents() {
        setBackground(Color.BLACK);
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        home = new javax.swing.JButton();
        replay = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        jLabel1.setText("GAME OVER");
        jLabel1.setForeground(new java.awt.Color(17, 255, 15));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                    {},
                {},
                {},
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        home.setText("Home");
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });

        replay.setText("RePlay");
        replay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(home, javax.swing.GroupLayout.DEFAULT_SIZE, 70, javax.swing.GroupLayout.DEFAULT_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(replay, javax.swing.GroupLayout.DEFAULT_SIZE, 71, javax.swing.GroupLayout.DEFAULT_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, javax.swing.GroupLayout.DEFAULT_SIZE))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(home)
                    .addComponent(replay))
                .addContainerGap(248, Short.MAX_VALUE))
        );
    }
    private void replayActionPerformed(java.awt.event.ActionEvent evt) {
        Container parent = this.getParent();
        parent.remove(this);
        board = new Board();
        parent.add(board);
        board.requestFocus();
        parent.repaint();
        parent.revalidate();
    }

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {
        Container parent = this.getParent();
        parent.remove(this);
        homeses = new Homeses();
        parent.add(homeses);
        homeses.requestFocus();
        parent.repaint();
        parent.revalidate();
    }

    public void connectSQL(){
        try {
            sqLiteJDBC = new SQLiteJDBC();
            sqLiteJDBC.createTable();

            top = sqLiteJDBC.queryTop5();
            sqLiteJDBC.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            System.out.println(e);
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println(e);
        }
    }


    private javax.swing.JButton home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton replay;
    private javax.swing.JTable table;

}
