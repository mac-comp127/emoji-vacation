package edu.macalester.comp124.emojivacation;

import java.awt.Color;
import java.util.Random;

import comp124graphics.Arc;
import comp124graphics.CanvasWindow;
import comp124graphics.Ellipse;
import comp124graphics.Line;
import comp124graphics.Rectangle;
import comp124graphics.Triangle;

public class EmojiVacation {
    private static final Color
        SUN_YELLOW = new Color(255, 255, 120),
        SUN_BORDER_YELLOW = new Color(220, 220, 60),
        SKY_BLUE = new Color(204, 217, 255),
        CLOUD_COLOR = new Color(255, 255, 255, 128),
        TREE_TRUNK_COLOR = new Color(85, 53, 17),
        TREE_LEAVES_COLOR = new Color(23, 175, 19),
        GRASS_COLOR = new Color(188, 218, 159),
        MOUNTAIN_COLOR = new Color(118, 154, 254),

        EMOJI_FACE_COLOR = new Color(255, 228, 51),
        EMOJI_MOUTH_COLOR = new Color(120, 40, 45),

        NO_SLIDE_COLOR = new Color(34, 33, 26);

    private CanvasWindow canvas;
    private Random random;

    public static void main(String[] args) {
        EmojiVacation emojiVacation = new EmojiVacation();
        emojiVacation.doSlideShow();
    }

    public EmojiVacation() {
        canvas = new CanvasWindow("Emoji Family Vacation", 800, 600);
        random = new Random();
    }

    private void doSlideShow() {
        while(true) {
            createVacation(2, 3);
            canvas.pause(3000);

            canvas.removeAll();
            canvas.setBackground(NO_SLIDE_COLOR);
            canvas.pause(200);
        }
    }

    private void createVacation(int adults, int children) {
        canvas.setBackground(randomColorVariation(SKY_BLUE, 8));

        createSun(randomDouble(100, 700), randomDouble(60, 200), randomDouble(30, 50), randomInt(8, 24));

        createCloudRows();

        if(percentChance(50)) {
            createMountains(400, randomDouble(50, 300), randomInt(1, 5));
        }

        createGround(400);

        if(percentChance(60)) {
            createForest(410, 50, randomInt(0, 30));
        }

        createFamily(adults, children, 550);
    }

    private void createFamily(int adults, int children, double baselineY) {
        double adultSize = 160, childSize = 90, spacing = 20;

        double emojiX = 60;

        for(int n = 0; n < adults; n++) {
            createEmoji(emojiX, baselineY - adultSize, adultSize);
            emojiX += adultSize + spacing;
        }
        for(int n = 0; n < children; n++) {
            createEmoji(emojiX, baselineY - childSize, childSize);
            emojiX += childSize + spacing;
        }

        // Solution to “random family ordering” challenge
        // (replaces two for loops above):
        /*
        while(adults > 0 || children > 0) {
            double size;
            if(randomInt(0, adults + children - 1) < adults) {
                size = adultSize;
                adults--;
            } else {
                size = childSize;
                children--;
            }
            createEmoji(emojiX, baselineY - size, size);
            emojiX += size + spacing;
        }
        */
    }

    /**
     * Creates a emoji of a random type at the given position.
     */
    private void createEmoji(double left, double top, double size) {
        if(percentChance(20)) {
            createFrowny(left, top, size);
        } else {
            createSmiley(left, top, size);
        }
    }

    /**
     * Creates a basic smiley face.
     */
    private void createSmiley(double left, double top, double size) {
        Ellipse face = new Ellipse(left, top, size, size);
        face.setStrokeColor(Color.BLACK);
        face.setFillColor(EMOJI_FACE_COLOR);
        face.setFilled(true);
        canvas.add(face);

        createStandardEyes(left, top, size);
        createSmile(left + size * 0.2, top + size * 0.4, size * 0.6, size * 0.4);
    }

    private void createStandardEyes(double left, double top, double size) {
        createEye(left + size * 0.33, top + size * 0.33, size * 0.06);
        createEye(left + size * 0.67, top + size * 0.33, size * 0.06);
    }

    /**
     * Creates a sad face.
     */
    private void createFrowny(double left, double top, double size) {
        Ellipse face = new Ellipse(left, top, size, size);
        face.setStrokeColor(Color.BLACK);
        face.setFillColor(EMOJI_FACE_COLOR);
        face.setFilled(true);
        canvas.add(face);

        createStandardEyes(left, top, size);
        createFrown(left + size * 0.2, top + size * 0.6, size * 0.6, size * 0.4);
    }

