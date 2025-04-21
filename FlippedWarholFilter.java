import java.awt.Color;

/**
 * Warhol-style filter with mirrored quadrants:
 * Top-left: Original
 * Top-right: Red tint, mirrored horizontally
 * Bottom-left: Green tint, mirrored vertically
 * Bottom-right: Blue tint, mirrored horizontally and vertically
 */
public class FlippedWarholFilter extends Filter
{
    private RedTintFilter redFilter;
    private GreenTintFilter greenFilter;
    private BlueTintFilter blueFilter;
    private MirrorFilter mirrorFilter;

    /**
     * Constructor for the FlippedWarholFilter class.
     * 
     * @param name The name of the filter.
     */
    public FlippedWarholFilter(String name)
    {
        super(name);
        redFilter = new RedTintFilter("Red");
        greenFilter = new GreenTintFilter("Green");
        blueFilter = new BlueTintFilter("Blue");
        mirrorFilter = new MirrorFilter("Mirror");
    }

    /**
     * Apply this filter to an image.
     * 
     * @param  image  The image to be changed by this filter.
     */
    public void apply(OFImage image)
    {
        int width = image.getWidth() / 2;
        int height = image.getHeight() / 2;

        // Create quarter-size original
        OFImage original = new OFImage(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                original.setPixel(x, y, image.getPixel(x * 2, y * 2));
            }
        }

        // Create tinted copies
        OFImage red = new OFImage(original);
        OFImage green = new OFImage(original);
        OFImage blue = new OFImage(original);

        redFilter.apply(red);
        greenFilter.apply(green);
        blueFilter.apply(blue);

        // Apply flipping
        mirrorFilter.apply(red); // horizontal flip

        OFImage greenFlip = flipVertical(green);     // vertical flip
        mirrorFilter.apply(blue);                    // horizontal flip
        OFImage blueFlip = flipVertical(blue);       // now flipped both

        // Combine all into final image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setPixel(x, y, original.getPixel(x, y));             // top-left
                image.setPixel(x + width, y, red.getPixel(x, y));          // top-right
                image.setPixel(x, y + height, greenFlip.getPixel(x, y));   // bottom-left
                image.setPixel(x + width, y + height, blueFlip.getPixel(x, y)); // bottom-right
            }
        }
    }

    private OFImage flipVertical(OFImage img)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        OFImage result = new OFImage(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setPixel(x, y, img.getPixel(x, height - 1 - y));
            }
        }

        return result;
    }
}