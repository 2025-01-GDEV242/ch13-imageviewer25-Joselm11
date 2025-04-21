import java.awt.Color;

/**
 * An image filter to remove color from an image.
 * 
 * @author Michael Kölling and David J. Barnes.
 * @version 1.0
 */
public class RedTintFilter extends Filter
{
    /**
     * Constructor for objects of class GrayScaleFilter.
     * @param name The name of the filter.
     */
    public RedTintFilter(String name)
    {
        super(name);
    }

    /**
     * Apply this filter to an image.
     * 
     * @param  image  The image to be changed by this filter.
     */
    public void apply(OFImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Color pix = image.getPixel(x, y);

                int red = (int)(pix.getRed() * 1.5);
                int green = (int)(pix.getGreen() * 0.5);
                int blue = (int)(pix.getBlue() * 0.5);

                //Clamp, ensures limit
                red = Math.min(255, red);
                green = Math.min(255, green);
                blue = Math.min(255, blue);

                image.setPixel(x, y, new Color(red, green, blue));
            }
        }
    }
}
