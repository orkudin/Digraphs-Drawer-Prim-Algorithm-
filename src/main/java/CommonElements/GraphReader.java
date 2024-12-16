package CommonElements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {
    public static MyGraph readGraphFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int V = Integer.parseInt(reader.readLine()); // read number of vertices
        int E = Integer.parseInt(reader.readLine());// read number of edges
        MyGraph graph = new MyGraph(V);

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 3) {
                int v = Integer.parseInt(parts[0]);
                int w = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                graph.addEdge(v, w, weight); // using default weight of 1.0, change it if needed
            }
        }
        reader.close();
        return graph;
    }
}
