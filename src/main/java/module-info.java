module AutoLoad {
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires javafx.controls;

    opens com.leisure.autoload.controller to javafx.fxml;
    exports com.leisure.autoload;
}
