package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String FILE = "src/connection.xml.xml";
    private static Connection con;
    //1.-crear una instancia de la propia clase
    private static ConnectionBD _instance;

    private ConnectionBD() {
        XMLmanager.readXML(new connectionproperties(), FILE);
        try{
            connectionproperties properties = null;
            con = DriverManager.getConnection(properties.getURL(),properties.getUser(),properties.getPassword());
        }catch(SQLException e){
            e.printStackTrace();
            con = null;
        }
    }
    //3.- método público que me devuelva la instancia ya creada, si la primera vez la crea
    public static Connection getConnection(){
        if(con == null){
            _instance = new ConnectionBD();
        }
        return con;
    }
}
