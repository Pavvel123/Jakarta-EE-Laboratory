package pl.core.listener;

import java.io.IOException;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.avatar.domain.service.AvatarService;
import pl.user.domain.service.UserService;
import pl.user.persistance.entity.UserEntity;
import pl.user.persistance.id.UserId;
import pl.user.persistance.repository.UserRepository;

@WebListener
public class InitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        {
            UserEntity user = new UserEntity();
            user.setId(UserId.of(java.util.UUID.fromString("8edb6a9e-c2e8-491f-9224-3eb35c5d90c2")));
            user.setLogin("user0");
            user.setEmail("user0@example.com");
            userRepository.save(user);
            System.out.println("Utworzono użytkownika testowego: " + user.getId());
        }
        
        for (int i = 1; i <= 4; i++) {
            UserEntity user = new UserEntity();
            user.setId(UserId.of(java.util.UUID.randomUUID()));
            user.setLogin("user" + i);
            user.setEmail("user" + i + "@example.com");
            userRepository.save(user);
            System.out.println("Utworzono użytkownika testowego: " + user.getId());
        }

        sce.getServletContext().setAttribute("userService", userService);

        try {
            String avatarDir = sce.getServletContext().getInitParameter("avatar.storage.dir");
            AvatarService avatarService = new AvatarService(avatarDir);
            sce.getServletContext().setAttribute("avatarService", avatarService);
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się zainicjalizować katalogu avatarów", e);
        }
    }
}