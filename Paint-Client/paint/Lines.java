/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import com.sun.javafx.font.FontResource;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Thanh
 */
public class Lines extends Shape implements Draw {

    double x1;
    double y1;
    double x2;
    double y2;
    String name;
    String color;
    String author;
    GraphicsContext gc;
    Line line = new Line();
    Point startpoint;
    Point endpoint;

    public Lines(Point start, Point end, String name, String color, double ssize,String Author) {
        super(start, end, name, color, ssize,Author);
        this.x1 = start.X;
        this.y1 = start.Y;
        this.x2 = end.X;
        this.y2 = end.Y;
        this.name = name;
        this.color = color;
        this.size = ssize;
        this.author = Author;
    }

    public Lines() {

    }

    @Override
    public void autodraw(Canvas canvas, Point start, Point end, String color, double size) {
        gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.web(color));
        gc.setLineWidth(size);
        line.setStartX(start.X);
        line.setStartY(start.Y);

        line.setEndX(end.X);
        line.setEndY(end.Y);
        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

    }
    double size;

    @Override
    public void freedraw(Canvas canvas, Slider tsize, ColorPicker color, Client client,String author,boolean isConnected) {
        canvas.setCursor(Cursor.DEFAULT);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            gc.setStroke(color.getValue());
            gc.setLineWidth(tsize.getValue());
            line.setStartX(e.getX());
            line.setStartY(e.getY());
            startpoint = new Point(e.getX(), e.getY(), size, "line", color.getValue().toString(),author);
        });
        canvas.setOnMouseDragged(e -> {
            size = tsize.getValue();
        });
        canvas.setOnMouseReleased(e -> {
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            endpoint = new Point(e.getX(), e.getY(), size, "line", color.getValue().toString(),author);
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            Shape shape = new Shape(startpoint, endpoint, "line", color.getValue().toString(), size,author);
            if (size > 0 && isConnected) {
                try {
                    client.SendObject(endpoint);
                    client.SendObject2(shape);
                } catch (IOException ex) {
                    Logger.getLogger(Lines.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
