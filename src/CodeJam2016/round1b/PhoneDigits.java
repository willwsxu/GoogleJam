/*
 * quick description: phone digits are replaced with upper letter words
 * mixed them up
 * phone difits is in nondecreasing order
 * Give a String S, find out the phone number
 */
package CodeJam2016.round1b;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class PhoneDigits {
    String[] digits=new String[]{"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
    // unique: Z-0, G-8, U-4, W-2, X-6 
    // H-3(8), F-5(4), V-7(F-5), S for 6 and 7
    // I-9 (exclude 5 6 8)
    // N-1 (exclude 7 and 9)
    boolean find(char unique, String name) {
        if (str.isEmpty())
            return false;
        if (str.contains(unique)) {
            for (int i=0; i<name.length(); i++)
                str.remove((Character)name.charAt(i));
            return true;
        }
        return false;
    }
    List<Character> str = new ArrayList<>(2000);
    void solve(String s)
    {
        for (int i=0; i<s.length(); i++)
            str.add(s.charAt(i));
        List<Integer> phone=new ArrayList();
        while(find('Z', "ZERO"))
            phone.add(0);
        while(find('G', "EIGHT"))
            phone.add(8);
        while(find('U', "FOUR"))
            phone.add(4);
        while(find('W', "TWO"))
            phone.add(2);
        while(find('X', "SIX"))
            phone.add(6);
        
        while(find('H', "THREE"))
            phone.add(3);
        while(find('F', "FIVE"))
            phone.add(5);
        
        while(find('V', "SEVEN"))
            phone.add(7);
        while(find('I', "NINE"))
            phone.add(9);
                
        while(find('N', "ONE"))
            phone.add(1);
        Collections.sort(phone);
        for(int i:phone)
            out.print(i);
        out.println();
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("A-large-practice.in");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String S = sc.next(); // 3 ≤ N ≤ 2000
            out.print("Case #"+(i+1)+": ");
            new PhoneDigits().solve(S);
        }
    }
}
