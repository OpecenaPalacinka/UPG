import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class MyGraphics2D extends Graphics2D {
    private StringBuffer svg = new StringBuffer();
    private Color curColor = Color.BLACK;
    private AffineTransform at = getTransform();

    public MyGraphics2D(int width, int height){
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:jfreesvg=\"http://www.jfree.org/jfreesvg/svg\" width=\"" + width + "\" height=\"" + height + "\" text-rendering=\"auto\" shape-rendering=\"auto\">");
    }


    public String getSVGElement() {
        return svg.toString() +"</svg>";
    }

    @Override
    public void draw(Shape shape) {

    }

    @Override
    public boolean drawImage(Image image, AffineTransform affineTransform, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public void drawImage(BufferedImage bufferedImage, BufferedImageOp bufferedImageOp, int i, int i1) {

    }

    @Override
    public void drawRenderedImage(RenderedImage renderedImage, AffineTransform affineTransform) {

    }

    @Override
    public void drawRenderableImage(RenderableImage renderableImage, AffineTransform affineTransform) {

    }

    @Override
    public void drawString(String s, int i, int i1) {

    }

    @Override
    public void drawString(String s, float v, float v1) {

    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, int i, int i1) {

    }

    @Override
    public boolean drawImage(Image image, int i, int i1, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, Color color, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, Color color, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public boolean drawImage(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, Color color, ImageObserver imageObserver) {
        return false;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void drawString(AttributedCharacterIterator attributedCharacterIterator, float v, float v1) {

    }

    @Override
    public void drawGlyphVector(GlyphVector glyphVector, float v, float v1) {

    }

    @Override
    public void fill(Shape shape) {

    }

    @Override
    public boolean hit(Rectangle rectangle, Shape shape, boolean b) {
        return false;
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return null;
    }

    @Override
    public void setComposite(Composite composite) {

    }

    @Override
    public void setPaint(Paint paint) {

    }

    @Override
    public void setStroke(Stroke stroke) {

    }

    @Override
    public void setRenderingHint(RenderingHints.Key key, Object o) {

    }

    @Override
    public Object getRenderingHint(RenderingHints.Key key) {
        return null;
    }

    @Override
    public void setRenderingHints(Map<?, ?> map) {

    }

    @Override
    public void addRenderingHints(Map<?, ?> map) {

    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

    @Override
    public Graphics create() {
        return null;
    }

    @Override
    public void translate(int x, int y) {
        if(at == null) {
            at = new AffineTransform();
        }
        at.translate(x,y);
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(Color color) {
        this.curColor = color;
    }

    @Override
    public void setPaintMode() {

    }

    @Override
    public void setXORMode(Color color) {

    }

    @Override
    public Font getFont() {
        return null;
    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return null;
    }

    @Override
    public Rectangle getClipBounds() {
        return null;
    }

    @Override
    public void clipRect(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setClip(int i, int i1, int i2, int i3) {

    }

    @Override
    public Shape getClip() {
        return null;
    }

    @Override
    public void setClip(Shape shape) {

    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void drawLine(int i, int i1, int i2, int i3) {

    }

    @Override
    public void fillRect(int i, int i1, int i2, int i3) {

    }

    @Override
    public void clearRect(int i, int i1, int i2, int i3) {

    }

    @Override
    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
       // double[] matrix = at.getMatrix();

        svg.append("<ellipse cx=\"" + (x + width/2) + "\" " +
                " cy=\"" + (y +height/2) + "\" " +
                " rx=\"" + (width/2) + "\" " +
                " ry=\"" + (height/2) + "\" " +
                " stroke=\"" + colorToSvg() + "\" " +
                " fill=\"none\"" +
                " transform=rotate\"("+20+")\" " +
                "/>");
    }

    private String colorToSvg() {
        return  "rgb(" + curColor.getRed() + "," + curColor.getGreen() + "," +curColor.getBlue()+")";
    }

    @Override
    public void fillOval(int i, int i1, int i2, int i3) {

    }

    @Override
    public void drawArc(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void fillArc(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void drawPolyline(int[] ints, int[] ints1, int i) {

    }

    @Override
    public void drawPolygon(int[] ints, int[] ints1, int i) {

    }

    @Override
    public void fillPolygon(int[] ints, int[] ints1, int i) {

    }

    @Override
    public void translate(double v, double v1) {

    }

    @Override
    public void rotate(double v) {
        if(at == null) {
            at = new AffineTransform();
        }
        at.rotate(Math.toRadians(v));
    }

    @Override
    public void rotate(double v, double v1, double v2) {

    }

    @Override
    public void scale(double v, double v1) {

    }

    @Override
    public void shear(double v, double v1) {

    }

    @Override
    public void transform(AffineTransform affineTransform) {

    }

    @Override
    public void setTransform(AffineTransform affineTransform) {
        if(affineTransform == null){
            at = null;
        } else {
            at = new AffineTransform(affineTransform);
        }
    }

    @Override
    public AffineTransform getTransform() {
        if(at == null) return null;
        return new AffineTransform(at);
    }

    @Override
    public Paint getPaint() {
        return null;
    }

    @Override
    public Composite getComposite() {
        return null;
    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public Color getBackground() {
        return null;
    }

    @Override
    public Stroke getStroke() {
        return null;
    }

    @Override
    public void clip(Shape shape) {

    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return null;
    }
}
