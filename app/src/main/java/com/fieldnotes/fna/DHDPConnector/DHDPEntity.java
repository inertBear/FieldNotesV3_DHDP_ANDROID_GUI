package com.fieldnotes.fna.DHDPConnector;

/**
 * Holds the names of entities registered with DHDP
 */
public enum DHDPEntity {
    DHDP("DHDP"),
    DHDPConnector("DHDPConnector"),
    FieldNotes("FieldNotes");

    private String value;

    DHDPEntity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
