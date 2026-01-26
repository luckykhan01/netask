package mminc.netask.model;

import mminc.netask.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
    List<User> getAllUsers();
    User updateUser(User userDetails, Long id);
}
