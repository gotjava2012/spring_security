package slyDev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import slyDev.model.LogInHistory;
import slyDev.repository.LogInHistoryRepository;

@Service
public class LogInHistoryService {
    @Autowired
    LogInHistoryRepository logInHistoryRepository;

    public void save(LogInHistory login) {
        logInHistoryRepository.save(login);
    }
}
