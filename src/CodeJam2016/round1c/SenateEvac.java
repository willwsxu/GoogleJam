/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2016.round1c;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SenateEvac {
    
    class Party
    {
        char a;
        int  n;
        public Party(char a, int n)
        {
            this.a=a;
            this.n=n;
        }
        void decreemnt()
        {
            n--;
        }
    }
    char getAlpha(int i)
    {
        if (i>=0 && i<26)
            return (char)('A'+i);
        return 'Z';
    }
    public void greedy(Integer P[])
    {
        int total=0;
        int N=P.length;
        List<String> plan = new ArrayList<>();
        List<Party> senate = new ArrayList<>();
        for (int i=0; i<N; i++) {
            total+=P[i];
            senate.add(new Party(getAlpha(i), P[i]));
        }
        
        Comparator<Party> cmp = (p1,p2)->p2.n-p1.n;
        while (total>0) {
            Collections.sort(senate, cmp);
            // take one from the top
            StringBuilder sb = new StringBuilder();
            sb.append(senate.get(0).a);
            senate.get(0).decreemnt();
            if ( --total>0 ) {
                Collections.sort(senate, cmp);
                sb.append(senate.get(0).a);
                senate.get(0).decreemnt();
                total--;
            }      
            plan.add(sb.toString());
        }
        for (int i=0; i<plan.size(); i++)
            out.print(plan.get(i)+" ");
        out.println();
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("jam2016tests\\round1b\\closematch-l.in.txt");
        //sc = googlejam.ContestHelper.getFileScanner("jam2016tests\\round1b\\closematch-t.txt");
        
        int TC = sc.nextInt(); // 1 to 50
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt();  // 2 to 26
            Integer P[] = new Integer[N];   // 1 to 1000
            for (int j=0; j<N; j++)
                P[j] = sc.nextInt();
            out.print("Case #"+(i+1)+": ");
            new SenateEvac().greedy(P);
        }
    }
}
