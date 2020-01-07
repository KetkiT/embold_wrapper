package com.embold.emboldwrapper.json;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;

import com.embold.emboldwrapper.exception.EmboldWrapperException;

/**
 * Wrapper for JSON parsing
 * 
 *
 */
public class JsonConfig {
	public static String getStringThrow(JsonObject object, String key) throws EmboldWrapperException {
		if (!object.containsKey(key)) {
			throw new EmboldWrapperException("Json Format error : key: " + key + " not found in json");
		}

		return object.getString(key);
	}

	public static String getString(JsonObject object, String key) throws EmboldWrapperException {
		return object.containsKey(key) ? object.getString(key) : null;
	}

	public static int getInt(JsonObject object, String key, int defaultVal) throws EmboldWrapperException {
		return object.containsKey(key) ? object.getInt(key) : defaultVal;
	}

	public static boolean getBoolean(JsonObject object, String key, boolean defaultVal) throws EmboldWrapperException {
		return object.containsKey(key) ? object.getBoolean(key) : defaultVal;
	}

	public static String[] getStringArrayThrow(JsonObject object, String key) throws EmboldWrapperException {
		if (!object.containsKey(key)) {
			throw new EmboldWrapperException("Json Format error :key: " + key + " not found in json");
		}

		return jsonArrToStrArr(object.getJsonArray(key));
	}

	public static List<String> getStringListThrow(JsonObject object, String key) throws EmboldWrapperException {
		if (!object.containsKey(key)) {
			throw new EmboldWrapperException("Json Format error : key: " + key + " not found in rules json");
		}

		JsonArray arr = object.getJsonArray(key);

		List<String> result = new ArrayList<String>();

		for (int i = 0; i < arr.size(); ++i) {
			result.add(arr.getString(i));
		}
		return result;
	}
	
	public static String[] getStringArray(JsonObject object, String key) throws EmboldWrapperException {
		String[] result = null;
		if (object.containsKey(key)) {
			result = jsonArrToStrArr(object.getJsonArray(key));
		}

		return result;
	}

	public static JsonObject getJsonObjectThrow(JsonObject object, String key) throws EmboldWrapperException {
		if (!object.containsKey(key)) {
			throw new EmboldWrapperException("Json Format error :key: " + key + " not found in json");
		}

		return object.getJsonObject(key);
	}

	public static JsonObject getJsonObject(JsonObject object, String key) throws EmboldWrapperException {
		return object.containsKey(key) ? object.getJsonObject(key) : null;
	}

	
	public static JsonArray getJsonArrayThrow(JsonObject object, String key) throws EmboldWrapperException {
		if (!object.containsKey(key)) {
			throw new EmboldWrapperException("Json Format error : key: " + key + " not found in json");
		}

		return object.getJsonArray(key);
	}

	private static String[] jsonArrToStrArr(JsonArray arr) {
		String[] result = new String[arr.size()];

		for (int i = 0; i < arr.size(); ++i) {
			result[i] = arr.getString(i);
		}
		return result;
	}
}
