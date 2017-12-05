/*
 * An N-omino is a 2D shape formed by joining N unit cells fully along their edges in some way
 * we consider two N-ominoes to be the same if one can be transformed into the other via reflection and/or rotation
 * Problem descrption
 * Richard will choose any one of the possible X-ominoes
 * Gabriel must use at least one copy of that X-omino, plus any other X-ominos to fill RxC grid
 *  with no overlaps and no spillover
 * If Gabriel can completely fill in the grid, he wins; otherwise, Richard wins.
 */
package CodeJam2015.qualification;

import static java.lang.System.out;
import java.util.Scanner;

public class Omino {
    
    boolean caseStudy(int X, int R, int C) // return if Gabriel wins
    {
        if (R*C%X>0)   // cells in grid must be multiples of X
            return false;
        // ###
        // #.#
        // ##.
        if (X>=7)       // can choose one form to enclose an empty center
            return false;
        if (R>C) {
            R = R^C;  // consider each bit on R and C, 0 if same
            C = R^C;  // C is now original R
            R = R^C;  // R is now original C
        }
        switch (X) {
            case 3:     return R>1;
            // ###
            // .#.
            case 4:     return R>2;
            // #..
            // ##.
            // .##
            case 5:
                if (R==3 && C==5)
                    return false;
                return R>2;
            // .#..
            // ####
            // .#..
            case 6:     return R>3;
        }
        return true;
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\qualification\\D-large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int X = sc.nextInt();  // 1 ≤ X, R, C ≤ 20
            int R = sc.nextInt();
            String ans=new Omino().caseStudy(X, R, sc.nextInt())?"GABRIEL":"RICHARD";
            out.println("Case #"+(i+1)+": "+ans);
        }
    }
}
