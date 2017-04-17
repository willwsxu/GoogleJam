package googlejam.Round1A17;


import static java.lang.System.out;
import java.util.Scanner;


public class AlphaCake {
    char[][]grid;
    boolean[][]used;
    int R, C;
    
    void fillRect(int r, int c)
    {
        char match='?';
        int left=c, right =c;
        for (int j=c-1; j>=0; j--) {
            if (used[r][j])
                break;
            match = grid[r][j];
            left=j;
            if (match !='?')
                break;
        }
        out.println("match "+match+"left:right"+left+":"+right);
        
        for (int j=c+1; j<C;  j++) {
            if (used[r][j])
                break;
            if (grid[r][j] !='?' && match !='?')
                break;
            if (grid[r][j] !='?')
                match = grid[r][j];
            right++;
        }
        out.println("match "+match+"left:right"+left+":"+right);
        int top=r;
        int bottom=r;
        if (match=='?') {
            if (top>0 && !used[top-1][left]) {
                if (grid[top-1][left]!='?') {
                    match=grid[top-1][left];
                    right=left;
                }
            }
        }
        if (match=='?') {
            if (top<R-1 && !used[top+1][left]) {
                if (grid[top+1][left]!='?') {
                    match=grid[top+1][left];
                    right=left;
                }
            }
        }
        out.println("match "+match+"left:right:top:bottom"+left+":"+right+":"+top+":"+bottom);
        outfor:
        for (int i=r+1; i<R; i++) {
            for (int j=left; j<=right;  j++) {
                if (grid[i][j] !='?' && match !='?')
                    break outfor;
                if (grid[i][j] !='?') {
                    match = grid[i][j];
                    right=j;
                }
            }  
            bottom++;
        }
        out.println("match "+match+"left:right:top:bottom"+left+":"+right+":"+top+":"+bottom);
        for (int i=top; i<=bottom; i++) {
            for (int j=left; j<=right;  j++) {
                if (grid[i][j]=='?')
                    grid[i][j]=match;
                else if (grid[i][j]!=match)
                    out.println("mismatch "+grid[i][j]+":"+match);
                used[i][j]=true;
            }
        }
    }
    
    void bruteforce()
    {
        used = new boolean[R][C];
        for (int i=0; i<R;i++)
            for (int j=0;j<C; j++){
                if (grid[i][j]=='?') {
                    fillRect(i, j);
                }
            }                
    }
    
    // First, within each row, we can extend each existing letter into all cells to the right of that letter, 
    // until we reach another existing letter or the edge of the cake. Then, we can extend the leftmost 
    // existing letter (if any) into all cells to the left of that letter.
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
        if ( first<0)  // no leeters in the grid, never should happen
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
        //googlejam.ContestHelper.redirect("out.txt");
        scan = googlejam.ContestHelper.getFileScanner("alphacake-t.txt");
        
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