    /**
     * Creates a single eye-colored eye, centered at the given location.
     */
    private void createEye(double x, double y, double radius) {
        Ellipse eye = new Ellipse(x - radius, y - radius, radius * 2, radius * 2);
        eye.setFillColor(Color.BLACK);
        eye.setFilled(true);
        eye.setStroked(false);
        canvas.add(eye);
    }

    /**
     * Draws a smiling mouth, using an arc bounded by the given box.
     */
    private void createSmile(double top, double left, double width, double height) {
        Arc mouth = new Arc(top, left, width, height, 200.0, 140.0);
        mouth.setStrokeColor(EMOJI_MOUTH_COLOR);
        mouth.setStrokeWidth(4);
        canvas.add(mouth);
    }

    /**
     * Draws a frowning mouth, using an arc bounded by the given box.
     */
    private void createFrown(double top, double left, double width, double height) {
        Arc mouth = new Arc(top, left, width, height, 20.0, 140.0);
        mouth.setStrokeColor(EMOJI_MOUTH_COLOR);
        mouth.setStrokeWidth(4);
        canvas.add(mouth);
    }

    /**
     * Fills the bottom of the screen with a solid color. Even emojis need to stand somewhere!
     *
     * @param horizonY The top of the “ground” rectangle.
     */
    private void createGround(double horizonY) {
        Rectangle ground = new Rectangle(
            0, horizonY,
            canvas.getWidth(), canvas.getHeight() - horizonY);
        ground.setFillColor(randomColorVariation(GRASS_COLOR, 32));
        ground.setFilled(true);
        ground.setStroked(false);
        canvas.add(ground);
    }

    /**
     * Creates a mountain range.
     *
     * @param baseY  The vertical position of the foot of the mountains
     * @param size   The height of each layer of mountains, and width of each triangle
     * @param layers The number of layers of mountain ranges to create
     */
    private void createMountains(double baseY, double size, int layers) {
        for(int layer = layers - 1; layer >= 0; layer--) {
            createLayerOfMountains(baseY - layer * size * 0.2, size);
        }
    }

    /**
     * Creates one layer of a mountain range.
     *
     * @param layerBaseY The position of the feet of the mountains
     * @param size The maximum height of the peaks
     */
    private void createLayerOfMountains(double layerBaseY, double size) {
        double layerLeft = randomDouble(-size, 0);
        double layerRight = canvas.getWidth() + size;
        Color layerColor = randomColorVariation(MOUNTAIN_COLOR, 16);

        double x = layerLeft;
        while(x < layerRight) {
            double curHeight = randomDouble(size * 0.4, size),
                   curWidth = curHeight * randomDouble(1.0, 1.6);
            Triangle peak = new Triangle(
                x - curWidth, layerBaseY,
                x, layerBaseY - curHeight,
                x + curWidth, layerBaseY);
            peak.setFillColor(layerColor);
            peak.setFilled(true);
            peak.setStroked(false);
            canvas.add(peak);
            x += curWidth * 0.5;
        }
    }

    /**
     * Creates many trees spanning the full width of the screen.
     *
     * @param baseY Vertical position of the highest tree’s tree
     * @param ySpan Vertical distance spanned by the tree trunks’ bases
     * @param count Number of trees
     */
    private void createForest(double baseY, double ySpan, int count) {
        for(int n = 0; n < count; n++) {
            createTree(randomDouble(0, canvas.getWidth()), baseY + n * ySpan / count, 80, 90);
        }
    }

    /**
     * Creates a tree with a brown trunk and idyllic green leaves.
     *
     * @param centerX      The horizontal center of the trunk
     * @param baseY        The vertical position of the base of the trunk
     * @param trunkHeight  The distance from the trunk’s base to the center of the leaves
     * @param leavesSize   The width and height of the cluster of leaves
     */
    private void createTree(double centerX, double baseY, double trunkHeight, double leavesSize) {
        Color trunkColor = randomColorVariation(TREE_TRUNK_COLOR, 8);
        double trunkWidth = trunkHeight * 0.2;

        Rectangle trunk = new Rectangle(
            centerX - trunkWidth / 2, baseY - trunkHeight,
            trunkWidth, trunkHeight);
        trunk.setFillColor(trunkColor);
        trunk.setFilled(true);
        trunk.setStroked(false);
        canvas.add(trunk);

        // A little roundness at the bottom of the trunk
        double baseEllipseHeight = trunkWidth * 0.25;
        Ellipse trunkBase = new Ellipse(
            centerX - trunkWidth / 2, baseY - baseEllipseHeight / 2,
            trunkWidth, baseEllipseHeight);
        trunkBase.setFillColor(trunkColor);
        trunkBase.setFilled(true);
        trunkBase.setStroked(false);
        canvas.add(trunkBase);

        createPuffs(
            centerX, baseY - trunkHeight,
            leavesSize, leavesSize,
            false,
            leavesSize * 0.2,
            randomColorVariation(TREE_LEAVES_COLOR, 16));
    }

