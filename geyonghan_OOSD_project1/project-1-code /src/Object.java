import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;

public abstract class Object {
    private double position_x;
    private double position_y;
    private final Image OBJECT_IMAGE;

    public Object(double position_x, double position_y, String imageFilename) {
        this.position_x = position_x;
        this.position_y = position_y;
        OBJECT_IMAGE = new Image(imageFilename);
    }

    public Image getOBJECT_IMAGE() {
        return OBJECT_IMAGE;
    }

    public double getPosition_x() {
        return position_x;
    }

    public double getPosition_y() {
        return position_y;
    }

    public void setPosition_x(double position_x) {
        this.position_x = position_x;
    }

    public void setPosition_y(double position_y) {
        this.position_y = position_y;
    }

    public Rectangle getBoundingBox() {
        /*transfer all objects into 'Box', easy to judge whether collisions happen*/
        Point position = new Point(this.getPosition_x() + OBJECT_IMAGE.getWidth() / 2.0,
                this.getPosition_y() + OBJECT_IMAGE.getHeight() / 2.0);
        return OBJECT_IMAGE.getBoundingBoxAt(position);
    }
    public abstract void placeObject();

}
