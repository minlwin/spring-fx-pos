package com.jdc.pos.views;

import com.jdc.pos.model.entity.Category;
import com.jdc.pos.model.service.CategoryService;
import com.jdc.pos.views.popups.CategoryEdit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.SVGPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class Categories extends AbstractController{

    @FXML
    private TilePane container;

    @Autowired
    private CategoryService service;

    @FXML
    private void initialize() {
        reload();
    }

    @FXML
    private void addNew() {
        CategoryEdit.showView(this::save);
    }

    private void save(Category category) {
        service.save(category);
        reload();
    }

    private void reload() {
        container.getChildren().clear();
        service.findAll().stream().forEach(category -> container.getChildren().add(new CategoryItem(category, this::save)));
    }

    private class CategoryItem extends HBox {

        public CategoryItem(Category category, Consumer<Category> consumer) {

            SVGPath icon = new SVGPath();
            icon.setContent("M7 7c0-1.109-0.891-2-2-2s-2 0.891-2 2 0.891 2 2 2 2-0.891 2-2zM23.672 16c0 0.531-0.219 1.047-0.578 1.406l-7.672 7.688c-0.375 0.359-0.891 0.578-1.422 0.578s-1.047-0.219-1.406-0.578l-11.172-11.188c-0.797-0.781-1.422-2.297-1.422-3.406v-6.5c0-1.094 0.906-2 2-2h6.5c1.109 0 2.625 0.625 3.422 1.422l11.172 11.156c0.359 0.375 0.578 0.891 0.578 1.422z");
            Label name = new Label();
            name.setText(category.getName());

            getChildren().addAll(icon, name);
            getStyleClass().add("category-item");

            setOnMouseClicked(event -> {

                if(event.getClickCount() == 2){
                    CategoryEdit.showView(category, consumer);
                }
            });
        }
    }
}
