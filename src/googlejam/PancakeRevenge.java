
package googlejam;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PancakeRevenge {
    List<Integer> pangrp = new ArrayList<>(100); // count consecutive with same face
    
    // simpily as pattern +-+-+-, no need to flip face up pancakes at bottom
    // if top is +, flip it to -, +-+-+- -> --+-+- (same as -+-+-) -> +-+-+ (same as +-+-)
    // if top is -, see example above -+-+-
    int flip(String p) // +-+- pancakes from top to bottom
    {
        int count=1;
        char last=p.charAt(0);
        for (int i=1; i<p.length(); i++) {
            if (p.charAt(i)==last)
                count++;
            else {
                pangrp.add(count*(last=='+'?1:-1));  // postive if face up
                count=1;
                last=p.charAt(i);
            }
        }
        pangrp.add(count*(last=='+'?1:-1));  // postive if face up
        //out.println(pangrp);
        if ( p.charAt(p.length()-1)=='+')  // bottom cake is face up, no need to flip
            return pangrp.size()-1;
        return pangrp.size();
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        scan = googlejam.ContestHelper.getFileScanner("pancake-b.txt");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String pancakes = scan.nextLine(); // 1 to 100
            if (pancakes.isEmpty())
                pancakes = scan.nextLine();
            out.println("Case #"+(i+1)+": "+new PancakeRevenge().flip(pancakes));
        }
    }
}
