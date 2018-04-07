
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
            //    System.err.println("rectangle "+rows+" "+cols);
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
        }
        return new int[]{i, j};
    }
    static boolean solve(int A)
    {
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
            cells.add(new int[]{i1,j1});
            
            //AI, guess next move
            if (topleft[0]==0) {  // make it at least 3x3
                topleft[0]=i1;
                bottomright[0]=i1+2;
                topleft[1]=j1;
                bottomright[1]=j1+2;
                i=i1+2;
                j=j1+2;
            } else {
                if (i1<topleft[0])
                    topleft[0]=i1;
                if (j1<topleft[1])
                    topleft[1]=j1;
                if (i1>bottomright[0])
                    bottomright[0]=i1;
                if (j1>bottomright[1])
                    bottomright[1]=j1;
                if (cells.size()<4) {
                    i=topleft[0]+1;
                    j=bottomright[1]-1;
                } else {
                    int[]rc=choose(cells, topleft, bottomright, A);
                    i=rc[0];
                    j=rc[1];
                }   
            }
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
