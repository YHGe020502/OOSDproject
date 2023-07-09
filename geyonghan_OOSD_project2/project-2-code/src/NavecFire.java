import bagel.*;

/**
 * Class is to create and place four fires around the navec and set its attack system.
 * This class delivers some functions that can put fires image with different rotate angles around the
 * navec image, and judge whether the player is collided with fire and then print the attack message.
 * @author Yonghan Ge
 * @version 1.0*/
public class NavecFire extends DemonFire{
    /**This method is the constructor to create a demonFire.
     * @param player_position_x This is the first parameter to NavecFire method to store player's x position
     * @param player_position_y This is the second parameter to NavecFire method to store player's y position
     * @param Enemy_position_x This is the third parameter to NavecFire method to store enemy's x position
     * @param Enemy_position_y This is the fourth parameter to NavecFire method to store enemy's y position*/
    public NavecFire(double player_position_x,double player_position_y, double Enemy_position_x,double Enemy_position_y){
        super(player_position_x,player_position_y,Enemy_position_x,Enemy_position_y);
        this.setCurrentImage(new Image("res/navec/navecFire.png"));
        this.setDamagePoint(20);
    }
    /**This method is to draw the fire into position with its rotated angle.
     * When the player's position is in a position that satisfies the fire trigger,
     * by judging which side of the navec to trigger the fire at different positions with its specific angle drawn.
     * @param player This is the first parameter to placeFire that store the state of player and use to check collisions*/
    public void placeFire(Player player) {
        DrawOptions rotation;
        /*place fire in for different corners of the enemies and rotate its directions*/
        if (player_position_x <= getEnemy_position_x() && player_position_y <= getEnemy_position_y()) {
            setFire_position_x(getEnemy_position_x() - getCurrentImage().getWidth() / 2.0 - 30);
            setFire_position_y(getEnemy_position_y() - getCurrentImage().getHeight() / 2.0 - 20);
            getCurrentImage().draw(getFire_position_x(), getFire_position_y());
        } else if (player_position_x <= getEnemy_position_x() && player_position_y > getEnemy_position_y()) {
            rotation = new DrawOptions();
            rotation.setRotation(ROTATE * 3 / 2);
            setFire_position_x(getEnemy_position_x() - getCurrentImage().getWidth() / 2.0 - 30);
            setFire_position_y(getEnemy_position_y() - getCurrentImage().getHeight() / 2.0 + 80);
            getCurrentImage().draw(getFire_position_x(), getFire_position_y(), rotation);
        } else if (player_position_x > getEnemy_position_x() && player_position_y <= getEnemy_position_y()) {
            rotation = new DrawOptions();
            rotation.setRotation(ROTATE / 2);
            setFire_position_x(getEnemy_position_x() - getCurrentImage().getWidth() / 2.0 + 80);
            setFire_position_y(getEnemy_position_y() - getCurrentImage().getHeight() / 2.0 - 30);
            getCurrentImage().draw(getFire_position_x(), getFire_position_y(), rotation);
        } else if (player_position_x > getEnemy_position_x() && player_position_y > getEnemy_position_y()) {
            rotation = new DrawOptions();
            rotation.setRotation(ROTATE);
            setFire_position_x(getEnemy_position_x() - getCurrentImage().getWidth() / 2.0 + 80);
            setFire_position_y(getEnemy_position_y() - getCurrentImage().getHeight() / 2.0 + 80);
            getCurrentImage().draw(getFire_position_x(), getFire_position_y(), rotation);
        }
        /*cause attack to the player when she touches the fire*/
        if (checkPlayerCollision(player) && !player.isInvincible()) {
            player.decreaseHealthPoint(getDamagePoint());
            player.setInvincible(true);
            printMessage(player);
        }
    }
    /**This method is to print message when player is collides with fire.
     * @param player This is the first parameter to printMessage that store the health point of player.
     * */
    public void printMessage(Player player){
        System.out.println("Navec inflicts 20 damage points on Fae. Fae's current health: "+player.getHealthPoint()
                +"/"+player.getMAX_HEALTH_POINT());
    }
}
