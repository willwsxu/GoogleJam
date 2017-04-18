
package googlejam.Round1A16;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// IDEA: all the numbers has to apprear twice, in row list and column list
public class RankFile {
    
    int[] lists;
    int N;
    RankFile(int[] lists, int N)
    {
        this.lists=lists;
        this.N=N;
    }
    void solve()
    {
        Arrays.sort(lists);
        int last=lists[0];
        int count=1;
        List<Integer> missing = new ArrayList<>();
        for(int i=1; i<lists.length; i++) {
            if (lists[i] != last) {
                if (count%2==1) { // odd occurrence
                    missing.add(last);
                }
                last = lists[i];
                count=1;
            } else
                count++;
        }
        if (count%2==1)
            missing.add(last);
        if (missing.size() !=N)
            out.println("expect #"+N+" got "+missing.size());
        for (int i : missing)
            out.print(i+" ");
        out.println();
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        scan = googlejam.ContestHelper.getFileScanner("rankfile-l.in.txt");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt(); // 2 to 50
            int lists[]=new int[2*N*N-N];
            int m=0;
            for ( int j=0; j<2*N-1; j++) 
            {
                for (int k=0; k<N; k++) {
                    lists[m++]=scan.nextInt();
                }
            }
            out.print("Case #"+(i+1)+": ");
            new RankFile(lists, N).solve();
        }
    }
}
