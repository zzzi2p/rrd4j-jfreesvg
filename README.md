Adapter for RRD4J to create SVG images
======================================

Requires Java 8 or higher, [RRD4J 3.10 or higher](https://github.com/rrd4j/rrd4j) and [JFreeSVG](https://github.com/jfree/jfreesvg).
See below for Apache Batik version.

Note that JFreeSVG says Java 11 is required but that's apparently only for modules,
works fine with Java 8 when built from source.


### Benefits

- Faster server-side rendering
- Greatly reduced server-side memory usage
- Crisper, scaleable images
- Smaller image files (especially if gzipped)
- Much less bandwidth usage if images are gzipped in-flight
- JFreeSVG binary is only 50KB
- Rendered images almost identical to the PNG versions


### Usage Example

No ImageIO/BufferedImage/ImageWriter required!

```java
RRDGraph graph = new RrdGraph(graphdef, new SVGImageWorker(width, height));
outputstream.write(graph.getRrdGraphInfo().getBytes());
```

### DEMO

![Sample Image](https://raw.githubusercontent.com/zzzi2p/rrd4j-jfreesvg/main/sample.svg)

![Sample Image 2](https://raw.githubusercontent.com/zzzi2p/rrd4j-jfreesvg/main/test.svg)

### Apache Batik Adapter

An equivalent adapter for [Apache Batik](https://xmlgraphics.apache.org/batik/) is in BatikSVGImageWorker.java.

Specific Batik jars/modules required, if you don't want to just use the 4 MB batik-all.jar,
these total about 1 MB, or "only" 20x larger than jfreesvg:

- batik-awt-util.jar
- batik-constants.jar
- batik-dom.jar
- batik-ext.jar
- batik-i18n.jar
- batik-svggen.jar
- batik-util.jar
- batik-xml.jar

Note that some features may require additional jars, untested.

### License

Apache 2.0 (same as RRD4J)
Note that JFeeSVG is GPLv3.
