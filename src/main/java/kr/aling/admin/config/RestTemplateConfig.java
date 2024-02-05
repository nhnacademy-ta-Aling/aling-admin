package kr.aling.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 다른 서버와 REST-API 통신하기 위한 RestTemplate 설정
 *
 * @author : 이수정
 * @since : 1.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplate에 적용할 ClientHttpRequestFactory Bean.
     *
     * @return RestTemplate에 적용할 ClientHttpRequestFactory
     * @author : 이수정
     * @since : 1.0
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(50_000);
        factory.setReadTimeout(10_000);

        return factory;
    }

    /**
     * RestTemplate Bean.
     *
     * @param clientHttpRequestFactory
     * @return RestTemplate
     * @author : 이수정
     * @since : 1.0
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }
}
