/*
 * Magic trick, 4x4 cards from 1 to 16
 * volunteer pick a card, say which row
 * cards mixed, volunter say which row his card is in 
 * simulate the magic, find the card or bad mafic or bad volunteer
 */
// simple strategy: search every item in one from another row
package CodeJam2014.qualification;

import static java.lang.System.out;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Trick {
    
    String match(int c1[], int c2[]) {
        Set<Integer> c1Set = new HashSet<>();
        for (int c: c1)
            c1Set.add(c);
        int magicNo=0;
        int matchCount=0;
        for (int c: c2) {
            if (c1Set.contains(c)) {
                matchCount++;
                magicNo=c;
            }            
        }
        if (matchCount==0)
            return "Volunteer cheated!";
        else if (matchCount>1)
            return "Bad magician!";
        else
            return ""+magicNo;
    }
    String solve(int r1, int[][]cards1, int r2, int[][]cards2)
    {
        return match(cards1[r1-1], cards2[r2-1]);
    }
    public static void test()
    {
        out.println(new Trick().solve(2, new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}, 
                          3, new int[][]{{1,2,5,4},{3,11,6,15},{9,10,7,12},{13,14,8,16}}));
        out.println(new Trick().solve(2, new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}, 
                          2, new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}));
        out.println(new Trick().solve(2, new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}, 
                          3, new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}));
    }
    static Scanner sc = new Scanner(System.in);  
    public static int[][] fillMatrix(int n, int m)
    {
        int a[][]=new int[n][m];
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                a[i][j]=sc.nextInt();
            }
        return a;
    }
    private static void judge()
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2014\\qualification\\A-small-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int r1 = sc.nextInt();
            int cards1[][]=fillMatrix(4,4);
            int r2 = sc.nextInt();
            int cards2[][]=fillMatrix(4,4);
            String ans=new Trick().solve(r1, cards1, r2, cards2);
            out.println("Case #"+(i+1)+": "+ans);
        }
    }
    public static void main(String[] args)  
    {
        judge();
    }
}
