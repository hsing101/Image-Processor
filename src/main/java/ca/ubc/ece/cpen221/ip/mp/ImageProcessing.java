package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import java.util.*;

public class ImageProcessing {

    /* ===== TASK 3 ===== */

    /**
     * Returns the cosine similarity of the given 2 images.
     *
     * @param img1 is not null
     * @param img2 is not null
     *
     * @return the cosine similarity
     */
    public static double cosineSimilarity(Image img1, Image img2) {
        ImageTransformer image1 = new ImageTransformer(img1);
        ImageTransformer image2 = new ImageTransformer(img2);
        Image greyImg1 = image1.grayscale();
        Image greyImg2 = image2.grayscale();

        // Ensure both images have the same dimensions
        if (greyImg1.height() != greyImg2.height() || greyImg1.width() != greyImg2.width()) {
            throw new IllegalArgumentException("Images must have the same dimensions");
        }

        List<Integer> vector1 = new ArrayList<>();
        List<Integer> vector2 = new ArrayList<>();

        for (int row = 0; row < greyImg1.height(); row++) {
            for (int col = 0; col < greyImg1.width(); col++) {
                vector1.add(greyImg1.getRGB(col, row));
                vector2.add(greyImg2.getRGB(col, row)); // Add corresponding values to both vectors
            }
        }

        double dotProduct = 0, magnitude1 = 0, magnitude2 = 0;

        // Calculate the dot product and magnitudes
        for (int i = 0; i < vector1.size(); i++) {
            int value1 = vector1.get(i);
            int value2 = vector2.get(i);

            dotProduct += value1 * value2;
            magnitude1 += Math.pow(value1, 2);
            magnitude2 += Math.pow(value2, 2);
        }

        // Compute the magnitudes
        double length1 = Math.sqrt(magnitude1);
        double length2 = Math.sqrt(magnitude2);

        // Return the cosine similarity (handling division by zero case)
        if (length1 == 0 || length2 == 0) {
            return 0;
        }

        return dotProduct / (length1 * length2);
    }

    /**
     * Returns the sorted list of images according to their cosine similarities with a given image.
     * The order of elements is in descending order of their cosine similarity.
     *
     * @param img is not null
     * @param matchingCandidates is not null
     *
     * @return the sorted list
     */
    public static List<Image> bestMatch(Image img, List<Image> matchingCandidates) {
        ImageTransformer image = new ImageTransformer(img);
        Image givenImage = image.grayscale();
        Map<Double, Image> map = new HashMap<>();

        List<Image> bestMatches = new ArrayList<>();

        for (Image candidate: matchingCandidates) {
            ImageTransformer t = new ImageTransformer(candidate);
            Image givenCandidate = t.grayscale();
            map.put(cosineSimilarity(givenImage, givenCandidate), candidate);
        }

        Map<Double, Image> sortedMap = new TreeMap<>();
        sortedMap.putAll(map);
        for (Double value : sortedMap.keySet()) {
            bestMatches.add(map.get(value));
        }

        return bestMatches;
    }
}
