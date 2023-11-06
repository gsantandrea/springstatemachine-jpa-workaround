package org.example.statemachine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Configuration
    @EnableStateMachineFactory
    public static class MachineConfig extends StateMachineConfigurerAdapter<String, String> {

        @Autowired
        private StateMachineModelFactory modelFactory;

        @Autowired
        private StateMachineRuntimePersister<String,String, String> stateMachineRuntimePersister;

        @Override
        public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
            config
                    .withPersistence()
                    .runtimePersister(stateMachineRuntimePersister);
        }

        @Override
        public void configure(StateMachineModelConfigurer<String,String> model) throws Exception {
            model
                    .withModel()
                    .factory(modelFactory);
        }

    }

    @Configuration
    public static class ServiceConfig {

        @Bean
        public StateMachineService<String,String> stateMachineService(
                StateMachineFactory<String,String> stateMachineFactory,
                StateMachineRuntimePersister<String,String, String> stateMachineRuntimePersister) {
            return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
        }
    }


}
