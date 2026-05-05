module org.example.comicverse {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.comicverse to javafx.fxml;
    exports org.example.comicverse;
}