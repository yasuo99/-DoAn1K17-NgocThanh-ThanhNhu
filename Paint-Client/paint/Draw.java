/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 *
 * @author Thanh
 */
public interface Draw {
    public void freedraw(Canvas canvas,Slider tsize,ColorPicker color,Client client,String Author,boolean isConnected);
    public void autodraw(Canvas canvas,Point start,Point end,String color,double size);
    public void stopdraw(Canvas canvas);
}
