package codejam2018.qual;


import static java.lang.Math.abs;
import java.util.Scanner;

/*
 * Cubic UFO, fail both tests
 */

public class CubicUFO {
    
    static double sqrt2=Math.sqrt(2);
    static String solveSmall(double A)
    {
        double projX=A/2;
        double x=1.0/4/projX; // similar triangle
        double y=Math.sqrt(abs(.25-x*x));
        StringBuilder sb=new StringBuilder();
        sb.append(x);
        sb.append(" ");
        sb.append(y);
        sb.append(" 0\n");
        if (A>1.0)
            sb.append(-y);
        else
            sb.append(y);
        sb.append(" ");
        sb.append(x);
        sb.append(" 0\n");
        sb.append("0 0 0.5 ");
        return sb.toString();
    }
    static String solve(double A )
    {
        if (A<sqrt2 || A-sqrt2<0.000001)
            return solveSmall(A); 
        return "";
    }
    static Scanner sc = new Scanner(System.in);  
    private static void judge()
    {
        int T=sc.nextInt();
        for (int t=1; t<=T; t++) {
            double d=sc.nextDouble();
            String ans=solve(d);
            System.out.println("Case #"+t+":\n"+ans);
        }
    }
    static void test()
    {        
        System.out.println(solve(1));
        System.out.println(solve(1.414213));
        System.out.println(solve(1.21));
        System.out.println(solve(1.414214));
    }
    public static void main(String[] args)  
    {
        judge();
    }
}
