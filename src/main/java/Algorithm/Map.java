package Algorithm;

public class Map {
    /**
     * This class represents a Map , Built from an image.
     * with all parameters needed to image's adjustments to a global coordinate system.
     * Class should allow a flexible conversion from a coordinate representation to it's Pixel representation
     * to image and Vice versa
     * also , it should aloow calculations such as distance between two points represented as pixels
     * and also the angle between two pixels.
     * the key of this code will be to calculate proportion right.
     *
     * initialize a string represent the path to image , into a Frame
     * then , define two anchor points , at the top left and bottom down right.
     * coords to pixel rep and vice versa
     */

    private static final String map = "Ariel1.png";

    // read image through a MenuBar object.
    //try img=Image.Io.read

    //gui class needs to implement MouseListener interface , define it's methods!
    // to set listener to class type : this.addMouseListener(this)
    //asuming someones press over a coordinate , in the void paint method (Grapics g) , imply g.fillOval and set its coords
    //there will be a repaint() each time we want to restart message displayed on to frame.
}
