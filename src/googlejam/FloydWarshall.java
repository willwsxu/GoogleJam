/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlejam;

import static java.lang.Long.min;
import static java.lang.System.out;

public class FloydWarshall {
    protected double adjMat[][];
    protected long INF=1000000000000000000L;
    protected int N;
    public FloydWarshall(long a[][], int N)
    {
        this.N=N;
        adjMat = new double[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (a[i][j]>0)
                    adjMat[i][j]=a[i][j];
                else {
                    if (i==j)
                        adjMat[i][j]=0;
                    else
                        adjMat[i][j]=INF;
                }
            }
        }
        print(true);
        dp();
        print(true);
    }
    void dp()
    {
        for (int k=0; k<N; k++) {
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    adjMat[i][j]=Double.min(adjMat[i][j], adjMat[i][k]+adjMat[k][j]);
                }
            }
        }
    }
    public void print(boolean isLong)
    {        
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (isLong)
                    out.print((long)adjMat[i][j]+" ");
                else
                    out.print(adjMat[i][j]+" ");
            }
            out.println();
        }
    }
}
