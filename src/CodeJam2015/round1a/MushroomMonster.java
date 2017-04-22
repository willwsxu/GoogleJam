/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.Scanner;

public class MushroomMonster {
    
    // eat only when number is down
    long greedy1(int[] plates)
    {
        long total=0;
        for (int i=1; i<plates.length; i++) {
            if (plates[i]<plates[i-1])
                total += plates[i-1]-plates[i];
        }
        return total;
    }
    // eat at constant rate
    // find the most decrease, that determine the rate
    long greedy2(int[] plates)
    {
        int maxrate = 0;  // rate per 10 sec, not 1 sec
        for (int i=1; i<plates.length; i++) 
            maxrate = max(maxrate, plates[i-1]-plates[i]);
        long total=0;      
        for (int i=0; i<plates.length-1; i++) 
            total += min(maxrate, plates[i]);
        return total;
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("jam2015tests\\round1a\\A-large-practice.in.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt();  // 2 to 1000
            int[]plates = new int[N];  // 0 ≤ mi ≤ 10000
            for (int j=0; j<N; j++) {
                plates[j] = sc.nextInt();
            }
            MushroomMonster m = new MushroomMonster();
            out.print("Case #"+(i+1)+": ");
            out.println(m.greedy1(plates)+" "+m.greedy2(plates));
        }
    }
}
