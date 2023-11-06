package repositoryworkaround;


import org.example.entity.SMRuntime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.example.repository.SMRuntimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachine;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//This wrapper class is a temporary workaround for the following problem:
// https://github.com/spring-projects/spring-statemachine/issues/511
// (it should be solved in the next version of Spring StateMachine...)
@Slf4j
public class JpaStateMachineRepositoryClass implements JpaStateMachineRepository {

    @Autowired
    SMRuntimeRepository repo;

    public JpaStateMachineRepositoryClass() {
        log.info("************************************ new");

    }


        @Override
    public <S extends JpaRepositoryStateMachine> S save(S entity) {
        log.info("************************************ save");
        repo.save(JRSMtoSMRuntimeMapper(entity));
        return entity;
    }

    @Override
    public <S extends JpaRepositoryStateMachine> Iterable<S> saveAll(Iterable<S> entities) {
        log.info("************************************ saveAll");

        for (S item : entities) {
            log.info("************************************ saveAll -> saving entity ...");
            repo.save(JRSMtoSMRuntimeMapper(item));
        }

        return entities;
    }

    @Override
    public Optional<JpaRepositoryStateMachine> findById(String s) {
        log.info("************************************ findById");
        Optional<SMRuntime> v = repo.findById(s);
        return v.map((e) -> SMRuntimeToJRSMMapper(e));
    }

    @Override
    public boolean existsById(String s) {
        log.info("************************************ existsById");
        return repo.existsById(s);
    }

    @Override
    public Iterable<JpaRepositoryStateMachine> findAll() {
        log.info("************************************ findAll");
        Iterable<SMRuntime> smrResult = repo.findAll();
        List<JpaRepositoryStateMachine> result = new ArrayList<>();
        for (SMRuntime item : smrResult) {
            result.add(SMRuntimeToJRSMMapper(item));
        }
        return  result;
    }

    @Override
    public Iterable<JpaRepositoryStateMachine> findAllById(Iterable<String> strings) {
        log.info("************************************ findAllById");
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        log.info("************************************ count");
        return repo.count();
    }

    @Override
    public void deleteById(String s) {
        log.info("************************************ deleteById");
        repo.deleteById(s);
    }

    @Override
    public void delete(JpaRepositoryStateMachine entity) {
        log.info("************************************ delete");
        repo.delete(JRSMtoSMRuntimeMapper(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        log.info("************************************ deleteAllById");
        throw new NotImplementedException();

    }

    @Override
    public void deleteAll(Iterable<? extends JpaRepositoryStateMachine> entities) {
        log.info("************************************ deleteAll");
        throw new NotImplementedException();

    }

    @Override
    public void deleteAll() {
        log.info("************************************ deleteAll");
        throw new NotImplementedException();

    }

    public static JpaRepositoryStateMachine SMRuntimeToJRSMMapper(SMRuntime entity){
        JpaRepositoryStateMachine jsrm = new JpaRepositoryStateMachine();
        jsrm.setMachineId(entity.getMachineId());
        jsrm.setState(entity.getState());
        jsrm.setStateMachineContext(entity.getStateMachineContext());
        return  jsrm;
    }

    public static SMRuntime JRSMtoSMRuntimeMapper(JpaRepositoryStateMachine entity){
        SMRuntime smr = new SMRuntime();
        smr.setMachineId(entity.getMachineId());
        smr.setState(entity.getState());
        smr.setStateMachineContext(entity.getStateMachineContext());
        return  smr;
    }

}
