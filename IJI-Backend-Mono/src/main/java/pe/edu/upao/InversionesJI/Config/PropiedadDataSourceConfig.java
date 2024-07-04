package pe.edu.upao.InversionesJI.Config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "pe.edu.upao.InversionesJI.MicroServiceRepository",
        entityManagerFactoryRef = "propiedadEntityManagerFactory",
        transactionManagerRef = "propiedadTransactionManager"
)
public class PropiedadDataSourceConfig {

    @Bean(name = "propiedadDataSource")
    @ConfigurationProperties(prefix = "spring.propiedad.datasource")
    public DataSource propiedadDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://micro:5432/PropiedadService");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean(name = "propiedadEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean propiedadEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("propiedadDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("pe.edu.upao.InversionesJI.MicroServiceEntity")
                .persistenceUnit("propiedad")
                .build();
    }

    @Bean(name = "propiedadTransactionManager")
    public PlatformTransactionManager propiedadTransactionManager(
            @Qualifier("propiedadEntityManagerFactory") EntityManagerFactory propiedadEntityManagerFactory) {
        return new JpaTransactionManager(propiedadEntityManagerFactory);
    }
}
