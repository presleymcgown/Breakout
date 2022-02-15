import acm.graphics.GCanvas;
import acm.graphics.GOval;

public class Ball extends GOval {

    public double deltaX = 1;
    public double deltaY = -1;
    private GCanvas screen;
    public boolean lost = false;

    public Ball (double x, double y, double size, GCanvas screen){

        super(x, y, size, size);
        setFilled(true);

        this.screen = screen;

    }

    public void handleMove(){

        //move the ball
        move(deltaX, -deltaY);

        //check to see if the ball hits the top of the screen.
        //check to see if the ball hits the bottom of the screen.
        //check to see if the ball hits the left side of screen.
        //check to see if the ball hits the right side of screen.
        if(getY() <= 0){
            //start moving down
            deltaY *= -1;
        }else if(getY() >= screen.getHeight() - getHeight()){
            //lose a life
            lost = true;
        }else if(getX() <= 0){
            //start moving right
            deltaX *= -1;
        }else if(getX() >= screen.getWidth() - getWidth()){
            //start moving left
            deltaX *= -1;
        }

    }

    public void bounce(){

        deltaY *= -1;

    }

    public void bounceLeft(){

        deltaY *= -1;
        deltaX = -Math.abs(deltaX);

    }

    public void bounceRight(){

        deltaY *= -1;
        deltaX = Math.abs(deltaX);

    }

}
