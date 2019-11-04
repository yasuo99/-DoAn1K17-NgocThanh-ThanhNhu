/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.Serializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

/**
 *
 * @author Thanh
 */
public class Shape implements Serializable{
    Point start;
    Point end;
    String shapename;
    String color;
    double size;
    public Shape(Point start,Point end,String shapename,String color,double size){
        this.start = start;
        this.end = end;
        this.shapename = shapename;
        this.color = color;
        this.size = size;
    }
    public Shape(){
        
    }
    public void draw(Canvas canvas,ColorPicker color,Slider size){
        
    }
    public void autodraw(Point start,Point end){
        
    }
    public void setStartPoint(Point start){
        this.start = start;
    }
    public void setEndPoint(Point end)
    {
        this.end = end;
    }
    public Point getStartPoint()
    {
        return this.start;
    }
    public Point getEndPoint(){
        return this.end;
    }
}
