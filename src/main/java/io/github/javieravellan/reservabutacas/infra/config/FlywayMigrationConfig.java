package io.github.javieravellan.reservabutacas.infra.config;

import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Log4j2
public class FlywayMigrationConfig {
    private final DataSource dataSource;
    private boolean migrated = false;

    @Value("${spring.flyway.locations}")
    private String locations;

    public FlywayMigrationConfig(DataSource dataSource) {
        log.info("Creando FlywayMigrationConfig");
        this.dataSource = dataSource;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void migrate() {
        if (migrated) {
            log.info("Flyway ya ha migrado");
            return;
        }

        log.info("=============================");
        log.info("Inicia la migración de Flyway");
        log.info("=============================");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(locations)
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
        migrated = true;
        log.info("=============================");
        log.info("Migración de Flyway completada");
        log.info("=============================");

    }
}
