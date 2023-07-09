import java.util.ArrayList;
/**
 * Class is to create the timeControl that can change demons and navec's moving speed.
 * This class delivers some functions that can increase and decrease the scale of the time control and use the scale to
 * change and set the demons' and navec's speed
 * @author Yonghan Ge
 * @version 1.0*/
public class TimeControl {
    private static final int MAX_COUNT = 3;
    private static final int MIN_COUNT = -3;
    private static final double IN_NUM = 1.5;
    private static final double DE_NUM = 0.5;
    private int scale;
    /** This is the constructor to create a timeControl */
    public TimeControl() {
        this.scale = 0;
    }
    /**This method is increase the timeScale.
     * @param demons This is the first parameter for increaseTimeScale used in setSpeed
     * @param navec This is the second parameter for increaseTimeScale used in setSpeed.
     */
    public void increaseTimeScale(ArrayList<Demon> demons, Navec navec) {
        scale+=1;
        if (scale <= MAX_COUNT && scale >= MIN_COUNT) {
            setSpeed(demons, navec);
            System.out.println("Sped up, Speed: " + scale);
        } else {
            /*scale can not exceed max*/
            if (scale > MAX_COUNT) {
                scale = 3;
            }
        }
    }
    /**This method is to decrease the timeScale.
     * @param demons This is the first parameter for decreaseTimeScale used in setSpeed
     * @param navec This is the second parameter for decreaseTimeScale used in setSpeed.
     */
    public void decreaseTimeScale(ArrayList<Demon> demons, Navec navec) {
        scale-=1;
        if (scale <= MAX_COUNT && scale >= MIN_COUNT) {
            setSpeed(demons, navec);
            System.out.println("Slowed down, Speed: " + scale);
        } else {
            /*scale can not below min*/
            if (scale < MIN_COUNT) {
                scale = -3;
            }
        }
    }
    /**This method is to set speed according to different timescale.
     * @param demons This is the first parameter in order to use to set demons' speed
     * @param navec This is the second parameter in order to use to set navec's speed
     */
    public void setSpeed(ArrayList<Demon> demons, Navec navec) {
        /*consider how to change speed for all the conditions of scale with different number*/
        if (scale == 3) {
            /*start speed *1.5^3 */
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 3));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 3));
            }
        } else if (scale == 2) {
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 2));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 2));
            }
        } else if (scale == 1) {
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 1));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 1));
            }
        } else if (scale == 0) {
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 0));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(IN_NUM, 0));
            }
        } else if (scale == -1) {
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(DE_NUM, 1));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(DE_NUM, 1));
            }
        } else if (scale == -2) {
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(DE_NUM, 2));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(DE_NUM, 2));
            }
        } else if (scale == -3) {
            navec.setMoveSpeed(navec.getStartSpeed() * Math.pow(DE_NUM, 3));
            for (Demon demon : demons) {
                demon.setMoveSpeed(navec.getStartSpeed() * Math.pow(DE_NUM, 3));
            }
        }
    }
}
