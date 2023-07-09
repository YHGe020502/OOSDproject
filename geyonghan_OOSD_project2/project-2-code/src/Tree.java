/**
 * Class is to create and place the tree.
 * This class delivers some functions that get the bounding box of tree and place it.
 * @author Yonghan Ge
 * @version 1.0*/
public class Tree extends Object {
    /**This method is the constructor to create a tree.
     * @param position_x This is the first parameter of Object method to store tree's x position
     * @param position_y This is the second parameter of Object method to store tree's y position
     * @param imageFilename This is the third parameter of Object method to store tree's image*/
    public Tree(double position_x, double position_y, String imageFilename) {
        super(position_x, position_y,imageFilename);
    }
    /**This method is to place and draw the tree*/
    public void placeObject() {
        getOBJECT_IMAGE().drawFromTopLeft(this.getPosition_x(), this.getPosition_y());
    }
}
