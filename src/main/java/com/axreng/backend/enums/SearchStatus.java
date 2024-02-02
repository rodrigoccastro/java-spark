package com.axreng.backend.enums;

public enum SearchStatus {
    ACTIVE("active"),
    DONE("done");

    private final String label;

    SearchStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}