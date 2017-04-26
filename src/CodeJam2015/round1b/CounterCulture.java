/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015.round1b;

import static CodeJam2015.round1b.NumberHelper.highDigit;
import static java.lang.System.out;
import java.util.Scanner;

class NumberHelper
{
    static int highDigit(long N)
    {
        long digit = 0;
        while (N>0) {
            digit = N%10;
            N /=10;
        }
        return (int)digit;
    }
    static long scale(long N)  // 120 scale is 100
    {
        long s=1;
        while (N>0) {
            N /=10;
            s *= 10;
        }
        return s/10;
    }
    static long reverse(long N)
    {
        long rev=0;
        while (N>0) {
            rev = rev*10+N%10;
            N /=10;
        }
        return rev;
    }    
    
    static void test()
    {
        out.println(highDigit(5432100000000L));
        out.println(scale(1432100000000L));
        out.println(reverse(1432100000000L));
    }
}

public class CounterCulture {
    
    long count10s[]=new long[20]; // max is 14 0
    void preCalc(int zeros)
    {
        count10s[0]=1;
        // 11->19, 91->100
        for (int d=1; d<zeros; d++) {
            count10s[d] += count10s[d-1];
        }
        count10s[1]=10;
    }
    CounterCulture()
    {
        
    }
    long recurse(long N)
    {
        if (N==12)
            return 12;
        int high = highDigit(N);
        int low = (int)(N%10);
        long cnt=0;
        if ( high==1 ) { // 103
            long s = NumberHelper.scale(N);
            cnt = N%s;
            cnt += 2;
            N=s-2;
        } else if (low>high)  //29
        {
            cnt = (low-high+1);
            N -= cnt;
        }
        N = NumberHelper.reverse(N);
        return cnt+1+recurse(N);
    }
    
    long iterate(long N)  //backward
    {
        long cnt=0;
        while (N>12) {
            int high = highDigit(N);
            int low = (int)(N%10);
            if ( high==1 ) { // 103
                long s = NumberHelper.scale(N);
                cnt += N%s;
                cnt += 2;
                N=s-2;
            } else if (low>=high)  //29, 777
            {
                cnt += (low-high+1);
                N -= (low-high+1);
            } else if (low==0) {  // special case of ending 0
                cnt++;
                N--;
                continue;
            }
            N = NumberHelper.reverse(N);
            cnt++;
        }
        if (N !=12)
            out.println("error");
        return cnt+12;
    }
    
    long count(long N)
    {
        if (N<21)
            return N;
        return iterate(N);
    }
    void test()
    {
        NumberHelper.test();
        out.println(count(19));
        out.println(count(23));
        out.println(count(29));
        out.println(count(77));
        out.println(count(100));
        out.println(count(103));
        out.println(count(87654321098765L));    
        out.println(count(7777777777L));      
        out.println(count(20000000000000L));                 
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //new CounterCulture().test();
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1b\\A-Large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            long N = sc.nextLong();  // 1 ≤ N ≤ 10^14
            out.print("Case #"+(i+1)+": ");
            out.println(new CounterCulture().count(N));
        }
    }
}
