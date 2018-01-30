package org.opennms.telemetry.nxos.app;

import org.opennms.telemetry.nxos.proto.TelemetryBis;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.InvalidProtocolBufferException;

public class NxosTelemetryParser {
    private static final ExtensionRegistry s_registry = ExtensionRegistry.newInstance();

    static {
        TelemetryBis.registerAllExtensions(s_registry);
    }

    public static  TelemetryBis.Telemetry parseNxosTelemetryBuffer(byte[] bs) {
        TelemetryBis.Telemetry msg = null;
        try {
            msg = TelemetryBis.Telemetry.parseFrom(bs, s_registry);
            System.out.printf("Telemetry buffer contents : node_id = %s,  collectionId = %d", msg.getNodeIdStr(),
                    msg.getCollectionId());
        } catch (InvalidProtocolBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }
}
