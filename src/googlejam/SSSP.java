/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlejam;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


class Edge
{
    private final int v; // one vertex
    private final int w; // the other vertex
    private final long weight; // edge weight
    public Edge(int v, int w, long weight)
    {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    public long weight()
    { return weight; }
    public int either()
    { return v; }
    public int other(int vertex)
    {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge");
    }
    // direct edge
    public int from()
    { return v; }
    public int to()
    { return w; }
}

class WeightedGraph {
    private final int   V; // number of vertices
    private int         E; // number of edges
    List<Edge>[]        adj;// adjacency lists

    WeightedGraph(int V)
    {
        this.V = V;
        E=0;
        adj = new List[V];  // -Xlint:unchecked
        for (int v = 0; v < V; v++) // Initialize all lists
            adj[v] = new ArrayList<>(10);
    }
    public int V() { return V; }
    public int E() { return E; }
    public List<Edge> adj(int v) {
        return adj[v];
    }
    public void addEdge(Edge e) // undirected
    {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }
    public void addDirectEdge(int v, int w, long wt)
    {
        adj[v].add(new Edge(v, w, wt));
        E++;        
    }
    public void addDirectEdge(Edge e)
    {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        E++;
        //out.println(" edge from "+(v+1)+" to "+(w+1)+" w="+e.weight());
    }
}

// shortest path from s
// vertex is 0 based
// modified from Dijkstra algorithm
public class SSSP
{
    class PQItem{
        int v;
        long weight;
        PQItem(int v, long w)
        {
            this.v = v;
            weight=w;
        }
        public long getWeight() {
            return weight;
        }
        @Override
        public boolean equals(Object o)
        {
            if ( !(o instanceof Integer))
                return false;
            Integer w = (Integer)o;
            return v==w;
        }
    }
    private int   s; // source node
    private final WeightedGraph g;
    private Edge[] edgeTo;
    private long[] distTo;
    private PriorityQueue<PQItem> pq;
    
    // s, v, w index from 0
    public SSSP(int N)
    {
        g = new WeightedGraph(N);
        edgeTo=null;
        distTo=null;
        pq=null;
        s=0;
    }
    
    public void solve(int s)
    {
        this.s=s;
        edgeTo = new Edge[g.V()];
        distTo = new long[g.V()];
        Comparator<PQItem> cmp = Comparator.comparingLong(a->a.weight);
        pq = new PriorityQueue<>(g.V()*2, cmp);
        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Long.MAX_VALUE;
        }
        distTo[s] = 0;
        
        pq.add(new PQItem(s, 0));     
        while (!pq.isEmpty()) {
            relax(pq.poll());
        }   
    }
    public void addDirectEdge(int v, int w, long wt)
    {
        g.addDirectEdge(v, w, wt);
    }
    
    private void relax(PQItem pqi)
    {
        int v = pqi.v;
        if (distTo[v] != pqi.getWeight())  // ignore dup node
            return;
        for(Edge e : g.adj(v))
        {
            int w = e.to();
            //out.println(" dist to v "+(v+1)+" is "+distTo[v]+" w="+w);
            if (distTo[w] > distTo[v] + e.weight())
            {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                //if (pq.contains(w)) {
                //    pq.remove(new Integer(w));
                //}
                // don't bother to delete old one, 10% better performance
                pq.add(new PQItem(w, distTo[w]));
                //out.println("update dist to w "+(w+1)+" is "+distTo[w]);
            }
        }
    }
    public long distTo(int v) // standard client query methods
    {
        return distTo[v];
    }
}
