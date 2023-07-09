import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * Class is to create and place the player, set its moving function and its health bar.
 * This class delivers some functions that can move the player, control its state of attack, invincible, cool down and save,
 * check collisions with other objects and bound, and set its health bar with colors
 * @author Yonghan Ge
 * @version 1.0*/
public class Player extends Object {
    private int healthPoint;
    private final int MAX_HEALTH_POINT = 100;
    private static final int HEALTH_POSITION_X = 10;
    private static final int HEALTH_POSITION_Y = 25;
    private double previousPosition_x;
    private double previousPosition_y;
    private static final int GREEN_RANGE = 65;
    private static final int RED_RANGE = 35;
    private final DrawOptions COLOUR = new DrawOptions();
    private final Font FONT = new Font("res/frostbite.ttf", 30);
    private final Image leftFace = new Image("res/fae/faeLeft.png");
    private final Image rightFace = new Image("res/fae/faeRight.png");
    private final Image attackLeftFace = new Image("res/fae/faeAttackLeft.png");
    private final Image attackRightFace = new Image("res/fae/faeAttackRight.png");
    private Image currentImage;
    private final static int MIN_HEALTH_POINT = 0;
    private static final int COOL_DOWN_TIME_ATTACK = 2000;
    private static final int COOL_DOWN_TIME_SAVE = 20000;
    private static final int ATTACK_TIME = 1000;
    private static final int INVINCIBLE_TIME = 3000;
    private static final int SAVE_TIME = 1000;
    private boolean isAttack;
    private boolean isCoolDownAttack;
    private boolean isCoolDownSave;
    private boolean isInvincible;
    private boolean isSave;
    private boolean faceRight;
    private double attackTime;
    private double coolDownTime;
    private double invincibleTime;
    private double saveTime;
    /**This method is the constructor to create a player.
     * @param position_x This is the first parameter to Demon method to store player's x position
     * @param position_y This is the second parameter to Demon method to store player's y position
     * @param imageFilename This is the third parameter to store player's image*/
    public Player(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y, imageFilename);
        this.healthPoint = 100;
        this.previousPosition_x = position_x;
        this.previousPosition_y = position_y;
        this.currentImage = new Image(imageFilename);
        faceRight = true;
        isCoolDownAttack = false;
        isCoolDownSave = false;
        isAttack = false;
        isInvincible = false;
        isSave = false;
        attackTime = 0;
        coolDownTime=0;
        invincibleTime=0;
        saveTime = 0;
    }
    /**This method is to get the image set in the currentImage
     * @return Image This returns the current Image*/
    public Image getCurrentImage() {
        return currentImage;
    }
    /**This method is to get the invincible state of player
     * @return true This returns the player is invincible*/
    public boolean isInvincible() {
        return isInvincible;
    }
    /**This method is to set the invincible state of player
     * @param invincible This is the first parameter to store the invincible state of player*/
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
    /**This method is to get player's max health point
     * @return double This returns the max health point of player*/
    public int getMAX_HEALTH_POINT() {
        return MAX_HEALTH_POINT;
    }
    /**This method is to get player's health point
     * @return double This returns the health point of player*/
    public int getHealthPoint() {
        return healthPoint;
    }
    public void placeObject() {
    }
    /**This method is to move the player according to input,
     * set and draw the images of player while it is in the different moving directions and state,
     * control its invincible, attack and coolDown state
     * check its collisions with other objects and move back to previous when colliding with wall, tree and bound.
     * @param input This is the first parameter to store the input to control movement of player
     * @param objects This is the second parameter to store all objects' position in order to check collisions
     * @param sinkholes This is the third parameter to store sinkholes' position in order to check collisions
     * @param demons This is the fourth parameter to store demons' position in order to check collisions
     * @param navec This is the fifth parameter to store navec's position in order to check collisions
     * @param topLeft This is the sixth parameter to store bounding topLeft position
     * @param bottomRight This is the seventh parameter to store bounding bottomRight position
     */
    public void placeObject(Input input, ArrayList<Object> objects, ArrayList<Sinkhole> sinkholes, ArrayList<Demon> demons,
                            Navec navec, Point topLeft, Point bottomRight) {
        if (input.isDown(Keys.UP)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            setPosition_y(this.getPosition_y() - 2.0);
        } else if (input.isDown(Keys.DOWN)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            setPosition_y(this.getPosition_y() + 2.0);
        } else if (input.isDown(Keys.LEFT)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            currentImage = leftFace;
            faceRight = false;
            setPosition_x(this.getPosition_x() - 2.0);
        } else if (input.isDown(Keys.RIGHT)) {
            previousPosition_x = this.getPosition_x();
            previousPosition_y = this.getPosition_y();
            currentImage = rightFace;
            setPosition_x(this.getPosition_x() + 2.0);
        }
        if (input.wasPressed(Keys.A) && !isCoolDownAttack) {
            isAttack = true;
            /*change the image to state of attack*/
            if (faceRight) {
                currentImage = attackRightFace;
            } else {
                currentImage = attackLeftFace;
            }
        }
        if(input.wasPressed(Keys.S) && !isCoolDownSave) {
            isSave = true;
        }
        /*back to the previous position when collides with tree&wall and out of bound*/
        if ((checkObjectCollide(objects, this)) || (boundOff(topLeft, bottomRight, this))) {
            this.setPosition_x(previousPosition_x);
            this.setPosition_y(previousPosition_y);
        }
        /*check whether the time of frame reaching the time limit of attack*/
        if (isAttack) {
            attackTime++;
            checkDemonCollision(demons, navec);
            if((attackTime *1000/60)>ATTACK_TIME){
                isAttack = false;
                /*after attack, the coolDown time would start*/
                isCoolDownAttack = true;
                attackTime = 0;
                /*change the image to state of normal*/
                if(faceRight){
                    currentImage = rightFace;
                }else{
                    currentImage = leftFace;
                }
            }
        }
       /*check whether the time of frame reaching the time limit of coolDownAttack*/
       if(isCoolDownAttack){
           coolDownTime++;
           if((coolDownTime*1000/60)>COOL_DOWN_TIME_ATTACK){
               isCoolDownAttack = false;
               coolDownTime = 0;
           }
       }
        if(isCoolDownSave){
            coolDownTime++;
            if((coolDownTime*1000/60)>COOL_DOWN_TIME_SAVE){
                isCoolDownSave = false;
                coolDownTime = 0;
            }
        }
        /*check whether the time of frame reaching the time limit of invincible*/
       if(isInvincible){
           invincibleTime++;
           if((invincibleTime*1000/60)>INVINCIBLE_TIME){
               isInvincible = false;
               invincibleTime = 0;
           }
       }
        if(isSave){
            saveTime++;
            if((saveTime*1000/60)>SAVE_TIME){
                isSave = false;
                saveTime = 0;
                isCoolDownSave=true;
            }
        }
        /*if healthPoint is below 50, player would have a chance to back to 50%, coolDown time is 20000 millisecond*/
        if(this.isSave){
            if(healthPoint<MAX_HEALTH_POINT/2) {
                healthPoint=50;
                System.out.println("Fae uses a healing skill to save herself." +
                        " Fae's current health: " + healthPoint + "/" + MAX_HEALTH_POINT);
            }
        }
        checkStepOnSinkholes(sinkholes, this);
        currentImage.drawFromTopLeft(this.getPosition_x(), this.getPosition_y());
        HealthBar();
    }
    /**This method is to judge whether player is bound off.
     * @param topLeft This is the first parameter of boundOff that store the point of topLeft.
     * @param bottomRight This is the second parameter of boundOff that store the point of bottomRight.
     * @param player This is the third parameter of boundOff that store position of player.
     * @return true if player is going bound off.*/
    public boolean boundOff(Point topLeft, Point bottomRight, Player player) {
        return ((player.getPosition_x() < topLeft.x) || (player.getPosition_x() > bottomRight.x)
                || (player.getPosition_y() < topLeft.y) || (player.getPosition_y() > bottomRight.y));
    }

    /**This method is to judge whether player is collides with object.
     * @param objects This is the first parameter of checkObjectCollide that store the bounding box of objects.
     * @param player This is the second parameter of checkObjectCollide that store the bounding box of player.
     * @return true if player is collides with object.*/
    public boolean checkObjectCollide(ArrayList<Object> objects, Player player) {
        Rectangle playerBox = player.getBoundingBox();
        for (Object object : objects) {
            Rectangle objectBox = object.getBoundingBox();
            if (playerBox.intersects(objectBox)) {
                return true;
            }
        }
        return false;
    }
    /**This method is to judge whether player is collides with sinkhole.
     * @param sinkholes This is the first parameter that store the bounding box of sinkholes.
     * @param player This is the second parameter that store the bounding box of player.*/
    public void checkStepOnSinkholes(ArrayList<Sinkhole> sinkholes, Player player) {
        Rectangle playerBox = player.getBoundingBox();
        for (Sinkhole sinkhole : sinkholes) {
            Rectangle sinkholeBox = sinkhole.getBoundingBox();
            if (playerBox.intersects(sinkholeBox) && sinkhole.isHoleExist()) {
                /*when stepping on sinkholes, sinkholes would disappear and player would get attacks*/
                sinkhole.setHoleExist(false);
                int ATTACK = 30;
                player.decreaseHealthPoint(ATTACK);
                System.out.println("Sinkhole inflicts 30 damage point on Fae." +
                        " Fae's current health: " + healthPoint + "/" + MAX_HEALTH_POINT);
            }
        }
    }
    /**This method is to judge whether player is collides with demons.
     * @param demons This is the first parameter that store the bounding box of demons.
     * @param navec This is the second parameter that store the bounding box of navec.
     */
    public void checkDemonCollision(ArrayList<Demon> demons, Navec navec) {
        Rectangle playerBox = this.getBoundingBox();
        Rectangle navecBox = navec.getBoundingBox();
        int damagePoint = 20;
        if (playerBox.intersects(navecBox) && !navec.isInvincible()) {
            navec.decreaseHealthPoint(damagePoint);
            navec.setInvincible(true);
            System.out.println("Fae inflicts 20 damage points on Navec. " + "Navec's current health: " + navec.getHealthPoint() + "/" + Navec.getMaxHealthPoint());
        }
        for (Demon demon : demons) {
            Rectangle demonBox = demon.getBoundingBox();
            if (playerBox.intersects(demonBox) && !demon.isInvincible()) {
                demon.decreaseHealthPoint(damagePoint);
                demon.setInvincible(true);
                System.out.println("Fae inflicts 20 damage points on Demon. " + "Demon's current health: " + demon.getHealthPoint() + "/" + Demon.getMaxHealthPoint());
            }
        }
    }
    /**This method is to decrease player's health point after attack.
    * @param attack This is the first parameter that store the int of attack point needed to be reduced after attack.*/
    public void decreaseHealthPoint(int attack) {
        /*when health point is below 0, it will become 0*/
        healthPoint = Math.max(healthPoint - attack, MIN_HEALTH_POINT);
    }
    /**This method is to get color of drawing
     * @return drawOptions This returns the color */
    public DrawOptions getColour() {
        return COLOUR;
    }
    /**This method is to transfer health point to percentage and draw into position
     */
    public void HealthBar() {
        double percentage = healthPoint / (double) MAX_HEALTH_POINT * 100;
        setHealthBarColour(percentage);
        FONT.drawString((int) percentage + "%", HEALTH_POSITION_X, HEALTH_POSITION_Y, getColour());
    }
    /**This method is to set the colour of health bar according to health point percentage.
     * @param percentage This is the first parameter of setHealthBarColour that store the health point percentage of player.
     */
    public void setHealthBarColour(double percentage) {
        if (percentage > GREEN_RANGE) {
            COLOUR.setBlendColour(0, 0.8, 0.2);
        } else if (percentage <= GREEN_RANGE && percentage > RED_RANGE) {
            COLOUR.setBlendColour(0.9, 0.6, 0);
        } else if (percentage <= RED_RANGE) {
            COLOUR.setBlendColour(1, 0, 0);

        }
    }

}
