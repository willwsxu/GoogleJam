/*
 * Given the size of the board (R x C) and the number of hidden mines M, 
 * is it possible (however unlikely) to win in one click?
 */
package CodeJam2014.qualification;

import static java.lang.System.out;

public class MineSweeperMaster {
    
    final char EMPTY=' ';
    final char MINE='*';
    
    char mines[][];
    int ROW;
    int COL;
    boolean answer=false;
    
    MineSweeperMaster(int R, int C, int M)
    {
        ROW=R; COL=C;
        mines=new char[R][C];
        if (backTracking(0,0, M)) {
        } else
            out.println("Impossible");
    }
    
    void print()
    {
        for (int i=0; i<ROW; i++) {
            for (int j=0; j<COL; j++) {
                if (mines[i][j]!=MINE)
                    out.print('.');
                else
                    out.print(MINE);
            }
            out.println();
        }
    }
    boolean backTracking(int r, int c, int mine)
    {
        if (answer)
            return answer;
        boolean ans=false;
        if (mine==0) {
            return answer=validConfig();
        }
        if (r>0) {
            if (mines[r-1][c]!=MINE)
                return false;
        }
        mines[r][c]=MINE;
        if (--mine<1) {
            return answer=validConfig();
        } else {
            if (c<COL-1) {  // fill mine to right
                if (backTracking(r, c+1, mine)) {
                    ans = true;
                }
            }
            if (!ans && r<ROW-1) { // fill mine to next row
                for (int j=0; j<COL; j++) {
                    if (mines[r+1][j]!=MINE) {
                        if (backTracking(r+1, j, mine)) {
                            ans = true;
                        }    
                        break;
                    }
                }
            }
        }
        mines[r][c]=EMPTY;
        return ans;
    }
    
    // compute mines around cell (r,c)
    int compute(int r, int c)
    {
        int m=0;
        int dR[]=new int[]{-1,-1,-1,0,0,1,1,1};
        int dC[]=new int[]{-1,0,1,-1,1,-1,0,-1};
        for (int i=0; i<dR.length; i++)  { // check 8 neighbors
            if (r+dR[i]<0 || r+dR[i]>=ROW || c+dC[i]<0 || c+dC[i]>=COL)
                continue;
            if ( mines[r+dR[i]][c+dC[i]]=='*')
                m++;
        }
        return m;
    }
    
    void visit(int r, int c)
    {
        if (mines[r][c]!=EMPTY) // visit none empty cells
            return;
        mines[r][c]=(char)compute(r,c);
        if (mines[r][c]>0)  // stop if this cell is around mine 
            return;
        int dR[]=new int[]{-1,-1,0};  // visit left or top
        int dC[]=new int[]{-1,0,-1};
        for (int i=0; i<dR.length; i++)  { // check 3 neighbors
            if (r+dR[i]<0 || c+dC[i]<0)
                continue;
            visit(r+dR[i], c+dC[i]);
        }        
    }
    boolean validConfig()
    {
        // reset all cells to empty unless it is mine
        for (int i=0; i<ROW; i++) {
            for (int j=0; j<COL; j++) {
                if (mines[i][j]!=MINE)
                    mines[i][j]=EMPTY;
            }
        }
        visit(ROW-1, COL-1);  // visit from bottom right
        for (int i=0; i<ROW; i++) {
            for (int j=0; j<COL; j++) {
                if (mines[i][j]==EMPTY) // cells not visited
                    return false;
            }
        }
        print();
        return true;
    }
    
    public static void test()
    {
        new MineSweeperMaster(3,1,1);
        new MineSweeperMaster(4,7,3);
        new MineSweeperMaster(10,10,82);
    }
    public static void main(String[] args)  
    {
        test();
    }
}

