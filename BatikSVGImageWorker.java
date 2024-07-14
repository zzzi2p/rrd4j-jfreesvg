package net.i2p.rrd4j;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.rrd4j.graph.ImageWorker;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

/**
 *  RRD4J adapter for Apache Batik
 *
 *  Requires: Apache Batik https://xmlgraphics.apache.org/batik/download.html
 *  Requires: rrd4j 3.10 or higher
 *  Ref: https://github.com/rrd4j/rrd4j/issues/165
 *
 *  Specific Batik jars/modules required, if you don't want to just use the 4 MB batik-all.jar,
 *  these total about 1 MB:
 *<ul>
 *<li>batik-awt-util.jar
 *<li>batik-constants.jar
 *<li>batik-dom.jar
 *<li>batik-ext.jar
 *<li>batik-i18n.jar
 *<li>batik-svggen.jar
 *<li>batik-util.jar
 *<li>batik-xml.jar
 *</ul>
 *  Note that some features may require additional jars, untested.
 *
 *  Usage:
 *  No ImageIO/BufferedImage/ImageWriter required!
 *
 *<pre>
 *      RRDGraph graph = new RrdGraph(graphdef, new BatikSVGImageWorker(width, height));
 *      outputstream.write(graph.getRrdGraphInfo().getBytes());
 *</pre>
 *
 *  License: Apache 2.0 (same as RRD4J)
 *
 *  @since 2024-06-20
 *  @author zzz
 */
public class BatikSVGImageWorker extends ImageWorker {
    private SVGGraphics2D g2d;
    private AffineTransform initialAffineTransform;
    private int imgWidth;
    private int imgHeight;

    public BatikSVGImageWorker(int width, int height) {
        resize(width, height);
    }

    protected void resize(int width, int height) {
        imgWidth = width;
        imgHeight = height;
        // https://xmlgraphics.apache.org/batik/using/svg-generator.html
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
        g2d = new SVGGraphics2D(document);
        g2d.setSVGCanvasSize(new Dimension(imgWidth, imgHeight));
        g2d.setClip(0, 0, imgWidth, imgHeight);
        setG2d(g2d);
        initialAffineTransform = g2d.getTransform();
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    }

    protected void reset(Graphics2D g2d) {
        g2d.setTransform(initialAffineTransform);
        g2d.setClip(0, 0, imgWidth, imgHeight);
    }

    protected void makeImage(OutputStream os) throws IOException {
        Writer out = new OutputStreamWriter(os, "UTF-8");
        g2d.stream(out, false);
    }

    /**
     *  Overridden because the SVG format essentially strips leading/trailing spaces,
     *  causing alignment issues in ValueAxis with the %x.y number formatting.
     *  Consecutive spaces within text are also probably collapsed, that is not addressed here.
     */
    @Override
    protected void drawString(String text, int x, int y, Font font, Paint paint) {
        super.drawString(text.trim(), x, y, font, paint);
    }

    /**
     *  Overridden because the SVG format essentially strips leading/trailing spaces,
     *  causing alignment issues in ValueAxis with the %x.y number formatting.
     *  Consecutive spaces within text are also probably collapsed, that is not addressed here.
     */
    @Override
    protected double getStringWidth(String text, Font font) {
        return super.getStringWidth(text.trim(), font);
    }
}
