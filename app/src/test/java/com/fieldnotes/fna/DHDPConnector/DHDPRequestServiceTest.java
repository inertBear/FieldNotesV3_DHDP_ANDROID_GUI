package com.fieldnotes.fna.DHDPConnector;

import com.fieldnotes.fna.ExampleImpl.model.FieldNote;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DHDPRequestServiceTest {

    @Test
    public void mergeHeaderAndBodyTest() throws Exception {
        DHDPHeader header = DHDPHeader.newBuilder()
                .setCreator("USER")
                .setOrganization("DEVHUNTER")
                .setRequestType(DHDPRequestType.LOGIN)
                .setOriginator("GUI")
                .setRecipient("DHDP")
                .build();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("USERNAME", "USER1");
        bodyMap.put("PASSWORD", "PASSWORD1");
        DHDPBody body = new FieldNote(bodyMap);

        JSONObject requestObject = DHDPRequestService.getInstance().merge(header, body);
        //TODO: assert
    }
}