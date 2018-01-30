package org.opennms.telemetry.nxos;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import org.opennms.telemetry.nxos.app.NxosTelemetryParser;
import org.opennms.telemetry.nxos.proto.TelemetryBis;

import com.google.common.io.Resources;

public class NxosTelemetryParserTest {

	@Test
	public void testJSON() throws Exception {
		byte[] bytes = Resources.toByteArray(Resources.getResource("json.bin"));
		JSONObject json = new JSONObject(new String(Arrays.copyOfRange(bytes, 6, bytes.length)));
		Assert.assertNotNull(json);
		System.out.println(json);

		Assert.assertEquals("nexus9k", json.getString("node_id_str"));
		Assert.assertEquals("4.44", json.getJSONObject("data").getString("load_avg_1min"));
	}


	@Test
	public void testGPB() throws Exception {
		byte[] bytes = Resources.toByteArray(Resources.getResource("gpb.bin"));
		final TelemetryBis.Telemetry msg =NxosTelemetryParser.parseNxosTelemetryBuffer(Arrays.copyOfRange(bytes, 6, bytes.length));
		Assert.assertNotNull(msg);
		System.out.println(msg);

		Assert.assertEquals("nexus9k", msg.getNodeIdStr());
		Assert.assertEquals("load_avg_1min", msg.getDataGpbkvList().get(0).getFields(1).getFieldsList().get(0).getFields(0).getName());
		Assert.assertEquals("1.25", msg.getDataGpbkvList().get(0).getFields(1).getFieldsList().get(0).getFields(0).getStringValue());
	}

	@Test
	public void testJSONWithoutOffset() throws Exception {
		byte[] bytes = Resources.toByteArray(Resources.getResource("json.bin"));
		JSONObject json = new JSONObject(new String(bytes));
		Assert.assertNotNull(json);
	}

	@Test
	public void testGPBWithoutOffset() throws Exception {
		byte[] bytes = Resources.toByteArray(Resources.getResource("gpb.bin"));
		final TelemetryBis.Telemetry msg =NxosTelemetryParser.parseNxosTelemetryBuffer(bytes);
		Assert.assertNotNull(msg);
	}

}
