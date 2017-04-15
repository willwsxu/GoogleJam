
package googlejam;

// Grid helper class
public class Grid {
    int[][] grid;
    Grid(int N) {
        grid=new int[N][N];
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
}
