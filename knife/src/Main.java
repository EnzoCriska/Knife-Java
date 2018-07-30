import javax.swing.*;

public class Main extends JFrame implements Commons {
    public Main(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setResizable(false);
        add(new Board());
    }

    public static void main(String[] args) {
        new Main();
    }
}
