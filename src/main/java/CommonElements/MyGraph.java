package CommonElements;

// Graph class implementation (with CommonElements.Edge and CommonElements.Bag)
public class MyGraph {
    private final int V; // number of vertices
    private int E;       // number of edges
    private Bag<Edge>[] adj; //adjacency lists

    public MyGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w, double weight) {
        Edge e = new Edge(v, w, weight);
        addEdge(e);
    }

//    public void addEdge(Edge e) {
//        int v = e.either();
//        int w = e.other(v);
//        adj[v].add(e);
//        adj[w].add(e);
//        E++;
//    }

    public void addEdge(Edge e) {
        int v = e.either();
        adj[v].add(e); // Add the directed edge only in one direction
        E++;
    }


    public Iterable<Edge> adj(int v) {
        return adj[v];
    }


}
