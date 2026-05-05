module org.example.comicverse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;

    opens org.example.comicverse to javafx.fxml;
    opens dataAccess to java.xml.bind;
    exports org.example.comicverse;
}