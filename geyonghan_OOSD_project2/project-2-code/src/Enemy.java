import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/** The class is to set enemy's moving function and its health bar.
 * This class delivers some functions that can move the enemy, change its moving directions, check its collisions with
 * other objects and bound off situation and set enemy's health bar.
 * @author Yonghan Ge
 * @version 1.0*/
public class Enemy {
    private double position_x;
    private double position_y;
    private Image currentImage;
    private int healthPoint;
    private boolean isInvincible;
    private boolean isAggressive;
    protected final Font FONT = new Font("res/frostbite.ttf", 15);
    private double invincibleTime;
    protected final static int MIN_HEALTH_POINT = 0;
    protected final double MIN_SPEED = 0.2;
    protected final double MAX_SPEED = 0.7;
    private double moveSpeed = MIN_SPEED + new Random().nextDouble() * (MAX_SPEED - MIN_SPEED);
    private double startSpeed;
    protected static final int UP = 0;
    protected static final int DOWN = 1;
    protected static final int LEFT = 2;
    protected static final int RIGHT = 3;  /*set direction to number, easy to random choose*/
    protected int moveDirection = new Random().nextInt(4);
    protected final static int INVINCIBLE_TIME = 3000;
    protected static final int GREEN_RANGE = 65;
    protected static final int RED_RANGE = 35;
    protected final DrawOptions COLOUR = new DrawOptions();
    /**This method is the constructor to create a demon.
     * @param position_x This is the first parameter to Enemy method to store Enemy's x position
     * @param position_y This is the second parameter to Enemy method to store Enemy's y position.*/
    public Enemy(double position_x, double position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.isInvincible = false;
        this.invincibleTime = 0;
        this.startSpeed = moveSpeed;
    }
    /**This method is to get the image set in the currentImage
     * @return Image This is returns the current Image*/
    public Image getCurrentImage() {
        return currentImage;
    }
    /**This method is to set the image into currentImage
     * @param currentImage This is the first parameter to store the image set in the currentImage*/
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
    /**This method is to get the aggressive state of enemy
     * @return true This returns the enemy is aggressive*/
    public boolean isAggressive() {
        return isAggressive;
    }
    /**This method is to set the image into currentImage
     * @param aggressive This is the first parameter to store the image set in the currentImage*/
    public void setAggressive(boolean aggressive) {
        isAggressive = aggressive;
    }
    /**This method is to get Enemy's x position
     * @return double This returns the x position of enemy position*/
    public double getPosition_x() {
        return position_x;
    }
    /**This method is to set the x position of enemy
     * @param position_x This is the first parameter to store the x position of enemy*/
    public void setPosition_x(double position_x) {
        this.position_x = position_x;
    }
    /**This method is to get Enemy's invincible time
     * @return double This returns the invincible time of enemy */
    public double getInvincibleTime() {
        return invincibleTime;
    }
    /**This method is to set invincible time of enemy
     * @param invincibleTime This is the first parameter to store the invincible time of enemy*/
    public void setInvincibleTime(double invincibleTime) {
        this.invincibleTime = invincibleTime;
    }
    /**This method is to add invincible time*/
    public void setInvincibleTime() {
        this.invincibleTime++;
    }
    /**This method is to get Enemy's y position
     * @return double This returns the y position of enemy position*/
    public double getPosition_y() {
        return position_y;
    }
    /**This method is to set health point of enemy
     * @param healthPoint This is the first parameter to store the health point of enemy*/
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }
    /**This method is to set the y position of enemy
     * @param position_y This is the first parameter to store the y position of enemy*/
    public void setPosition_y(double position_y) {
        this.position_y = position_y;
    }
    /**This method is to get the invincible state of enemy
     * @return true This returns the enemy is invincible*/
    public boolean isInvincible() {
        return isInvincible;
    }
    /**This method is to set invincible state of enemy
     * @param invincible This is the first parameter to store the invincible state of enemy*/
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
    /**This method is to get Enemy's health point
     * @return double This returns the health point of enemy */
    public int getHealthPoint() {
        return healthPoint;
    }
    /**This method is to get color of drawing
     * @return drawOptions This returns the color */
    public DrawOptions getCOLOUR() {
        return COLOUR;
    }
    /**This method is to set moving speed of enemy
     * @param moveSpeed This is the first parameter to store the move speed of enemy*/
    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    /**This method is to get start speed of enemy
     * @return double This returns the start speed of enemy */
    public double getStartSpeed() {
        return startSpeed;
    }
    /**This method is to decrease the health point of the enemy
     * @param attackedPoint This is the first parameter to store the point needed to be reduced
     * after attacked by player*/
    public void decreaseHealthPoint(int attackedPoint){
        healthPoint = Math.max(healthPoint - attackedPoint, MIN_HEALTH_POINT);
    }
    /**This method is to place and move enemy according to its move direction*/
    public void placeEnemy(){
        if(moveDirection == UP){
            this.setPosition_y(this.getPosition_y()-moveSpeed);
        }else if(moveDirection == DOWN){
            this.setPosition_y(this.getPosition_y()+moveSpeed);
        }else if(moveDirection == RIGHT) {
            this.setPosition_x(this.getPosition_x()+moveSpeed);
        }else if(moveDirection == LEFT) {
            this.setPosition_x(this.getPosition_x()-moveSpeed);
        }
    }
   /**This method is to change the opposite direction of the enemy*/
    public void placeEnemyDirectionBack() {
        if (moveDirection == UP) {
            moveDirection = DOWN;
        } else if (moveDirection == DOWN) {
            moveDirection = UP;
        } else if (moveDirection == RIGHT) {
            moveDirection = LEFT;
        } else if (moveDirection == LEFT) {
            moveDirection = RIGHT;
        }
    }
    /**This method is to get the bounding box of the enemy occupied in order to help checking collisions.
     * @return Rectangle the bounding box which enemy occupied in the shape of rectangle.
     */
    public Rectangle getBoundingBox() {
        Point position = new Point(this.getPosition_x() + currentImage.getWidth() / 2.0,
                this.getPosition_y() + currentImage.getHeight() / 2.0);
        return currentImage.getBoundingBoxAt(position);
    }
    /**This method is to judge whether enemy is collides with object.
     * @param objects This is the first parameter of checkObjectCollision that store the bounding box of object.
     * @return true if enemy is collides with object.
     * */
    public boolean checkObjectCollision(ArrayList<Object> objects) {
        Rectangle enemyBox = this.getBoundingBox();
        for (Object object : objects) {
            Rectangle objectBox = object.getBoundingBox();
            if (enemyBox.intersects(objectBox)) {
                return true;
            }
        }return false;
    }
    /**This method is to judge whether enemy is collides with sinkhole.
     * @param sinkholes This is the first parameter of stepSinkhole that store the bounding box of sinkholes.
     * @return true if enemy is collides with sinkholes.*/
    public boolean stepSinkhole(ArrayList<Sinkhole> sinkholes) {
        Rectangle enemyBox = this.getBoundingBox();
        for (Sinkhole sinkhole : sinkholes) {
            Rectangle sinkholeBox = sinkhole.getBoundingBox();
            /*need to satisfy when enemy intersect with hole and hole is still existed*/
            if (enemyBox.intersects(sinkholeBox) && sinkhole.isHoleExist()) {
                return true;
            }
        }return false;
    }
    /**This method is to judge whether enemy is collides with sinkhole.
     * @param topLeft This is the first parameter of boundOff that store the point of topLeft.
     * @param bottomRight This is the second parameter of boundOff that store the point of bottomRight.
     * @param enemy This is the third parameter of boundOff that store position of enemy.
     * @return true if enemy is going bound off.*/
    public boolean boundOff(Point topLeft, Point bottomRight, Enemy enemy){
        return ((enemy.getPosition_x() < topLeft.x) || (enemy.getPosition_x() > bottomRight.x)
                || (enemy.getPosition_y() < topLeft.y) ||(enemy.getPosition_y() > bottomRight.y));
    }
    /**This method is to set the colour of health bar according to health point percentage.
     * @param percentage This is the first parameter of setHealthBarColour that store the health point percentage of player.
     */
    public void setHealthBarColour(double percentage) {
        /*change colours when percentage meets range bound*/
        if (percentage > GREEN_RANGE) {
            COLOUR.setBlendColour(0, 0.8, 0.2);
        } else if (percentage <= GREEN_RANGE && percentage > RED_RANGE) {
            COLOUR.setBlendColour(0.9, 0.6, 0);
        } else if (percentage <= RED_RANGE) {
            COLOUR.setBlendColour(1, 0, 0);

        }
    }
}
