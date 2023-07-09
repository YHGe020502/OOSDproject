import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/** The class is to set the image, state and activity of the navec.
 * This class delivers some functions that can place navec with its image under different conditions, check and control its duration of
 * invincible state and change its moving direction while colliding with other object and bound line
 * place fire when player coming into the attack area.
 * @author Yonghan Ge
 * @version 1.0*/
public class Navec extends Enemy {
    private static final int MAX_NAVEC_HEALTH_POINT = 80;
    private static final int NAVEC_ATTACK_RANGE = 200;
    private final Image NAVEC_INVINCIBLE_LEFT = new Image("res/navec/navecInvincibleLeft.png");
    private final Image NAVEC_INVINCIBLE_RIGHT = new Image("res/navec/navecInvincibleRight.png");
    private final Image NAVEC_LEFT = new Image("res/navec/navecLeft.png");
    private final Image NAVEC_RIGHT = new Image("res/navec/navecRight.png");

    private NavecFire navecfire;
    /**This method is to get navec's max health point
     * @return double This is returns navec's max health point*/
    public static int getMaxHealthPoint(){
        return MAX_NAVEC_HEALTH_POINT;
    }
    /**This method is the constructor to create a navec.
     * @param position_x This is the first parameter to Navec method to store navec's x position
     * @param position_y This is the second parameter to Navec method to store navec's y position*/
    public Navec(double position_x, double position_y) {
        super(position_x, position_y);
        this.setHealthPoint(MAX_NAVEC_HEALTH_POINT);
        this.setAggressive(true);
        if (moveDirection == RIGHT) {
            setCurrentImage(NAVEC_RIGHT);
        } else {
            setCurrentImage(NAVEC_LEFT);
        }
    }
    /**This method is to judge whether player is collides with navec.
     * @param player This is the first parameter to checkEnemyPlayerCollision that store the bounding box of player.
     * @return true if player is collides with navec.
     * */
    public boolean checkEnemyPlayerCollision(Player player){
        Rectangle playerBox = player.getBoundingBox();
        return this.getAttackBox().intersects(playerBox);
    }
    /**This method is to get the bounding box of the attack area navec has in order to help checking collisions.
     * @return Rectangle the bounding box which attack area occupied in the shape of rectangle.
     */
    public Rectangle getAttackBox() {
        Point topLeft = new Point((getPosition_x() + (getCurrentImage().getWidth() / 2.0) - NAVEC_ATTACK_RANGE / 2.0),
                (getPosition_y() + (getCurrentImage().getHeight() / 2.0)) - NAVEC_ATTACK_RANGE / 2.0);
        return new Rectangle(topLeft, NAVEC_ATTACK_RANGE, NAVEC_ATTACK_RANGE);
    }
    /**This method is to transfer health point to percentage and draw into position
     */
    public void HealthBar(){
        double percentage = getHealthPoint() /(double) MAX_NAVEC_HEALTH_POINT *100;
        setHealthBarColour(percentage);
        FONT.drawString((int)percentage +"%",getPosition_x(),getPosition_y()-6,getCOLOUR());
    }
    /**This method is to draw the images of navec in different state and moving directions,
     * control its invincible state and move direction
     * check its collisions and place fire when player came into its attack box.
     * @param trees This is the first parameter to placeNavec method to store all trees' position in order to check collisions
     * @param sinkholes This is the second parameter to placeNavec method to store sinkholes' position in order to check collisions
     * @param player This is the third parameter to placeNavec method to store player's position in order to check collisions and set fire
     * @param topLeft This is the fourth parameter to placeNavec method to store bounding topLeft position
     * @param bottomRight This is the fifth parameter to placeNavec method to store bounding bottomRight position
     */
    public void placeNavec(ArrayList<Object> trees, ArrayList<Sinkhole> sinkholes, Player player, Point topLeft, Point bottomRight){
        placeEnemy();
        /*set image in different directions*/
        if(moveDirection == RIGHT){
            /*judge the state of demon set the invincible image when it is invincible*/
            if(isInvincible()){
                setCurrentImage(NAVEC_INVINCIBLE_RIGHT);
            }else{
                setCurrentImage(NAVEC_LEFT);
            }
        }else{
            if(isInvincible()){
                setCurrentImage(NAVEC_INVINCIBLE_LEFT);
            }else{
                setCurrentImage(NAVEC_LEFT);
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
        if (checkObjectCollision(trees) || stepSinkhole(sinkholes) || boundOff(topLeft,bottomRight,this)){
            placeEnemyDirectionBack();
        }
        /*trigger fire after player come into the attack box*/
        if(checkEnemyPlayerCollision(player) && getHealthPoint() != MIN_HEALTH_POINT){
            navecfire = new NavecFire(player.getPosition_x() + player.getCurrentImage().getWidth() / 2.0, player.getPosition_y() + player.getCurrentImage().getHeight() / 2.0,
                    getPosition_x() + getCurrentImage().getWidth() / 2.0, getPosition_y() + getCurrentImage().getHeight() / 2.0);
            navecfire.placeFire(player);
        }
        /*if navec is still live, draw it with health bar*/
        if(getHealthPoint()!= MIN_HEALTH_POINT){
            getCurrentImage().drawFromTopLeft(getPosition_x(),getPosition_y());
            HealthBar();
        }
    }
}
