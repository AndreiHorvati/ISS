module com.example.monitorizareangajati {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.monitorizareangajati to javafx.fxml;
    exports com.example.monitorizareangajati;
    exports com.example;
    opens com.example to javafx.fxml;
}