package Classes.snake;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.util.Random;
        import java.util.Arrays;

public class SnakePanel extends JPanel implements ActionListener{
    //declaring the size of the window to be constant  throughout
    static final int width = 1200;
    static final int height = 600;

    static final int unit = 50;

    //frequency and place where food will spawn randomly
    Timer timer;
    Random random;

    int fx;
    int fy;
    int foodeaten;

    //staring body length of the snake
    int bodylength = 3;

    //indicates if the game has been started yet or not
    boolean game_flag =false;

    char dir = 'R';

    static final int delay = 160;


    static final int gsize = (width*height)/(unit*unit);
    final int x_snake [] = new int[gsize];
    final int y_snake [] = new int[gsize];



    SnakePanel(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.addKeyListener(new MyKey());
        this.setFocusable(true);
        random = new Random();
        Game_start();
    }
    public void Game_start(){
        newFoodPosition();
        game_flag = true;
        timer= new Timer(delay, this);
        timer.start();

    }
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic){
        if(game_flag){
            //to fill the location off the food unit with an oval
            graphic.setColor(Color.red);
            graphic.fillOval(fx, fy, unit, unit);
            for(int i = 0; i <bodylength; i++){
                //to fill the head of the snake
                if(i==0){
                    graphic.setColor(Color.orange);
                    graphic.fillRect(x_snake[i], y_snake[i], unit, unit);

                }
                //to fill the rest of the body
                else{
                    graphic.setColor(Color.green);
                    graphic.fillRect(x_snake[i], y_snake[i], unit, unit);

                }
            }

            graphic.setColor(Color.red);
            graphic.setFont((new Font("Comic Sans", Font.BOLD, 40)));
            FontMetrics font_me = getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+foodeaten,(width-font_me.stringWidth("Score"+foodeaten))/2, graphic.getFont().getSize());
        }
        else{
            gameOver(graphic);
        }
    }
    public void move() {

        //updating the whole body of the snake apart from its head
        for (int i = bodylength; i > 0; i--) {
            x_snake[i] = x_snake[i - 1];
            y_snake[i] = y_snake[i - 1];
        }

        //updating the head of the snake according the direction
        switch (dir) {
            case 'U':
                y_snake[0] = y_snake[0] - unit;
                break;
            case 'L':
                x_snake[0] = x_snake[0] - unit;
                break;
            case 'D':
                y_snake[0] = y_snake[0] + unit;
                break;
            case 'R':
                x_snake[0] = x_snake[0] + unit;
                break;
        }

    }
    public void gameOver(Graphics graphic){

        //to display the score
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics font_me =getFontMetrics(graphic.getFont());
        graphic.drawString("Score: "+ foodeaten, (width-font_me.stringWidth("Score: "+foodeaten))/2, graphic.getFont().getSize());

        //to display game over text
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        FontMetrics font_me1 =getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!" , (width-font_me1.stringWidth("GAME OVER!"))/2, height/2);

        //to display prompt to replay
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics font_me2 =getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay" , (width-font_me2.stringWidth("Press R to replay"))/2, height/2 - 150);

    }

    public void newFoodPosition(){
        //to set display of food at random coordinates

        fx=random.nextInt((int)(width/unit))*unit;
        fy=random.nextInt((int)(height/unit))*unit;


    }

    public void checkhit(){
        //for checking collision of snake with itself and walls
        for(int i = bodylength; i>0; i--){
            if((x_snake[0] == x_snake[i]) && (y_snake[0] == y_snake[i])){
                game_flag = false;
            }
        }

        if(x_snake[0]<0){
            game_flag=false;
        }
        else if(x_snake[0]>width){
            game_flag=false;
        }
        else if(y_snake[0]<0){
            game_flag=false;
        }
        else if(y_snake[0]>height){
            game_flag=false;
        }

        if(game_flag == false){
            timer.stop();
        }


    }

    public void eat(){
        if((x_snake[0]==fx)&&(y_snake[0]==fy)){
            bodylength++;
            foodeaten++;
            newFoodPosition();
        }
    }

    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!game_flag){
                        foodeaten=0;
                        bodylength=3;
                        dir='R';
                        Arrays.fill(x_snake,0);
                        Arrays.fill(y_snake, 0);
                        Game_start();
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0){
        if(game_flag){
            move();
            eat();
            checkhit();

        }
        repaint();
    }

}
