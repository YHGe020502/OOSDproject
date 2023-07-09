/**
 * Class is to create and place the wall.
 * This class delivers some functions that get the bounding box of wall and place it.
 * @author Yonghan Ge
 * @version 1.0*/
public class Wall extends Object {
    /**This method is the constructor to create a wall.
     * @param position_x This is the first parameter of Object method to store wall's x position
     * @param position_y This is the second parameter of Object method to store wall's y position
     * @param imageFilename This is the third parameter of Object method to store wall's image*/
    public Wall(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y,imageFilename);
    }
    /**This method is to place and draw the wall*/
    public void placeObject() {
        getOBJECT_IMAGE().drawFromTopLeft(this.getPosition_x(), this.getPosition_y());
    }
}

