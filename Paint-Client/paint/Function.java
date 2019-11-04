/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Thanh
 */
public class Function {

    @FXML
    GraphicsContext gc;

    public void Open(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        Stage stage = new Stage();
        stage.setTitle("Open file");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpeg
                = new FileChooser.ExtensionFilter("jpg files (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpeg, extFilterPNG, extFilterpng);
        File file = fileChooser.showOpenDialog(stage);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            gc.drawImage(image, 0, 0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void New(Canvas canvas) {
        Text text = new Text();
        text.setText("Save file before create new one?");
        Button yes = new Button();
        yes.setText("Yes");
        Button no = new Button();
        no.setText("No");

        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(text, yes, no);

        Stage createStage = new Stage();
        createStage.setTitle("Save file before create new one?");
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefHeight(50);
        root.getChildren().add(hbox);
        Scene canvasScene = new Scene(root);
        createStage.setTitle("Warning");
        createStage.setScene(canvasScene);
        createStage.show();

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SaveAs(canvas);
                createStage.close();
                gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
            }
        });
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
                createStage.close();
            }
        });

    }

    public void SaveAs(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Save file");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void About() {
        Text author = new Text("Đội ngũ thực hiện: Luyện Ngọc Thanh, Nguyễn Thành Như.");
        author.setFont(Font.font("Time New Roman", 15));
        Text copyright = new Text("Sản phẩm đã được đăng kí ISO: 9001:2020.");
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(author, copyright);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefHeight(200);
        root.getChildren().add(vbox);
        Scene canvasScene = new Scene(root);
        createStage.setTitle("About");
        createStage.setScene(canvasScene);
        createStage.show();
    }

    public void Undo(Canvas canvas, List<WritableImage> undo, Index i) {
        gc = canvas.getGraphicsContext2D();
        if (i.i > 0) {
            i.i--;
            System.out.println(i.i);           
            WritableImage img = undo.get(i.i);
            gc.drawImage(img, 0, 0);
        }
    }

    public void Redo(Canvas canvas, List<WritableImage> undo, Index i) {
        gc = canvas.getGraphicsContext2D();
        if (i.i >= 0) {
            i.i++;
            if (i.i >= undo.size()) {
                i.i--;
            } else {
                WritableImage img = undo.get(i.i);
                gc.drawImage(img, 0, 0);
            }
        }
    }

    public void Exit() {
        System.exit(0);
    }

    public void StoreImage(Canvas canvas, List<WritableImage> undo) {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(new SnapshotParameters(), writableImage);
        undo.add(writableImage);
    }

    public int CountStore(List<WritableImage> undo) {
        return undo.size();
    }
}
