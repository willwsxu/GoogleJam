package codejam2018.r1a;


import java.util.Arrays;
import java.util.Collections;

/*
 * R robots shoping for total B bits, each robot can check out with one cashier, not do nothing
 * Each cashier can check out at most Mi bits, at rate Si sec per bit, plus Pi sec for handling payment
 * How to distribute bits to robots so total check out time is minimized, 
   assume The robots all start interacting with their cashiers at time 0, total C cashiers

1 ≤ Mi ≤ 10^9, for all i.
1 ≤ Si ≤ 10^9, for all i.
1 ≤ Pi ≤ 10^9, for all i.
1 ≤ R ≤ C ≤ 1000.
1 ≤ B ≤ 10^9.
 */

// ideas: assume minimum time is T, then we can how many bits each cashier can handler
//   min(Mi, (T-Pi)/Si), sum of top R values should = B
public class BitParty {
    
    long binarySearch(int R, int B, int[]M, int[]S, int[]P, long low, long hi)
    {
        if (low==hi)
            return low;
        long mid=(hi+low)/2;
        long bits[]=new long[M.length];
        for (int i=0; i<M.length; i++) {
            bits[i]=Long.min(M[i], (mid-P[i])/S[i]);
        }
        Arrays.sort(bits);
        //System.out.println("mid="+mid+" "+Arrays.toString(bits));
        int total=0;
        for (int i=bits.length-1; i>=bits.length-R; i--) {  // reverse order
            total += bits[i];
            if (total>=B) {
                //System.out.println(total+">="+B+" at "+i);
                return binarySearch(R, B, M, S, P, low, mid);
            }
        }
        //System.out.println(total+"<"+B);
        return binarySearch(R, B, M, S, P, mid+1, hi);
    }
    long solve(int R, int B, int C, int[]M, int[]S, int[]P)
    {      
        int maxS = Arrays.stream(S).max().getAsInt();
        int maxP = Arrays.stream(P).max().getAsInt();
        long MaxTime=(long)B*maxS+maxP;  // upper bound
        //System.out.println(MaxTime);
        return binarySearch(R, B, M, S, P, 1, MaxTime);
    }
    
    public static void main(String[] args)  
    {
        long ans=new BitParty().solve(2, 2, 2, new int[]{1,1}, new int[]{2,1}, new int[]{3,2});
        System.out.println(ans==5);
        ans=new BitParty().solve(2, 2, 2, new int[]{1,2}, new int[]{2,1}, new int[]{3,2});
        System.out.println(ans==4);
        ans=new BitParty().solve(3, 4, 5, new int[]{2,2,2,2,2}, new int[]{3,1,4,2,5}, new int[]{3,5,2,4,1});
        System.out.println(ans==7);
    }
}
