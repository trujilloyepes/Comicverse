package dataAccess;

public class connectionproperties {
    private String server;
    private String port;
    private String database;
    private String user;
    private String password;

    public connectionproperties(String servere, String port, String database, String user, String password) {
        this.server = server;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;

    }

    public connectionproperties() {

    }

    public String getUser() {return user;}
    public String getPassword() {return password;}
    public String getURL(){return "jdbc:mysql://"+server+":"+port+"/"+database;}
}

