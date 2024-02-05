package kr.aling.admin.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DataSource 설정 Properties.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "aling.mysql")
public class DataSourceProperties {

    private String driver;
    private String url;
    private String username;
    private String password;
    private Integer initialSize;
    private Integer maxTotal;
    private Integer minIdle;
    private Integer maxIdle;
    private Integer maxWait;
    private String query;
}
