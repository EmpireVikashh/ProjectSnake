package Classes.snake;
import javax.swing.JFrame;


    public class SnakeFrame extends JFrame{

        SnakeFrame(){
            this.add(new SnakePanel());
            this.setTitle("Snake");
            this.setResizable(false);
            this.setVisible(true);
            this.pack();
        }
    }
