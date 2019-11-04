/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Thanh
 */
public class Rectangles extends Shape implements Draw {

    GraphicsContext gc;
    double x1; //toa do x cua tam eclipse
    double y1; //to do y cua tam eclipse
    double x2; //toa do x cua vi tri tha chuot
    double y2;  //toa do y cua vi tri tha chuot
    Rectangle rect = new Rectangle();
    String name;
    String color;
    double size;
    public Rectangles(Point start, Point end,String shapename,String color,double ssize) {
        super(start, end,shapename,color,ssize);
        this.x1 = start.X;
        this.y1 = start.Y;
        this.x2 = end.X;
        this.y2 = end.Y;
        this.name = shapename;
        this.color = color;
        this.size = ssize;
    }
    public Rectangles(){
        
    }
    @Override
    public void autodraw(Canvas canvas, Point start, Point end, String color, double size) {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(size);
        gc.setStroke(Color.web(color));
        rect.setX(start.X);
        rect.setY(start.Y);
        rect.setWidth(Math.abs((end.X - rect.getX())));
        rect.setHeight(Math.abs((end.Y - rect.getY())));
        //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
        if (rect.getX() > end.X) {
            rect.setX(end.X);
        }
        //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
        if (rect.getY() > end.Y) {
            rect.setY(end.Y);
        }
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

    }
    
    @Override
    public void freedraw(Canvas canvas, Slider tsize, ColorPicker color) {
        canvas.setCursor(Cursor.DEFAULT);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            gc.setStroke(color.getValue());
            gc.setFill(color.getValue());
            gc.setLineWidth(tsize.getValue());
            rect.setX(e.getX());
            rect.setY(e.getY());
        });
        canvas.setOnMouseDragged(e -> {
            size = tsize.getValue();
        });
        canvas.setOnMouseReleased(e -> {
            rect.setWidth(Math.abs((e.getX() - rect.getX())));
            rect.setHeight(Math.abs((e.getY() - rect.getY())));
            //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
            if (rect.getX() > e.getX()) {
                rect.setX(e.getX());
            }
            //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
            if (rect.getY() > e.getY()) {
                rect.setY(e.getY());
            }
            gc.setLineWidth(size);
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        });
    }

    @Override
    public void stopdraw(Canvas canvas) {
        gc = null;
        canvas.setOnMousePressed(e -> {
        });
        canvas.setOnMouseReleased(e -> {
        });
    }

}
