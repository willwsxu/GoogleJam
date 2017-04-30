package CodeJam2017.round1b;


import static java.lang.System.out;
import java.util.Scanner;

/*
 * Brief Description: On a road of D Km, there are N horses at pos Ki with spped Si
 * When a horse catch up, cannot pass and must to the speed to match the slow horse
 * What is the maximum constant speed Annie can choose she does not pass other horses
 */

public class CruiseControl {
    
    long K[]; // start pos
    long S[]; // speed
    int N; //horses
    long D;
    int limit()  // find the slowest horse with longest time to reach the finishline
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
        int slow = limit(); // s=d/t
        double s = (double)D*S[slow]/(D-K[slow]);
        out.println(String.format("%.6f", s));
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1b\\A-large.in");
        
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
