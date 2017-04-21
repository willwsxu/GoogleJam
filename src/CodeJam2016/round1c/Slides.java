/*
 * Brief Description: Take slide from building 1 to B in exact M ways
 * Cycle would produce infinite ways so it is not allowed. hence no slides should come from B
 * so it is easier to presume slide direct is from i to j with i<j
 * if B is 2, only 1 way, 2^0
 * if B is 3, 2 ways, 13, 123, 2^1
 * if B is 4, 4 ways, 14, 124, 1234, 134, 2^2
 * In general, the total combination is to choose any buildings in between 1 and B
 *   max ways is 2^(B-2)
 * from i to B, there are 2^(B-1-i) ways
 * this corresponds to bit value, first bit 1 is building B-1, etc
 * e.g if B is 4, M is 3, convert M to binary as b011, 
 *  building 2 and 3 should be fully connected to 4, 24, 234, 34
 *  connect 1 to 2 or 3: 124, 1234, 134
 * if B is 4, M is 4, M is b100, connect  fully 1 to 4. 
 * if B is 4, M is 5, IMPOSSIBLE
 * output format, BxB grid, each row represent build i, each column is building J
 *  value 1 mean there is a slide from i to j
 */
package CodeJam2016.round1c;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

public class Slides {
    char grid[][];
    void connectFully(int i, int j)
    {
        if (i>=j-1)
            return;
        for (int k=i+1; k<j; k++)
            grid[i][k]='1';
        connectFully(i+1, j);
    }
    void print(int B)
    {
        for (int i=0; i<B; i++) {
            for (int j=0; j<B; j++)
                out.print(grid[i][j]);
            out.println();
        }
    }
    void combinatorics(int B, long M)
    {
        long maxWays = 1<<(B-2);
        if ( M>maxWays ) {
            out.println("IMPOSSIBLE");
            return;
        }
        grid = new char[B][B];
        for (char[] c: grid)
            Arrays.fill(c, '0');
        if (M==maxWays) {
            connectFully(0, B);
        } else {
            // find the position highest 1 bit
            int high=Long.toBinaryString(M).length();
            connectFully(B-high-1, B);
            for (int i=0; i<high; i++) {
                if ( (M&1)>0) {
                    grid[0][B-2-i]='1';
                }
                M >>= 1;
            }
        }
        out.println("POSSIBLE");
        print(B);
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("jam2016tests\\round1c\\slides-l.in.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int B = sc.nextInt();  // 2 ≤ B ≤ 50
            long M = sc.nextLong(); // 1 ≤ M ≤ 10^18
            out.print("Case #"+(i+1)+": ");
            if ( B<2 || M<1 || B>50)
                continue;
            new Slides().combinatorics(B, M);
        }
    }
}
