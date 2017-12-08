/*
 * You gain 2 cookies per second at game start
 * Any time you have at least C cookies, you can buy a cookie farm. 
 *  a cookie farm costs you C cookies and gives you an extra F cookies per second.
 * Once you have X cookies that you haven't spent on farms, you win
 */
package CodeJam2014.qualification;

import googlejam.MyScanner;
import static java.lang.System.out;
import java.text.DecimalFormat;

// Strategy: compute time needed to get X cookies without buying farm, t1
// compute total needed to get to X, after buy a farm, t2
// if t2<t1, choose t2, and repeat the comparison of buying next farm or not
public class Cookie {
    // 1 ≤ C ≤ 10000. 1 ≤ F ≤ 100. 1 ≤ X ≤ 100000.
    String solve(double C, double F, double X) {
        
        double rate=2;
        double t1=X/rate; // time to reach X cookies without buying farm
        double farmTime=C/rate;  // time take to buy ith farm
        double t2=X/(rate+F)+farmTime;  // time to reach X after buy the farm
        while (t2 <t1) {
            rate += F;
            t1=t2;                  // reset t1
            farmTime += C/rate;     // buy another farm
            t2=X/(rate+F)+farmTime; // time after buying next farm
        }
        DecimalFormat df=new DecimalFormat("#.#######");
        return df.format(t1);
    }
    public static void test()
    {
        out.println(new Cookie().solve(30, 1, 2).equals("1"));
        out.println(new Cookie().solve(30, 2, 100).equals("39.1666667"));
        out.println(new Cookie().solve(30.5, 3.14159, 1999.1999).equals("63.9680013"));
        out.println(new Cookie().solve(500, 4, 2000).equals("526.1904762"));
    }
    static MyScanner sc = new MyScanner("tests\\jam2014\\qualification\\B-large-practice.in.txt");  
    public static void main(String[] args)  
    {
        judge();
    }
    private static void judge()
    {
        googlejam.ContestHelper.redirect("out.txt");
        int TC = sc.ni(); // 1 to 100
        for (int i=0; i<TC; i++) {
            double c = sc.nextDouble();
            double f = sc.nextDouble();
            double x = sc.nextDouble();
            String ans = new Cookie().solve(c, f, x);
            out.println("Case #"+(i+1)+": "+ans);
        }
    }
}

