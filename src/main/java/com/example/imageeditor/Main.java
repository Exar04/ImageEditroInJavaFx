package com.example.imageeditor;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main extends Application {

    private List<Filter> filters = Arrays.asList(
            new Filter("Invert", c -> c.invert()),
            new Filter("Grayscale", c -> c.grayscale()),
            new Filter("Black and White", c -> valueOf(c) < 1.5 ? Color.BLACK : Color.WHITE),
            new Filter("Red", c -> Color.color(1.0, c.getGreen(), c.getBlue())),
            new Filter("Green", c -> Color.color(c.getRed(), 1.0, c.getBlue())),
            new Filter("Blue", c -> Color.color(c.getRed(), c.getGreen(), 1.0))
    );

    private double valueOf(Color c) {
        return c.getRed() + c.getGreen() + c.getBlue();
    }

    private Parent createContent() throws MalformedURLException {
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        // Taking input as Image

        Stage primaryStage = new Stage();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Image");

        ImageView imageView = null;
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        File file = chooser.showOpenDialog(new Stage());

        String imagePath = file.toURI().toURL().toString();
        System.out.println("File is :" + imagePath);// This line is unnecessary
//      ***********************************************************************************************************
//        Image inputImage = new Image(imagePath,true);
        ImageView view1 = new ImageView(new Image(imagePath,true));
        ImageView view2 = new ImageView();

        MenuBar bar = new MenuBar();
        Menu menu = new Menu("Filter...");


        filters.forEach(filter -> {
            MenuItem item = new MenuItem(filter.name);
            item.setOnAction(e -> {
                view2.setImage(filter.apply(view1.getImage()));

                Image outputImage = filter.apply(view1.getImage());
                File file2 = chooser.showSaveDialog(new Stage());
                if(file2 != null){
                    System.out.println(file2);
                }

            });

            menu.getItems().add(item);
        });

        Menu menu2 = new Menu("save");
        MenuItem SaveAs = new MenuItem("Save as");
        SaveAs.setOnAction(e -> {
            File file2 = chooser.showSaveDialog(new Stage());
            if(file2 != null){
//                ImageIO.write(outputImage, "jpg", file2.toURI());
                System.out.println(file2);
            }
            System.out.println("Saved");
        });
        menu2.getItems().add(SaveAs);

        MenuItem Overwrite = new MenuItem("Overwrite");
        Overwrite.setOnAction(e -> {
            System.out.println("Overwritten");
        });
        menu2.getItems().add(Overwrite);


        bar.getMenus().add(menu);
        bar.getMenus().add(menu2);

        root.setTop(bar);
        root.setCenter(new HBox(view1, view2));

        return root;
    }

    private static class Filter implements Function<Image, Image> {

        private String name;
        private Function<Color, Color> colorMap;

        Filter(String name, Function<Color, Color> colorMap) {
            this.name = name;
            this.colorMap = colorMap;
        }

        @Override
        public Image apply(Image source) {
            int w = (int) source.getWidth();
            int h = (int) source.getHeight();

            WritableImage image = new WritableImage(w, h);

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    Color c1 = source.getPixelReader().getColor(x, y);
                    Color c2 = colorMap.apply(c1);

                    image.getPixelWriter().setColor(x, y, c2);
                }
            }

            return image;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


