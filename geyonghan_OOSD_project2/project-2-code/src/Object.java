import bagel.*;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Point;

/**
 * Class is to create and place the object.
 * This class delivers some functions that get the bounding box of the object and place it.
 * @author Yonghan Ge
 * @version 1.0*/
public abstract class Object {
    private double position_x;
    private double position_y;
    private final Image OBJECT_IMAGE;
    /**This method is the constructor to create a object.
     * @param position_x This is the first parameter of Object method to store object's x position
     * @param position_y This is the second parameter of Object method to store object's y position
     * @param imageFilename This is the third parameter of Object method to store object's image*/
    public Object(double position_x, double position_y, String imageFilename) {
        this.position_x = position_x;
        this.position_y = position_y;
        OBJECT_IMAGE = new Image(imageFilename);
    }
    /**This method is to get the image set in the OBJECT_IMAGE
     * @return Image This returns the Image*/
    public Image getOBJECT_IMAGE() {
        return OBJECT_IMAGE;
    }
    /**This method is to get Object's x position
     * @return double This returns the x position of object position*/
    public double getPosition_x() {
        return position_x;
    }
    /**This method is to get Object's y position
     * @return double This returns the y position of object position*/
    public double getPosition_y() {
        return position_y;
    }
    /**This method is to set the x position of object
     * @param position_x This is the first parameter to store the x position of object*/
    public void setPosition_x(double position_x) {
        this.position_x = position_x;
    }
    /**This method is to set the y position of object
     * @param position_y This is the first parameter to store the y position of object*/
    public void setPosition_y(double position_y) {
        this.position_y = position_y;
    }
    /**This method is to get the bounding box of the object in order to help checking collisions.
     * @return Rectangle the bounding box which object occupied in the shape of rectangle.
     */
    public Rectangle getBoundingBox() {
        /*transfer all objects into 'Box', easy to judge whether collisions happen*/
        Point position = new Point(this.getPosition_x() + OBJECT_IMAGE.getWidth() / 2.0,
                this.getPosition_y() + OBJECT_IMAGE.getHeight() / 2.0);
        return OBJECT_IMAGE.getBoundingBoxAt(position);
    }
    /**This method is to place and draw the object*/
    public abstract void placeObject();
}
