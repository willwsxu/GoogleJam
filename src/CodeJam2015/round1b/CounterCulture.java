/*
 * Brief Description: Count from 1 to N, by adding 1 or flip the digits
 * output: minimal number to count
 * Observation:
 * flip once from one scale to next, e.g. from 11 to 100
 * 11,12,21,22,23,32,33,34,...,87,88,89,98,99,100 total 26
 * 11..19,91..100 total 20
 * Once at the scale, separate number into left and right half
 *  flip the left number, counting up this much and then flip, then counting to N
 *  e.g. 23456, split into 23 and 456, count to 10032, flip 23001, count to 23456
 *  if right part is all 0, follow above scheme with N-1
 *  if left part is 100, no need to split and flip, just count
 */
package CodeJam2015.round1b;

import static CodeJam2015.round1b.NumberHelper.highDigit;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
    static void split (long N, long[]half)
    {
        String str = Long.toString(N);
        int h = str.length()/2;         // left len<=right len
        String left=str.substring(0, h);
        String right=str.substring(h);
        half[0]=Long.parseLong(left);
        half[1]=Long.parseLong(right);
    }
    static int digits(long N)
    {
        return Long.toString(N).length();
    }
    static long pow(int base, int exp)
    {
        long p=1;
        while (exp-->0)
            p *= base;
        return p;
    }
    
    static void test()
    {
        out.println(highDigit(5432100000000L));
        out.println(scale(1432100000000L));
        out.println(reverse(1432100000000L));
    }
}

public class CounterCulture {
    
    static Set<Long> scales = new HashSet<>();
    static long count10s[]=new long[20]; // max is 14 0s
    
    static long flip(long left, long right, int p) // flip 19 to 91, 199 to 991
    {
        return right*NumberHelper.pow(10, p)+left;
    }
    static void preCalc(int zeros)
    {
        count10s[0]=1;
        // 11->19, 91->100
        for (int d=1; d<zeros; d++) {
            count10s[d] += count10s[d-1];
            long right = NumberHelper.pow(10, (d+1)/2)-1;
            count10s[d] += right;
            if (d>1){
                long f = flip(1, right, d/2);
                long inc = NumberHelper.pow(10, d)-f+1;
                count10s[d] += inc+1;
                if (inc<0)
                    out.println("error inc "+d);
            }
        }
        //out.println(Arrays.toString(count10s));
    }
    static {
        preCalc(15);
        for (long i=1; i<1000000000000000L; i *=10)
            scales.add(i);
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
    
    long splitFlip(long N)
    {
        if (scales.contains(N))
            return 0;
        long[] split=new long[2];
        NumberHelper.split(N, split);
        if (split[1]==0)
            return 1+splitFlip(N-1);
        else if (scales.contains(split[0])) {
            return N%NumberHelper.scale(N);
        } else {
            long count = NumberHelper.reverse(split[0]);
            long N2 = NumberHelper.reverse(NumberHelper.scale(N)+count);
            return N-N2+1+count+1;
        }
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
        if (N<10)
            return N;
        long cnt1 = count10s[NumberHelper.digits(N)-1];
        long cnt2 = splitFlip(N);
        //out.println(N+" count : "+cnt1+"+"+cnt2);
        return cnt1+cnt2;
    }
    void test()
    {
        NumberHelper.test();
        out.println();
        out.println(count(9));
        out.println(count(11));
        out.println(count(19));
        out.println(count(23));
        out.println(count(29));
        out.println(count(77));
        out.println(count(100));
        out.println(count(200));
        out.println(count(201));
        out.println(count(103)); 
        out.println(count(1234));    
        out.println(count(7777777777L));      
        out.println(count(87654321098765L));     
        out.println(count(20000000000000L));   
        out.println(count(100000000000000L));               
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1b\\A-Large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            long N = sc.nextLong();  // 1 ≤ N ≤ 10^14
            out.print("Case #"+(i+1)+": ");
            out.println(new CounterCulture().count(N));
        }
    }
}
