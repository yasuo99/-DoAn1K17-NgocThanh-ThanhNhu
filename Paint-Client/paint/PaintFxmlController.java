package paint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

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
    TextField userName;
    @FXML
    Button onlineButton;
    @FXML
    ColorPicker colorPicker;
    List<WritableImage> undo = new ArrayList();
    Index index = new Index();

    int brushselectedcount = 0;
    int pencilselectedcount = 0;
    int eraserselectedcount = 0;
    int addtextselectedcount = 0;
    int connectselectedcount = 0;
    boolean brushselected = false;
    boolean pencilselected = false;
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
    Client client;
    Ellipses ellipses = new Ellipses();
    Circles circles = new Circles();
    Rectangles rect = new Rectangles();
    Lines line = new Lines();
    boolean isConnected = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            brushButton.setTooltip(new Tooltip(brush.toolName));
            pencilButton.setTooltip(new Tooltip(pencil.toolName));
            eraserButton.setTooltip(new Tooltip(eraser.toolName));
            addTextButton.setTooltip(new Tooltip(addText.toolName));

            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(new SnapshotParameters(), writableImage);
            undo.add(writableImage);
            index.i += 1;
//            brushTool = canvas.getGraphicsContext2D();
//            BoxBlur blur = new BoxBlur();
//        blur.setWidth(1);
//        blur.setHeight(1);
//        blur.setIterations(1);
//        brushTool.setEffect(blur);   
//            brushTool.setStroke(Color.BLACK);
//            canvas.setOnMouseDragged(e -> {
//                double size = tsize.getValue();
//                double x = e.getX() - size;
//                double y = e.getY() - size;
//                brushTool.setFill(Color.BLACK);
//                brushTool.fillRoundRect(x, y, size * 2, size * 2, size * 2, size * 2);               
//            });                
            canvas.setOnMouseDragged(e -> {

            });
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
    public void brushselected(ActionEvent e) throws IOException, ClassNotFoundException {
        eraserselectedcount = 0;
        pencilselectedcount = 0;
        addtextselectedcount = 0;
        brushselectedcount++;
        if (brushselectedcount == 2) {
            brushselectedcount = 0;
            brush.SetCursor(canvas, brushselectedcount);
            brush.UnselectTool(canvas);
            brushselected = false;
        }
        if (brushselectedcount == 1) {
            brushselected = true;
            pencilselected = false;
            eraserselected = false;
            addtextselected = false;
            brush.SetCursor(canvas, brushselectedcount);
            brush.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
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
            pencilselected = false;
        }
        if (pencilselectedcount == 1) {
            brushselected = false;
            pencilselected = true;
            eraserselected = false;
            addtextselected = false;
            pencil.SetCursor(canvas, pencilselectedcount);
            pencil.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
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
            eraserselected = false;
        }
        if (eraserselectedcount == 1) {
            eraserselected = true;
            pencilselected = false;
            eraser.SetCursor(canvas, eraserselectedcount);
            eraser.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
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
            addText.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
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
    public void connectselected(ActionEvent e) throws IOException {
        connectselectedcount++;
        if (userName.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thiếu thông tin kết nối");
            alert.setContentText("Vui lòng điền username của bạn vào!");
            alert.showAndWait();
        } else {           
            if (connectselectedcount == 1) {
                client = new Client("localhost", 4444);
                isConnected = true;
                try {
                    client.Connect();
                    if (brushselected) {
                        brush.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                    }
                    if (pencilselected) {
                        pencil.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                    }
                    if (eraserselected) {
                        eraser.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                    }
                    if (addtextselected) {
                        addText.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                    }
                    if (lineselect) {
                        line.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                    }
                    if (rectselect) {
                        rect.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                    }
                    if (ellipseselect) {
                        ellipses.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                    }
                    if (circleselect) {
                        circles.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                    }
                    if (client.isConnected) {
                        onlineButton.setText("Disconnect");
                    }
                    else{
                        connectselectedcount = 0;
                    }
                } catch (IOException ex) {
                    connectselectedcount = 0;
                }
            }
            if (connectselectedcount == 2) {
                onlineButton.setText("Connect");
                Point x = new Point(0, 0, 0, "disconnect", "", "");
                client.SendObject(x);
                client.Disconnect();
                connectselectedcount = 0;
                client.isConnected = false;
                isConnected = false;
                if (brushselected) {
                    brush.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                }
                if (pencilselected) {
                    pencil.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                }
                if (eraserselected) {
                    eraser.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                }
                if (addtextselected) {
                    addText.ToolFunction(canvas, tsize, undo, index, client, colorPicker, userName.getText(),isConnected);
                }
                if (lineselect) {
                    line.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                }
                if (rectselect) {
                    rect.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                }
                if (ellipseselect) {
                    ellipses.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                }
                if (circleselect) {
                    circles.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
                }
            }
        }
    }
    int countellipse = 0;
    int countrect = 0;
    int countcircle = 0;
    int countline = 0;
    boolean ellipseselect = false;
    boolean rectselect = false;
    boolean circleselect = false;
    boolean lineselect = false;

    @FXML
    public void ellipseselected(ActionEvent e) {

        countellipse++;
        countcircle = 0;
        countline = 0;
        countrect = 0;
        if (countellipse == 1) {
            ellipses.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
            ellipseselect = true;
            rectselect = false;
            circleselect = false;
            lineselect = false;
        }
        if (countellipse == 2) {
            countellipse = 0;
            ellipseselect = false;
            ellipses.stopdraw(canvas);
        }
    }

    @FXML
    public void circleselected(ActionEvent e) {
        countcircle++;
        countellipse = 0;
        countline = 0;
        countrect = 0;

        if (countcircle == 1) {
            circles.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
            ellipseselect = false;
            rectselect = false;
            circleselect = true;
            lineselect = false;
        }
        if (countcircle == 2) {
            countcircle = 0;
            circleselect = false;
            circles.stopdraw(canvas);
        }
    }

    @FXML
    public void rectangleselected(ActionEvent e) {

        countrect++;
        countellipse = 0;
        countline = 0;
        countcircle = 0;

        if (countrect == 1) {
            ellipseselect = false;
            rectselect = true;
            circleselect = false;
            lineselect = false;
            rect.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
        }
        if (countrect == 2) {
            countrect = 0;
            rectselect = false;
            rect.stopdraw(canvas);
        }
    }

    @FXML
    public void lineselected(ActionEvent e) {

        countline++;
        countellipse = 0;
        countcircle = 0;
        countrect = 0;

        if (countline == 1) {
            ellipseselect = false;
            rectselect = false;
            circleselect = true;
            lineselect = true;
            line.freedraw(canvas, tsize, colorPicker, client, userName.getText(),isConnected);
        }
        if (countline == 2) {
            countline = 0;
            lineselect = false;
            line.stopdraw(canvas);
        }
    }
}
