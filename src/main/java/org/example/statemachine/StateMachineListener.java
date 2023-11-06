package org.example.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class StateMachineListener extends StateMachineListenerAdapter<String,String> {

    private final LinkedList<String> messages = new LinkedList<String>();

    public List<String> getMessages() {
        return messages;
    }

    public void resetMessages() {
        messages.clear();
    }

    @Override
    public void stateContext(StateContext<String,String> stateContext) {
        log.info(MessageFormat.format(
                "State Context: {0}",
                stateContext
        ));

        if (stateContext.getStage() == StateContext.Stage.STATE_ENTRY) {
            messages.addFirst("Enter " + stateContext.getTarget().getId());
            log.info(stateContext.getStateMachine().getId() + "Enter " + stateContext.getTarget().getId());
        } else if (stateContext.getStage() == StateContext.Stage.STATE_EXIT) {
            messages.addFirst("Exit " + stateContext.getSource().getId());
            log.info(stateContext.getStateMachine().getId() + "Exit " + stateContext.getSource().getId());
        } else if (stateContext.getStage() == StateContext.Stage.STATEMACHINE_START) {
            messages.addLast("Machine started");
            log.info(stateContext.getStateMachine().getId() + "Machine started");
        } else if (stateContext.getStage() == StateContext.Stage.STATEMACHINE_STOP) {
            messages.addFirst("Machine stopped");
            log.info(stateContext.getStateMachine().getId() + "Machine stopped");
        }
    }
}
