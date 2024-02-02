package com.projectrefocus.service.graph.dto;

import com.projectrefocus.service.dundas.enums.GraphType;

public class Graph {

    private String url;
    private GraphType type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GraphType getType() {
        return type;
    }

    public void setType(GraphType type) {
        this.type = type;
    }
}