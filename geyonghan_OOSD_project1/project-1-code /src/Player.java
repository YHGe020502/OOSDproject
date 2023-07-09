import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends Object {
    public Player(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y, imageFilename);
        this.healthPoint = 100;
        this.previousPosition_x = position_x;
        this.previousPosition_y = position_y;
        this.currentImage = new Image(imageFilename);
    }
    private int healthPoint;
    private final int MAX_HEALTH_POINT = 100;
    private static final int HEALTH_POSITION_X = 10;
    private static final int HEALTH_POSITION_Y = 25;
    private static final double STEP_SIZE = 2.0;
    private double previousPosition_x;
    private double previousPosition_y;
    private static final int GREEN_RANGE = 65;
    private static final int RED_RANGE = 35;
    private final DrawOptions COLOUR = new DrawOptions();
    private final Font FONT = new Font("res/frostbite.ttf", 30);
    private final Image leftFace = new Image("res/faeLeft.png");
    private final Image rightFace = new Image("res/faeRight.png");
    private Image currentImage;

    public int getHealthPoint() {
        return healthPoint;
    }

    public void placeObject() {}

    public void placeObject(Input input, Wall[] WALLS, Sinkhole[] SINKHOLES, Point topLeft, Point bottomRight) {
        if (input.isDown(Keys.UP)) {
            /*store the previous station information*/
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            setPosition_y(this.getPosition_y() - STEP_SIZE);
        } else if (input.isDown(Keys.DOWN)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            setPosition_y(this.getPosition_y() + STEP_SIZE);
        } else if (input.isDown(Keys.LEFT)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            currentImage = leftFace;
            setPosition_x(this.getPosition_x() - STEP_SIZE);
        } else if (input.isDown(Keys.RIGHT)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            currentImage = rightFace;
            setPosition_x(this.getPosition_x() + STEP_SIZE);
        }
        if ((checkCollideWalls(WALLS, this)) || (boundOff(topLeft,bottomRight,this))){
            /*Player can return to previous position when we control it to collide with walls or bound off*/
            this.setPosition_x(previousPosition_x);
            this.setPosition_y(previousPosition_y);
        }
        checkStepOnSinkholes(SINKHOLES,this); /*check whether steps on sinkholes*/
        currentImage.drawFromTopLeft(this.getPosition_x(),this.getPosition_y());
        HealthBar();
    }
    public boolean boundOff(Point topLeft, Point bottomRight, Player player){
        return ((player.getPosition_x() < topLeft.x) || (player.getPosition_x() > bottomRight.x)
                || (player.getPosition_y() < topLeft.y) ||(player.getPosition_y() > bottomRight.y));
    }

    public boolean checkCollideWalls(Wall[] WALLS , Player player) {
        Rectangle playerBox = player.getBoundingBox();
        for (Wall wall : WALLS) {
            if (wall != null) {
                /*get walls' boxes(location as 'Box')*/
                Rectangle wallBox = wall.getBoundingBox();
                if (playerBox.intersects(wallBox)) {
                    /*player's 'Box' has collided with walls*/
                    return true;
                }
            }
        }
        return false;
    }
    public void checkStepOnSinkholes(Sinkhole[] SINKHOLES, Player player){
        Rectangle playerBox = player.getBoundingBox();
        for (Sinkhole sinkhole : SINKHOLES) {
            if (sinkhole != null) {
                Rectangle sinkholeBox = sinkhole.getBoundingBox();
                /*need to satisfy when player intersect with hole and hole is still existed*/
                if (playerBox.intersects(sinkholeBox) && sinkhole.isHoleExist()) {
                    /*let hole disappear and minus the attack after stepping on*/
                    sinkhole.setHoleExist(false);
                    int ATTACK = 30;
                    player.decreaseHealthPoint(ATTACK);
                    System.out.println("Sinkhole inflicts 30 damage point on Fae." +
                            " Fae's current health: " + healthPoint + "/" + MAX_HEALTH_POINT);
                }
            }
        }
    }
    public void decreaseHealthPoint(int attack){
        int MIN_HEALTH_POINT = 0;
        healthPoint = Math.max(healthPoint - attack, MIN_HEALTH_POINT);/*when health point is below 0, it will become 0*/
    }
    public DrawOptions getColour() {
        return COLOUR;
    }
    public void HealthBar(){
        /*use double to get exact percentage number*/
        double percentage = healthPoint /(double) MAX_HEALTH_POINT *100;
        setHealthBarColour(percentage);
        FONT.drawString((int)percentage +"%",HEALTH_POSITION_X,HEALTH_POSITION_Y,getColour());
    }
    public void setHealthBarColour(double percentage){
        /*change colours when percentage meets range bound*/
        if(percentage > GREEN_RANGE) {
            COLOUR.setBlendColour(0,0.8,0.2);
        }else if(percentage <=GREEN_RANGE && percentage >RED_RANGE) {
            COLOUR.setBlendColour(0.9,0.6,0);
        }else if(percentage <=RED_RANGE){
            COLOUR.setBlendColour(1,0,0);

        }

    }
}


