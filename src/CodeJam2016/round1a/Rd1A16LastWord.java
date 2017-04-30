package CodeJam2016.round1a;


import static java.lang.System.out;
import java.util.Scanner;


public class Rd1A16LastWord {
    
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        scan = googlejam.ContestHelper.getFileScanner("round1A16Lastword-l.in.txt");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            StringBuilder build = new StringBuilder();
            for (int j=0; j<line.length(); j++) {
                if (j==0)
                    build.append(line.charAt(j));
                else if (line.charAt(j)>=build.charAt(0))
                    build.insert(0, line.charAt(j));// insert letter to beginning if it is larger
                else
                    build.append(line.charAt(j));
            }
            
            out.print("Case #"+(i+1)+": ");
            out.println(build.toString());
        }
    }
}
