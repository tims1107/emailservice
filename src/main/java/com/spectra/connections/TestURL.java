//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectra.connections;

import connections.DBList;
import connections.Servers;

import java.io.Serializable;
import java.util.List;

public class TestURL implements Serializable {
    private final String name = this.getName();

    private final String username = this.getUsername();
    private final String password = this.getPassword();
    private final String servicename = this.getServicename();
    private final String drivertype = this.getDrivertype();
    private final String protocol = this.getProtocol();
    private final String type = this.getType();
    private final String database = this.getDatabase();
    private final String uri = this.getUri();
    private final String driver = this.getDriver();
    private final String source = this.getSource();
    private final String validquery = this.getValidquery();
    private final String collection = this.getCollection();
    private final List<Servers> servers = this.getServers();
    private final int minutes = this.getMinutes();
    private final int starttime = this.getStarttime();
    private final int readtimeout = this.getReadtimeout();
    private final String env = this.getEnv();
    private final String lab = this.getLab();
    private final List<DBList> dblist = this.getDblist();

    TestURL() {
    }

    public List<DBList> getDblist() {
        return this.dblist;
    }

    public String getEnv() {
        return this.env;
    }

    public String getLab() {
        return this.lab;
    }

    public int getReadtimeout() {
        return this.readtimeout;
    }

    public int getStarttime() {
        return this.starttime;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public List<Servers> getServers() {
        return this.servers;
    }

    public String getCollection() {
        return this.collection;
    }

    public String getSource() {
        return this.source;
    }

    public String getDriver() {
        return this.driver;
    }

    public final String getUri() {
        return this.uri;
    }

    public final String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }


    public final String getUsername() {
        return this.username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getServicename() {
        return this.servicename;
    }

    public final String getDrivertype() {
        return this.drivertype;
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public String getDatabase() {
        return this.database;
    }

    public final String getValidquery() {
        return this.validquery;
    }
}

