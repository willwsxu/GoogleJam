/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2017.round1b;

import googlejam.FloydWarshall;
import static java.lang.Double.min;
import static java.lang.Long.min;
import static java.lang.System.out;
import java.util.Scanner;


public class PonyExpress {
    
    int N;
    long D[][];
    int S[];    // speed
    long E[];   // horse endurance
    double f[]; // dynamic programming state, min time from city 1 to N
    PonyExpress(long e[], int[] s, long[][]d)
    {
        E=e;  S=s; D = d;
        N = E.length;
        f=new double[N];
    }
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
            if (dist<=E[i])
                minT=min(minT, (double)dist/S[i]+dp(j));
            //out.println("i="+i+",j="+",min="+minT);
        }
        if (minT==Double.MAX_VALUE)
            out.println("bad min at "+i);
        f[i]=minT;
        return minT;
    }
    void solveSmall()
    {
        out.println(String.format("%.9f", dp(0)));
        //out.println(Arrays.toString(f));
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1b\\C-small-practice.in.txt");
        sc = googlejam.ContestHelper.getFileScanner("ponyexpress-t.txt");
        
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
            int u[]=new int[Q];
            int v[]=new int[Q];
            for (int j=0; j<Q; j++) {
                u[j] = sc.nextInt();
                v[j] = sc.nextInt();
            }
            FloydWarshallDouble fw = new FloydWarshallDouble(D, N);
            fw.transform(E, S);
            out.print("Case #"+(i+1)+": ");
            //new PonyExpress(E,S, D).solveSmall();
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

class FloydWarshallDouble extends FloydWarshall
{
    FloydWarshallDouble(long d[][], int N)
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
        print(false);
    }
}