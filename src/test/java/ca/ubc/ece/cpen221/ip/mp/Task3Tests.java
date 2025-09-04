package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ca.ubc.ece.cpen221.ip.mp.ImageProcessing.bestMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Tests {
    /**
     * @return test image
     */
    public Image createTestImage() {
        Image expectedBlockImage = new Image(100, 100);
        int width = expectedBlockImage.width();
        int height = expectedBlockImage.height();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                expectedBlockImage.set(col, row, Color.WHITE);
            }
        }
        return expectedBlockImage;
    }

    @Test
    public void test_cosineSimilarity() {
        Image originalImg = createTestImage();
        originalImg.save("resources/tests/testImage2.jpg");
        Image img1 = new Image("resources/tests/list1.jpg");
        Image img2 = new Image("resources/tests/list2.jpg");
        Image img3 = new Image("resources/tests/list3.jpg");
        ArrayList<Image> arraylist = new ArrayList<>();
        arraylist.add(img3);
        arraylist.add(img1);
        arraylist.add(img2);
        ArrayList<Image> expectedList = new ArrayList<>();
        expectedList.add(img1);
        expectedList.add(img2);
        expectedList.add(img3);
        List<Image> expected = expectedList;
        List<Image> list = arraylist;
        List outputList = bestMatch(originalImg, list);
        assertEquals(expected, outputList);
    }

}
