package org.example.statemachine;

import org.springframework.statemachine.config.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class StateMachineModelFactory implements org.springframework.statemachine.config.model.StateMachineModelFactory<String, String> {

    @Override
    public StateMachineModel<String, String> build() {

        Collection<StateData<String, String>> stateDataList = new ArrayList<>();
        StateData stateData1 = new StateData<String, String>("S1", true);
        stateData1.setEnd(false);
        stateDataList.add(stateData1);

        StateData stateData2 = new StateData<String, String>("S2", false);
        stateData2.setEnd(false);
        stateDataList.add(stateData2);

        StateData stateData3 = new StateData<String, String>("S3", false);
        stateData2.setEnd(true);
        stateDataList.add(stateData2);

        StatesData<String, String> states = new StatesData<>(stateDataList);

        Collection<TransitionData<String, String>> transitionDataList = new ArrayList<>();
        transitionDataList.add(
                new TransitionData(
                        "S1",
                        "S2",
                        "event1")

        );

        transitionDataList.add(
                new TransitionData(
                        "S2",
                        "S3",
                        "event2")
        );
        TransitionsData<String, String> transitions = new TransitionsData<>(transitionDataList);

        // Configure State Machine with states and transitions
        StateMachineModel<String, String> model = new DefaultStateMachineModel<>(
                null,        //it must be null in order to inherit interceptor from default model
                states,
                transitions
        );

        return model;
    }

    @Override
    public StateMachineModel<String, String> build(String machineId) {
        return build();
    }
}
