package com.encoway.ecasta.systems;

/**
 * Represents an url of a testsystem.
 * 
 * @author azzouz
 */
public class Url {

    private String protokol;
    private String host;
    private String port;
    private String context;

    public Url(String protokol, String host, String port, String context) {
        this.protokol = protokol;
        this.host = host;
        this.port = port;
        this.context = context;
    }

    @Override
    public String toString() {
        if (port.equals("")) {
            return protokol + "://" + host + "/" + context;
        }
        return protokol + "://" + host + ":" + port + "/" + context;
    }

    public String getProtokoll() {
        return protokol;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getContext() {
        return context;
    }

    public void setProtokoll(String protokoll) {
        this.protokol = protokoll;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setContext(String context) {
        this.context = context;
    }

}
