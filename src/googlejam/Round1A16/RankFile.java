
package googlejam.Round1A16;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RankFile {
    
    List<int[]> lists;
    int min, max;
    int [][]grid;
    boolean rl[]; // row list
    boolean cl[]; // col list
    RankFile(List<int[]> lists, int min, int max, int N)
    {
        this.lists=lists;
        this.min=min;
        this.max = max;
        grid = new int[N][N];
        rl = new boolean[N];
        cl = new boolean[N];
    }
    int[] find(int ind, int val)
    {
        for (int i=0; i<lists.size(); i++) {
            int[] list = lists.get(i);
            if (list[ind]==val) {
                lists.remove(i);
                return list;
            }
        }
        return null;
    }
    void copyRow(int ind, int[] list)
    {
        for (int i=0; i<grid.length; i++)
            grid[ind][i]=list[i];
    }
    void copyCol(int ind, int[] list)
    {
        for (int i=0; i<grid.length; i++)
            grid[i][ind]=list[i];
    }
    void solve()
    {
        int N = grid.length;
        int[] list = find(0, min);
        if (list==null)
            return; //error
        rl[0]=true; // fill row first
        copyRow(0, list);
        list = find(0, min);
        if ( list != null) {
            cl[0]=true;
            copyCol(0, list);
        }
        list = find(N-1, max);
        if (list==null)
            return; //error
        if (list[0]==grid[0][N-1]) {
            cl[N-1]=true;
            copyCol(N-1, list);
        } else {
            rl[N-1]=true;
            if (cl[0]) {
                if (grid[N-1][0]!=list[0])
                    return;// error
            }
            copyRow(N-1, list);            
        }        
        list = find(N-1, max);
        if (list!=null) {
            if (rl[N-1]) {
                cl[N-1]=true;
                copyCol(N-1, list);     // check error           
            } else {
                rl[N-1]=true;
                copyRow(N-1, list);     // check error             
            }
        }
    }
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        googlejam.ContestHelper.redirect("out.txt");
        scan = googlejam.ContestHelper.getFileScanner("round1A16Lastword-l.in.txt");
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt(); // 2 to 50
            List<int[]> lists=new ArrayList<>(2*N-1);
            int min=999999;
            int max=0;
            for ( int j=0; j<2*N-1; j++) 
            {
                int [] list = new int[N];
                for (int k=0; k<N; k++) {
                    list[k]=scan.nextInt();
                    if (list[k]>max)
                        max = list[k];
                    if (list[k]<min)
                        min = list[k];
                }
            }
            out.print("Case #"+(i+1)+": ");
            new RankFile(lists, min, max, N).solve();
        }
    }
}
