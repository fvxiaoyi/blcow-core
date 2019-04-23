package cn.blcow.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import cn.blcow.core.properties.COSProperties;

@Configuration
@EnableConfigurationProperties({ COSProperties.class })
public class COSClientConfig {

	@Bean(destroyMethod = "shutdown")
	public COSClient COSClient(COSProperties properties) {
		COSCredentials cred = new BasicCOSCredentials(properties.getAccessKey(), properties.getSecretKey());
		ClientConfig clientConfig = new ClientConfig(new Region(properties.getRegionName()));
		COSClient cosclient = new COSClient(cred, clientConfig);
		return cosclient;
	}
}
