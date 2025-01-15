package org.example.appchat_hanlder.config;

import org.example.appchat_hanlder.entity.User;
import org.example.appchat_hanlder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        // 检查是否已存在管理员账号
        if (!userRepository.existsByUsername("admin")) {
            // 创建并保存管理员账号
            User admin = User.createAdmin();
            userRepository.save(admin);
        }
    }
}