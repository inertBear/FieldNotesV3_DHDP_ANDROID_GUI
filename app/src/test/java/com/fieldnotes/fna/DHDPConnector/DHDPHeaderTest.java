package com.fieldnotes.fna.DHDPConnector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DHDPHeaderTest {
    private final String USERNAME = "Username";
    private final DHDPOrganization ORGANIZATION = DHDPOrganization.DEVHUNTER;
    private final DHDPRequestType REQUEST_TYPE = DHDPRequestType.LOGIN;
    private final DHDPEntity ORIGINATOR = DHDPEntity.DHDPConnector;
    private final DHDPEntity RECIPIENT = DHDPEntity.DHDP;

    @Test
    public void testCreateDHDPHeaderWithAllFields() throws Exception {
        DHDPHeader header = DHDPHeader.newBuilder()
                .setCreator(USERNAME)
                .setOrganization(ORGANIZATION)
                .setRequestType(REQUEST_TYPE)
                .setOriginator(ORIGINATOR)
                .setRecipient(RECIPIENT)
                .build();

        assertEquals(USERNAME, header.getCreator());
        assertEquals(ORGANIZATION, header.getOrganization());
        assertEquals(REQUEST_TYPE, header.getRequestType());
        assertEquals(ORIGINATOR, header.getOriginator());
        assertEquals(RECIPIENT, header.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderWithNullValues() throws Exception {
        DHDPHeader.newBuilder()
                .setCreator(null)
                .setOrganization(null)
                .setRequestType(null)
                .setOriginator(null)
                .setRecipient(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderWithoutRequestType() throws Exception {
        DHDPHeader.newBuilder()
                .setCreator(USERNAME)
                .setOrganization(ORGANIZATION)
                .setOriginator(ORIGINATOR)
                .setRecipient(RECIPIENT)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderOnlyCreator() throws Exception {
        DHDPHeader.newBuilder()
                .setCreator(USERNAME)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderMissingOrganization() throws Exception {
        DHDPHeader.newBuilder()
                .setCreator(USERNAME)
                .setRequestType(REQUEST_TYPE)
                .setOriginator(ORIGINATOR)
                .setRecipient(RECIPIENT)
                .build();
    }

    @Test
    public void testToString() {
        final String expectedString = "{\"ORGANIZATION\":\"DEVHUNTER\",\"RECIPIENT\":\"DHDP\"," +
                "\"CREATOR\":\"Username\",\"REQUEST_TYPE\":\"LOGIN\",\"ORIGINATOR\":\"DHDPConnector\"}";

        DHDPHeader header = DHDPHeader.newBuilder()
                .setCreator(USERNAME)
                .setOrganization(ORGANIZATION)
                .setRequestType(REQUEST_TYPE)
                .setOriginator(ORIGINATOR)
                .setRecipient(RECIPIENT)
                .build();

        assertEquals(USERNAME, header.getCreator());
        assertEquals(ORGANIZATION, header.getOrganization());
        assertEquals(REQUEST_TYPE, header.getRequestType());
        assertEquals(ORIGINATOR, header.getOriginator());
        assertEquals(RECIPIENT, header.getRecipient());

        String actualString = header.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void getReservedKeysTest() {
        assertEquals(6, DHDPHeader.getReservedKeys().size());
    }
}
