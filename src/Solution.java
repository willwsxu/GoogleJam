
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*

 */

public class Solution {
    static Scanner sc = new Scanner(System.in);  
    static Random rand=new Random();
    static void print(List<int[]> cells, int[]topleft, int[]bottomright)
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
        
    static int[] addCell(List<int[]> cells, int[]topleft, int[]bottomright, int i1, int j1, int A) {        
        //AI, guess next move
        if (grid==null) {
            cells.add(new int[]{i1, j1});
            if (topleft[0]==0) {  // make it at least 3x3
                topleft[0]=i1;
                bottomright[0]=i1+2;
                topleft[1]=j1;
                bottomright[1]=j1+2;
                return new int[]{i1+2, j1+2};
            } else {
                if (i1<topleft[0])
                    topleft[0]=i1;
                if (j1<topleft[1])
                    topleft[1]=j1;
                if (i1>bottomright[0])
                    bottomright[0]=i1;
                if (j1>bottomright[1])
                    bottomright[1]=j1;
                if (cells.size()<4)
                    return new int[]{topleft[0]+1, bottomright[1]-1};
            }
        } else
            grid[i1-topleft[0]][j1-topleft[1]]=true;
        return choose(cells, topleft, bottomright, A);
    }
    static boolean grid[][]=null;
    static int[] choose(List<int[]> cells, int[]topleft, int[]bottomright, int A)
    {
        int cols=0;
        int rows=0;
        if (grid==null) {
            cols=bottomright[1]-topleft[1]+1;
            rows=bottomright[0]-topleft[0]+1;
            int Area=rows*cols;
            if (Area>=A) {
                //System.err.println("rectangle "+rows+" "+cols);
                grid=new boolean[rows][cols];
                for (int[] c: cells)
                    grid[c[0]-topleft[0]][c[1]-topleft[1]]=true;
            }
        } else {
            rows=grid.length;
            cols=grid[0].length;
        }
        if ( grid!=null) {
            rows -=2;
            cols -=2;            
        }
        int i=topleft[0]+rand.nextInt(rows);
        int j=topleft[1]+rand.nextInt(cols);
        if (grid!=null) {
            i++;
            j++;
            int r=i-topleft[0];
            int c=j-topleft[1];
            int loop=9;
            while (grid[r-1][c-1] && grid[r-1][c] && grid[r-1][c+1] && grid[r][c-1]
                     && grid[r][c] && grid[r][c+1] && grid[r+1][c-1] && grid[r+1][c] && grid[r+1][c+1]) {
                i=topleft[0]+rand.nextInt(rows)+1;
                j=topleft[1]+rand.nextInt(cols)+1;
                r=i-topleft[0];
                c=j-topleft[1];
                if (loop--==0)
                    break;
            }
        }
        return new int[]{i, j};
    }
    static boolean solve(int A)
    {
        grid=null;
        int topleft[]=new int[]{0,0};
        int bottomright[]=new int[]{0,0};
        int i=500, j=500;
        int count=1000;
        List<int[]> cells=new ArrayList<>();
        while (count-->0) {
            System.out.println(i+" "+j);
            int i1=sc.nextInt();
            int j1=sc.nextInt();
            if (i1<0 || j1<0) {
                print(cells, topleft, bottomright);
                return false;
            }
            if (i1==0 && j1==0) {
                //print(cells, topleft, bottomright);
                return true;
            }
            int[]rc = addCell(cells, topleft, bottomright, i1, j1, A);
            i=rc[0];
            j=rc[1];
        }
        print(cells, topleft, bottomright);
        return false;
    }
    
    public static void main(String[] args)  
    {
        int T=sc.nextInt();
        //System.err.println("Test "+T);
        while (T-->0) {
            boolean ret=solve(sc.nextInt());
            //System.err.println(ret);
        }
    }
}
