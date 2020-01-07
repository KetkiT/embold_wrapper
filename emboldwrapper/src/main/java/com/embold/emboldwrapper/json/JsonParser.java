package com.embold.emboldwrapper.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import com.embold.emboldwrapper.exception.EmboldWrapperException;

public class JsonParser {
	private JsonObject scanSettings;
	
	public JsonParser(File scanSettingsJsonFile) throws EmboldWrapperException {
		JsonReader reader = null;
		try {
			reader = Json.createReader(new FileReader(scanSettingsJsonFile));
			scanSettings = reader.readObject();
		} catch (FileNotFoundException e) {
			throw new EmboldWrapperException("File Not found", e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	/*public JsonParser(String scanSettingsJsonString) throws EmboldWrapperException {
		try (JsonReader reader = Json.createReader(new StringReader(scanSettingsJsonString))) {
			parseJson(reader);
		}
	}*/
	
	public JsonObject getGammaAccessJsonObj(String jsonKey) throws EmboldWrapperException {
		JsonObject gammaAccessJsonObj = null;
		try {	
			gammaAccessJsonObj = JsonConfig.getJsonObjectThrow(scanSettings,jsonKey);
		} catch (JsonParsingException e) {
			throw new EmboldWrapperException("Invalid json",e);
		}
		return gammaAccessJsonObj;
	}
	
	
}
