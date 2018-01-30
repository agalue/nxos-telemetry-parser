package org.opennms.telemetry.nxos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.opennms.telemetry.nxos.app.NxosTelemetryParser;
import org.opennms.telemetry.nxos.proto.TelemetryBis;

import com.google.common.io.Resources;


public class NxosTelemetryParserIT {
    
    
    @Test
    public void verifyWithoutOffset() throws IOException {
        

        byte[] nxosMsgBytes = Resources.toByteArray(Resources.getResource("nxos-proto-buf.raw"));

        TelemetryBis.Telemetry msg = NxosTelemetryParser.parseNxosTelemetryBuffer(nxosMsgBytes);
        
        assertNotNull(msg);
        
    }

    @Test
    public void verifyWithOffset() throws IOException {

        String load_avg_str = null;

        byte[] nxosMsgBytes = Resources.toByteArray(Resources.getResource("nxos-proto-buf.raw"));
        byte[] offsetBytes = new byte[nxosMsgBytes.length - 6];
        System.arraycopy(nxosMsgBytes, 6, offsetBytes, 0, offsetBytes.length);
        TelemetryBis.Telemetry msg = NxosTelemetryParser.parseNxosTelemetryBuffer(offsetBytes);

        assertNotNull(msg);
        
        
        if (!msg.getDataGpbkvList().isEmpty()) {
            if (!msg.getDataGpbkvList().get(0).getFieldsList().isEmpty()
                    && msg.getDataGpbkvList().get(0).getFieldsList().size() >= 2) {
                if (!msg.getDataGpbkvList().get(0).getFieldsList().get(1).getFieldsList().isEmpty()) {
                    if (!msg.getDataGpbkvList().get(0).getFieldsList().get(1).getFieldsList().get(0).getFieldsList()
                            .isEmpty()) {
                        if (msg.getDataGpbkvList().get(0).getFieldsList().get(1).getFieldsList().get(0).getFieldsList()
                                .get(0).getName().equals("load_avg_1min")) {
                            load_avg_str = msg.getDataGpbkvList().get(0).getFieldsList().get(1).getFieldsList().get(0)
                                    .getFieldsList().get(0).getStringValue();

                        }
                    }
                }
            }
        }
        assertEquals("1.25", load_avg_str);
    }
    
    
    
}
