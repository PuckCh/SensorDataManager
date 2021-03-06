/* **************************************************
 Copyright (c) 2014, Idiap
 Hugues Salamin, hugues.salamin@idiap.ch

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.dataformatter.json.push;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ubhave.dataformatter.json.PushSensorJSONFormatter;
import com.ubhave.sensormanager.config.SensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pushsensor.ConnectionStrengthData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class ConnectionStrengthFormatter extends PushSensorJSONFormatter
{

	private final static String STRENGTH = "strength";

	public ConnectionStrengthFormatter(final Context context)
	{
		super(context, SensorUtils.SENSOR_TYPE_CONNECTION_STRENGTH);
	}

	@Override
	protected void addSensorSpecificData(JSONObject json, SensorData data)
			throws JSONException
	{
		ConnectionStrengthData connectionData = (ConnectionStrengthData) data;
		json.put(STRENGTH, connectionData.getStrength());
	}

	@Override
	public SensorData toSensorData(String jsonString)
	{
		JSONObject jsonData = super.parseData(jsonString);
		if (jsonData != null)
		{
			long dataReceivedTimestamp = super.parseTimeStamp(jsonData);
			SensorConfig sensorConfig = super.getGenericConfig(jsonData);
			ConnectionStrengthData data = new ConnectionStrengthData(
					dataReceivedTimestamp, sensorConfig);
			try
			{
				data.setStrength(((Integer) jsonData.get(STRENGTH)).intValue());

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}
		return null;
	}
}
