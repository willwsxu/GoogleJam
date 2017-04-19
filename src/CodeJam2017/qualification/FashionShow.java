package CodeJam2017.qualification;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FashionShow {
    char grid[][];
    char gridOri[][];
    char gridMax[][];
    int N2;
    int N;
    
    static void print(char[][]g)
    {
        for (char [] row: g)
            out.println(Arrays.toString(row));
    }
    // check if new value is valid
    boolean isValid(int r, int c, char m)
    {
        char save = grid[r][c];
        grid[r][c]=m;
        boolean ret = isValid_(r, c, m);
        grid[r][c]=save;
        return ret;
    }
    boolean isValid_(int r, int c, char m)
    {
        int x=0, p=0, o=0;
        int x2=0, p2=0,o2=0;
        for (int i=0; i< N; i++) {
            switch (grid[r][i]) {
                case '+': p++; break;
                case 'x': x++; break;
                case 'o': o++; break;
            }
            switch (grid[i][c]) {
                case '+': p2++; break;
                case 'x': x2++; break;
                case 'o': o2++; break;
            }
        }
        if (x+o>1) {// check row valid
            //out.println("bad row o="+ o +" x="+x+" += "+p);
            return false;
        }
        if (x2+o2>1) { // check col valid
            //out.println("bad col o="+ o2 +" x="+x2+" += "+p2);
            return false;
        }
        x=0; p=0; o=0;
        for (int i=0, j=c-r; i<N && j<N; i++, j++) {
            if (j<0)
                continue;
            switch (grid[i][j]) {
                case '+': p++; break;
                case 'x': x++; break;
                case 'o': o++; break;
            }
        }
        if (p+o>1){ // check backward diagnoal valid
            //out.println("bad backward o="+ o +" x="+x+" += "+p);
            return false;
        }
        x=0; p=0; o=0;
        int i = r+c;
        int j=0;
        while (i>=N) {
            i--;
            j++;
        }
        for (; i>=0 && j<N; i--, j++) {
            switch (grid[i][j]) {
                case '+': p++; break;
                case 'x': x++; break;
                case 'o': o++; break;
            }
        }
        if (p+o>1) { // check forward diagnoal valid
            //out.println("bad forward o="+ o +" x="+x+" += "+p);
            return false;        
        }
        return true;
    }
    static long points[]=new long[]{2, 4, 7, 10, 12, 14, 17, 20, 22, 24, 27, 30, 32, 34, 37, 40, 42, 44, 47, 50, 52, 54, 57, 60, 62, 64, 67, 70, 72, 74, 77, 80, 82, 84, 87, 90, 92, 94, 97, 100, 102, 104, 107, 110, 112, 114, 117, 120, 122, 124, 127, 130, 132, 134, 137, 140, 142, 144, 147, 150, 152, 154, 157, 160, 162, 164, 167, 170, 172, 174, 177, 180, 182, 184, 187, 190, 192, 194, 197, 200, 202, 204, 207, 210, 212, 214, 217, 220, 222, 224, 227, 230, 232, 234, 237, 240, 242, 244, 247, 250};
    static void getMaxPoints()
    {
        for (int i=0; i<100; i++)
        {
            FashionShow f=new FashionShow(i+1, 0);
            points[i]=f.maxPoints;
        }
        out.println(Arrays.toString(points));
    }
    
    void testValid()
    {
        grid = new char[3][3];        
        for (char[] row: grid)
            Arrays.fill(row, '.');
        N=3; N2=9;
        print(grid);
        grid[0][0]='+';
        out.println(isValid(0,0,'+'));
        out.println(isValid(1,1,'+'));
        out.println(isValid(1,1,'x'));
        out.println(isValid(2,2,'o'));
        grid[0][0]='o';
        out.println(isValid(0,1,'+'));
        out.println(isValid(0,2,'x'));
        out.println(isValid(1,0,'+'));
        out.println(isValid(2,0,'x'));
        grid[0][1]='+';
        out.println(isValid(1,0,'x'));
        out.println(isValid(1,0,'+'));
        out.println(isValid(1,2,'x'));
        out.println(isValid(1,2,'o'));
        grid[1][1]='x';
        out.println(isValid(2,0,'+'));
        out.println(isValid(2,0,'o'));
        out.println(isValid(2,2,'o'));
        out.println(isValid(2,1,'+'));
        out.println(isValid(1,2,'+'));
        out.println(isValid(0,2,'+'));
    }
    long compute(char[][]grid)
    {
        long points=0;
        for (char[] r: grid)
            for (char c: r)
            {
                switch (c) {
                    case '+': points++; break;
                    case 'x': points++; break;
                    case 'o': points += 2; break;
                }
            }
        return points;
    }
    long maxPoints=0;
    // rc = r*N+c
    int findNext(int rc)
    {
        while (rc<N2) {
            int r = rc/N;
            int c = rc%N;
            if (grid[r][c]=='+' || grid[r][c]=='x') {
                if ( isValid(r, c, 'o'))
                    break;
            } else if (grid[r][c]=='.') {
                if ( isValid(r, c, 'o'))
                    break;
                else if ( isValid(r, c, '+'))
                    break;
                else if ( isValid(r, c, 'x'))
                    break;
            }
            rc++;
        }
        return rc;
    }
    void backtracking(int rc) {
        if (rc==N2) {
            long points = compute(grid);
            if (maxPoints<points) {
                maxPoints=points;
                //print(grid);
                copy(gridMax, grid);
                //out.println(maxPoints);
            }
            return; // done
        }
        //if ( maxPoints==points[N-1])
        //    return;
        //rc = findNext(rc);
        int r = rc/N;
        int c = rc%N;
        
        if (isValid(r, c, grid[r][c]))
            backtracking(rc+1);
        else
            return;
        if (grid[r][c]=='+' || grid[r][c]=='x') {
            if ( isValid(r, c, 'o')) {
                grid[r][c]='o';
                backtracking(rc+1);
            }
        } else if (grid[r][c]=='.') {
            if ( isValid(r, c, 'o')) {
                grid[r][c]='o';
                backtracking(rc+1);
            }
            if ( isValid(r, c, 'x')) {
                grid[r][c]='x';
                backtracking(rc+1);  
            }
            if ( isValid(r, c, '+')) {
                grid[r][c]='+';
                backtracking(rc+1); 
            }
        }
    }
    // 
    static void copy(char [][]dest, char [][]source)
    {
        for (int i=0; i<source.length; i++)
            for(int j=0; j<source.length; j++)
                dest[i][j]=source[i][j];
    }
    void solveRookBishop()
    {
        Rook rk = new Rook(grid);
        Bishop bp = new Bishop(grid);
        bp.greedy();
        for (int i=0; i<N;i++) {
            int c= rk.board[i];
            if (bp.board[i][c]=='.')
                bp.board[i][c]='x';
            else if (bp.board[i][c]=='+')
                bp.board[i][c]='o';
        }
        gridMax=bp.board;
        maxPoints = compute(gridMax);        
    }
    FashionShow(int N, int M)
    {
        grid = new char[N][N];
        gridOri = new char[N][N];
        
        for (char[] row: grid)
            Arrays.fill(row, '.');
        for (int i=0; i<M; i++) {
            String line = scan.nextLine();
            if ( line.isEmpty())
                line = scan.nextLine();
            String elem[] = line.split(" ");
            int r = Integer.parseInt(elem[1]);
            int c = Integer.parseInt(elem[2]);
            grid[r-1][c-1]=elem[0].charAt(0);
        }
        N2 = N*N;
        this.N = N;
        copy(gridOri, grid);      
        
        solveRookBishop();
        //gridMax = new char[N][N];
        //backtracking(0);  
        
        List<String> add = new ArrayList<>(10);
        for (int i=0; i<N; i++)
            for(int j=0; j<N; j++)
            {
                if (gridOri[i][j] !=gridMax[i][j]) {
                    add.add(gridMax[i][j]+" "+(i+1)+" "+(j+1));
                }
            }      
        out.println(maxPoints+" "+add.size());
        for (int i=0; i<add.size(); i++)
            out.println(add.get(i));
        //print(gridMax);
    }
    
    static Scanner scan = new Scanner(System.in);  
    public static void main(String[] args)
    {        
        googlejam.ContestHelper.redirect("out.txt");
        scan = googlejam.ContestHelper.getFileScanner("fashionshow-l.in.txt");
        //scan = googlejam.ContestHelper.getFileScanner("fashionshow-t1.txt");
        
        int TC = scan.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt(); // 1 to 100  grid
            int M = scan.nextInt(); // 0 to N^2  models  
            out.print("Case #"+(i+1)+": ");
            new FashionShow(N, M);
        }  
    }
}

