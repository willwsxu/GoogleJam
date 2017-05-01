/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlejam;


public class Cylinder
{
    long r, h;
    int  id;
    double face;
    double side;
    static int uniqueID=0;
    public Cylinder(int r, int h)
    {
        id = ++uniqueID;
        this.r=r; this.h=h;
        face=Math.PI*r*r;;
        side = 2*Math.PI*r*h;
    }
    public double area()
    {
        return face+side;
    }
    public double area_side()
    {
            return side;
    }
    public long radius()
    {
        return r;
    }
    public long height()
    {
        return h;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof Cylinder) {
            Cylinder other = (Cylinder)s;
            return r==other.r && h==other.h && id ==other.id;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(r*h);
    }
    @Override
    public String toString()
    {
        return r+":"+h;
    }
}

