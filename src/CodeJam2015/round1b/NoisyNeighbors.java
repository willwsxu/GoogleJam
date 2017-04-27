/*
 * Brief description: In a R x C grid of apartments, rent out to N tenants, one per apartmrnt
 * whenever any two occupied apartments share a wall (and not just a corner), 
 * this will add one point of unhappiness to the building
 * What is the minimal unhappiness value of the building
 * Observations:
 * max N with 0 unhappiness value
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

import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room{
    int wall;
    int avail;
    Room(int w, int a) {
        wall=w; avail=a;
    }
    @Override
    public String toString()
    {
        return wall+"-"+avail;
    }
}
public class NoisyNeighbors {
    
    static int score(List<Room> rooms, int tenant)
    {
        //out.println(Arrays.toString(rooms)+tenant);
        int s=0;
        for (int i=0; i<rooms.size(); i++) {
            if (tenant>rooms.get(i).avail) {
                s += rooms.get(i).avail*rooms.get(i).wall;
                tenant -= rooms.get(i).avail;
            }
            else {
                s += tenant*rooms.get(i).wall;
                tenant=0;
                break;  // it is done (bug alert)
            }
        }
        if ( tenant>0 )
            out.println("sccore Error tenant "+tenant);
        return s;
    }
    
    // occupy  optimally to get maxN to achieve 0 unhappiness
    static int greedy(int R, int C, int t)
    {
        List<Room> rm = new ArrayList<>();
        int inners=(R-2)*(C-2)/2;
        // oddxodd
        if (R%2>0 && C%2>0) {// 2 types;
            int sideRoom = R*C/2-inners;
            rm.add (new Room(3, sideRoom));
            rm.add (new Room(4, inners));   
            return score(rm, t);
        } else { // 3 types;
            int sideRoom = R*C/2-inners-2;
            rm.add (new Room(2, 2)); // corner
            rm.add (new Room(3, sideRoom));  
            rm.add (new Room(4, inners));    
            return score(rm, t);    
        }
    }
    // in odd x odd, fill initial R*C/2 tentants, instead of R*C/2+1
    // .X.X.
    // X.X.X
    // .X.X.
    // X.X.X
    // .X.X.
    static int lessGreedy(int R, int C, int t)
    {
        List<Room> rm = new ArrayList<>();  // 3 types;
        int inners=(R-2)*(C-2)/2+1;
        int sideRoom = R*C/2+1-inners-4;
        rm.add ( new Room(2, 4) ); // corner
        rm.add ( new Room(3, sideRoom));  
        rm.add ( new Room(4, inners));    
        return score(rm, t);            
    }
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
            out.println("bad input, N> R*C");
            return 0;
        }
        int total = N-(R*C+1)/2;
        //out.println("tenants "+total);
        if (R==1) {  //X.X., X.X.X
            List<Room> rm = new ArrayList<>();
            if (C%2>0) { //X.X.X  bug alert, two different cases
                rm.add(new Room(2, C/2));
            } else {// 2 types;
                rm.add( new Room(1, 1) );
                rm.add( new Room(2, C/2-1));
            
            }
            return score(rm, total);
        }
        int s1=greedy(R,C,total);
        if (R%2>0 &&C%2>0) {
            int s2=lessGreedy(R,C,total+1);
            //out.println(s1+":"+s2);
            return min(s1, s2);
        }
        return s1;
    }
    static void test()
    {
        out.println(unhappiness(1,1,1));    // 0
        out.println(unhappiness(1,2,1));    // 0 
        out.println(unhappiness(1,2,2));    // 1 
        out.println(unhappiness(1,3,2));    // 0
        out.println(unhappiness(1,3,3));    // 2 
        out.println(unhappiness(4,1,2));    // 0
        out.println(unhappiness(4,1,3));    // 1
        out.println(unhappiness(4,1,4));    // 3
        
        out.println(unhappiness(2,2,0));    // 0
        out.println(unhappiness(2,2,1));    // 0
        out.println(unhappiness(2,2,2));    // 0
        out.println(unhappiness(2,2,3));    // 2
        out.println(unhappiness(2,2,4));    // 4
        
        out.println(unhappiness(3,3,4));    // 0
        out.println(unhappiness(3,3,5));    // 0
        out.println(unhappiness(3,3,6));    // 3
        out.println(unhappiness(3,3,7));    // 6
        out.println(unhappiness(3,3,8));    // 8
        out.println(unhappiness(3,3,9));    // 12
        
        out.println(unhappiness(2,3,3));    // 0
        out.println(unhappiness(2,3,4));    // 2
        out.println(unhappiness(2,3,5));    // 4
        out.println(unhappiness(2,3,6));    // 7  
        out.println(unhappiness(5,2,0));          
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1b\\B-large-practice.in.txt");
        
        int TC = sc.nextInt(); // 1 to 1000
        for (int i=0; i<TC; i++) {
            int R = sc.nextInt();  // 1 ≤ R*C ≤ 10000
            int C = sc.nextInt();
            int N = sc.nextInt();  // 0 ≤ N ≤ R*C
            out.print("Case #"+(i+1)+": ");
            out.println(unhappiness(R,C,N));
        }
    }
}
