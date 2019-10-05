package com.fieldnotes.fna.DHDPConnector;

/**
 * Holds the names of organizations registered with DHDP
 */
public enum DHDPOrganization {
    DEVHUNTER("DEVHUNTER"),
    RALPH_H_LANG_HYDROGEOLOGY("RALPH_H_LANG_HYDROGEOLOGY");

    private String value;

    DHDPOrganization(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
