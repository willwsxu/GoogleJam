/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015.round1a;

import static java.lang.System.out;
import java.util.Scanner;

class GCD
{
    static long gcd(long p, long q)
    {
        return q==0?p:gcd(q, p%q);
    }
    static long lcm(long p, long q) {
        return p*q/gcd(p,q);
    }
    static long lcm(long a[], long seed, int s) {
        if (s==a.length-1)
            return lcm(seed, a[s]);
        else if (s<a.length-1) {
            long m=lcm(a[s], a[s+1]);
            long m2 = lcm(seed, m);
            if (s==a.length-2)
                return m2;
            else
                return lcm(a, m2, s+2);
        }
        else
            return seed;
    }
    static void test()
    {
        out.println(gcd(12,8));
        out.println(lcm(12,8));
        out.println(lcm(new long[]{12,8,3,5,9,1}, 1, 0));
    }
}
public class Haircut {
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        GCD.test();
    }
}
