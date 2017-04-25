/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlejam;

import static java.lang.System.out;


public class GCD
{
    public static long gcd(long p, long q)
    {
        return q==0?p:gcd(q, p%q);
    }
    public static long lcm(long p, long q) {
        return p*(q/gcd(p,q));   // divide first to avoid overflow
    }
    public static long lcm(long a[], long seed, int s) {
        if (s==a.length-1)
            return lcm(seed, a[s]);
        else if (s<a.length-1) {
            return lcm(a, lcm(seed,a[s]), s+1);
        }
        else
            return seed;
    }
    public static void test()
    {
        out.println(gcd(12,8));
        out.println(lcm(120000000000L,80000000000L));
        out.println(lcm(new long[]{12,8,3,5,9,1}, 12, 1));
    }
}