package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseData;
import org.example.entity.SMRuntime;
import org.example.repository.SMRuntimeRepository;
import org.example.statemachine.StateMachineListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Component
@Slf4j
public class ExampleService {

    @Autowired
    SMRuntimeRepository smRuntimeRepository;

    @Autowired
    JpaPersistingStateMachineInterceptor<String, String, String> stateMachineRuntimePersister;

    private StateMachine<String, String> currentStateMachine;


    private final StateMachineListener listener = new StateMachineListener();

    @Autowired
    private StateMachineService<String, String> stateMachineService;

    private synchronized StateMachine<String, String> getStateMachine(String machineId) {
        listener.resetMessages();
        if (currentStateMachine == null) {
            currentStateMachine = stateMachineService.acquireStateMachine(machineId);
            currentStateMachine.addStateListener(listener);
            currentStateMachine.startReactively().block();

        } else if (!ObjectUtils.nullSafeEquals(currentStateMachine.getId(), machineId)) {
            stateMachineService.releaseStateMachine(currentStateMachine.getId());
            currentStateMachine.stopReactively().block();
            currentStateMachine = stateMachineService.acquireStateMachine(machineId);
            currentStateMachine.addStateListener(listener);
            currentStateMachine.startReactively().block();
        }
        return currentStateMachine;
    }


    public ResponseData updateStateMachine(Long id) {
        String machine = MessageFormat.format("sm_{0}", id);
        String newState = null;
        StateMachine<String, String> stateMachine = getStateMachine(machine);
        String currentState = stateMachine.getState().getId();
        log.info("pre-state: " + currentState);

        SMRuntime smDb = smRuntimeRepository.findById(machine).get();
        log.info("pre-state (DB): " + smDb.getState());

        String event = "event1";

        Mono<Message<String>> v = Mono.just(MessageBuilder
                .withPayload(event)
                .build());
        stateMachine.sendEvent(v).subscribe();

        log.info("post-state: " + newState);

        newState = stateMachine.getState().getId();
        log.info("post-state (DB): " + smDb.getState());

        return new ResponseData(newState);

    }


}
