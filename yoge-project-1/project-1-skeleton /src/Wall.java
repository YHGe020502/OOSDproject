public class Wall extends Object {
    public Wall(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y,imageFilename);
    }
    public void placeObject() {
        getOBJECT_IMAGE().drawFromTopLeft(this.getPosition_x(), this.getPosition_y());
    }
}