//
class Rook
{
    int []board;  // rows (vertical), store values of column
    boolean colFilled[]; // speed up look up of used column
    int N;
    Rook(char grid[][])
    {
        N=grid.length;
        board = new int[N];
        colFilled = new boolean[N];
        for (int r=0; r<N; r++)
        {
            board[r]=-1;
            for (int c=0; c<N; c++) {
                if (grid[r][c]=='x'||grid[r][c]=='o') {
                    board[r]=c;
                    colFilled[c]=true;
                    break;
                }
            }
        }
        recurse(0);
        //out.println("Rook final:"+Arrays.toString(board));
    }
    void recurse(int r)
    {
        if (r==N)
            return;
        if (board[r]>=0) {
            recurse(r+1);
            return;
        }
        for (int c=0; c<N; c++) {
            if (colFilled[c])
                continue;
            colFilled[c] = true;
            board[r]=c;
            recurse(r+1);
            break;
        }            
    }
    boolean bDone=false;
    void backtracking(int r)  // no need to backtrack here
    {
        if ( r==N) {
            out.println("Rook:"+Arrays.toString(board));
            bDone=true;
            return;
        }
        if (board[r]>=0) {
            recurse(r+1);
            return;
        }
        for (int c=0; c<N; c++) {
            if (colFilled[c])
                continue;
            if (bDone)
                break;
            colFilled[c] = true;
            board[r]=c;
            recurse(r+1);
            colFilled[c] = false;
        }
    }
    char[][]getGrid()
    {
        char[][]g=new char[N][N];
        for (char[] row: g)
            Arrays.fill(row, '.');
        for (int r=0; r<N; r++)
            g[r][board[r]]='x';
        FashionShow.print(g);
        return g;
    }
}
class Bishop
{
    char [][]board;  // rows (vertical), store values of column
    boolean fd[]; //foward diagnoal, r+c is same
    boolean bd[]; // backward diagonal, r-c same
    
