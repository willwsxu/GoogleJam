package codejam2018.r1a;


import java.util.Arrays;

/*
 * RxC waffle, each cell may contain at most 1 chocolate chip
 * make exactly H different horizontal cuts and exactly V different vertical cuts
 * is it possible each piece has same chocolate chips
 */

public class WaffleChopper {
    // vcuts store col of V cuts, does not include last part (last col)
    boolean vcut(int sum[][], int[]hcuts, int[] vcuts, int count, int chips)
    {
        if (count==hcuts.length) { // got a complete col cuts
            //System.out.println("vcuts: "+Arrays.toString(vcuts));
            for (int i=0; i<hcuts.length; i++) {
                for (int j=0; j<vcuts.length; j++) {
                    if (sum[hcuts[i]][vcuts[j]] != chips*(j+1)*(i+1)) {
                        //System.out.println("fail "+sum[hcuts[i]][vcuts[j]]+" at "+i+" "+j);
                        return false;
                    }
                }
            }
            return true;
        }
        int target=chips*(hcuts.length+1)*(count+1);
        int lastrow=sum.length-1;
        
        for (int c=count>0?vcuts[count-1]+1:0; c<sum[0].length; c++) {
            if (sum[lastrow][c]==target) {
                vcuts[count]=c;
                if (vcut(sum, hcuts, vcuts, count+1, chips))
                    return true;
            }
            if (sum[lastrow][c]>target)
                return false;            
        }
        return false;
    }
    // hcuts store row of H cuts, does not include last part (last row)
    // count from 0 to H
    boolean hcut(int sum[][], int[]hcuts, int[]vcuts, int count, int chips)
    {
        if (count==hcuts.length) { // got a complete row cuts
            //System.out.println("hcuts: "+Arrays.toString(hcuts));
            return vcut(sum, hcuts, vcuts, 0, chips);
        }
        int target=chips*(vcuts.length+1)*(count+1);
        int lastcol=sum[0].length-1;
        //System.out.println(target);
        for (int r=count>0?hcuts[count-1]+1:0; r<sum.length; r++) {
            if (sum[r][lastcol]==target) {
                hcuts[count]=r;
                if (hcut(sum, hcuts, vcuts, count+1, chips))
                    return true;
            }
            if (sum[r][lastcol]>target)
                return false;
        }
        return false;
    }
    boolean solve(String[] chips, int H, int V) {
        int total=0;
        int sum[][]=new int[chips.length][chips[0].length()];
        for (int i=0; i<chips.length; i++) {
            for  (int j=0; j<chips[0].length(); j++) {
                sum[i][j]=0;
                if (chips[i].charAt(j)=='@') {
                    total++;
                    sum[i][j]=1;
                }
                if (i>0)
                    sum[i][j] += sum[i-1][j];
                if (j>0)
                    sum[i][j] += sum[i][j-1];
                if (i>0 && j>0)                    
                    sum[i][j] -= sum[i-1][j-1];
            }
        }
        //for (int s[]: sum)
        //    System.out.println(Arrays.toString(s));
        if (total==0)
            return true;
        int pieces=(H+1)*(V+1);
        if (total%pieces>0)
            return false;
        int chipCount=total/pieces;
        //System.out.println(chipCount);
        return hcut(sum, new int[H], new int[V], 0, chipCount);
    }
    public static void main(String[] args)  
    {
        boolean ans=new WaffleChopper().solve(new String[]{".@@..@",".....@","@.@.@@"}, 1,1);
        System.out.println(ans);
        ans=new WaffleChopper().solve(new String[]{"@@@","@.@","@.@","@@@"}, 1,1);
        System.out.println(ans==false);
        ans=new WaffleChopper().solve(new String[]{".....",".....",".....","....."}, 1,1);
        System.out.println(ans);
        ans=new WaffleChopper().solve(new String[]{"..@@","..@@","@@..","@@.."}, 1,1);
        System.out.println(ans==false);
        ans=new WaffleChopper().solve(new String[]{"@.@@","@@.@","@.@@"}, 2,2);
        System.out.println(ans);
        ans=new WaffleChopper().solve(new String[]{".@.@","@.@.",".@.@"}, 1,2);
        System.out.println(ans==false);
    }
}
