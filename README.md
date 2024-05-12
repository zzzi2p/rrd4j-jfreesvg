Adapter for RRD4J to create SVG images
======================================

Requires Java 8 or higher, [RRD4J 3.9.1 or higher](https://github.com/rrd4j/rrd4j) and [JFreeSVG](https://github.com/jfree/jfreesvg).

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

### TODO

Create a version for [TwelveMonkeys](https://github.com/haraldk/TwelveMonkeys) + [Apache Batik](https://xmlgraphics.apache.org/batik/)


### DEMO

![Sample Image](https://raw.githubusercontent.com/zzzi2p/rrd4j-jfreesvg/main/sample.svg)


### License

Apache 2.0 (same as RRD4J)
Note that JFeeSVG is GPLv3.
