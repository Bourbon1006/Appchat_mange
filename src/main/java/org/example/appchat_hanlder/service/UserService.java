package org.example.appchat_hanlder.service;

import org.example.appchat_hanlder.entity.User;
import org.example.appchat_hanlder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void updateRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!"USER".equals(newRole) && !"ADMIN".equals(newRole)) {
            throw new RuntimeException("无效的角色值");
        }

        user.setRole(newRole);
        userRepository.save(user);
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if ("ADMIN".equals(user.getRole())) {
            // 检查是否是最后一个管理员
            long adminCount = userRepository.countByRole("ADMIN");
            if (adminCount <= 1) {
                throw new RuntimeException("无法删除最后一个管理员账号");
            }
        }

        userRepository.delete(user);
    }
}