    /**
     * Puts the sun in your sky.
     *
     * @param radius   Radius of the sun’s inner circle, not including rays.
     * @param rayCount The number of rays emanating from the sun. May be 0.
     */
    private void createSun(double centerX, double centerY, double radius, int rayCount) {
        Ellipse sun = new Ellipse(
            centerX - radius, centerY - radius,
            radius * 2, radius * 2);
        sun.setFillColor(SUN_YELLOW);
        sun.setFilled(true);
        sun.setStrokeColor(SUN_BORDER_YELLOW);
        sun.setStrokeWidth(3);
        canvas.add(sun);

        createSunRays(centerX, centerY, radius * 1.2, radius * 1.4, rayCount);
    }

    /**
     * Draws the rays around the sun.
     */
    private void createSunRays(double centerX, double centerY, double innerRadius, double outerRadius, int rayCount) {
        for(int n = 0; n < rayCount; n++) {
            double theta = Math.PI * 2 * n / rayCount;
            double dx = Math.cos(theta),
                   dy = Math.sin(theta);
            Line ray = new Line(
                centerX + dx * innerRadius, centerY + dy * innerRadius,
                centerX + dx * outerRadius, centerY + dy * outerRadius);
            ray.setStrokeWidth(3);
            ray.setStrokeColor(SUN_YELLOW);
            canvas.add(ray);
        }
    }

    /**
     * Creates clouds of a random size and puffiness, spaced in neat rows and scattered horizontally.
     */
    private void createCloudRows() {
        double cloudRowSize = randomDouble(20, 120);
        double cloudPuffSize = randomDouble(20, 40);
        for(double y = 0; y < canvas.getHeight(); y += cloudRowSize) {
            createPuffs(
                randomDouble(0, 800), y,
                randomDouble(100, 200), cloudRowSize * 0.6,
                true,
                cloudPuffSize,
                CLOUD_COLOR);
        }
    }

    /**
     * Adds a clump of circular puffs. Useful for clouds and treetops.
     *
     * @param centerX    The midpoint of the whole cloud
     * @param baselineY  The y-coordinate of the midpoints of the puffs that make up the bottom edge
     * @param width      The approximate width (with random variation) spanned by the centers of the
     *                   cloud’s puffs. This does not include the size of the individual puffs.
     * @param height     The approximate extent of the puff centers above the cloud’s baseline
     * @param flatBottom True = semicircle (with bottom on baseline);
     *                   false = full circle (centered on baseline)
     * @param puffSize   The size of circles that make up the cloud, with random variation.
     */
    private void createPuffs(
            double centerX, double baselineY,
            double width, double height,
            boolean flatBottom,
            double puffSize,
            Color puffColor) {

        double maxTheta;
        if(flatBottom) {
            maxTheta = Math.PI;
        } else {
            maxTheta = Math.PI * 2;
        }
        double puffDensity = 0.3;
        int puffCount = (int) Math.ceil(Math.PI * width * height / puffSize * puffDensity);
        for(int puffNum = 0; puffNum < puffCount; puffNum++) {
            double theta = randomDouble(0, maxTheta);
            double r = randomDouble(0, 1);
            double curPuffSize = puffSize * randomDouble(0.5, 1.5);
            Ellipse puff = new Ellipse(
                centerX   - curPuffSize / 2 + Math.cos(theta) * r * width / 2,
                baselineY - curPuffSize / 2 - Math.sin(theta) * r * height / 2,
                curPuffSize, curPuffSize);
            puff.setFillColor(randomColorVariation(puffColor, 3));
            puff.setFilled(true);
            puff.setStroked(false);
            canvas.add(puff);
        }
    }

    /**
     * Convenience to return a random floating point number, min ≤ n < max.
     */
    private double randomDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * Convenience to return a random integer, min ≤ n ≤ max.
     * Note that max is inclusive.
     */
    private int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Convenience to return true with the given percent change (0 = always false, 100 = always true).
     */
    private boolean percentChance(double chance) {
        return random.nextDouble() * 100 < chance;
    }

    /**
     * Returns a slightly different color than the given one. Useful for making a bunch of items not look
     * entirely identical.
     */
    private Color randomColorVariation(Color color, int amount) {
        return new Color(
            colorChannelVariation(color.getRed(), amount),
            colorChannelVariation(color.getGreen(), amount),
            colorChannelVariation(color.getBlue(), amount),
            color.getAlpha());
    }

    /**
     * Varies the given value randomly, pinned to [0...255].
     */
    private int colorChannelVariation(int c, int amount) {
        return Math.min(255, Math.max(0, c + randomInt(-amount, amount)));
    }
}
