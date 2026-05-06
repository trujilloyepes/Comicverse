package dataAccess;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "connection")
public class connectionproperties {
    private String server;
    private String port;
    private String database;
    private String user;
    private String password;

    public connectionproperties(String server, String port, String database, String user, String password) {
        this.server = server;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public connectionproperties() {}

    public String getServer() { return server; }
    public void setServer(String server) { this.server = server; }

    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getURL() { return "jdbc:mysql://" + server + ":" + port + "/" + database; }
}

