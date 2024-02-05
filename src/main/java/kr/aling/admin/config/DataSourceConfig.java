package kr.aling.admin.config;

import javax.sql.DataSource;
import kr.aling.admin.common.properties.DataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataSource 설정.
 *
 * @author : 이수정
 * @since : 1.0
 */
@RequiredArgsConstructor
@Configuration
public class DataSourceConfig {

    private final DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(properties.getDriver());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        dataSource.setInitialSize(properties.getInitialSize());
        dataSource.setMaxTotal(properties.getMaxTotal());
        dataSource.setMinIdle(properties.getMinIdle());
        dataSource.setMaxIdle(properties.getMaxIdle());
        dataSource.setMaxWaitMillis(properties.getMaxWait());

        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setValidationQuery(properties.getQuery());

        return dataSource;
    }
}
