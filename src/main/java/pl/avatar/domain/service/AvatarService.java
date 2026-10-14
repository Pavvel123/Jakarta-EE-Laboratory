package pl.avatar.domain.service;

import java.io.InputStream;
import java.nio.file.*;

import pl.user.persistance.id.UserId;

import java.io.IOException;

public class AvatarService {
    private final Path storageDir;

    public AvatarService(String directory) throws IOException {
        this.storageDir = Paths.get(directory);
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }
    }

    public void saveAvatar(UserId userId, InputStream imageStream) throws IOException {
        Path filePath = storageDir.resolve(userId.toString() + ".png");
        Files.copy(imageStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] getAvatar(UserId userId) throws IOException {
        Path filePath = storageDir.resolve(userId.toString() + ".png");
        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        }
        return null;
    }

    public boolean deleteAvatar(UserId userId) throws IOException {
        Path filePath = storageDir.resolve(userId.toString() + ".png");
        return Files.deleteIfExists(filePath);
    }
}