package EagerPrimMST;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import CommonElements.Edge;
import CommonElements.MyGraph;
import static CommonElements.GraphDrawer.*;
import static CommonElements.GraphReader.readGraphFromFile;


public class EagerPrimMST {

    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ pq;
    private Queue<Edge> mst;

    public EagerPrimMST(MyGraph graph, int startVertex) {
        int V = graph.V();
        edgeTo = new Edge[V];
        distTo = new double[V];
        marked = new boolean[V];
        mst = new LinkedList<>();
        pq = new IndexMinPQ(V);

        for (int v = 0; v < V; v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[startVertex] = 0.0;
        pq.insert(startVertex, 0.0);

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            visit(graph, v);
        }
    }


    private void visit(MyGraph graph, int v) {
        if(distTo[v]!=Double.POSITIVE_INFINITY){
            if(edgeTo[v]!=null) mst.add(edgeTo[v]);
        }

        marked[v] = true;

        for (Edge e : graph.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;

            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }


    public Iterable<Edge> mst() {
        return mst;
    }

    public double weight() {
        double totalWeight = 0.0;
        for (Edge edge : mst) {
            totalWeight += edge.weight();
        }
        return totalWeight;
    }

    public static void main(String[] args) {
        String filePath = "src/main/resources/tinyG.txt"; // Path to your .txt file with graph data
        Instant startTimeReadGraph = Instant.now();
        MyGraph graph = null;
        try {
            graph  = readGraphFromFile(filePath);
        } catch (IOException e) {
            System.err.println("Error reading graph data from file: " + e.getMessage());
            return;
        }
        if (graph == null) return;
        Instant endTimeReadGraph = Instant.now();
        double durationReadGraph = (double) java.time.Duration.between(startTimeReadGraph, endTimeReadGraph).toNanos() /1_000_000; // Timer for Duration Read Graph in milliseconds

        Instant startTimeMST = Instant.now();
        EagerPrimMST mst = new EagerPrimMST(graph, 0);
        long endTime = System.currentTimeMillis();
        Instant endTimeMST = Instant.now();
        double durationMST = (double) java.time.Duration.between(startTimeMST, endTimeMST).toNanos() /1_000_000; // Timer for Duration Eager Prim MST in milliseconds



        System.out.println("Eager Prim MST Edges:");
        for (Edge edge : mst.mst()) {
            System.out.println(edge);
        }
        System.out.println("Total MST Weight: " + mst.weight());
        System.out.println("Execution Time of Read Graph File: " + durationReadGraph + " milliseconds");
        System.out.println("Execution Time of MST: " + durationMST + " milliseconds");

        try{
            generateGraphviz(graph,"graph.png", String.valueOf(durationReadGraph));
            generateGraphvizMST(mst.mst(),"graph_eager_mst.png", String.valueOf(durationMST));
//            generateGraphviz(graph);
        }catch(IOException e){
            System.err.println("Error generating graph image: " + e.getMessage());
        }
    }


}

