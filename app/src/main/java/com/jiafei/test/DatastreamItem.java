package com.jiafei.test;

import java.util.List;

public class DatastreamItem {
    String id;
    List<DatapointItem> datapoints;

    public String getId() {
        return id;
    }

    public List<DatapointItem> getDatapoints() {
        return datapoints;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDatapoins(List<DatapointItem> datapoints) {
        this.datapoints = datapoints;
    }
}
