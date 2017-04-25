/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015.round1a;

import googlejam.GCD;
import static java.lang.System.out;
import java.util.Scanner;

public class Haircut {
    long M[];
    int B;
    long N;
    long countAt(long m) {  // count barbers available at a particular minute
        if (m<=0)
            return 0;
        long barb=0;
        for (int i=0; i<B; i++)
            barb += m/M[i];
        return barb;
    }
    
    // binary search to find the minute with this many available barbers
    long bst(long lo, long hi, long count) {
        if (lo>=hi)
            return lo;
        long mid = (lo+hi)/2;
        if (countAt(mid)<count)
            return bst(mid+1, hi, count);
        else
            return bst(lo, mid, count);
    }
    Haircut(long M[], long N)
    {
        this.M=M;
        this.B=M.length;
        this.N=N;
    }
    long solve()
    {
        if ( N<=B )
            return N;
        if (B==1)
            return B;
        long lcm = GCD.lcm(M, M[0], 1); // repeating cycle
        long barb = countAt(lcm);
        long rem = (N-B)%barb;  
        if (rem==0)
            return B;  // the dead last in a cycle
        long tim = bst(1, lcm, rem);
        // count how many before this minute
        long before=countAt(tim-1);  // barbs available in previous min
        if ( before == rem )
            out.println("error at "+tim);
        rem -= before; // barb # at this minute
        int avail=0;
        for (int i=0; i<B; i++) {
            if (tim%M[i]==0) {
                if (++avail==rem)
                    return i+1;
            }
        }
        out.println("error ");
        return 0;
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("jam2015tests\\round1a\\A-large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int B = sc.nextInt();  // 1 ≤ B ≤ 1000
            long N = sc.nextLong(); // 1 ≤ N ≤ 10^9
            long M[] = new long[B]; //1 ≤ Mk ≤ 100000
            for (int k=0; k<B; k++)
                M[k]=sc.nextInt();
            out.print("Case #"+(i+1)+": ");
            out.println(new Haircut(M, B).solve());
        }
    }
}
