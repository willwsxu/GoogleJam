/*
 * Input is digit string show how many people at each shyness level
 * shyness level from 0 to Smax
 * people at each shyness level can range from 0 to 9
 * people only stand if current stading count >= their shyness
 * Output
 * What is the minimum number of friends that you need to invite to guarantee a standing ovation?
 */
package CodeJam2015.qualification;

import CodeJam2015.round1a.Haircut;
import static java.lang.System.out;
import java.util.Scanner;

public class StandingOvation {
    
    int solve(String p)
    {
        int standing=0;
        int invite=0;
        for (int i=0; i<p.length(); i++)
        {
            int people = p.charAt(i)-'0';
            if (people==0)
                continue;
            if (i>standing) { // need 
                invite += (i-standing);
                //out.println("i="+i+" invite="+invite);
                standing += (i-standing);
            }
            standing += people;
        }
        return invite;
    }
    public static void test()
    {
        out.println(new StandingOvation().solve("11111"));
        out.println(new StandingOvation().solve("09"));
        out.println(new StandingOvation().solve("110011"));
        out.println(new StandingOvation().solve("1"));
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\qualification\\A-large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int Sm = sc.nextInt();  // 1 ≤ B ≤ 1000
            String p = sc.next();
            out.print("Case #"+(i+1)+": ");
            out.println(new StandingOvation().solve(p));
        }
    }
}
