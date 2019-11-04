/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.*;
/**
 *
 * @author Thanh
 */
public class Tools {
    public String toolName;
    public Tools(){
        
    }
    public Tools(String name)
    {
        this.toolName = name;
    }
    public void SetCursor(Canvas canvas, int tcount)
    {
        
    }
    public void UnselectTool(Canvas canvas)
    {
    }
    public void ToolFunction(Canvas canvas,Slider tsize, List<WritableImage> undo, Index i,Client client,ColorPicker color,String author,boolean isConnected){
        
    }
}
