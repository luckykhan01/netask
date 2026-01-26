package mminc.netask.service;

import lombok.RequiredArgsConstructor;
import mminc.netask.exception.ResourceNotFoundException;
import mminc.netask.model.User;
import mminc.netask.model.UserService;
import mminc.netask.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service

public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User with id " + id + "not found"));
    }

    @Override
    public void deleteUser(Long id) {
        User delUser = getUserById(id);
        userRepository.delete(delUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User userDetails, Long id) {
        User user = getUserById(id);
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        return userRepository.save(user);
    }
}
