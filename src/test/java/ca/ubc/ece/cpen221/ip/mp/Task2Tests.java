package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Tests {

    @Test
    public void test_Denoising() {
        Image originalImg = createTestImage();
        Image expectedImg = expectedDenoiseImage();
        originalImg.save("resources/tests/testImage.jpg");
        expectedImg.save("resources/tests/denoiseExpected.jpg");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.denoise();
        outputImage.save("resources/tests/denoiseOutput.jpg");
        assertEquals(expectedImg, outputImage);
    }

    /**
     * @return test Image
     */
    public Image createTestImage() {
        Image expectedBlockImage = new Image(3, 3);
        int width = expectedBlockImage.width();
        int height = expectedBlockImage.height();
        for (int row = 0; row < height; row++) {
            if ( row == 0 || row == 2) {
                for (int col = 0; col < width; col++) {
                    expectedBlockImage.set(col, row, Color.BLACK);
                }
            }
            else {
                for (int col = 0; col < width; col++) {
                    expectedBlockImage.set(col, row, Color.WHITE);
                }                }
        }
        return expectedBlockImage;
    }
    /**
     * @return Expected denoise Image
     */
    public Image expectedDenoiseImage() {
        Image expectedDenoiseImage = new Image(3, 3);
        int width = expectedDenoiseImage.width();
        int height = expectedDenoiseImage.height();
        int R1 = Color.BLACK.getRed();
        int G1 = Color.BLACK.getGreen();
        int B1 = Color.BLACK.getBlue();
        int R2 = Color.WHITE.getRed();
        int G2 = Color.WHITE.getGreen();
        int B2 = Color.WHITE.getBlue();

        Color mix = new Color((R1+R2)/2, (G1+G2)/2, (B1+B2)/2);
        for (int row = 0; row < height; row++) {
            if ( row == 0 || row == 2) {
                for (int col = 0; col < width; col++) {
                    expectedDenoiseImage.set(col, row, mix);
                }
            }
            else {
                for (int col = 0; col < width; col++) {
                    expectedDenoiseImage.set(col, row, Color.BLACK);
                }                }
        }
        return expectedDenoiseImage;
    }



    @Test
    public void test_Weathering() {
        Image originalImg = new Image("resources/95006.jpg");
        Image expectedImg = new Image("resources/tests/95006-weathered.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.weather();
        assertEquals(expectedImg, outputImage);
    }
    /**
     * @return Expected block Image
     */
    public Image expectedBlockImage() {
        Image expectedBlockImage = new Image(3, 3);
        int width = expectedBlockImage.width();
        int height = expectedBlockImage.height();
        int R1 = Color.BLACK.getRed();
        int G1 = Color.BLACK.getGreen();
        int B1 = Color.BLACK.getBlue();
        int R2 = Color.WHITE.getRed();
        int G2 = Color.WHITE.getGreen();
        int B2 = Color.WHITE.getBlue();

        Color mix = new Color((R1+R2)/2, (G1+G2)/2, (B1+B2)/2);
        for (int row = 0; row < height; row++) {
            if ( row == 0 || row == 1) {
                for (int col = 0; col < width; col++) {
                    expectedBlockImage.set(col, row, mix);
                }
            }
            else {
                for (int col = 0; col < width; col++) {
                    expectedBlockImage.set(col, row, Color.BLACK);
                }                }
        }
        return expectedBlockImage;
    }
    @Test
    public void test_BlockPaint() {
        Image originalImg = new Image("resources/tests/testImage.jpg");
        Image expectedImg = expectedBlockImage();
        expectedImg.save("resources/tests/BlockExpected.jpg");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.blockPaint(2);
        outputImage.save("resources/tests/BlockOutput.jpg");
        assertEquals(expectedImg, outputImage);

    }
}

