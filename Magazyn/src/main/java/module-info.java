module com.example.magazyn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens com.example.magazyn to javafx.fxml;
    exports com.example.magazyn;
}