package com.embold.emboldwrapper;

import javax.json.JsonObject;

import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.json.JsonConfig;

public class GammaAccess {

	private String url;
	private String userName;
	private String password;
	private String token;
	private JsonObject gammaAccessJsonObject;


	private void parseJsonObject(JsonObject jsonObject) throws EmboldWrapperException {
		url = JsonConfig.getStringThrow(jsonObject, EmboldConstants.GA_URL);
		userName = JsonConfig.getString(jsonObject, EmboldConstants.GA_USERNAME);
		password = JsonConfig.getString(jsonObject, EmboldConstants.GA_PASSWORD);
		token = JsonConfig.getString(jsonObject, EmboldConstants.GA_TOKEN);
	}
	
	public GammaAccess(String url,String userName,String password,String accessToken) {
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.token = accessToken;
	}
	public GammaAccess(JsonObject jsonObject) throws EmboldWrapperException {
		parseJsonObject(jsonObject);
	}

	public String getToken() {
		return token;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public JsonObject getGammaAccessJsonObject() {
		return gammaAccessJsonObject;
	}

	@Override
	public String toString() {
		return "GammaAccess [url=" + url + ", userName=" + userName + ", password=" + password + "]";
	}

}
