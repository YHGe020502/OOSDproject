/**
 * Class is to create and place the sinkholes.
 * This class delivers some functions that get the bounding box of the sinkholet and place it.
 * @author Yonghan Ge
 * @version 1.0*/
public class Sinkhole extends Object {
    private boolean holeExist;
    /**This method is the constructor to create a sinkhole.
     * @param position_x This is the first parameter of Object method to store sinkhole's x position
     * @param position_y This is the second parameter of Object method to store sinkhole's y position
     * @param imageFilename This is the third parameter of Object method to store sinkhole's image*/
    public Sinkhole(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y, imageFilename);
        this.holeExist = true;
    }
    /**This method is to check whether the sinkhole is still exist
     * @return true if the sinkhole exists*/
    public boolean isHoleExist() {
        return holeExist;
    }
    /**This method is to set the state of sinkhole that whether it exists
     * @param holeExist it stores the presence of sinkholes*/
    public void setHoleExist(boolean holeExist) {
        this.holeExist = holeExist;
    }
    /**This method is to place and draw the sinkhole*/
    @Override
    public void placeObject() {
        if (isHoleExist()) {
            /*only draw if sinkhole is still exist*/
            getOBJECT_IMAGE().drawFromTopLeft(this.getPosition_x(), this.getPosition_y());
        }
    }
}
