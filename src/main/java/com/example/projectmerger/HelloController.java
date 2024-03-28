package com.example.projectmerger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane ap_mergIi;

    @FXML
    private Button bt_fileChoser;

    @FXML
    private Button bt_findJavaClass;

    @FXML
    private Button btn_mergIt;

    @FXML
    private Label lb_filePath;

    @FXML
    private Label lbl_complateMerge;

    @FXML
    private TextField tf_newClassName;

    @FXML
    private VBox vbox_javaClasses;
    String filePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_mergIt.setVisible(false);
        bt_fileChoser.setOnMouseClicked(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose a folder");
            directoryChooser.setInitialDirectory(new java.io.File("."));
            java.io.File selectedDirectory = directoryChooser.showDialog(Main.BaseStage);
            if (selectedDirectory != null) {
                lb_filePath.setText(selectedDirectory.getAbsolutePath());
                filePath = selectedDirectory.getAbsolutePath();
                bt_findJavaClass.setVisible(true);
            }
        });
        bt_findJavaClass.setOnMouseClicked(event -> {
            JavaFileMerger.imports.clear();
            JavaFileMerger.javaFiles.clear();
            JavaFileMerger.findJavaFiles(filePath);
            vbox_javaClasses.getChildren().clear();
            vbox_javaClasses.getChildren().removeAll();
            List<File> classes = new ArrayList<>(JavaFileMerger.javaFiles);
            for(File f:classes){
                AnchorPane root = new AnchorPane();
                root.setPrefHeight(55);
                root.setPrefWidth(650);
                root.setStyle("-fx-background-color: #c1b7b7; -fx-background-radius: 15px;");

                Label label = new Label(f.getPath().toString().replace(filePath,""));
                label.setPrefHeight(42);
                label.setPrefWidth(397);
                AnchorPane.setLeftAnchor(label, 26.0);
                AnchorPane.setTopAnchor(label, 7.0);

            //    String S= Paths.get("src/main/resources/com/example/projectmerger/rec.png").toAbsolutePath().normalize().toString();
                ImageView recImageView = new ImageView(new Image(Main.class.getResource("rec.png").toExternalForm()));
                recImageView.setFitHeight(31);
                recImageView.setFitWidth(24);
                AnchorPane.setLeftAnchor(recImageView, 650.0);
                AnchorPane.setTopAnchor(recImageView, 16.0);
                recImageView.setPickOnBounds(true);
                recImageView.setPreserveRatio(true);
                recImageView.setOnMouseClicked(event1 -> {
                    classes.remove(f);
                    vbox_javaClasses.getChildren().remove(root);
                });
                root.getChildren().addAll(label, recImageView);


                vbox_javaClasses.getChildren().add(root);
            }
            ap_mergIi.setVisible(true);
            JavaFileMerger.javaFiles = classes ;
            tf_newClassName.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.length()>=1){
                    btn_mergIt.setVisible(true);

                }else{
                    btn_mergIt.setVisible(false);
                }
            });
            btn_mergIt.setOnMouseClicked(event1 -> {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Choose a destination folder");
                directoryChooser.setInitialDirectory(new java.io.File("."));
                java.io.File selectedDirectory = directoryChooser.showDialog(Main.BaseStage);
                if (selectedDirectory != null) {

                    JavaFileMerger.destinationFilePath = selectedDirectory.getAbsolutePath();

                }
                if(tf_newClassName.getText().length()>1){
                    File f =new File(JavaFileMerger.folderPath+"//"+tf_newClassName.getText()+".java");
                    if(f.exists()){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("file exist ");
                        alert.setContentText("new File Name exist !!"+"\n"+"do you want to remove Previous file\n ");

                        ButtonType yesButton = new ButtonType("Yes");
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                        alert.getButtonTypes().setAll(yesButton, noButton);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == yesButton) {
                            try {
                                Files.delete(f.toPath());
                                JavaFileMerger.mergeIt(tf_newClassName.getText());
                                lbl_complateMerge.setText("all file Merge");
                                lbl_complateMerge.setVisible(true);
                                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                alert1.setTitle("Done ");
                                alert1.setContentText("All files were successfully merged ");
                                alert1.show();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        } else if (result.get() == noButton) {
                        }
                    }else{
                    JavaFileMerger.mergeIt(tf_newClassName.getText());
                        lbl_complateMerge.setText("all file Merge");
                        lbl_complateMerge.setVisible(true);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Done ");
                        alert1.setContentText("All files were successfully merged ");
                        alert1.show();


                    }}
            });

        });

    }
}