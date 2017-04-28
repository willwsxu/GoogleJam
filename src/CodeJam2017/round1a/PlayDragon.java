/*
 * Description:
You have Hd health points and an attack power of Ad, and the knight has Hk health points and an attack power of Ak. 
If your health drops to 0 or below at any point; you are knocked out and you instantly lose; 
if the knight's health drops to 0 or below at any point, the knight is knocked out and you win!

You will battle the knight in a series of turns. On each turn, you go first, and you can choose and execute any one of the following actions.

Attack: Reduce the opponent's health by your own attack power.
Buff: Increase your attack power by B for the rest of the battle.
Cure: Your health becomes Hd.
Debuff: Decrease the opponent's attack power by D for the rest of the battle. If a Debuff would 
 cause the opponent's attack power to become less than 0, it instead sets it to 0.
Then, if the knight's health is greater than 0 following your action, the knight will execute an Attack action. 
 After that, the turn ends. (Note that a turn in which you defeat the knight still counts as a turn even though the knight does not get to act.)

Note that buffs stack with each other; every buff adds an additional B to your attack power. Similarly, debuffs stack with each other.
Game Plan:
 if Ad > Hk, attack and kill
 if Hd <= Ak, Cure (or will be dead)
   if Hd-Ak <=Ak, must cure forever unless Debuff will cause Ad>Hk
 Debuff
 */
package CodeJam2017.round1a;

import static java.lang.System.out;
import java.util.Scanner;


public class PlayDragon {
    long Hd, Ad;    //Dragon
    long Hk, Ak;    //Knight
    long B, D;      //Buff, Debuff
    static long INF=999999999999999999L;
    PlayDragon(long hd, long ad, long hk, long ak, long b, long d)
    {
        Hd=hd;  Ad=ad;
        Hk=hk;  Ak=ak;
        B=b;    D=d;
        long s = solveSmall(Hd, Ad, Hk, Ak);
        if (s>=INF)
            out.println("IMPOSSIBLE");
        else
            out.println(s);
    }
    long solveSmall(long hd, long ad, long hk, long ak)  // 1 to 100
    {
        //out.println(hd+":"+ad+","+hk+":"+ak);
        if (ad>=hk)
            return 1;
        else if ( hd<= ak) {
            hd =Hd-ak; // cure, attacked;
            if (hd<= ak)
                return INF;
            else
                return 1+solveSmall(hd, ad, hk, ak);
        } else {
            if (2*ad>=hk)
                return 1+solveSmall(hd-ak, ad, hk-ad, ak); // only requires 1 more moves
            if (D>0 && ak>0) {
                ak -= D;
                if (ak<0)
                    ak=0;
                return 1+solveSmall(hd-ak, ad, hk, ak); // debuff
            }
            else if (B>0) {
                ad += B;
                return 1+solveSmall(hd-ak, ad, hk, ak); // Buff
            }
            else
                return 1+solveSmall(hd-ak, ad, hk-ad, ak); // attack first
        }
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //googlejam.ContestHelper.redirect("out.txt");
        //sc = googlejam.ContestHelper.getFileScanner("tests\\jam2015\\round1b\\B-large-practice.in.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            long Hd = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long Ad = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long Hk = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long Ak = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long B = sc.nextLong();  // 0 ≤ Hd ≤ 10^9
            long D = sc.nextLong();  // 0 ≤ Hd ≤ 10^9
            new PlayDragon(Hd, Ad, Hk, Ak, B, D);
        }
    }
}
