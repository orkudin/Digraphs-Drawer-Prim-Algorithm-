package CommonElements;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;

public class GraphDrawer {
    public static void generateGraphviz(MyGraph graph, String file_name, String durationMs) throws IOException {
        StringBuilder dotSource = new StringBuilder();
        dotSource.append("digraph G {\n");
        dotSource.append("label = \"Execution Time: ").append(durationMs).append(" milliseconds\";\n");
        for (int v = 0; v < graph.V(); v++) {
            for (Edge e : graph.adj(v)) {
                dotSource.append("  ").append(e.either())
                        .append(" -> ").append(e.other(e.either()))
                        .append(" [label=\"").append(String.format("%.2f", e.weight())).append("\"];\n");
            }
        }
        dotSource.append("}\n");


        File file = new File(file_name);
        // Use Graphviz.fromString to provide the dot source, and increase memory allocation to 64mb:
        Graphviz.fromString(dotSource.toString())
                .totalMemory(67108864) //64MB of memory for the Graphviz rendering process
                .render(Format.PNG)
                .toFile(file);
        System.out.println("Graph image generated on file " + file.getAbsolutePath());
    }

    public static void generateGraphvizMST(Iterable<Edge> mstEdges, String file_name, String durationMs) throws IOException {
        StringBuilder dotSource = new StringBuilder();
        dotSource.append("digraph MST {\n");
        dotSource.append("label = \"Execution Time: ").append(durationMs).append(" milliseconds\";\n");
        for (Edge e : mstEdges) {
            dotSource.append("  ").append(e.either())
                    .append(" -> ").append(e.other(e.either()))
                    .append(" [label=\"").append(String.format("%.2f", e.weight())).append("\"];\n");
        }
        dotSource.append("}\n");
        File file = new File(file_name);
        Graphviz.fromString(dotSource.toString())
                .totalMemory(67108864)
                .render(Format.PNG)
                .toFile(file);
        System.out.println("MST image generated on file "+ file.getAbsolutePath());

    }
}
