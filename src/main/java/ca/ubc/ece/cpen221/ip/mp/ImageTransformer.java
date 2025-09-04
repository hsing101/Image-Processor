package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;

import java.awt.Color;
import java.util.*;

/**
 * This datatype (or class) provides operations for transforming an image.
 *
 * <p>The operations supported are:
 * <ul>
 *     <li>The {@code ImageTransformer} constructor generates an instance of an image that
 *     we would like to transform;</li>
 *     <li></li>
 * </ul>
 * </p>
 */

public class ImageTransformer {

    private Image image;
    private int width;
    private int height;

    /**
     * Creates an ImageTransformer with an image. The provided image is
     * <strong>never</strong> changed by any of the operations.
     *
     * @param img is not null
     */
    public ImageTransformer(Image img) {
        // TODO: Implement this method
        this.image = img;
        this.width = img.width();
        this.height = img.height();
    }

    /**
     * Obtain the grayscale version of the image.
     *
     * @return the grayscale version of the instance.
     */
    public Image grayscale() {
        Image gsImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = image.get(col, row);
                Color gray = Image.toGray(color);
                gsImage.set(col, row, gray);
            }
        }
        return gsImage;
    }

    /**
     * Obtain a version of the image with only the red colours.
     *
     * @return a reds-only version of the instance.
     */
    public Image red() {
        Image redImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int originalPixel = image.getRGB(col, row);
                int alpha = (originalPixel >> 24) & 0xFF;
                int red = (originalPixel >> 16) & 0xFF;
                int desiredColor = (alpha << 24) | (red << 16) | (0 << 8) | (0);
                redImage.setRGB(col, row, desiredColor);
            }
        }
        return redImage;
    }


    /* ===== TASK 1 ===== */
    /**
     * Copy an image.
     *
     * @param I image to be copied (not null)
     */

    public void copy(Image I){
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int Pixel = I.getRGB(col, row);
                image.setRGB(col, row, Pixel);
            }
        }
    }
    /**
     * Returns the mirror image of an instance.
     *
     * @return the mirror image of the instance.
     */
    public Image mirror() {
        // TODO: Implement this method
        Image mirrorImage = new Image(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int originalPixel = image.getRGB(col, row);
                mirrorImage.setRGB(width - col - 1, row, originalPixel);
            }
        }

        return mirrorImage;
    }

    /**
     * <p>Returns the negative version of an instance.<br />
     * If the colour of a pixel is (r, g, b) then the colours of the same pixel
     * in the negative of the image are (255-r, 255-g, 255-b).</p>
     *
     * @return the negative of the instance.
     */
    public Image negative() {
        // TODO: Implement this method
        Image negativeImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = image.get(col, row);
                int blue = color.getBlue();
                int green = color.getGreen();
                int red = color.getRed();
                Color newColor = new Color(255-red, 255-green, 255-blue);
                negativeImage.set(col, row, newColor);
            }
        }
        return negativeImage;
    }

    /**
     * <p>Returns the posterized version of an instance.<br />
     * If the value of the colour is between 0 and 64 (both limits inclusive), set it to 32;
     * If the value of the colour is between 65 and 128, set it to 96;
     * if the value of the colour is between 129 and 255, set it to 222.
     *
     * @return the posterized version of the image.
    */
    public Image posterize() {
        Image posterizedImage = new Image(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color color = image.get(col, row);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int[] colors={red,green,blue};
                for (int i=0; i<colors.length ; i++){
                    if (0 <= colors[i] && colors[i] <= 64){
                        colors[i] = 32;
                    }
                    else if (65 <= colors[i] && colors[i] <= 128){
                        colors[i] = 96;
                    }
                    else if (97 <= colors[i] && colors[i] <= 255){
                        colors[i] = 222;
                    }
                }
                Color newColor = new Color(colors[0], colors[1], colors[2]);
                posterizedImage.set(col, row, newColor);
            }
        }
        return posterizedImage;
    }


    /* ===== TASK 2 ===== */
    /** Returns the median of elements in an arrayList
     *
     * @param nums is not null
     *
     * @return the median of given list.
     * */
    public int median(ArrayList<Integer> nums){
        Collections.sort(nums);
        if (nums.size()%2==0){
            return (nums.get(nums.size()/2)+nums.get(nums.size()/2-1))/2;
        }
        return nums.get((nums.size()-1)/2);
    }
    /** Returns the average of elements in an arrayList
     *
     * @param nums is not null
     *
     * @return the average of given list.
     * */
    public int avg(ArrayList<Integer> nums){
        int sum = 0;
        for (int i=0; i<nums.size(); i++){
            sum += nums.get(i);
        }
        return sum/nums.size();
    }

    /** Returns the color object with median color values of all the elements of the input array
     *
     * @param pixels is not null
     *
     * @return the median color
     * */
    public Color getMedianColor(Color[] pixels){
        ArrayList<Integer> red = new ArrayList<Integer>();
        ArrayList<Integer> green = new ArrayList<Integer>();
        ArrayList<Integer> blue = new ArrayList<Integer>();
        for (Color pixel : pixels) {
            red.add(pixel.getRed());
            green.add(pixel.getGreen());
            blue.add(pixel.getBlue());
        }
        Color newColor = new Color(median(red),median(green),median(blue));

        return newColor;
    }
    /** Returns the color object with minimum color value of all the elements of the input array
     *
     * @param pixels is not null
     *
     * @return the minimum color
     * */
    public Color getMinColor(Color[] pixels){
        ArrayList<Integer> red = new ArrayList<Integer>();
        ArrayList<Integer> green = new ArrayList<Integer>();
        ArrayList<Integer> blue = new ArrayList<Integer>();
        for (Color pixel : pixels) {
            red.add(pixel.getRed());
            green.add(pixel.getGreen());
            blue.add(pixel.getBlue());
        }
        Color newColor = new Color(Collections.min(red),Collections.min(green),Collections.min(blue));

        return newColor;
    }
    /** Returns the color object with average color value of all the elements of the input array
     *
     * @param pixels is not null
     *
     * @return the average color
     * */
    public Color getAvgColor(Color[] pixels){
        ArrayList<Integer> red = new ArrayList<Integer>();
        ArrayList<Integer> green = new ArrayList<Integer>();
        ArrayList<Integer> blue = new ArrayList<Integer>();
        for (Color pixel : pixels) {
            red.add(pixel.getRed());
            green.add(pixel.getGreen());
            blue.add(pixel.getBlue());
        }
        Color newColor = new Color(avg(red),avg(green),avg(blue));

        return newColor;
    }
    /**
     * Returns the denoised image of an instance.
     * Replace the value of a pixel by the median value of that pixel and its neighbouring pixels.
     *
     * @return the denoised version of the image.
     */
    public Image denoise() {
        // TODO: Implement this method
        Image denoiseImage = new Image(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color pixel0 = image.get(col, row);
                boolean leftCol = col>0;
                boolean rightCol = col+1<width;
                boolean topRow = row>0;
                boolean bottomRow = row+1<height;

                if (!leftCol){
                    if (!topRow){
                        Color pixel1 = image.get(col+1, row);
                        Color pixel2 = image.get(col, row+1);
                        Color pixel3 = image.get(col+1, row+1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor= getMedianColor(pixels);
                        denoiseImage.set(col, row, newColor);

                    }
                    else if(!bottomRow){
                        Color pixel1 = image.get(col+1, row);
                        Color pixel2 = image.get(col, row-1);
                        Color pixel3 = image.get(col+1, row-1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor= getMedianColor(pixels);
                        denoiseImage.set(col, row, newColor);
                    }
                    else {
                        Color pixel1 = image.get(col+1, row);
                        Color pixel2 = image.get(col, row+1);
                        Color pixel3 = image.get(col, row-1);
                        Color pixel4 = image.get(col+1, row+1);
                        Color pixel5 = image.get(col+1, row-1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                        Color newColor= getMedianColor(pixels);
                        denoiseImage.set(col, row, newColor);
                    }
                }
                else if(!rightCol){
                    if (!topRow){
                        Color pixel1 = image.get(col-1, row);
                        Color pixel2 = image.get(col, row+1);
                        Color pixel3 = image.get(col-1, row+1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor= getMedianColor(pixels);
                        denoiseImage.set(col, row, newColor);

                    }
                    else if(!bottomRow){
                        Color pixel1 = image.get(col-1, row);
                        Color pixel2 = image.get(col, row-1);
                        Color pixel3 = image.get(col-1, row-1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor= getMedianColor(pixels);
                        denoiseImage.set(col, row, newColor);
                    }
                    else {
                        Color pixel1 = image.get(col-1, row);
                        Color pixel2 = image.get(col, row+1);
                        Color pixel3 = image.get(col, row-1);
                        Color pixel4 = image.get(col-1, row+1);
                        Color pixel5 = image.get(col-1, row-1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                        Color newColor= getMedianColor(pixels);
                        denoiseImage.set(col, row, newColor);
                    }
                }
                else if (!topRow){
                    Color pixel1 = image.get(col+1, row);
                    Color pixel2 = image.get(col+1, row+1);
                    Color pixel3 = image.get(col, row+1);
                    Color pixel4 = image.get(col-1, row);
                    Color pixel5 = image.get(col-1, row+1);
                    Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                    Color newColor= getMedianColor(pixels);
                    denoiseImage.set(col, row, newColor);
                }
                else if (!bottomRow){
                    Color pixel1 = image.get(col+1, row);
                    Color pixel2 = image.get(col+1, row-1);
                    Color pixel3 = image.get(col, row-1);
                    Color pixel4 = image.get(col-1, row);
                    Color pixel5 = image.get(col-1, row-1);
                    Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                    Color newColor= getMedianColor(pixels);
                    denoiseImage.set(col, row, newColor);
                }
                else{
                    Color pixel1 = image.get(col+1, row);
                    Color pixel2 = image.get(col+1, row-1);
                    Color pixel3 = image.get(col+1, row+1);
                    Color pixel4 = image.get(col-1, row);
                    Color pixel5 = image.get(col-1, row-1);
                    Color pixel6 = image.get(col-1, row+1);
                    Color pixel7 = image.get(col, row-1);
                    Color pixel8 = image.get(col, row+1);
                    Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5, pixel6, pixel7, pixel8};

                    Color newColor= getMedianColor(pixels);
                    denoiseImage.set(col, row, newColor);
                }
            }
        }
        return denoiseImage;
    }

    /**
     * Returns the weathered version of an image
     * Replace the value of a pixel by the minimum value among the value of that pixel and its neighbours
     *
     * @return a weathered version of the image.
     */
    public Image weather() {
        // TODO: Implement this method
        Image weatherImage = new Image(width,height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color pixel0 = image.get(col, row);
                boolean leftCol = col > 0;
                boolean rightCol = col + 1 < width;
                boolean topRow = row > 0;
                boolean bottomRow = row + 1 < height;

                if (!leftCol) {
                    if (!topRow) {
                        Color pixel1 = image.get(col + 1, row);
                        Color pixel2 = image.get(col, row + 1);
                        Color pixel3 = image.get(col + 1, row + 1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor = getMinColor(pixels);
                        weatherImage.set(col, row, newColor);

                    }
                    else if (!bottomRow) {
                        Color pixel1 = image.get(col + 1, row);
                        Color pixel2 = image.get(col, row - 1);
                        Color pixel3 = image.get(col + 1, row - 1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor = getMinColor(pixels);
                        weatherImage.set(col, row, newColor);
                    }
                    else {
                        Color pixel1 = image.get(col + 1, row);
                        Color pixel2 = image.get(col, row + 1);
                        Color pixel3 = image.get(col, row - 1);
                        Color pixel4 = image.get(col + 1, row + 1);
                        Color pixel5 = image.get(col + 1, row - 1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                        Color newColor = getMinColor(pixels);
                        weatherImage.set(col, row, newColor);
                    }
                }
                else if (!rightCol) {
                    if (!topRow) {
                        Color pixel1 = image.get(col - 1, row);
                        Color pixel2 = image.get(col, row + 1);
                        Color pixel3 = image.get(col - 1, row + 1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor = getMinColor(pixels);
                        weatherImage.set(col, row, newColor);

                    }
                    else if (!bottomRow) {
                        Color pixel1 = image.get(col - 1, row);
                        Color pixel2 = image.get(col, row - 1);
                        Color pixel3 = image.get(col - 1, row - 1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3};

                        Color newColor = getMinColor(pixels);
                        weatherImage.set(col, row, newColor);
                    }
                    else {
                        Color pixel1 = image.get(col - 1, row);
                        Color pixel2 = image.get(col, row + 1);
                        Color pixel3 = image.get(col, row - 1);
                        Color pixel4 = image.get(col - 1, row + 1);
                        Color pixel5 = image.get(col - 1, row - 1);
                        Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                        Color newColor = getMinColor(pixels);
                        weatherImage.set(col, row, newColor);
                    }
                }
                else if (!topRow) {
                    Color pixel1 = image.get(col + 1, row);
                    Color pixel2 = image.get(col + 1, row + 1);
                    Color pixel3 = image.get(col, row + 1);
                    Color pixel4 = image.get(col - 1, row);
                    Color pixel5 = image.get(col - 1, row + 1);
                    Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                    Color newColor = getMinColor(pixels);
                    weatherImage.set(col, row, newColor);

                }
                else if (!bottomRow) {
                    Color pixel1 = image.get(col + 1, row);
                    Color pixel2 = image.get(col + 1, row - 1);
                    Color pixel3 = image.get(col, row - 1);
                    Color pixel4 = image.get(col - 1, row);
                    Color pixel5 = image.get(col - 1, row - 1);
                    Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5};

                    Color newColor = getMinColor(pixels);
                    weatherImage.set(col, row, newColor);
                }
                else {
                    Color pixel1 = image.get(col + 1, row);
                    Color pixel2 = image.get(col + 1, row - 1);
                    Color pixel3 = image.get(col + 1, row + 1);
                    Color pixel4 = image.get(col - 1, row);
                    Color pixel5 = image.get(col - 1, row - 1);
                    Color pixel6 = image.get(col - 1, row + 1);
                    Color pixel7 = image.get(col, row - 1);
                    Color pixel8 = image.get(col, row + 1);
                    Color[] pixels = {pixel0, pixel1, pixel2, pixel3, pixel4, pixel5, pixel6, pixel7, pixel8};

                    Color newColor = getMinColor(pixels);
                    weatherImage.set(col, row, newColor);
                }
            }
        }
        return weatherImage;
    }

/**
 * Replace the pixels of an image with the average value of each block.
 * Each block is of size blockSize x blockSize, and each pixel in a block
 * If a block at the edge of the image is smaller than blockSize (i.e., right or bottom edge),
 *
 * @param blockSize The size of the blocks (not null)
 *
 * @return A block-painted version of the image
 */

    public Image blockPaint(int blockSize) {
        // TODO: Implement this method
        Image blockPaintImage = new Image(width,height);
        for (int row = 0; row < height; row+=blockSize) {
            for (int col = 0; col < width; col+=blockSize) {
                boolean noHeight = row + blockSize > height;
                boolean noWidth = col + blockSize > width;
                ArrayList<Color> pixelsList = new ArrayList<>();
                if (noHeight && noWidth) {
                    for (int i = row; i < height; i++) {
                        for (int j = col; j < width; j++) {
                            pixelsList.add(image.get(j, i));
                        }
                    }
                    Color[] pixels = pixelsList.toArray(new Color[pixelsList.size()]);
                    Color newColor = getAvgColor(pixels);
                    for (int i = row; i < height; i++) {
                        for (int j = col; j < width; j++) {
                            blockPaintImage.set(j, i, newColor);
                        }
                    }
                }
                else if (noHeight) {
                    for (int i = row; i < height; i++) {
                        for (int j = col; j < col + blockSize; j++) {
                            pixelsList.add(image.get(j, i));
                        }
                    }
                    Color[] pixels = pixelsList.toArray(new Color[pixelsList.size()]);
                    Color newColor = getAvgColor(pixels);
                    for (int i = row; i < height; i++) {
                        for (int j = col; j < col + blockSize; j++) {
                            blockPaintImage.set(j, i, newColor);
                        }
                    }
                }
                else if (noWidth) {
                    for (int i = row; i < row + blockSize; i++) {
                        for (int j = col; j < width; j++) {
                            pixelsList.add(image.get(j, i));
                        }
                    }
                    Color[] pixels = pixelsList.toArray(new Color[pixelsList.size()]);
                    Color newColor = getAvgColor(pixels);
                    for (int i = row; i < row + blockSize; i++) {
                        for (int j = col; j < width; j++) {
                            blockPaintImage.set(j, i, newColor);
                        }
                    }
                }
                else {
                    for (int i = row; i < row + blockSize; i++) {
                        for (int j = col; j < col + blockSize; j++) {
                            pixelsList.add(image.get(j, i));
                        }
                    }
                    Color[] pixels = pixelsList.toArray(new Color[pixelsList.size()]);
                    Color newColor = getAvgColor(pixels);
                    for (int i = row; i < row + blockSize; i++) {
                        for (int j = col; j < col + blockSize; j++) {
                            blockPaintImage.set(j, i, newColor);
                        }
                    }
                }

            }
        }
        return blockPaintImage;
    }


    /* ===== TASK 4 ===== */
    /**public Image greenScreen(Color screenColour, Image backgroundImage) {
        int width = this.width;
        int height = this.height;
        boolean[][] visited = new boolean[width][height];
        int targetRGB = screenColour.getRGB();

        // Variables to track the bounding rectangle
        int minX = width, minY = height, maxX = 0, maxY = 0;
        boolean foundRegion = false;

        // Find the largest connected region of the target color
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!visited[x][y] && this.get(x, y).getRGB() == targetRGB) {
                    minX = maxX = x;
                    minY = maxY = y;
                    foundRegion = true;
                    dfs(x, y, targetRGB, visited);
                    break; // stop after finding the first region
                }
            }
            if (foundRegion) break;
        }

        if (!foundRegion) {
            System.out.println("No region found with the specified color.");
            return this; // Return the original image if no region is found
        }

        // Create a new image for the result
        Image resultImage = new Image(width, height); // Assumes a constructor that takes width and height

        // Overlay the background image on the region, tiling if necessary
        int bgWidth = backgroundImage.width();
        int bgHeight = backgroundImage.height();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x >= minX && x <= maxX && y >= minY && y <= maxY &&
                        this.get(x, y).getRGB() == targetRGB) {
                    // Replace target color with background image pixel
                    int bgX = (x - minX) % bgWidth;
                    int bgY = (y - minY) % bgHeight;
                    resultImage.set(x, y, backgroundImage.get(bgX, bgY));
                } else {
                    // Keep original pixel
                    resultImage.set(x, y, this.get(x, y));
                }
            }
        }

        return resultImage; // Return the new image with the green screen effect applied
    }

    // Recursive DFS method to explore the region
    private void dfs(int x, int y, int targetRGB, boolean[][] visited) {
        visited[x][y] = true;

        // Update bounding rectangle
        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);
        minY = Math.min(minY, y);
        maxY = Math.max(maxY, y);

        // Directions for DFS traversal: left, right, up, down
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < this.width() && newY >= 0 && newY < this.height()
                    && !visited[newX][newY] && this.get(newX, newY).getRGB() == targetRGB) {
                dfs(newX, newY, targetRGB, visited);
            }
        }
    }*/


    /**public Map<Integer[], Color> getNeighbours(int col, int row) {
        Map<Integer[], Color> pixelMap = new HashMap<>();
        Color pixel0 = image.get(col, row);
        Integer[] coordinate0 = {col, row};
        pixelMap.put(coordinate0, pixel0);
        boolean leftCol = col > 0;
        boolean rightCol = col + 1 < width;
        boolean topRow = row > 0;
        boolean bottomRow = row + 1 < height;
        if (!leftCol) {
            if (!topRow) {
                Integer[][] coordinates ={{col+1,row}, {col,row+1},{col+1,row+1}};
                for (Integer[] coordinate : coordinates) {
                    Color pixel = image.get(coordinate[0], coordinate[1]);
                    pixelMap.put(coordinate, pixel);
                }


            }
            else if (!bottomRow) {
                Integer[][] coordinates ={{col+1,row}, {col,row-1},{col+1,row-1}};
                for (Integer[] coordinate : coordinates) {
                    Color pixel = image.get(coordinate[0], coordinate[1]);
                    pixelMap.put(coordinate, pixel);
                }

            }
            else {
                Integer[][] coordinates ={{col+1,row}, {col,row+1},{col,row-1},{col+1,row+1},{col+1,row-1}};
                for (Integer[] coordinate : coordinates) {
                    Color pixel = image.get(coordinate[0], coordinate[1]);
                    pixelMap.put(coordinate, pixel);
                }

            }
        }
        else if (!rightCol) {
            if (!topRow) {
                Integer[][] coordinates ={{col-1,row}, {col,row+1},{col-1,row+1}};
                for (Integer[] coordinate : coordinates) {
                    Color pixel = image.get(coordinate[0], coordinate[1]);
                    pixelMap.put(coordinate, pixel);
                }
            }
            else if (!bottomRow) {
                Integer[][] coordinates ={{col-1,row}, {col,row-1},{col-1,row-1}};
                for (Integer[] coordinate : coordinates) {
                    Color pixel = image.get(coordinate[0], coordinate[1]);
                    pixelMap.put(coordinate, pixel);
                }

            }
            else {
                Integer[][] coordinates ={{col-1,row}, {col,row+1},{col,row-1},{col-1,row-1},{col-1,row+1}};
                for (Integer[] coordinate : coordinates) {
                    Color pixel = image.get(coordinate[0], coordinate[1]);
                    pixelMap.put(coordinate, pixel);
                }

            }
        }
        else if (!topRow) {
            Integer[][] coordinates ={{col+1,row}, {col+1,row+1},{col,row+1},{col-1,row},{col-1,row+1}};
            for (Integer[] coordinate : coordinates) {
                Color pixel = image.get(coordinate[0], coordinate[1]);
                pixelMap.put(coordinate, pixel);
            }

        }
        else if (!bottomRow) {
            Integer[][] coordinates ={{col+1,row}, {col+1,row-1},{col,row-1},{col-1,row},{col-1,row-1}};
            for (Integer[] coordinate : coordinates) {
                Color pixel = image.get(coordinate[0], coordinate[1]);
                pixelMap.put(coordinate, pixel);
            }
        }
        else {
            Integer[][] coordinates ={{col+1,row}, {col+1,row-1},{col+1,row+1},{col-1,row},{col-1,row-1},{col-1,row+1}, {col,row-1},{col,row+1}};
            for (Integer[] coordinate : coordinates) {
                Color pixel = image.get(coordinate[0], coordinate[1]);
                pixelMap.put(coordinate, pixel);
            }

        }
        return pixelMap;
    }


    public boolean present(Integer[] givenCoordinate, Color givenColor, Image image) {
        int width = image.width();
        int height = image.height();
        for (int col=0; col<width; col++){
            for (int row=0; row<height; row++){
                Integer[] coordinate ={col,row};
                if (Arrays.equals(coordinate, givenCoordinate) && givenColor.equals(image.get(col, row))) {
                    return true;
                }
            }
        }
        return false;
    }
    public Rectangle getMaxRectangle(Map<Rectangle, Integer> regions){
        List<Integer> regionsSize = new ArrayList<>(regions.values());
        Integer MaxRegion = Collections.max(regionsSize);
        for (Rectangle rectangle : regions.keySet()){
            if (regions.get(rectangle).equals(MaxRegion)){
                return rectangle;
            }
        }
        return null;
    }

    public Image greenScreen(Color screenColour, Image backgroundImage) {
        // TODO: Implement this method
        Image greenScreenImage = new Image(width,height);
        Map<Integer[], Color> passNeighbours = new HashMap<>();
        Map<Integer[], Color> uniqueElements = new HashMap<>();
        ArrayList<Integer> rowValues = new ArrayList<>();
        ArrayList<Integer> colValues = new ArrayList<>();
        Map<Rectangle, Integer> regions = new HashMap<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                Color pixel = image.get(col, row);
                Integer[] coordinate = {col, row};

                if (pixel.equals(screenColour) && !present(coordinate, pixel, greenScreenImage)) {

                    uniqueElements.put(coordinate, pixel);

                    int pixelCount = 0;
                    greenScreenImage.set(col, row, pixel);
                    pixelCount++;

                    while (!uniqueElements.isEmpty()) {

                        Set<Integer[]> pixelCoordinates = uniqueElements.keySet();

                        for (Integer[] pixelCoordinate : pixelCoordinates) {

                            int pixelCol = pixelCoordinate[0];
                            int pixelRow = pixelCoordinate[1];
                            Map<Integer[], Color> neighbours = getNeighbours(pixelCol, pixelRow);
                            Set<Integer[]> neighbourCoordinates = neighbours.keySet();

                            for (Integer[] neighbourCoordinate : neighbourCoordinates) {

                                Color neighbourPixel = neighbours.get(neighbourCoordinate);
                                int neighbourPixelCol = neighbourCoordinate[0];
                                int neighbourPixelRow = neighbourCoordinate[1];

                                if (neighbourPixel.equals(screenColour) && !present(neighbourCoordinate, neighbourPixel, greenScreenImage)) {

                                    greenScreenImage.set(neighbourPixelCol, neighbourPixelRow, neighbourPixel);
                                    pixelCount++;
                                    rowValues.add(neighbourPixelRow);
                                    colValues.add(neighbourPixelCol);
                                    passNeighbours.put(neighbourCoordinate, neighbourPixel);
                                }
                            }

                        }
                        uniqueElements.clear();
                        uniqueElements.putAll(passNeighbours);
                        passNeighbours.clear();
                    }
                    int maxRow = Collections.max(rowValues);
                    int maxCol = Collections.max(colValues);
                    int minRow = Collections.min(rowValues);
                    int minCol = Collections.min(colValues);
                    Rectangle rectangle = new Rectangle(minCol, minRow, maxCol, maxRow);
                    regions.put(rectangle, pixelCount);
                    rowValues.clear();
                    colValues.clear();
                }
            }

        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color pixel = image.get(col, row);
                greenScreenImage.set(col, row, pixel);
            }
        }

        Rectangle maxRectangle = getMaxRectangle(regions);
        int minCol = maxRectangle.xTopLeft;
        int minRow = maxRectangle.yTopLeft;
        int maxCol = maxRectangle.xBottomRight;
        int maxRow = maxRectangle.yBottomRight;
        int bgWidth = backgroundImage.width();
        int bgHeight = backgroundImage.height();
        boolean moreRows = (maxRow - minRow)< bgWidth;
        boolean moreCols = (maxCol - minCol)< bgHeight;
        if (moreRows && moreCols){
            for (int row=minRow; row<=maxRow; row++ ){
                for (int col=minCol; col<=maxCol; col++){
                    greenScreenImage.set(col, row, backgroundImage.get(col, row));
                }
            }
        }
        else if(moreRows){
            for (int row=minRow; row<=maxRow; row++){
                int startCol = minCol;
                while(startCol+bgWidth<=maxCol) {
                    for (int col = startCol; col < startCol + bgWidth; col++) {
                        greenScreenImage.set(col, row, backgroundImage.get(col, row));
                    }
                    startCol += bgWidth;
                }
                for(int col=startCol; col<=maxCol; col++){
                    greenScreenImage.set(col, row, backgroundImage.get(col, row));
                }
            }
        }
        else if(moreCols){
            for (int col=minCol; col<=maxCol; col++){
                int startRow = minRow;
                while(startRow+bgHeight<=maxRow) {
                    for (int row = startRow; row < startRow + bgHeight; row++) {
                        greenScreenImage.set(col, row, backgroundImage.get(col, row));
                    }
                    startRow += bgHeight;
                }
                for(int row=startRow; row<=maxRow; row++){
                    greenScreenImage.set(col, row, backgroundImage.get(col, row));
                }
            }
        }
        else{
            int startRow = minRow;
            int startCol = minCol;
            while(startRow+bgHeight<=maxRow) {
                while (startCol+bgWidth<=maxCol) {
                    for (int row = startRow; row < startRow + bgHeight; row++) {
                        for (int col = startCol; col < startCol + bgWidth; col++) {
                            greenScreenImage.set(col, row, backgroundImage.get(col, row));
                        }
                    }
                    startCol += bgWidth;
                }
                startRow += bgHeight;
            }
            int endRow = startRow;
            int endCol = startCol;
            for (int row=endRow; row<=maxRow; row++){
                startCol = minCol;
                while(startCol+bgWidth<=maxCol) {
                    for (int col = startCol; col < startCol + bgWidth; col++) {
                        greenScreenImage.set(col, row, backgroundImage.get(col, row));
                    }
                    startCol += bgWidth;
                }
            }

            for (int col=endCol; col<=maxCol; col++){
                startRow = minRow;
                while(startRow+bgHeight<=maxRow) {
                    for (int row = startRow; row < startRow + bgHeight; row++) {
                        greenScreenImage.set(col, row, backgroundImage.get(col, row));
                    }
                    startRow += bgHeight;
                }
            }
            for (int row=endRow; row<=maxRow; row++){
                for (int col=endCol; col<=maxCol; col++){
                    greenScreenImage.set(col, row, backgroundImage.get(col, row));
                }
            }
        }

        return greenScreenImage;
    }*/



    /* ===== TASK 5 ===== */

    public Image alignTextImage() {
        // TODO: Implement this method
        return null;
    }
}
