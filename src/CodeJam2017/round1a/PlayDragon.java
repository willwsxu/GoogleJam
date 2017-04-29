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
 Debuff, Buff, Attack
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
        long s = solveSmall(Hd, Ad, Hk, Ak, INF, INF);
        if (s>=INF)
            out.println("IMPOSSIBLE");
        else {
            long maxBufUsed=buffUsed;
            long maxDeBufUsed=debuffUsed;
            long minVal=Long.MAX_VALUE;
            for (long j=0; j<=maxDeBufUsed; j++) 
            {
                long last=Long.MAX_VALUE;
                for (int i=0; i<=maxBufUsed; i++) {            
                    buffUsed=0;
                    debuffUsed=0;
                    long s1 = solveSmall(Hd, Ad, Hk, Ak, i, j);
                    //out.print("buf "+i+"-"+s1+",");
                    if ( s1>last)
                        break;
                    last = s1;
                }
                if (minVal > last)
                    minVal = last;
            }
            if (s>minVal)
                s = minVal;
            else if (minVal >s && minVal <INF)
                out.println("error maxBufUsed"+maxBufUsed+" last "+minVal+" maxDeBufUsed "+maxDeBufUsed);
            out.println(s);
        }
    }
    long buffUsed=0;
    long debuffUsed=0;
    long solveSmall(long hd, long ad, long hk, long ak, long maxB, long maxDB)  // 1 to 100
    {
        //out.println(hd+":"+ad+","+hk+":"+ak);
        if (ad>=hk)  // kill
            return 1;
        else if ( hd<= ak) {
            if ( D>0 && hd>ak-D) { // debuff
                ak -= D;
                debuffUsed++;
                return 1+solveSmall(hd-ak, ad, hk, ak, maxB, maxDB);
            } else {
                hd =Hd-ak; // cure
                if (hd<= ak)
                    return INF;
                else
                    return 1+solveSmall(hd, ad, hk, ak, maxB, maxDB);
            }
        } else {
            if (D>0 && ak>0 && debuffUsed<maxDB) {
                ak -= D;
                debuffUsed++;
                if (ak<0)
                    ak=0;
                return 1+solveSmall(hd-ak, ad, hk, ak, maxB, maxDB); // debuff
            }
            else if (B>0 && buffUsed<maxB) {
                ad += B;
                buffUsed++;
                return 1+solveSmall(hd-ak, ad, hk, ak, maxB, maxDB); // Buff
            }
            else
                return 1+solveSmall(hd-ak, ad, hk-ad, ak, maxB, maxDB); // attack first
        }
    }
    static void test()
    {
        //new PlayDragon(14, 1, 28, 8, 1, 2);// case #13 small, 14
        new PlayDragon(91, 1, 46, 45, 0, 2);// case #36 small
    }
    static Scanner sc = new Scanner(System.in);  
    public static void main(String[] args)  
    {
        //test();
        googlejam.ContestHelper.redirect("out.txt");
        sc = googlejam.ContestHelper.getFileScanner("tests\\jam2017\\round1a17\\C-small-practice.in.txt");
        
        int TC = sc.nextInt(); // 1 to 100
        for (int i=0; i<TC; i++) {
            long Hd = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long Ad = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long Hk = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long Ak = sc.nextLong();  // 1 ≤ Hd ≤ 10^9
            long B = sc.nextLong();  // 0 ≤ Hd ≤ 10^9
            long D = sc.nextLong();  // 0 ≤ Hd ≤ 10^9
            out.print("Case #"+(i+1)+": ");
            new PlayDragon(Hd, Ad, Hk, Ak, B, D);
        }
    }
}
