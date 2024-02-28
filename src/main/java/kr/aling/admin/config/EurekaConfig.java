package kr.aling.admin.config;

import kr.aling.admin.common.feign.FeignClientBase;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Eureka 설정 class.
 *
 * @author 이수정
 * @since 1.0
 */
@EnableFeignClients(basePackageClasses = FeignClientBase.class)
@EnableEurekaClient
@Configuration
public class EurekaConfig {

}
