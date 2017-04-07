
package googlejam;

import static java.lang.System.out;
import java.util.Scanner;

public class PancakeRevenge {
    
    int flip(String p)
    {
        return 0;
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //scan = googlejam.CodeChef.getFileScanner("countingSheep-b.txt");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String pancakes = scan.nextLine(); // 1 to 100
            if (pancakes.isEmpty())
                pancakes = scan.nextLine();
            out.println("Case #"+(i+1)+": ");
        }
    }
}
