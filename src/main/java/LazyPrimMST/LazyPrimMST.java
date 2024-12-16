package LazyPrimMST;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import CommonElements.Edge;
import CommonElements.MyGraph;
import static CommonElements.GraphDrawer.*;
import static CommonElements.GraphReader.readGraphFromFile;


public class LazyPrimMST {

    private boolean[] marked;        // marked[v] = true if v on tree
    private Queue<Edge> mst;          // edges in the MST
    private PriorityQueue<Edge> pq; // crossing (cut) edges


    public LazyPrimMST(MyGraph graph, int startVertex) {
        marked = new boolean[graph.V()];
        mst = new LinkedList<>();
        pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));


        // Add all incident edges from startVertex to the priority queue
        visit(graph, startVertex);

        // Main loop, until we have V - 1 edges
        while (!pq.isEmpty() && mst.size() < graph.V() - 1) {
            Edge e = pq.poll();          // Get min edge from the priority queue
            int v = e.either();
            int w = e.other(v);

            if (marked[v] && marked[w]) continue;  // skip if both endpoints on tree

            mst.add(e);            // add edge e to the mst

            if (!marked[v]) visit(graph, v); //add vertices to tree and add their incident edges to pq
            if (!marked[w]) visit(graph, w); //add vertices to tree and add their incident edges to pq
        }
    }

    private void visit(MyGraph graph, int v) {
        marked[v] = true;   // Add v to the set of marked vertices on the tree
        // add all edges incident to v to the priority queue
        for (Edge e : graph.adj(v)) {
            if (!marked[e.other(v)])
                pq.add(e);
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
        String filePath = "src/main/resources/mediumG.txt"; // Path to your .txt file with graph data
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
        LazyPrimMST mst = new LazyPrimMST(graph, 0);
        Instant endTimeMST = Instant.now();
        double durationMST = (double) java.time.Duration.between(startTimeMST, endTimeMST).toNanos() /1_000_000; // Timer for Duration Lazy Prim MST in milliseconds

        System.out.println("Lazy Prim MST Edges:");
        for (Edge edge : mst.mst()) {
            System.out.println(edge);
        }
        System.out.println("Total MST Weight: " + mst.weight());
        System.out.println("Execution Time of Read Graph File: " + durationReadGraph + " milliseconds");
        System.out.println("Execution Time of MST: " + durationMST + " milliseconds");

        try{
            generateGraphviz(graph,"graph.png", String.valueOf(durationReadGraph));
            generateGraphvizMST(mst.mst(),"graph_lazy_mst.png", String.valueOf(durationMST));
        }catch(IOException e){
            System.err.println("Error generating graph image: " + e.getMessage());
        }
    }



}


