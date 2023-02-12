package com.workmotion.employeemanagementsystem.statemachine;

import com.workmotion.employeemanagementsystem.enums.EmployeeEvents;
import com.workmotion.employeemanagementsystem.enums.EmployeeStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
public class EmployeeStateMachineListener extends StateMachineListenerAdapter<EmployeeStates, EmployeeEvents> {

    @Override
    public void stateChanged(State<EmployeeStates, EmployeeEvents> from, State<EmployeeStates, EmployeeEvents> to) {
        log.info("State Changed: From " + from + " - To " + to);
    }
}