package cn.blcow.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.wechat")
public class WechatProperties {

	private String appId;

	private String appSecret;

	private String oauthRedirectUrl;

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getOauthRedirectUrl() {
		return oauthRedirectUrl;
	}

	public void setOauthRedirectUrl(String oauthRedirectUrl) {
		this.oauthRedirectUrl = oauthRedirectUrl;
	}

}