    int N;
    Bishop(char grid[][])
    {
        N=grid.length;
        board = new char[N][N];
        fd = new boolean[2*N-1];
        bd = new boolean[2*N-1];
        for (int r=0; r<N; r++) { 
            for (int c=0; c<N; c++) {
                board[r][c]='.';
                if (grid[r][c]=='+'||grid[r][c]=='o') {
                    board[r][c]='+';
                    fd[r+c]=true;
                    bd[N-1+c-r] = true;
                }
            }
        }
        N2=N*N;
        save = new char[N][N];
    }
    boolean bDone=false;
    int N2;//N*N
    char save[][];
    int maxBishop=0;
    int count(char[][] g)
    {
        int count=0;
        for(int i=0; i<N; i++)
            for (int j=0; j<N; j++)
                if (g[i][j]=='+')
                    count++;
        return count;
    }
    void recurse(int rc)
    {
        //out.println(rc);
        if ( rc==N2) {// || bDone
            //bDone=true;
            int c = count(board);
            if ( c>maxBishop ) {
                FashionShow.copy(save, board);
                FashionShow.print(save);
                maxBishop=c;
            }
            return;
        }
        int r = rc/N;
        int c = rc%N;
        if (fd[r+c] || bd[N-1+c-r])
            recurse(rc+1);
        else {
            board[r][c]='+';
            fd[r+c]=true;
            bd[N-1+c-r]=true;
            recurse(rc+1);
            fd[r+c]=false;
            bd[N-1+c-r]=false;
        }
    }
    int findLeastAvailRow()
    {
        int lo=Integer.MAX_VALUE;
        int rowmin=-1;
        for (int i=0; i<N;i++) {  // forward diagonal is the row
            int count=0;  // count empty on a row
            if (fd[i])  // row has a bishop
                continue;
            for (int j=i; j>=0; j--) { // backward diagonal is the column 
                if (!bd[N-1+i-2*j] )  // backward diagnoal is not attacked
                    count++;
            }
            if ( count<lo) {
                lo=count;
                rowmin=i;
            }
        }
        for (int i=N; i<2*N-1;i++) {  // forward diagonal is the row
            int count=0;  // count empty on a row
            if (fd[i])  // row has a bishop
                continue;
            for (int j=N-1; j>=i-N+1; j--) { // backward diagonal is the column
                if (!bd[N-1+i-2*j] )  // backward diagnoal is not attacked
                    count++;
            }
            if ( count<lo) {
                lo=count;
                rowmin=i;
            }
        }
        return rowmin;
    }
    void placeBishop(int r)
    {
        if (fd[r])
            return;
        if (r<N) {
            for (int j=r; j>=0; j--) {
                if (!bd[N-1+r-2*j] ) {
                    board[r][r-j]='+';
                    bd[N-1+r-2*j]=true;
                    break;
                }
            }
        } else {
            for (int j=N-1; j>=r-N+1; j--) {
                if (!bd[N-1+r-2*j] ) {
                    board[j][r-j]='+';
                    bd[N-1+r-2*j]=true;     
                    break;
                }
            }
        }
        fd[r]=true;
    }
    // rotate board 45 degree clock wise, treat biship like rook, but there are 2N-1 rows
    // row 0: (0,0)     row 1: (1,0), (0,1),... row N-1: (N-1,0) ...(0, N-1)
    // row N:  (N-1,1),...(1, N-1)
    // row N+1: (N-1, 2), ..., (2, N-1)
    // row 2N-2: (N-1, N-1)
    void greedy()
    {
        for (int i=0; i<2*N-1;i++) {
            int r = findLeastAvailRow();
            if (r<0)
                break;
            placeBishop(r);
        }
    }
    
    static void test()
    {
        Bishop b = new Bishop(new char[8][8]);
        b.greedy();
        FashionShow.print(b.board);
        b = new Bishop(new char[8][8]);
        b.recurse(0);
        FashionShow.print(b.board);
        FashionShow.print(b.save);
    }
}
