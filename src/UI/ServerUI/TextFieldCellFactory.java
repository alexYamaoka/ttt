package UI.ServerUI;

import Shared.UserInformation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class TextFieldCellFactory implements Callback<TableColumn<UserInformation, String>, TableCell<UserInformation, String>> {
    @Override
    public TableCell<UserInformation, String> call(TableColumn<UserInformation, String> userInformationStringTableColumn) {
        TextFieldCell textFieldCell = new TextFieldCell();
        return textFieldCell;
    }

    public class TextFieldCell extends TableCell<UserInformation, String> {
        private TextField textField;
        private SimpleStringProperty boundToCurrently = null;

        public TextFieldCell() {
            String strCss;
            strCss = "-fx-padding: 0";
            this.setStyle(strCss);

            textField = new TextField();
            this.setGraphic(textField);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                ObservableValue<String> ov = getTableColumn().getCellObservableValue(getIndex());
                SimpleStringProperty sp = (SimpleStringProperty) ov;

                if(this.boundToCurrently == null) {
                    this.boundToCurrently = sp;
                    this.textField.textProperty().bindBidirectional(sp);
                } else {
                    if(this.boundToCurrently != sp) {
                        this.textField.textProperty().unbindBidirectional(this.boundToCurrently);
                        this.boundToCurrently = sp;
                        this.textField.textProperty().bindBidirectional(this.boundToCurrently);
                    }
                }
            } else {
                this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }

    }
}
