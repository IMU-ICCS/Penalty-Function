package eu.melodic.vassilis.staff;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.config.properties")
@Slf4j
public class PenaltyFunctionProperties {
    private final Map<String, String> startupTimes = new HashMap<>();

    private final List<StateInfo> stateInfo;

    @Data
    public static class StateInfo {
        private double cores;
        private double memory;
        private double storage;

        public String toString() {
            return String.format("StateInfo(cores=%d,ram=%.2f,disk=%.2f)", (int)cores, memory, storage);
        }
    }
}