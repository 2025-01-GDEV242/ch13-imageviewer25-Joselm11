
import java.awt.Color;

/**
 * An image filter to apply a Warhol-style effect using existing tint filters.
 * 
 * Top-left: Original
 * Top-right: RedTintFilter
 * Bottom-left: GreenTintFilter
 * Bottom-right: BlueTintFilter
 * 
 * Assumes all tint filters modify the image in-place.
 * 
 * @author 
 * @version 1.0
 */
public class FlippedWarholFilter extends Filter
{
    private RedTintFilter redFilter;
    private GreenTintFilter greenFilter;
    private BlueTintFilter blueFilter;

    public FlippedWarholFilter(String name)
    {
        super(name);
        redFilter = new RedTintFilter("Red");
        greenFilter = new GreenTintFilter("Green");
        blueFilter = new BlueTintFilter("Blue");
    }

    public void apply(OFImage image)
    {
        int width = image.getWidth() / 2;
        int height = image.getHeight() / 2;

        // Scale original image to quarter size
        OFImage scaled = new OFImage(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color origColor = image.getPixel(x * 2, y * 2);
                scaled.setPixel(x, y, origColor);
            }
        }

        // Create tinted versions
        OFImage redImage = new OFImage(scaled);
        OFImage greenImage = new OFImage(scaled);
        OFImage blueImage = new OFImage(scaled);

        redFilter.apply(redImage);
        greenFilter.apply(greenImage);
        blueFilter.apply(blueImage);

        // Paste all quadrants
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setPixel(x, y, scaled.getPixel(x, y));                        // Top-left
                image.setPixel(x + width, y, redImage.getPixel(x, y));             // Top-right
                image.setPixel(x, y + height, greenImage.getPixel(x, y));          // Bottom-left
                image.setPixel(x + width, y + height, blueImage.getPixel(x, y));   // Bottom-right
            }
        }
    }
}