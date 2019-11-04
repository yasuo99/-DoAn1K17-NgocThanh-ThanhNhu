/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.Serializable;
/**
 *
 * @author Thanh
 */
public class Point implements Serializable {
    public double X;
    public double Y;
    public double Size;
    public String Type;
    public String Color;
    public String Author;
    public Point(double x,double y,double size,String type,String color,String author)
    {
        this.X = x;
        this.Y = y;
        this.Size = size;
        this.Type = type;
        this.Color = color;
        this.Author = author;
    }
    public Point getPoint()
    {
        return new Point(X,Y,Size,Type,Color,Author);
    }
}
