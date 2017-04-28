/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlejam;

import static java.lang.System.out;
import java.util.Arrays;


public class NumberHelper
{
    public static int highDigit(long N)
    {
        long digit = 0;
        while (N>0) {
            digit = N%10;
            N /=10;
        }
        return (int)digit;
    }
    public static long scale(long N)  // 120 scale is 100
    {
        long s=1;
        while (N>0) {
            N /=10;
            s *= 10;
        }
        return s/10;
    }
    public static long reverse(long N)
    {
        long rev=0;
        while (N>0) {
            rev = rev*10+N%10;
            N /=10;
        }
        return rev;
    }  
    public static long[] split (long N)
    {
        long[]half=new long[2];
        split(N, half);
        return half;
    }
    public static void split (long N, long[]half)
    {
        String str = Long.toString(N);
        int h = str.length()/2;         // left len<=right len
        String left=str.substring(0, h);
        String right=str.substring(h);
        if ( h>0)
            half[0]=Long.parseLong(left);
        else
            half[0]=0;
        half[1]=Long.parseLong(right);
    }
    public static int digits(long N)
    {
        return Long.toString(N).length();
    }
    public static long pow(int base, int exp)
    {
        long p=1;
        while (exp-->0)
            p *= base;
        return p;
    }
    
    public static void test()
    {
        out.println(highDigit(5432100000000L));
        out.println(scale(1432100000000L));
        out.println(reverse(1432100000000L));
        out.println(Arrays.toString(split(123)));
        out.println(Arrays.toString(split(1)));
        out.println(pow(10,2));
    }
}

