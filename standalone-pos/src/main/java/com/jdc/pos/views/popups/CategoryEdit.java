package com.jdc.pos.views.popups;

import com.jdc.pos.model.PosException;
import com.jdc.pos.model.entity.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.function.Consumer;

public class CategoryEdit {

    @FXML
    private Label title;
    @FXML
    private Label message;
    @FXML
    private TextField name;

    private Consumer<Category> saveHandler;
    private Category category;

    public static void showView(Consumer<Category> saveHandler) {
        showView(null, saveHandler);
    }

    public static void showView(Category category, Consumer<Category> saveHandler) {
        try {

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(CategoryEdit.class.getResource("CategoryEdit.fxml"));
            stage.setScene(new Scene(loader.load()));

            CategoryEdit edit = loader.getController();
            edit.init(category, saveHandler);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(Category category, Consumer<Category> saveHandler) {
        this.saveHandler = saveHandler;

        if(null == category) {
            this.category = new Category();
            this.title.setText("Add New Category");
        } else {
            this.category = category;
            this.title.setText("Edit Category");
            this.name.setText(category.getName());
        }

    }

    @FXML
    private void close() {
        name.getScene().getWindow().hide();
    }

    @FXML
    private void save() {

        try {
            category.setName(name.getText());
            saveHandler.accept(category);
            close();
        } catch (PosException e) {
            message.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
