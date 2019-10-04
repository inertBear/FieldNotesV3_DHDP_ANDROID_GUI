package com.fieldnotes.fna.DHDPConnector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DHDPHeaderTest {

    @Test
    public void testCreateDHDPHeaderWithAllFields() throws Exception {
        final String username = "Username";
        final String organization = "DevHunter";
        final DHDPRequestType requestType = DHDPRequestType.LOGIN;
        final String originator = "DHDPConnector";
        final String recipient = "DHDP";

        DHDPHeader header = DHDPHeader.newBuilder()
                .setCreator(username)
                .setOrganization(organization)
                .setRequestType(requestType)
                .setOriginator(originator)
                .setRecipient(recipient)
                .build();

        assertEquals(username, header.getCreator());
        assertEquals(organization, header.getOrganization());
        assertEquals(requestType, header.getRequestType());
        assertEquals(originator, header.getOriginator());
        assertEquals(recipient, header.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderWithNullValues() throws Exception {
        final String username = null;
        final String organization = null;
        final DHDPRequestType requestType = null;
        final String originator = null;
        final String recipient = null;

        DHDPHeader.newBuilder()
                .setCreator(username)
                .setOrganization(organization)
                .setRequestType(requestType)
                .setOriginator(originator)
                .setRecipient(recipient)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderWithoutRequestType() throws Exception {
        final String username = "Username";
        final String organization = "DevHunter";
        final String originator = "DHDPConnector";
        final String recipient = "DHDP";

        DHDPHeader.newBuilder()
                .setCreator(username)
                .setOrganization(organization)
                .setOriginator(originator)
                .setRecipient(recipient)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderOnlyCreator() throws Exception {
        DHDPHeader.newBuilder()
                .setCreator("Username")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDHDPHeaderMissingOrganization() throws Exception {
        final String username = "Username";
        final DHDPRequestType requestType = DHDPRequestType.LOGIN;
        final String originator = "DHDPConnector";
        final String recipient = "DHDP";

        DHDPHeader.newBuilder()
                .setCreator(username)
                .setRequestType(requestType)
                .setOriginator(originator)
                .setRecipient(recipient)
                .build();
    }

    @Test
    public void testToString() {
        final String username = "Username";
        final String organization = "DevHunter";
        final DHDPRequestType requestType = DHDPRequestType.LOGIN;
        final String originator = "DHDPConnector";
        final String recipient = "DHDP";
        final String expectedString = "{\"ORGANIZATION\":\"DevHunter\",\"RECIPIENT\":\"DHDP\"," +
                "\"CREATOR\":\"Username\",\"REQUEST_TYPE\":\"LOGIN\",\"ORIGINATOR\":\"DHDPConnector\"}";

        DHDPHeader header = DHDPHeader.newBuilder()
                .setCreator(username)
                .setOrganization(organization)
                .setRequestType(requestType)
                .setOriginator(originator)
                .setRecipient(recipient)
                .build();

        assertEquals(username, header.getCreator());
        assertEquals(organization, header.getOrganization());
        assertEquals(requestType, header.getRequestType());
        assertEquals(originator, header.getOriginator());
        assertEquals(recipient, header.getRecipient());

        String actualString = header.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void getReservedKeysTest() {
        assertEquals(6, DHDPHeader.getReservedKeys().size());
    }
}
