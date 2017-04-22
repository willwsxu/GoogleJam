/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015;

import static java.lang.System.out;
import java.util.Scanner;

public class MushroomMonster {
    
    // eat only when number is down
    void greedy1(int[] plates)
    {
        long total=0;
        int last=plates[0];
        for (int i=1; i<plates.length; i++) {
            if (plates[i]<last)
                total += last-plates[i];
            last = plates[i];
        }
        out.print(total);
    }
    // eat at constant rate
    // find the most decrease, that determine the rate
    void greedy2(int[] plates)
    {
    
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("jam2016tests\\round1c\\senateevac-l.in.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt();  // 2 to 1000
            int[]plates = new int[N];  // 0 ≤ mi ≤ 10000
            for (int j=0; j<N; j++) {
                plates[j] = sc.nextInt();
            }
            MushroomMonster m = new MushroomMonster();
            out.print("Case #"+(i+1)+": ");
            m.greedy1(plates);
            out.println();
        }
    }
}
