package emojivacation;

import edu.macalester.graphics.*;

import java.awt.Color;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ProvidedEmojis {
    private static final Color
        HEAD_COLOR = new Color(0xFFDE30),
        HEAD_OUTLINE_COLOR = new Color(0xAC9620),
        MOUTH_COLOR = new Color(0xE45B5B),
        IRIS_COLOR = new Color(0x294CD6),
        PUPIL_COLOR = new Color(0x000307),
        OCULAR_HIGHLIGHT_COLOR = new Color(0xD4FFFFFF, true),
        EYELID_COLOR = new Color(0x3E1919),
        NAUSEOUS_COLOR = new Color(0x589817),
        NAUSEOUS_LINE_COLOR = new Color(0x0F1A04);

    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Emoji", 900, 600);

        List<GraphicsGroup> faces = List.of(
            createSmileyFace(100),
            createFrownyFace(130),
            createWinkingFace(160),
            createNauseousFace(190),
            createContentedFace(210)
        );

        double x = 20;
        for (GraphicsGroup face : faces) {
            face.setPosition(x, 500 - face.getHeight());
            canvas.add(face);
            x += face.getWidth() + 10;
        }
    }

    /**
     * Creates a smiley face emoji.
     *
     * @param size The overall width and height of the emoji.
     * @return A graphic that you can add to a window, or place inside some other graphics group.
     */
    public static GraphicsGroup createSmileyFace(double size) {
        GraphicsGroup group = new GraphicsGroup();

        group.add(createHead(size, size));

        GraphicsGroup leftEye = createEye(size * 0.15);
        leftEye.setCenter(size * 0.3, size * 0.3);
        group.add(leftEye);

        GraphicsGroup rightEye = createEye(size * 0.15);
        rightEye.setCenter(size * 0.7, size * 0.3);
        group.add(rightEye);

        Arc mouth = createSmile(size * 0.6, size * 0.5);
        mouth.setCenter(size * 0.5, size * 0.75);
        group.add(mouth);

        return group;
    }

    public static GraphicsGroup createFrownyFace(double size) {
        GraphicsGroup group = new GraphicsGroup();

        group.add(createHead(size, size));

        GraphicsGroup leftEye = createEye(size * 0.15);
        leftEye.setCenter(size * 0.3, size * 0.3);
        group.add(leftEye);

        GraphicsGroup rightEye = createEye(size * 0.15);
        rightEye.setCenter(size * 0.7, size * 0.3);
        group.add(rightEye);

        Arc mouth = createFrown(size * 0.6, size * 0.5);
        mouth.setCenter(size * 0.5, size * 0.7);
        group.add(mouth);

        return group;
    }

    public static GraphicsGroup createWinkingFace(double size) {
        GraphicsGroup group = new GraphicsGroup();

        group.add(createHead(size, size));

        GraphicsGroup leftEye = createEye(size * 0.15);
        leftEye.setCenter(size * 0.3, size * 0.3);
        group.add(leftEye);

        Line rightEye = createFlatEyelid(size * 0.15, 0);
        rightEye.setCenter(size * 0.7, size * 0.3);
        group.add(rightEye);

        Arc mouth = createSmile(size * 0.6, size * 0.5);
        mouth.setCenter(size * 0.5, size * 0.75);
        group.add(mouth);

        return group;
    }

    public static GraphicsGroup createContentedFace(double size) {
        GraphicsGroup group = new GraphicsGroup();

        group.add(createHead(size, size));

        Arc leftEye = createClosedEye(size * 0.15);
        leftEye.setCenter(size * 0.3, size * 0.3);
        group.add(leftEye);

        Arc rightEye = createClosedEye(size * 0.15);
        rightEye.setCenter(size * 0.7, size * 0.3);
        group.add(rightEye);

        Arc mouth = createSmile(size * 0.6, size * 0.5);
        mouth.setCenter(size * 0.5, size * 0.75);
        group.add(mouth);

        return group;
    }

    public static GraphicsGroup createNauseousFace(double size) {
        GraphicsGroup group = new GraphicsGroup();

        Ellipse head = createHead(size, size);
        head.setFillColor(NAUSEOUS_COLOR);
        head.setStrokeColor(NAUSEOUS_LINE_COLOR);
        group.add(head);

        Line leftEye = createFlatEyelid(size * 0.15, -0.1);
        leftEye.setCenter(size * 0.3, size * 0.3);
        leftEye.setStrokeColor(NAUSEOUS_LINE_COLOR);
        group.add(leftEye);

        Line rightEye = createFlatEyelid(size * 0.15, 0.1);
        rightEye.setCenter(size * 0.7, size * 0.3);
        rightEye.setStrokeColor(NAUSEOUS_LINE_COLOR);
        group.add(rightEye);

        Arc mouth = createFrown(size * 0.6, size * 0.5);
        mouth.setCenter(size * 0.5, size * 0.75);
        mouth.setStrokeColor(NAUSEOUS_LINE_COLOR);
        group.add(mouth);

        return group;
    }

    private static GraphicsGroup createEye(double size) {
        GraphicsGroup eye = new GraphicsGroup();

        Ellipse eyeBall = new Ellipse(0, 0, size, size);
        eyeBall.setFillColor(PUPIL_COLOR);
        eyeBall.setStrokeColor(IRIS_COLOR);
        eyeBall.setStrokeWidth((float) (size * 0.2));
        eye.add(eyeBall);

        Ellipse ocularHighlight = new Ellipse(size * 0.5, size * 0.1, size * 0.4, size * 0.4);
        ocularHighlight.setFillColor(OCULAR_HIGHLIGHT_COLOR);
        ocularHighlight.setStroked(false);
        eye.add(ocularHighlight);

        return eye;
    }

    private static Arc createClosedEye(double size) {
        Arc eyelid = new Arc(0, 0, size * 1.2, size, -20, -140);
        eyelid.setStrokeWidth((float) (size * 0.12));
        eyelid.setStrokeColor(EYELID_COLOR);
        return eyelid;
    }

    private static Line createFlatEyelid(double width, double slope) {
        Line eye = new Line(0, 0, width, width * slope);
        eye.setStrokeWidth((float) (width * 0.15));
        eye.setStrokeColor(EYELID_COLOR);
        return eye;
    }

    private static Ellipse createHead(double height, double width) {
        Ellipse head = new Ellipse(0, 0, width, height);
        head.setFillColor(HEAD_COLOR);
        head.setStrokeColor(HEAD_OUTLINE_COLOR);
        head.setStrokeWidth(2);
        return head;
    }

    private static Arc createSmile(double ellipseWidth, double ellipseHeight) {
        Arc mouth = new Arc(0, 0, ellipseWidth, ellipseHeight, 200, 140);
        mouth.setStrokeColor(MOUTH_COLOR);
        mouth.setStrokeWidth(4);
        return mouth;
    }

    private static Line createFlatMouth(double width) {
        Line mouth = new Line(0, 0, width, 0);
        mouth.setStrokeColor(MOUTH_COLOR);
        mouth.setStrokeWidth((float) (width * 0.1));
        return mouth;
    }

    private static Arc createFrown(double ellipseWidth, double ellipseHeight) {
        Arc mouth = new Arc(0, 0, ellipseWidth, ellipseHeight, 20, 140);
        mouth.setStrokeColor(MOUTH_COLOR);
        mouth.setStrokeWidth(4);
        return mouth;
    }
}
