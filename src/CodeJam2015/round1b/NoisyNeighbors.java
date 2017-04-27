/*
 * Brief description: In a R x C grid of apartments, rent out to N tenants, one per apartmrnt
 * whenever any two occupied apartments share a wall (and not just a corner), 
 * this will add one point of unhappiness to the building
 * What is the minimal unhappiness value of the building
 * Observations:
 * max N with unhappiness value
 *  if both R and C odd, maxN=(R*C+1)/2, e.g. X has tentant, . is empty
 *  X.X   X.X.X  X.X.X
 *  .X.   .X.X.
 *  X.X   X.X.X
 * if R or C is even, maxN=R*N/2
 *  X.X.  X.X.X
 *  .X.X  .X.X.
 *  X.X.  X.X.X
 *  .X.X  .X.X.
 * Rooms sharing 4 walls, inner rooms, (R-2)*(C-2)
 *  4 corner rooms sharing 2 walls, rest sharing 3 walls, R*C-4-(R-2)*(C-2)
 */
package CodeJam2015.round1b;

import static java.lang.System.out;
import java.util.Scanner;

public class NoisyNeighbors {
    
    static int unhappiness(int R, int C, int N)
    {
        if (N<=(R*C+1)/2)
            return 0;
        if (R>C) {  // swap
            int t=R;
            R=C;
            C=t;
        }
        if (N>R*C) {
            out.println("bad input R C");
            return 0;
        }
        int total = N-(R*C+1)/2;
        out.println("tenants "+total);
        if (R==1) {  //X.X., X.X.X
            int unhap=1;
            if (C%2>0) // odd
                unhap++;               
            return unhap+(total-1)*2;
        }
        int corners=2;
        if (R%2>0 &&C%2>0)
            corners=0;
        if (total<=corners) 
            return total*2;
        int unhap=2*corners;  // choose corners first
        total -= corners;
        int inners=(R-2)*(C-2)/2;
        int sideRoom = R*C/2-inners;
        if ( total<=sideRoom ) {
            return unhap+total*3;
        }
        unhap += sideRoom*3;
        total -= sideRoom;
        return unhap+total*4;
    }
    static void test()
    {
        out.println(unhappiness(2,3,6));      
        out.println(unhappiness(4,1,2));        
        out.println(unhappiness(3,3,8));        
        out.println(unhappiness(5,2,0));          
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        test();
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1c\\B-large-practice.in.txt");
        /*
        int TC = sc.nextInt(); // 1 to 1000
        for (int i=0; i<TC; i++) {
            int R = sc.nextInt();  // 1 ≤ R*C ≤ 10000
            int C = sc.nextInt();
            int N = sc.nextInt();  // 0 ≤ N ≤ R*C
            out.print("Case #"+(i+1)+": ");
            out.println(unhappiness(R,C,N));
        }*/
    }
}
