package slyDev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slyDev.model.LogInHistory;
import slyDev.model.User;

import javax.transaction.Transactional;

public interface LogInHistoryRepository extends JpaRepository<LogInHistory, Integer> {

}
