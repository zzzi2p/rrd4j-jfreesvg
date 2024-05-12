Adapter for RRD4J to create SVG images
======================================

Requires Java 8 or higher, [RRD4J 3.9.1 or higher](https://github.com/rrd4j/rrd4j) and [JFreeSVG](https://github.com/jfree/jfreesvg).

Note that JFreeSVG says Java 11 is required but that's apparently only for modules,
works fine with Java 8 when built from source.

### Usage Example

No ImageIO/BufferedImage/ImageWriter required!

```java
RRDGraph graph = new RrdGraph(graphdef, new SVGImageWorker(width, height));
outputstream.write(graph.getRrdGraphInfo().getBytes());
```

### License

Apache 2.0 (same as RRD4J)
Note that JFeeSVG is GPLv3.
