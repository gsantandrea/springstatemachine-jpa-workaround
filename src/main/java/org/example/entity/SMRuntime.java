package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.springframework.statemachine.data.RepositoryStateMachine;

@Entity
@Table(name = "state_machine_table")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class SMRuntime extends RepositoryStateMachine {

    @Id
    @Column(name = "machine_id")
    private String machineId;

    @Column(name = "state")
    private String state;

    @Lob
    @Column(name = "state_machine_context", length = 10240)
    private byte[] stateMachineContext;

    @Override
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public byte[] getStateMachineContext() {
        return stateMachineContext;
    }

    public void setStateMachineContext(byte[] stateMachineContext) {
        this.stateMachineContext = stateMachineContext;
    }

}