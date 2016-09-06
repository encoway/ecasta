package com.encoway.ecasta.systems.custom.ui;

import com.encoway.ecasta.features.custom.ui.ImageButton;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.controller.NavigationViewController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * custom cell for the navigationview.
 * 
 * @author azzouz
 */
public class TestsystemListCell extends ListCell<Testsystem> {

    private HBox hbox = new HBox();
    private Label label = new Label("empty");
    private Pane pane = new Pane();
    private Button deleteButton;
    private Button editButton;
    private Testsystem lastItem;
    private NavigationViewController listener;

    public TestsystemListCell(NavigationViewController listener) {
        super();
        this.listener = listener;
        initButton();
        initEditButton();
        hbox.getChildren().addAll(label, pane, editButton, deleteButton);
        initDoubleClick();
        HBox.setHgrow(pane, Priority.ALWAYS);

    }

    private void initDoubleClick() {
        addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    listener.doubleClicked(lastItem.getId());
                }
            }
        });
    }

    private void initButton() {
        deleteButton = new ImageButton("icons/trash.png");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                listener.deleteButtonClicked(lastItem.getId());
            }
        });

    }

    private void initEditButton() {
        editButton = new ImageButton("icons/edit.png");
        editButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                listener.editButtonClicked(lastItem.getId());
            }
        });
    }

    @Override
    protected void updateItem(Testsystem item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            label.setText(item.getName() != null ? item.getName() : "<null>");
            setGraphic(hbox);
        }
    }

}
