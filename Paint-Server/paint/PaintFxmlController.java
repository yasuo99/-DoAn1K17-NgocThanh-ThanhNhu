package paint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;

public class PaintFxmlController implements Initializable {

    @FXML
    Slider tsize;
    @FXML
    Canvas canvas;
    @FXML
    ImageView imageview;
    @FXML
    Button brushButton;
    @FXML
    Button pencilButton;
    @FXML
    Button eraserButton;
    @FXML
    Button addTextButton;
    @FXML
    CheckBox checkbox;
    @FXML
    ColorPicker colorPicker;
    int countcheckboxclick = 0;
    
    List<WritableImage> undo = new ArrayList();
    Index index = new Index();

    int brushselectedcount = 0;
    int pencilselectedcount = 0;
    int eraserselectedcount = 0;
    int addtextselectedcount = 0;
    int connectselectedcount = 0;
    boolean brushselected = false;
    boolean pencilselected =false;
    boolean eraserselected = false;
    boolean addtextselected = false;
    //Các graphicscontext riêng cho từng tools
    GraphicsContext brushTool;
    GraphicsContext pencilTool;
    GraphicsContext eraserTool;
    GraphicsContext gc;
    //Khởi tạo các tools
    Brush brush = new Brush("Cọ vẽ");
    Pencil pencil = new Pencil("Bút chì");
    Eraser eraser = new Eraser("Tẩy");
    AddText addText = new AddText("Thêm chữ");
    Ellipses ellipses = new Ellipses();
    Circles circles = new Circles();
    Rectangles rect = new Rectangles();
    Lines line = new Lines();
    ArrayList list = new ArrayList<Point>();
    Index2 index2 = new Index2();
    Server server = new Server(4444,list,index2);
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            server.RunServer(canvas);                
            brushButton.setTooltip(new Tooltip(brush.toolName));
            pencilButton.setTooltip(new Tooltip(pencil.toolName));
            eraserButton.setTooltip(new Tooltip(eraser.toolName));
            addTextButton.setTooltip(new Tooltip(addText.toolName));

            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(new SnapshotParameters(), writableImage);
            undo.add(writableImage);
            index.i += 1;     
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void about(ActionEvent e) {
        Function function = new Function();
        function.About();
    }

    @FXML
    public void openfile(ActionEvent e) {
        Function function = new Function();
        function.Open(canvas);
    }

    @FXML
    public void newcanvas(ActionEvent e) {
        Function function = new Function();
        function.New(canvas);
    }

    @FXML
    public void saveas(ActionEvent e) {
        Function function = new Function();
        function.SaveAs(canvas);
    }

    @FXML
    public void exit(ActionEvent e) {
        Function function = new Function();
        function.Exit();
    }

    @FXML
    public void brushselected(ActionEvent e) {
        
        eraserselectedcount = 0;
        pencilselectedcount = 0;
        addtextselectedcount = 0;
        brushselectedcount++;
        if (brushselectedcount == 2) {
            brushselectedcount = 0;
            brush.SetCursor(canvas, brushselectedcount);
            brush.UnselectTool(canvas);
        }
        if (brushselectedcount == 1) {
            brush.SetCursor(canvas, brushselectedcount);
            brush.ToolFunction(canvas, tsize, undo, index);
        }
    }

    @FXML
    public void pencilselected(ActionEvent e) {
        eraserselectedcount = 0;
        brushselectedcount = 0;
        addtextselectedcount = 0;
        pencilselectedcount++;
        if (pencilselectedcount == 2) {
            pencilselectedcount = 0;
            pencil.SetCursor(canvas, pencilselectedcount);
            pencil.UnselectTool(canvas);
        }
        if (pencilselectedcount == 1) {
            pencil.SetCursor(canvas, pencilselectedcount);
            pencil.ToolFunction(canvas, tsize, undo, index);
        }
    }

    @FXML
    public void eraserselected(ActionEvent e) {
        pencilselectedcount = 0;
        brushselectedcount = 0;
        addtextselectedcount = 0;
        eraserselectedcount++;
        if (eraserselectedcount == 2) {
            eraserselectedcount = 0;
            eraser.SetCursor(canvas, eraserselectedcount);
            eraser.UnselectTool(canvas);
        }
        if (eraserselectedcount == 1) {
            eraser.SetCursor(canvas, eraserselectedcount);
            eraser.ToolFunction(canvas, tsize, undo, index);
        }
    }

    @FXML
    public void addtextselected(ActionEvent e) {
        addtextselectedcount++;
        brushselectedcount = 0;
        pencilselectedcount = 0;
        eraserselectedcount = 0;
        if (addtextselectedcount == 2) {
            addtextselectedcount = 0;
            addText.SetCursor(canvas, addtextselectedcount);
            addText.UnselectTool(canvas);
        }
        if (addtextselectedcount == 1) {
            addText.SetCursor(canvas, addtextselectedcount);
            addText.ToolFunction(canvas, tsize, undo, index);
        }
    }

    @FXML
    public void undoclicked(ActionEvent e) {
        Function function = new Function();
        function.Undo(canvas, undo, index);
    }

    @FXML
    public void redoclicked(ActionEvent e) {
        Function function = new Function();
        function.Redo(canvas, undo, index);
    }
    @FXML
    public void onlinechecked(ActionEvent e) throws IOException, ClassNotFoundException
    {
        countcheckboxclick++;
        if(countcheckboxclick == 2)
            countcheckboxclick = 0;       
        
    }
    int countellipse = 0;
    int countrect = 0;
    int countcircle = 0;
    int countline = 0;
    @FXML
    public void ellipseselected(ActionEvent e) {
        
        countellipse++;
        countcircle = 0;
        countline = 0;
        countrect = 0;
        if (countellipse == 1) {
            ellipses.freedraw(canvas, tsize, colorPicker);
        }
        if(countellipse == 2)
        {
            countellipse = 0;
            ellipses.stopdraw(canvas);
        }
    }

    @FXML
    public void circleselected(ActionEvent e) {
        countcircle++;
        countellipse = 0;
        countline = 0;
        countrect = 0;
        
        if(countcircle == 1)
        {
        circles.freedraw(canvas, tsize, colorPicker);
        }
        if(countcircle == 2)
        {
            countcircle = 0;
            circles.stopdraw(canvas);
        }
    }

    @FXML
    public void rectangleselected(ActionEvent e) {
        
        countrect++;
        countellipse = 0;
        countline = 0;
        countcircle = 0;
        
        if(countrect == 1)
        {
        rect.freedraw(canvas, tsize, colorPicker);
        }
        if(countrect == 2)
        {
            countrect = 0;
            rect.stopdraw(canvas);
        }
    }

    @FXML
    public void lineselected(ActionEvent e) {
        
        countline++;
        countellipse = 0;
        countcircle = 0;
        countrect = 0;
        
        if(countline == 1)
        {
        line.freedraw(canvas, tsize, colorPicker);
        }
        if(countline == 2)
        {
            countline = 0;
            line.stopdraw(canvas);
        }
    }
    }
