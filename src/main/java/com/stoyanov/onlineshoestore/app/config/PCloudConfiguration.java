package com.stoyanov.onlineshoestore.app.config;

import com.pcloud.sdk.ApiClient;
import com.pcloud.sdk.Authenticators;
import com.pcloud.sdk.PCloudSdk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PCloudConfiguration {

    @Value("${p_cloud.access_token}")
    private String accessToken;

    @Bean
    public ApiClient apiClient() {
        return PCloudSdk.newClientBuilder()
                .authenticator(Authenticators.newOAuthAuthenticator(accessToken))
                // Other configuration...
                .create();
    }
}
