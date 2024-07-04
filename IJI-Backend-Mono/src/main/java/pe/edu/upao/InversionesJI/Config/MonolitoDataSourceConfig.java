package pe.edu.upao.InversionesJI.Config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "pe.edu.upao.InversionesJI.Repository",
        entityManagerFactoryRef = "monolitoEntityManagerFactory",
        transactionManagerRef = "monolitoTransactionManager"
)
public class MonolitoDataSourceConfig {

    @Primary
    @Bean(name = "monolitoDataSource")
    @ConfigurationProperties(prefix = "spring.monolito.datasource")
    public DataSource monolitoDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://monolito:5432/InversionesJI");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Primary
    @Bean(name = "monolitoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean monolitoEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("monolitoDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("pe.edu.upao.InversionesJI.Entity")
                .persistenceUnit("monolito")
                .build();
    }

    @Primary
    @Bean(name = "monolitoTransactionManager")
    public PlatformTransactionManager monolitoTransactionManager(
            @Qualifier("monolitoEntityManagerFactory") EntityManagerFactory monolitoEntityManagerFactory) {
        return new JpaTransactionManager(monolitoEntityManagerFactory);
    }
}