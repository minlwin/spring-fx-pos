package com.jdc.pos.views;

import com.jdc.pos.model.entity.Category;
import com.jdc.pos.model.entity.Product;
import com.jdc.pos.model.service.CategoryService;
import com.jdc.pos.model.service.ProductService;
import com.jdc.pos.views.common.Dialog;
import com.jdc.pos.views.popups.ProductEdit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class Products extends AbstractController {

    @FXML
    private ComboBox<Category> category;
    @FXML
    private TextField name;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @FXML
    private void initialize() {
        category.getItems().clear();
        category.getItems().addAll(categoryService.findAll());

        MenuItem edit = new MenuItem("Edit Product");
        edit.setOnAction(event -> {
            Product product = tableView.getSelectionModel().getSelectedItem();
            if(null != product) {
                ProductEdit.edit(product, this::save, categoryService::findAll);
            }
        });

        MenuItem changeState = new MenuItem("Change Status");
        changeState.setOnAction(event -> {
            Product product = tableView.getSelectionModel().getSelectedItem();

            Dialog.DialogBuilder.builder()
                    .title("Change Status")
                    .message(String.format("Do you want to change status of %s?", product.getName()))
                    .okActionListener(() -> {
                        product.setValid(!product.isValid());
                        productService.save(product);
                        search();
                    })
                    .build().show();
        });

        tableView.setContextMenu(new ContextMenu(edit, changeState));
    }

    @FXML
    private TableView<Product> tableView;

    @FXML
    private void search() {
        tableView.getItems().clear();
        List<Product> list = productService.search(category.getValue(), name.getText());
        tableView.getItems().addAll(list);
    }

    @FXML
    private void clear() {
        category.setValue(null);
        name.clear();
        tableView.getItems().clear();
    }

    @FXML
    private void addNew() {
        ProductEdit.addNew(this::save, categoryService::findAll);
    }

    private void save(Product product) {
        productService.save(product);
        category.setValue(product.getCategory());
        search();
    }
}
