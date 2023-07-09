import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class is to create and place four fires around the demons and set their attack system.
 * This class delivers some functions that can put fires image with different rotate angles around the
 * demon image, and judge whether the player is collided with fire and then print the attack message.
 * @author Yonghan Ge
 * @version 1.0*/
public class DemonFire {
    protected final double player_position_x;
    protected final double player_position_y;
    protected final double Enemy_position_x;
    protected final double Enemy_position_y;
    private double Fire_position_x;
    private double Fire_position_y;
    protected final static double ROTATE = Math.PI;
    private Image currentImage;
    private int damagePoint;
    /**This method is the constructor to create a demonFire.
     * @param player_position_x This is the first parameter of DemonFire method to store player's x position
     * @param player_position_y This is the second parameter of DemonFire method to store player's y position
     * @param Enemy_position_x This is the third parameter of DemonFire method to store enemy's x position
     * @param Enemy_position_y This is the fourth parameter of DemonFire method to store enemy's y position*/
    public DemonFire(double player_position_x,double player_position_y, double Enemy_position_x,double Enemy_position_y){
        this.player_position_x=player_position_x;
        this.player_position_y=player_position_y;
        this.Enemy_position_x=Enemy_position_x;
        this.Enemy_position_y=Enemy_position_y;
        this.currentImage = new Image("res/demon/demonFire.png");
        this.damagePoint=10;
    }
    /**This method is to get Enemy's x position
     * @return double This returns the x position of enemy position*/
    public double getEnemy_position_x() {
        return Enemy_position_x;
    }
    /**This method is to get Enemy's y position
     * @return double This returns the y position of enemy position*/
    public double getEnemy_position_y() {
        return Enemy_position_y;
    }
    /**This method is to get fire's x position
     * @return double This returns the x position of fire position*/
    public double getFire_position_x() {
        return Fire_position_x;
    }
    /**This method is to get fire's y position
     * @return double This returns the y position of fire position*/
    public double getFire_position_y() {
        return Fire_position_y;
    }
    /**This method is to get the image set in the currentImage
     * @return Image This returns the current Image*/
    public Image getCurrentImage() {
        return currentImage;
    }
    /**This method is to set the image into currentImage
     * @param currentImage This is the first parameter to store the image set in the currentImage*/
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
    /**This method is to get the Damage point
     * @return Image This returns the damage point*/
    public int getDamagePoint() {
        return damagePoint;
    }
    /**This method is to set the damage point
     * @param damagePoint This is the first parameter to store the int number of damage point*/
    public void setDamagePoint(int damagePoint) {
        this.damagePoint = damagePoint;
    }
    /**This method is to set the x position of fire
     * @param fire_position_x This is the first parameter to store the x position of fire*/
    public void setFire_position_x(double fire_position_x) {
        Fire_position_x = fire_position_x;
    }
    /**This method is to set the y position of fire
     * @param fire_position_y This is the first parameter to store the y position of fire*/
    public void setFire_position_y(double fire_position_y) {
        Fire_position_y = fire_position_y;
    }
    /**This method is to draw the fire into position with its rotated angle.
     * When the player's position is in a position that satisfies the fire trigger,
     * by judging which side of the demon to trigger the fire at different positions with its specific angle drawn.
     * @param player This is the first parameter of placeFire that store the state of player and use to check collisions*/
    public void placeFire(Player player) {
        /*create rotation to judge the angle fire drawn*/
        DrawOptions rotation;
        /*place fire in for different corners of the enemies and rotate its directions*/
        if (player_position_x <= getEnemy_position_x() && player_position_y <= getEnemy_position_y()) {
            setFire_position_x(getEnemy_position_x()-currentImage.getWidth()/2.0-30);
            setFire_position_y(getEnemy_position_y()- currentImage.getHeight()/2.0-30);
            currentImage.draw(Fire_position_x, Fire_position_y);
        } else if (player_position_x <= getEnemy_position_x() && player_position_y > getEnemy_position_y()) {
            rotation = new DrawOptions();
            rotation.setRotation(ROTATE *3/2);
            setFire_position_x(getEnemy_position_x()-currentImage.getWidth()/2.0-30);
            setFire_position_y(getEnemy_position_y()- currentImage.getHeight()/2.0+60);
            currentImage.draw(Fire_position_x,Fire_position_y, rotation);
        } else if (player_position_x > getEnemy_position_x() && player_position_y <= getEnemy_position_y()) {
            rotation = new DrawOptions();
            rotation.setRotation(ROTATE/ 2);
            setFire_position_x(getEnemy_position_x()-currentImage.getWidth()/2.0+60);
            setFire_position_y(getEnemy_position_y()- currentImage.getHeight()/2.0-30);
            currentImage.draw(Fire_position_x,Fire_position_y, rotation);
        } else if (player_position_x > getEnemy_position_x() && player_position_y > getEnemy_position_y()) {
            rotation = new DrawOptions();
            rotation.setRotation(ROTATE);
            setFire_position_x(getEnemy_position_x()-currentImage.getWidth()/2.0+60);
            setFire_position_y(getEnemy_position_y()- currentImage.getHeight()/2.0+60);
            currentImage.draw(Fire_position_x, Fire_position_y,rotation);
        }
        /*cause attack to the player when she touches the fire*/
        if (checkPlayerCollision(player) && !player.isInvincible()) {
            player.decreaseHealthPoint(damagePoint);
            player.setInvincible(true);
            printMessage(player);
        }
    }
    /**This method is to get the bounding box of the fire occupied in order to help checking collisions.
     * @return Rectangle the bounding box which fire occupied in the shape of rectangle.
     */
    public Rectangle getBoundingBox() {
        /*transfer fire into 'Box', easy to judge whether collisions happen*/
        Point position = new Point(this.Fire_position_x,
                this.Fire_position_y);
        return currentImage.getBoundingBoxAt(position);
    }
    /**This method is to judge whether player is collides with fire.
     * @param player This is the first parameter of checkPlayerCollision that store the bounding box of player.
     * @return true if player is collides with fire.
     * */
    public boolean checkPlayerCollision(Player player){
        Rectangle playerBox = player.getBoundingBox();
        /*check whether 'box' of player collides with 'box' of fire*/
        return playerBox.intersects(this.getBoundingBox());
    }
    /**This method is to print message when player is collides with fire.
     * @param player This is the first parameter of printMessage that store the health point of player.
     */
    public void printMessage(Player player){
        System.out.println("Demon inflicts 10 damage points on Fae. Fae's current health: "+player.getHealthPoint()
                +"/"+player.getMAX_HEALTH_POINT());
    }
}
