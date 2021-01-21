package slyDev.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import slyDev.exception.CustomException;
import slyDev.model.LogInHistory;
import slyDev.model.User;
import slyDev.repository.LogInHistoryRepository;
import slyDev.repository.UserRepository;
import slyDev.security.JwtTokenProvider;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogInHistoryService logInHistoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String signin(String username, String password, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            return storeLoginDetailsAndReturnToken(request, user);
        } catch (AuthenticationException e) {
            throw new CustomException("Failure in login sign-in attempt", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private String storeLoginDetailsAndReturnToken(HttpServletRequest request, User user) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        LogInHistory login = new LogInHistory();
        login.setUserId(user.getId());
        login.setIp(ipAddress);
        login.setToken(token);
        logInHistoryService.save(login);
        return token;
    }

//  public String signin(String username, String password) {
//    try {
//      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//      String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
//      return token;
//    } catch (AuthenticationException e) {
//      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//  }

    public String signup(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String updatePassword(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomException("Error in trying to updated Password", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    }

    public String restPassword(String userName ,String newPassword , HttpServletRequest request) {
        if(!StringUtils.isEmpty(newPassword)){
            User user = search(userName);
            user.setPassword(newPassword);
            return updatePassword(user);
        }else
            throw new CustomException("Not able to updated password", HttpStatus.UNPROCESSABLE_ENTITY);

    }
}
