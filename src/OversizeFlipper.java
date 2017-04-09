
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

public class OversizeFlipper {
    String flip(char[] pancakes, int flipper) //flip from 2 to pancake size 1000
    {
        //out.println(pancakes+":"+flip);
        int flips=0;
        for (int i=0; i<=pancakes.length-flipper; i++) {
            if (pancakes[i]=='+')
                continue;
            flips++;
            for (int j=i; j<i+flipper; j++)
                pancakes[j] = pancakes[j]=='-'?'+':'-';
            //out.println(Arrays.toString(pancakes));
        }
        for (int i=pancakes.length-flipper+1; i < pancakes.length; i++) {
            if (pancakes[i]=='-')
                return "IMPOSSIBLE";
        }
        return ""+flips;
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //scan = googlejam.CodeChef.getFileScanner("A-large.in");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            String elem[]=line.split(" ");
            
            out.println("Case #"+(i+1)+": "+new OversizeFlipper().flip(elem[0].toCharArray(), Integer.parseInt(elem[1])));
        }
    }
}
