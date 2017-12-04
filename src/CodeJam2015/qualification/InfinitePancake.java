/*
 * D diners with none empty plates, ith diner  has Pi pancakes
 * infinite more diners with empty plates
 * Normally each diner can eat one pancake per min
 * Head server can pick special minute, pick a diner and distribute some of his pancakes to one other
 * no eating during special minute
 * output: smallest # of minutes to complete breakfast
 * strategy:
 * move can improve outcome, always can do moves first before eating
 * need to minimize total moves + max pancakes after all moves
 * a good strategy is to move x number pancakes to empty plate until all plates has at most x
 * try all x possible and find the minimum
 */
package CodeJam2015.qualification;

import static java.lang.System.out;
import java.util.Scanner;


public class InfinitePancake {
    
    int plates[];
    int MAX_PANCAKES=1000; // max in a plate
    InfinitePancake(int []plates)
    {
        this.plates=plates;
    }
    
    int ceiling(int num, int den)
    {
        return (num+den-1)/den;
    }
    
    int solve()
    {
        int ans=MAX_PANCAKES;
        // x is the max number of pancakes on each plate after move
        for (int x=1; x<MAX_PANCAKES; x++) {
            if (x>ans)
                break;
            // move x pancakes to empty plate until each plate has at most x
            int total=0;
            for (int d=0; d<plates.length; d++) {
                // each place moves=ceiling of p[i]/x-1
                total += (ceiling(plates[d], x)-1);
                if (total+x>ans)  // stop, worse answer
                    break;
            }
            // total moves + max pancakes is the minimum minutes need to finish eating
            if (total+x<ans) {
                ans=total+x;
                //out.println("max pancakes "+x);
            }
        }
        return ans;
    }
    public static void test()
    {
        out.println(new InfinitePancake(new int[]{30}).solve()==10);
        out.println(new InfinitePancake(new int[]{15, 17}).solve()==10);
        out.println(new InfinitePancake(new int[]{1}).solve()==1);
        out.println(new InfinitePancake(new int[]{4}).solve()==3);
        out.println(new InfinitePancake(new int[]{9}).solve()==5);
        out.println(new InfinitePancake(new int[]{1000}).solve());
    }
    static Scanner sc = new Scanner(System.in);  
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    public static void judge()
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\qualification\\B-large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int D = sc.nextInt();  // 1 ≤ D ≤ 1000, # of plates
            int ans=new InfinitePancake(ria(D)).solve();
            out.println("Case #"+(i+1)+": "+ans);
        }        
    }
    public static void main(String[] args)  
    {
        judge();
    }
}
