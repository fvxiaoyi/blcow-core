package cn.blcow.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cos")
public class COSProperties {
	private String accessKey;
	private String secretKey;
	private String regionName;
	private String simpleRegionName;
	private String defaultBucketName;

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getDefaultBucketName() {
		return defaultBucketName;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public void setDefaultBucketName(String defaultBucketName) {
		this.defaultBucketName = defaultBucketName;
	}

	public String getSimpleRegionName() {
		return simpleRegionName;
	}

	public void setSimpleRegionName(String simpleRegionName) {
		this.simpleRegionName = simpleRegionName;
	}

}
