package io.github.javieravellan.reservabutacas.infra.config;

import io.github.javieravellan.reservabutacas.application.Singleton;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        basePackages = {
                "io.github.javieravellan.reservabutacas.application",
                "io.github.javieravellan.reservabutacas.infra"
        },
        includeFilters =@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Singleton.class)
)
public class LoadSingletonConfig {
}
