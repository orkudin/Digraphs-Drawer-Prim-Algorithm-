package EagerPrimMST;

// EagerPrimMST.IndexMinPQ class (based on the one from Sedgewick and Wayne book)
public class IndexMinPQ {
    private int[] pq;
    private int[] qp;
    private double[] keys;
    private int n;
    private int maxN;
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException("maxN is a negative value.");
        this.maxN = maxN;
        n = 0;
        keys = new double[maxN + 1];
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];
        for (int i=0; i<=maxN; i++){
            qp[i] = -1;
        }
    }
    public boolean isEmpty() {
        return n==0;
    }
    public boolean contains(int i) {
        return qp[i] != -1;
    }
    public void insert(int i, double key){
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }
    public void change(int i, double key){
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }
    public int delMin()
    {
        int min = pq[1];
        exch(1, n--);
        sink(1);
        qp[min] = -1;
        pq[n+1] = -1;
        return min;
    }
    private void swim(int k){
        while (k > 1 && greater(k/2, k)){
            exch(k/2, k);
            k= k/2;
        }
    }
    private void sink(int k){
        while (2*k <= n){
            int j = 2*k;
            if (j<n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    private boolean greater(int i, int j) {
        return keys[pq[i]] > keys[pq[j]];
    }
    private void exch(int i, int j) {
        int t = pq[i]; pq[i] = pq[j]; pq[j] = t;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
}
