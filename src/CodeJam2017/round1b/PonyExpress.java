/*
 * Brief Description: There are N cities, each has a horse with speed Si and max travel distance Ei
 * Rider rides a horse from one city to another, at either city he can switch horses,
 * or continue with his current as long as its endurance allows
 * Answer queries: minimal time to deliver mail from citi ui to vi
 * Algorithm:
Apply Floyd-Warshall to input G, getting shortest distances between all pairs of nodes.
Create G' by adding all edges (i, j) such that the distance between i and j in G is less than 
  or equal to the horse starting at city i's endurance, and set their weights to 
  that same distance divided by that horse's speed.
Apply Floyd-Warshall to G' to get minimum times between all pairs of nodes.
Read queries and answer immediately from the output of the last step.
 */
// Technique: Dynamic programming, All Pairs Shortest Path
package CodeJam2017.round1b;

import googlejam.FloydWarshall;
import static java.lang.Double.min;
import static java.lang.System.out;
import java.util.Scanner;


public class PonyExpress {
    
    int N;      // N cities, N horses
    long D[][]; // Distance matrix between cities
    int S[];    // horse speed
    long E[];   // horse endurance
    PonyExpress(long e[], int[] s, long[][]d)
    {
        E=e;  S=s; D = d;
        N = E.length;
    }
    
    double f[]; // dynamic programming state, min time from city 1 to N
    double dp(int i) {
        if (i==N-1)
            return 0;
        if ( f[i] !=0 )
            return f[i];
        double minT = Double.MAX_VALUE;
        long dist=0;
        for (int j=i+1; j<N; j++) {
            if ( D[j-1][j]<=0)
                continue;
            dist += D[j-1][j];
            if (dist<=E[i]) // compare time between using same horse, or switch
                minT=min(minT, (double)dist/S[i]+dp(j));
            //out.println("i="+i+",j="+",min="+minT);
        }
        if (minT==Double.MAX_VALUE)
            out.println("bad min at "+i);
        f[i]=minT;
        return minT;
    }
    void solveSmall()
    {  // special case: city connect from one to other in single line
        f=new double[N];
        int u = sc.nextInt();
        int v = sc.nextInt();
        if ( u!=1 || v !=N)
            out.println("Error small test wrong u and v");
        print(dp(0));
        //out.println(Arrays.toString(f));
    }
    void apsp(int Q) // all pairs shortest path
    {
        FloydWarshallTime fw = new FloydWarshallTime(D, N);
        fw.transform(E, S);
        
        for (int j=0; j<Q; j++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            print(fw.sp(u-1, v-1));
            if (j<Q-1)
                out.print(" ");
        }
    }
    
    static void print(double d)
    {
        out.print(String.format("%.09f", d));        
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1b\\C-large-practice.in.txt");
        //sc = googlejam.ContestHelper.getFileScanner("ponyexpress-t.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) { // 2 <= N≤ 100
            int N = sc.nextInt();  // cities and horses
            int Q = sc.nextInt();  // queries, 1 to 100
            long E[] = new long[N]; // distance horses travel 1 ≤ Ei ≤ 10^9
            int  S[] = new int[N];  // max horse speed 1 ≤ Si ≤ 1000
            readES(E, S, N);
            long D[][] = new long[N][N]; // -1 ≤ Dij ≤ 10^9, Dii=0, Dij!=0
            for (int j=0; j<N; j++) {    // -1  means no route from i to j
                for (int k=0; k<N; k++)
                    D[j][k] = sc.nextLong();
            }
            out.print("Case #"+(i+1)+": ");
            //new PonyExpress(E,S, D).solveSmall();
            new PonyExpress(E,S, D).apsp(Q);
            out.println();
        }
    }
    static void readES(long E[], int S[], int N)
    {
        for (int j=0; j<N; j++) {
            E[j] = sc.nextLong();
            S[j] = sc.nextInt();
        }
    }
}

class FloydWarshallTime extends FloydWarshall
{
    FloydWarshallTime(long d[][], int N)
    {
        super(d, N);
    }
    public void transform(long E[], int S[])
    {
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (i!=j) {
                    if (adjMat[i][j]<=E[i])
                        adjMat[i][j] = (double)adjMat[i][j]/S[i];
                    else 
                        adjMat[i][j] = INF;
                }
            }
        }
        //print(false);
        dp();
        //print(false);        
    }
}