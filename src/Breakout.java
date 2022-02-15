import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;
import acm.util.RandomGenerator;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.PasswordAuthentication;

public class Breakout extends GraphicsProgram {

    /*
    **1) we don't have lives

        make an int lives variable that counts your lives, when you reach the bottom lives += 1 and when lives = 3 run the no more lives system

    **2) all the bricks only take one hit

        change the ball physics to if a brick is a certain color, it changes colors instead of breaking and when it is a certain shade it breaks.

    **3) what happens when you run out of lives

        set the bricksBroken and lives to 0 and reset all the bricks on the screen

    **4) how do I know how many lives I have left?

        add the lives GLabel to the screen AND change the ball color from green to orangey yellow to red

    **5) how do I know how many bricks I have broken

        add the bricksBroken GLabel to the screen



    **6) how can I make some bricks contain / be power ups?

         make a variable when you create the bricks to assign whether there is a power up.

    7) how can I make the game have more than one level?

    8) effects when a brick breaks?


     */

    private Ball ball;
    private PowerBall pBall;
    private Paddle paddle;
    private int numBricksInRow;
    private GLabel lives;
    private GLabel lbricksBroken;
    private int bricksBroken;
    public int yourLives = 3;
    private int paddleWidth = 50;

    private Color[] rowColors = {new Color(97, 204, 212), new Color(97, 204, 212),
            new Color(97, 212, 187), new Color(97, 212, 187),
            new Color(97, 212, 149), new Color(97, 212, 149),
            new Color(135, 212, 97), new Color(135, 212, 97),
            new Color(252, 177, 217), new Color(252, 177, 217)};

    private int brickLives[] = {0, 0, 1, 1, 1, 1, 2, 2, 2, 2};


    @Override
    public void init(){

        numBricksInRow = (int) (getWidth() / (Brick.WIDTH + 5.0));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                Brick brick = new Brick(10 + col * (Brick.WIDTH + 5), Brick.HEIGHT + row * (Brick.HEIGHT + 5), rowColors[row],row,brickLives[row], true, RandomGenerator.getInstance().nextInt(1, 50));
                add(brick);

                if(brick.randomPowerup == 13 || brick.randomPowerup == 3 || brick.randomPowerup == 5){

                    brick.powerUp = true;

                }
            }
        }



        ball = new Ball(getWidth() / 2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230,430, paddleWidth, 10);
        add(paddle);

        yourLives = 3;
        lives = new GLabel("lives left: " + yourLives);
        add(lives, 10, 460);

        lbricksBroken = new GLabel("brick hits: " + bricksBroken);
        add(lbricksBroken, 100, 460);

    }

    @Override
    public void run(){

        addMouseListeners();
        waitForClick();
        gameLoop();

    }

    @Override
    public void mouseMoved(MouseEvent me){

        //make sure that the paddle doesn't go off-screen.
        if((me.getX() < getWidth() - (paddle.getWidth() / 2) - 1.75) && (me.getX() > paddle.getWidth() / 2)){

            paddle.setLocation(me.getX() - paddle.getWidth() / 2, paddle.getY());

        }

    }

    private void gameLoop(){
        while(true){
            //move the ball
            ball.handleMove();

            //handle collisions
            handleCollisions();


            if(ball.lost){
                handleLost();

                ball.deltaX = 1;
                ball.deltaY = -1;


            }



            pause(4);

        }
    }

    private void handleCollisions(){

        //obj can store what we hit

        GObject obj = null;

        //check if the ball is about to hit something
        if(obj == null){

            //check the top right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY());


        }

        if(obj == null){

            //check to the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());

        }

        if(obj == null){

            //check to the bottom left corner
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());

        }

        if(obj == null){

            //check the bottom right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY());


        }

        //see if we hit something
        if(obj != null){

            //see what we hit!
            if(obj instanceof Paddle){

                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * 0.2))){
                    //hit the left edge
                    ball.bounceLeft();
                }else if(ball.getX() > (paddle.getX() + (paddle.getWidth() * 0.8))){
                    //hit the right edge
                    ball.bounceRight();
                }else{
                    //hit the middle
                    ball.bounce();
                }

            }

            if(obj instanceof Brick){

                ball.bounce();
                bricksBroken += 1;
                lbricksBroken.setLabel("brick hits: " + bricksBroken);

                    if(((Brick) obj).color.equals(new Color(252, 177, 217)) || !((Brick) obj).life) {

                        remove(obj);

                    }

                    if(((Brick) obj).color.equals(new Color (135, 212, 97))){

                        ((Brick) obj).setFillColor(new Color(135, 212, 97, 150));

                        ((Brick) obj).life = false;

                    }else if(((Brick) obj).color.equals(new Color(97, 212, 149))){

                        ((Brick) obj).setFillColor(new Color(97, 212, 149, 150));

                        ((Brick) obj).life = false;

                    }else if(((Brick) obj).color.equals(new Color(97, 212, 187))){

                        ((Brick) obj).setFillColor(new Color(97, 212, 187, 150));

                        ((Brick) obj).color = (new Color(97, 212, 187, 150));

                    }else if(((Brick) obj).color.equals(new Color(97, 212, 187, 150))){

                        ((Brick) obj).setFillColor(new Color(97, 212, 187, 75));

                        ((Brick) obj).life = false;

                    }else if(((Brick) obj).color.equals(new Color(97, 204, 212))){

                        ((Brick) obj).setFillColor(new Color(97, 204, 212, 150));

                        ((Brick) obj).color = new Color(97, 204, 212, 150);

                    }else if(((Brick) obj).color.equals(new Color(97, 204, 212, 150))){

                        ((Brick) obj).setFillColor(new Color(97, 204, 212, 75));

                        ((Brick) obj).life = false;

                    }

                    if(((Brick) obj).powerUp){
                         if(((Brick) obj).randomPowerup == 13){

                            yourLives += 1;
                            lives.setLabel("lives left: " + yourLives);

                        }
                    }



                if(bricksBroken == 330){
                    //then you need to make it the next level!

                }

            }

        }

        //if by the end of the method obj is still null, we hit nothing

    }

    private void handleLost(){
        ball.lost = false;
        yourLives -= 1;
        lives.setLabel("lives left: " + yourLives);
        if(yourLives == 2){
            ball.setFillColor(new Color(0, 0, 0, 153));
        } else if(yourLives == 1){
            ball.setFillColor(new Color(0, 0, 0, 33));
        }else if(yourLives == 0){
            ball.setFillColor(new Color(0, 0, 0, 0));
        }
        gameOver();
        reset();
    }

    private void gameOver(){
        if(yourLives == 0){
            Dialog.showMessage("game over! click ok to start again.");
            yourLives = 0;
            bricksBroken = 0;
            removeAll();
            init();
            reset();
        }
    }

    private  void reset(){
        ball.setLocation(getWidth() / 2 , 350);
        paddle.setLocation(230, 430);
        waitForClick();
    }

    public static void main(String[] args) {
        new Breakout().start();
    }

}