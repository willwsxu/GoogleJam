/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeJam2015.round1c;

import static java.lang.System.out;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class TypewriterMonkey {
    String key;     // keys on keyboard
    String target;  // target word
    int S;          // letters typed by Monkey
    int L;          // target len
    int K;          // key len
    int freq[] = new int[26]; // letter frequency
    Set<Character> tset = new HashSet<>();
    Set<Character> kset = new HashSet<>();
    TypewriterMonkey(String k, String t, int s)
    {
        key=k; target=t; S=s;
        K=k.length();
        L=t.length();
        for (int i=0; i<k.length(); i++) {
            freq[k.charAt(i)-'A']++;
            kset.add(k.charAt(i));
        }
        if (!contain()) {
            out.println("0.0");
            return;
        }
        int best = bestcase();
        double avg = avgcase();
        //out.println(best+" v "+avg);
        out.println(String.format("%.7f", best-avg));
    }
    boolean contain()
    {
        for (int i=0; i<L; i++) {
            if (freq[target.charAt(i)-'A']==0)
                return false;
        }
        return true;
    }
    // len of none overlap
    int overlap(String a)  // find overlap substring and must match to end of string
    {
        outter:
        for (int i=1; i<a.length(); i++) {
            for (int j=0; j<a.length()-i; j++)
                if (a.charAt(j)!=a.charAt(i+j))
                    continue outter;
            return i;
        }
        return L;
    }
    
    int bestcase()
    {
        for (int i=0; i<L; i++)
            tset.add(target.charAt(i));
        if (tset.size()==1)
            return S-L+1;
        int ovl = overlap(target);
        if (ovl==0) {
            out.println("overlap error");
            return 0;
        }
        else {
            return 1+(S-L)/ovl; // bug fix, ovl is string required to repeat
        }
    }
    double avgcase()
    {
        if (kset.size()==1)
            return bestcase();
        double p=1.0;
        for (int i=0; i<L; i++) {
            p *= (double)freq[target.charAt(i)-'A']/key.length();
        }
        if (p==0.0)
            out.println("error p");
        return p*(S-L+1); //Linearity of expectation 
    }
    static void test()
    {
        new TypewriterMonkey("AABCD", "ABA", 100);
        new TypewriterMonkey("AABCD", "ABC", 100);
    }
    
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1c\\B-large-practice.in.txt");
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int K = sc.nextInt();  // 1 ≤ K ≤ 100
            int L = sc.nextInt();  // 1 ≤ L ≤ S ≤ 100
            int S = sc.nextInt();  // 
            String key=sc.next();  // len K
            String target=sc.next();// len L
            out.print("Case #"+(i+1)+": ");
            new TypewriterMonkey(key, target, S);
        }
    }
}
