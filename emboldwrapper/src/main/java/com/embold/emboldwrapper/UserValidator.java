package com.embold.emboldwrapper;

import org.apache.commons.lang.StringUtils;

import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UserValidator {
	
 private String token;
 
	public void validateUser(GammaAccess gammaAccess) throws EmboldWrapperException {

		HttpResponse<JsonNode> response = null;
		if (StringUtils.isBlank(gammaAccess.getToken())) {
			try {
				String url = gammaAccess.getUrl()+EmboldConstants.BASE_URL+EmboldConstants.GAMMA_LOGIN_API;
				response = Unirest.post(url)
						.header("accept", "*/*")
						.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
						.field("username", gammaAccess.getUserName()).field("password", gammaAccess.getPassword()).asJson();
				if (200 == response.getStatus()) {
					token = response.getBody().getObject().getString("token");
				}
			} catch (UnirestException e) {
				throw new EmboldWrapperException("Unable to connect to Gamma.", e);
			}
		}else {
			token = gammaAccess.getToken();
		}
	}
	
	public String getValidAccessToken() {
		return token;
	}
	
}