public class Sinkhole extends Object {
    private boolean holeExist;

    public Sinkhole(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y, imageFilename);
        this.holeExist = true;
    }

    public boolean isHoleExist() {
        return holeExist;
    }

    public void setHoleExist(boolean holeExist) {
        this.holeExist = holeExist;
    }
    @Override
    public void placeObject() {
        if (isHoleExist()) {
            getOBJECT_IMAGE().drawFromTopLeft(this.getPosition_x(), this.getPosition_y());
        }
    }
}


