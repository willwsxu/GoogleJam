//https://code.google.com/codejam/contest/6254486/dashboard
package googlejam;

import static java.lang.System.out;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class CountingSheep {
    
    Set<Integer> digits = new TreeSet<>();
    void fillDigits(long val) {
        while(val>0) {
            digits.add((int)(val%10));
            val /= 10;
        }
    }
    
    String solve(long N) {  // 0 to 10^6
        if ( N==0 )
        {
            return "INSOMNIA";
        }
        int count=0;
        int noChange=0;
        int lastDigitSize=0;
        while (true) {
            fillDigits((++count)*N);
            //int s = digits.size();
            if ( digits.size()==10)
                return ""+N*count;
            out.println(count*N);
        }
    }
    
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //scan = googlejam.CodeChef.getFileScanner("countingSheep-b.txt");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();
            out.println("Case #"+(i+1)+": "+new CountingSheep().solve(N));
        }
    }
}
