package pl.user.persistance.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import pl.user.persistance.entity.UserEntity;
import pl.user.persistance.id.UserId;

public class UserRepository
{
    private final Map<UserId, UserEntity> datastore = new ConcurrentHashMap<>();

    public void save(UserEntity user) {
        datastore.put(user.getId(), user);
    }

    public Optional<UserEntity> findById(UserId id) {
        return Optional.ofNullable(datastore.get(id));
    }

    public List<UserEntity> findAll() {
        return new ArrayList<>(datastore.values());
    }
}