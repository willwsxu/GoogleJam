package CodeJam2017.qualification;


import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TidyNumber {
    
    static boolean isTidy(long n)
    {
        if (n<10)
            return true;
        long last = n%10;
        while (n>=10) {
            n /=10;
            if (n%10>last)
                return false;
            last = n%10;
        }
        return true;
    }
    
    static long findTidySlow(long num) 
    {
        while (num>0 && !isTidy(num)) {
            num--;
        }
        return num;
    }
    static long findTidy(long n) 
    {
        if (n<10)
            return n;
        List<Integer> digits = new ArrayList<>(50);
        while (n>0) {
            digits.add(0, (int)(n%10));
            n /=10;
        }
        for (int i=digits.size()-1; i>0; i--) {
            if (digits.get(i)<digits.get(i-1)) {
                for (int j=i; j<digits.size(); j++)
                    digits.set(j, 9);
                digits.set(i-1, digits.get(i-1)-1);
            }
        }
        long tidy=0;
        for (int j=0; j<digits.size(); j++)
            tidy = tidy*10+digits.get(j);
        return tidy;
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //scan = googlejam.CodeChef.getFileScanner("B-large.in");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            long num = scan.nextLong(); // 1 to 10^18
            out.println("Case #"+(i+1)+": "+findTidy(num));
        }
    }
}
