/*
 * quick description
 * Recipe kit contains N ingredients, each has a recommended weight for one serving
 * There are P packages for each ingredient, each package has a weight
 * package can be included in a kit if its weight is within [90% to 110%] of recommended weight, 
 *   could be for more than one servings
 * output: maximum kits can be produced
 */
package googlejam.Round1A17;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ratatouille {
    int R[];   // required ingredient weight, per serving
    int Q[][]; // ingredient weight
    int N, P;
    List<List<Multiple>> multiples;
    class Multiple
    {
        int lo;
        int hi;
        Multiple(int l, int h)
        {
            lo=l;
            hi=h;
        }
    }
    Ratatouille(int r[], int[][]q)
    {
        R=r;
        Q=q;
        N = R.length;
        P = Q[0].length;
        
        greedy();
        print();
    }
    void greedy()
    {
        multiples = new ArrayList<>(N);
        for (int i=0; i<N; i++) {
            multiples.add(new ArrayList<>());
            for (int j=0; j<P; j++) {
                int lodenom=11*R[i]; // multiple value by 10 so no need to use double
                int hidenom=9*R[i]; 
                int q = Q[i][j]*10;
                //double hi = (double)Q[i][j]/(.9*R[i]);
                //double lo = (double)Q[i][j]/(1.1*R[i]);
                int iLow = q/lodenom;
                if (q%lodenom>0)
                    iLow++;
                int iHi = q/hidenom;
                if (iLow<=iHi) {
                    multiples.get(i).add(new Multiple(iLow, iHi));
                }
                else
                    out.println("discard invalid package "+(j+1)+" wt="+Q[i][j]+" need "+R[i]);
            }
        }
    }
    
    void print()
    {
        for (int i=0; i<N; i++) {
            for (int j=0; j<multiples.get(i).size(); j++) {
                Multiple m =multiples.get(i).get(j);
                out.print(m.lo+"-"+m.hi+" ");
            }
            out.println();
        }
        
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("ratatouille-t.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt(); // 1 ≤ N ≤ 50
            int P = sc.nextInt(); // 1 ≤ P ≤ 50, N × P ≤ 1000
            int R[]=new int[N];     // 1 ≤ Ri ≤ 10^6
            for (int j=0; j<N; j++)
                R[j] = sc.nextInt();
            int Q[][]=new int[N][P]; //1 ≤ Qij ≤ 10^6
            for (int j=0; j<N; j++) {
                for (int k=0; k<P; k++) {
                    Q[j][k]=sc.nextInt();
                }
            }
            out.println("Case #"+(i+1)+": ");
            new Ratatouille(R, Q);
        }
    }
}
