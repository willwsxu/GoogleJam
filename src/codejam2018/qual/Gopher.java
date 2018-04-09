package codejam2018.qual;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*

 */

public class Gopher {
    static Scanner sc = new Scanner(System.in);  
    static Random rand=new Random();
    int moves=0;
    void print(List<int[]> cells)
    {
        int cols=bottomright[1]-topleft[1]+1;
        int rows=bottomright[0]-topleft[0]+1;
        char [][]board=new char[rows][cols];
        for (char[]r : board)
            Arrays.fill(r, '-');
        for (int[] c: cells) {
            board[c[0]-topleft[0]][c[1]-topleft[1]]='*';
        }
        for (char[]r : board) {
            System.err.println(Arrays.toString(r));
        }
    }
        
    int[] addCell(List<int[]> cells, int i1, int j1, int A) {  
        moves++;
        cells.add(new int[]{i1,j1});
        grid[i1-topleft[0]][j1-topleft[1]]=true;
        return choose(cells, A);
    }
    boolean grid[][]=null;
    int MaxCount=9;
    int count(int r, int c)
    {
        int cnt=0;
        if (grid[r-1][c-1])
            cnt++;
        if ( grid[r-1][c] )
            cnt++;
        if ( grid[r-1][c+1] )
            cnt++;
        if ( grid[r][c-1] )
            cnt++;
        if ( grid[r][c] )
            cnt++;
        if ( grid[r][c+1] )
            cnt++;
        if (grid[r+1][c-1] )
            cnt++;
        if (grid[r+1][c] )
            cnt++;
        if (grid[r+1][c+1])
            cnt++;
        return cnt;
    }
    int[] choose(List<int[]> cells, int A)
    {
        int k=1;
        for (k=1; k<grid[0].length-1; k++) {
            if (count(1,k)!=9)
                break;
        }
        return new int[]{2, k+1};
    }
    int topleft[]=new int[]{1,1};
    int bottomright[]=new int[]{0,0};
    boolean solve(int A)
    {
        // A is at most 200, make it 3 by x grid
        bottomright[0]=3;
        bottomright[1]=(A+2)/3;        
        grid=new boolean[3][bottomright[1]];
        int i=2, j=2;
        int count=1000;
        List<int[]> cells=new ArrayList<>();
        while (count-->0) {
            System.out.println(i+" "+j);
            int i1=sc.nextInt();
            int j1=sc.nextInt();
            if (i1<0 || j1<0) {
                print(cells);
                return false;
            }
            if (i1==0 && j1==0) {
                //print(cells, topleft, bottomright);
                return true;
            }
            int[]rc = addCell(cells, i1, j1, A);
            i=rc[0];
            j=rc[1];
        }
        print(cells);
        return false;
    }
    
    public static void main(String[] args)  
    {
        int T=sc.nextInt();
        while (T-->0) {
            Gopher go=new Gopher();
            boolean ret=go.solve(sc.nextInt());
            System.err.println(ret+" "+go.moves);
        }
    }
}
