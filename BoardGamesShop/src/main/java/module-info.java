module com.example.boardgamesshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.boardgamesshop to javafx.fxml;
    exports com.example.boardgamesshop;
    exports com.example.boardgamesshop.Controllers;
    opens com.example.boardgamesshop.Controllers to javafx.fxml;
}