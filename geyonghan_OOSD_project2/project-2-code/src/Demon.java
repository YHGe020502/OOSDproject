import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/** The class is to set the image, state and activity of the demon.
 * This class delivers some functions that can place demon with its image under different conditions, check and control its duration of
 * invincible state and change its moving direction while colliding with other object and bound line
 * place fire when player coming into the attack area.
 * @author Yonghan Ge
 * @version 1.0*/
public class Demon extends Enemy {
    private static final int MAX_DEMON_HEALTH_POINT = 40;
    private static final int DEMON_ATTACK_RANGE = 150;
    private final Image DEMON_INVINCIBLE_LEFT = new Image("res/demon/demonInvincibleLeft.png");
    private final Image DEMON_INVINCIBLE_RIGHT = new Image("res/demon/demonInvincibleRight.png");
    private final Image DEMON_LEFT = new Image("res/demon/demonLeft.png");
    private final Image DEMON_RIGHT = new Image("res/demon/demonRight.png");
    private DemonFire demonFire;
    /**This method is to get demon's max health point
     * @return double This is returns demon's max health point*/
    public static int getMaxHealthPoint(){
        return MAX_DEMON_HEALTH_POINT;
    }
    /**This method is the constructor to create a demon.
     * @param position_x This is the first parameter for Demon method to store demon's x position
     * @param position_y This is the second parameter for Demon method to store demon's y position*/
    public Demon(double position_x, double position_y) {
        super(position_x, position_y);
        this.setHealthPoint(MAX_DEMON_HEALTH_POINT);
        Random random = new Random();
        /*random the type of demon, true for aggressive, false for passive*/
        this.setAggressive(random.nextBoolean());
        if (moveDirection == RIGHT) {
            setCurrentImage(DEMON_RIGHT);
        } else {
            setCurrentImage(DEMON_LEFT);
        }
    }
    /**This method is to judge whether player is collides with demon.
     * @param player This is the first parameter to checkEnemyPlayerCollision that store the bounding box of player.
     * @return true if player is collides with demon.
     * */
    public boolean checkEnemyPlayerCollision(Player player){
        Rectangle playerBox = player.getBoundingBox();
        /*check whether 'box' of player collides with 'box' of demon*/
        return this.getAttackBox().intersects(playerBox);
    }
    /**This method is to get the bounding box of the attack area demons have in order to help checking collisions.
     * @return Rectangle the bounding box which attack area occupied in the shape of rectangle.
     */
    public Rectangle getAttackBox(){
        Point topLeft = new Point((getPosition_x() + (getCurrentImage().getWidth() / 2.0)-DEMON_ATTACK_RANGE/2.0),
                (getPosition_y() + (getCurrentImage().getHeight() / 2.0))-DEMON_ATTACK_RANGE/2.0);
        return new Rectangle(topLeft, DEMON_ATTACK_RANGE, DEMON_ATTACK_RANGE);
    }
    /**This method is to transfer health point to percentage and draw into position
     */
    public void HealthBar(){
        double percentage = getHealthPoint() /(double) MAX_DEMON_HEALTH_POINT *100;
        setHealthBarColour(percentage);
        FONT.drawString((int)percentage +"%",getPosition_x(),getPosition_y()-6,getCOLOUR());
    }
    /**This method is to set and draw the images of demon while it is in the different moving directions and state,
     * control its invincible state and move direction
     * check its collisions and place fire when player came into its attack box.
     * @param trees This is the first parameter to placeDemon method to store all trees' position in order to check collisions
     * @param sinkholes This is the second parameter to placeDemon method to store sinkholes' position in order to check collisions
     * @param player This is the third parameter to placeDemon method to store player's position in order to check collisions and set fire
     * @param topLeft This is the fourth parameter to placeDemon method to store bounding topLeft position
     * @param bottomRight This is the fifth parameter to placeDemon method to store bounding bottomRight position
     */
    public void placeDemon(ArrayList<Object> trees, ArrayList<Sinkhole> sinkholes, Player player, Point topLeft, Point bottomRight) {
        if (isAggressive()) {
            placeEnemy();
            /*set image in different directions*/
            if (moveDirection == RIGHT) {
                /*judge the state of demon set the invincible image when it is invincible*/
                if (isInvincible()) {
                    setCurrentImage(DEMON_INVINCIBLE_RIGHT);
                } else {
                    setCurrentImage(DEMON_RIGHT);
                }
            } else {
                if (isInvincible()) {
                    setCurrentImage(DEMON_INVINCIBLE_LEFT);
                } else {
                    setCurrentImage(DEMON_LEFT);
                }
            }
        }else {
            if (isInvincible()) {
                setCurrentImage(DEMON_INVINCIBLE_RIGHT);
            } else {
                setCurrentImage(DEMON_RIGHT);
            }
        }
        /*check whether the time of frame reaching the time limit of invincible*/
        if (isInvincible()) {
            setInvincibleTime();
            if ((getInvincibleTime()*1000 / 60 )> INVINCIBLE_TIME) {
                setInvincible(false);
                setInvincibleTime(0);
            }
        }
        /*check collides with trees, sinkholes and bound, need to change moving direction after collisions*/
        if (checkObjectCollision(trees) || stepSinkhole(sinkholes) || boundOff(topLeft, bottomRight, this)) {
            placeEnemyDirectionBack();
        }
        /*trigger fire after player come into the attack box*/
        if (checkEnemyPlayerCollision(player) && getHealthPoint() != MIN_HEALTH_POINT) {
            demonFire = new DemonFire(player.getPosition_x() + player.getCurrentImage().getWidth() / 2.0, player.getPosition_y() + player.getCurrentImage().getHeight() / 2.0,
                    getPosition_x() + getCurrentImage().getWidth() / 2.0, getPosition_y() + getCurrentImage().getHeight() / 2.0);
            demonFire.placeFire(player);
        }
        /*if demon is still live, draw it with health bar*/
        if(getHealthPoint() != MIN_HEALTH_POINT){
            getCurrentImage().drawFromTopLeft(getPosition_x(),getPosition_y());
            HealthBar();
        }
    }

}
