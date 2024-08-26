Adapters for RRD4J to create SVG images
=======================================

There are three flavors here, with different dependencies:
- SVGImageWorker: jfreesvg dependency (50KB, GPLv3)
- BatikSVGImageWorker: Apache Batik dependency (4MB, Apache)
- SimpleSVGImageWorker: no dependency (Apache)

See below for Apache Batik and Simple flavors.

### Benefits

- Faster server-side rendering
- Greatly reduced server-side memory usage
- Crisper, scaleable images and text
- Smaller image files (especially if gzipped)
- Much less bandwidth usage if images are gzipped in-flight
- Rendered images almost identical to the PNG versions


### jfreesvg Adapter

Requires Java 8 or higher, [RRD4J 3.10 or higher](https://github.com/rrd4j/rrd4j) and [JFreeSVG](https://github.com/jfree/jfreesvg).

Note that JFreeSVG says Java 11 is required but that's apparently only for modules,
works fine with Java 8 when built from source.

Pro: Only 50KB. Con: GPLv3


## Usage Example

No ImageIO/BufferedImage/ImageWriter required!

```java
RRDGraph graph = new RrdGraph(graphdef, new SVGImageWorker(width, height));
outputstream.write(graph.getRrdGraphInfo().getBytes());
```

## DEMO

![Sample Image](https://raw.githubusercontent.com/zzzi2p/rrd4j-jfreesvg/main/sample.svg)

![Sample Image 2](https://raw.githubusercontent.com/zzzi2p/rrd4j-jfreesvg/main/test.svg)

### Apache Batik Adapter

An equivalent adapter for [Apache Batik](https://xmlgraphics.apache.org/batik/) is in BatikSVGImageWorker.java.
Java 8 or higher.

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

Pro: Apache licensed. Con: 4MB (or 1MB if you use only the jars listed above)

## Usage Example

```java
RRDGraph graph = new RrdGraph(graphdef, new BatikSVGImageWorker(width, height));
outputstream.write(graph.getRrdGraphInfo().getBytes());
```

### Simple No-Dependency Adapter

An equivalent adapter without dependencies is in SimpleSVGImageWorker.java,
together with SimpleSVGGraphics2D and SimpleSVGMaker.
Java 8 or higher.

The Simple version supports everything rrd4j needs, Apache licensed,
about 1/4 of the size of jfreesvg, without any external dependencies.
Unsupported Graphics2D methods and features will throw UnsupportedOperationExceptions.

Pros: Apache licensed, only 10KB. Con: May not support all use cases; not all Graphics2D methods are implemented.

## Usage Example

```java
RRDGraph graph = new RrdGraph(graphdef, new SimpleSVGImageWorker(width, height));
outputstream.write(graph.getRrdGraphInfo().getBytes());
```

### License

All flavors are Apache 2.0 (same as RRD4J)
