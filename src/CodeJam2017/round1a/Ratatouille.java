/*
 * quick description
 * Recipe kit contains N ingredients, each has a recommended weight for one serving
 * There are P packages for each ingredient, each package has a weight
 * package can be included in a kit if its weight is within [90% to 110%] of recommended weight, 
 *   could be for more than one servings
 * output: maximum kits can be produced
 * strategy: calculate ranges of servings of each packet. discard packets not useable
 * solve simple cases first, no valid package in one of the ingrediant, one ingredient only
 * Sort packets by low range first, then hi range. high range is more useful and should be picked late
 * Loop on packages on first ingredient, end when any ingredient run out of packages
 * discard any package whose hi is lower than low
 */
// Techniques: List of List, stable sort twice by two factors
package CodeJam2017.round1a;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
        //print();
    }
    void greedy()
    {
        multiples = new ArrayList<>(N);
        for (int i=0; i<N; i++) {
            multiples.add(new ArrayList<>());
            for (int j=0; j<P; j++) {
                int lodenom=11*R[i]; // multiple value by 10 so no need to use double
                int hidenom=9*R[i];  // hi=Q/ (R*9/10)=10*Q/(9*R)
                int q = Q[i][j]*10;  // lo=Q/ (R*11/10)=10*Q/(11*R)
                int iLow = q/lodenom;
                if (q%lodenom>0)    // ceiling
                    iLow++;
                int iHi = q/hidenom; // floor
                if (iLow<=iHi) {
                    multiples.get(i).add(new Multiple(iLow, iHi));
                }
                else {
                    //out.println("discard invalid package "+(j+1)+" wt="+Q[i][j]+" need "+R[i]);
                }
            }
        }
        for (int k=0; k<N; k++) {
            if (0==multiples.get(k).size()) {
                out.println(0);
                return;       
            }
        }
        if (N==1) {
            out.println(multiples.get(0).size());
            return;
        }
        Comparator<Multiple> cmp1 = (m1, m2)->m1.lo-m2.lo;  // sort lo range
        Comparator<Multiple> cmp2 = (m1, m2)->m1.hi-m2.hi;  // sort hi range
        for (int i=0; i<N; i++) {
            Collections.sort(multiples.get(i), cmp1);
            Collections.sort(multiples.get(i), cmp2); // sort is stable
        }   
        int index[]=new int[N]; // for package 1 to P
        Arrays.fill(index, 0);
        int kits=0;
        outterfor:
        for (int j=0; j<multiples.get(0).size(); j++) { // packages of first ingredient
            Multiple first = multiples.get(0).get(j); // compare it rest
            for (int k=1; k<N; k++) {
                if (first.hi<multiples.get(k).get(index[k]).lo)
                {  // discard package from first ingredient
                    continue outterfor;
                }
                while (multiples.get(k).get(index[k]).hi<first.lo) {
                    if (++index[k]==multiples.get(k).size()) // discard package from ingredient 1 to N-1
                        break outterfor;  // no more left
                }
            }
            kits++;  
            // move to next packaage
            for (int k=1; k<N; k++) {
                if (++index[k]==multiples.get(k).size())
                    break outterfor;                
            }
        }
        out.println(kits);
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
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("round1a17\\ratatouille-s.txt");
        
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
            out.print("Case #"+(i+1)+": ");
            new Ratatouille(R, Q);
        }
    }
}
