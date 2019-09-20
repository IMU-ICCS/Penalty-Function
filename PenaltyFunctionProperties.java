package eu.melodic.vassilis.staff;


import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.*;
import org.slf4j.*;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Data
@Configuration
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.config.properties")
@Slf4j
public class PenaltyFunctionProperties {
    private final HashMap<String,String> myconfig = new HashMap<>();
}