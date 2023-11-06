package org.example.statemachine;

import repositoryworkaround.JpaStateMachineRepositoryClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;

@Configuration
public class StateMachinePersistenceConfig {

    @Bean
    public JpaStateMachineRepositoryClass workaroundBean() {
        return new JpaStateMachineRepositoryClass();
    }

    @Bean
    public JpaPersistingStateMachineInterceptor<String,String, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

}
