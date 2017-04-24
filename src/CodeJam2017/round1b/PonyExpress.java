/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2017.round1b;

import static java.lang.Double.min;
import java.util.Scanner;


public class PonyExpress {
    
    int N;
    long D[][];
    int S[];
    double f[];
    double dp(int i) {
        if (i==N)
            return 0;
        if ( f[i] !=0 )
            return f[i];
        double minT = Double.MAX_VALUE;
        for (int j=i+1; i<N; j++) {
            minT=min(minT, D[i][j]/S[i]+dp(j));
        }
        f[i]=minT;
        return minT;
    }
    void solveSmall()
    {
        
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1b\\B-large-practice.in.txt");
        //sc = googlejam.ContestHelper.getFileScanner("unicorns-t.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) { // 2 <= N≤ 100
            int N = sc.nextInt();  // cities and horses
            int Q = sc.nextInt();  // queries, 1 to 100
            long E[] = new long[N]; // distance horses travel 1 ≤ Ei ≤ 10^9
            int  S[] = new int[N];  // max horse speed 1 ≤ Si ≤ 1000
            for (int j=0; i<N; j++) {
                E[j] = sc.nextLong();
                S[j] = sc.nextInt();
            }
            long D[][] = new long[N][N]; // -1 ≤ Dij ≤ 10^9, Dii=0, Dij!=0
            for (int j=0; i<N; j++) {    // -1  means no route from i to j
                for (int k=0; k<N; k++)
                    D[j][k] = sc.nextLong();
            }
            int u = sc.nextInt();
            int v = sc.nextInt();
        }
    }
}
