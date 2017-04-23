package CodeJam2017.round1b;


import static java.lang.System.out;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class CruiseControl {
    
    long K[]; // start pos
    long S[]; // speed
    int N; //hores
    long D;
    int limit()
    {
        double maxtime=0;
        int which=0;
        for (int i=0; i<N; i++) {
            double t=(double)(D-K[i])/S[i];
            if (t>maxtime) {
                maxtime=t;
                which = i;
            }
        }
        return which;
    }
    CruiseControl(long D, long K[], long S[])
    {
        this.D = D; this.K=K;
        this.S = S; N = S.length;
    }
    void solve()
    {
        int slow = limit();
        double s = (double)D*S[slow]/(D-K[slow]);
        out.println(String.format("%.6f", s));
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("A-large.in");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) { //0 < Ki < D ≤ 10^9
            long D = sc.nextLong();
            int N = sc.nextInt(); //1 ≤ N ≤ 1000
            long K[]=new long[N];
            long S[]=new long[N]; //1 ≤ Si ≤ 10000
            for (int j=0; j<N; j++) {
                K[j]=sc.nextLong();
                S[j]=sc.nextInt();
            }
            out.print("Case #"+(i+1)+": ");
            new CruiseControl(D, K,S).solve();
        }
    }
}
