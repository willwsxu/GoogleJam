package CodeJam2017.round1a;


import static java.lang.System.out;
import java.util.Scanner;


public class AlphaCake {
    char[][]grid;
    int R, C;    
    
    // First, within each row, we can extend each existing letter into all cells to the right of that letter, 
    // until we reach another existing letter or the edge of the cake. Then, we can extend the leftmost 
    // existing letter (if any) into all cells to the left of that letter.
    // next step is to fill down and up
    void greedy()
    {
        for (int i=0; i<R;i++) {
            int first=-1;  // position of first letter
            for (int j=0;j<C; j++){
                if (grid[i][j]!='?') {
                    first=j;
                    break;
                }
            }
            if (first<0)  // no letter on this row
                continue;
            for (int j=first+1;j<C; j++){
                if (grid[i][j]=='?')
                    grid[i][j] = grid[i][j-1];  // extend to right            
            }
            for (int j=0;j<C; j++){
                if (grid[i][j]=='?')
                    grid[i][j] = grid[i][first];  // extend to leftmost    
                else
                    break;
            }
        }        
        //print();
        
        int first =-1;  // first fir
        for (int i=0; i<R;i++) {
            if (grid[i][0]!='?') {
                first=i;
                break;
            }
        }
        if ( first<0)  // no letters in the grid, never should happen
            return;
        for (int j=0; j<C;j++) {
            for (int i=first+1; i<R;i++) {
                if (grid[i][j]=='?') {
                    grid[i][j] = grid[i-1][j];  // extend to bottom 
                }
            }
            if (first==0)
                continue;
            for (int i=0; i<R;i++) {
                if (grid[i][j]=='?')
                    grid[i][j] = grid[first][j];  // extend to top    
                else
                    break;            
            }
        }
    }
    
    private void print()
    {
        for (int i=0; i<R;i++) {
            for (int j=0;j<C; j++)
                out.print(grid[i][j]);
            out.println();
        }
    }
    AlphaCake(String[] g)
    {
        R = g.length;
        C = g[0].length();
        grid = new char[R][C];
        for (int i=0; i<R;i++)
            for (int j=0;j<C; j++){
                grid[i][j] = g[i].charAt(j);
            }
        
        greedy();
        print();
    }
    
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        scan = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1A17\\alphacake-l.in.txt");
        
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int R = scan.nextInt();
            int C = scan.nextInt();
            String[] rows = new String[R];
            for (int j=0; j<R; j++) {
                rows[j] = scan.nextLine();
                if (rows[j].isEmpty())
                    rows[j] = scan.nextLine();
            }
            out.println("Case #"+(i+1)+": ");
            new AlphaCake(rows);
        }
    }
}
