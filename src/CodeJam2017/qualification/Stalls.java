package CodeJam2017.qualification;


import static java.lang.System.out;
import java.util.Scanner;

public class Stalls {
    
    static String findPos(long stalls, long p)
    {
        // BST level of p
        long occupied=0; // stalls used in all previous levels
        long leadIndex=1;
        while ( 2*leadIndex <= p) {
            occupied += leadIndex;
            leadIndex *= 2;
        }
        long avg = (stalls-occupied)/leadIndex;
        long rem = (stalls-occupied)%leadIndex;
        if (rem>0 && p-leadIndex<rem) {
            long left = avg/2;
            long right = (avg+1)/2;
            return ""+right+" "+left;
        } else {
            long left = (avg-1)/2;
            long right = avg/2;
            return ""+right+" "+left;
        }
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //scan = googlejam.CodeChef.getFileScanner("C-large.in");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            long N = scan.nextLong(); // 1 to 10^18            
            long k = scan.nextLong(); // 1 to N
            out.println("Case #"+(i+1)+": "+findPos(N, k));
        }
    }
}
