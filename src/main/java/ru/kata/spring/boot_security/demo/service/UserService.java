package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User findByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("Пользователь с именем '%s' не найден", username));
        Hibernate.initialize(user.getRoles());
        return user;
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(User user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRoles(user.getRoles());
        userRepository.save(newUser);
    }

    public void updateUser(User user, long id) {
        user.setId(id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean uniqueUsername(String username) {
        return userRepository.findUsersByUsername(username) == null;
    }
}
