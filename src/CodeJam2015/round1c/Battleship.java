/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015.round1c;

import static java.lang.System.out;
import java.util.Scanner;

public class Battleship {
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1c\\A-large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int R = sc.nextInt();  // 1 ≤ R ≤ 20
            int C = sc.nextInt();  // 1 ≤ C ≤ 20
            int W = sc.nextInt();  // 1 ≤ W ≤ C
            out.print("Case #"+(i+1)+": ");
            if (W==1)
                out.println(R*C);
            else {
                int m = C/W;
                int total=0;
                if (R>1) // test other rows every W
                {
                    total = m*(R-1);
                } 
                if (C%W==0)  // odd column need one extra test
                    m--;    
                total += (m+W);
                out.println(total);
            }
        }
    }
}
