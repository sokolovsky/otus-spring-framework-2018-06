package ru.otus.spring.sokolovsky.hw13.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.spring.sokolovsky.hw13.domain.Entity;

import java.util.List;

public interface AccessGrantTransaction {
    AccessGrantTransaction forEntity(Entity entity);

    AccessGrantTransaction currentUserIsOwner();

    AccessGrantTransaction withRoles(List<String> roles);

    AccessGrantTransaction userPermissions(List<String> permissions);

    Entity getEntity();

    UserDetails getUserDetails();

    boolean isCurrentUserIsOwner();

    List<String> getRoles();

    List<String> getPermissions();

    void execute();
}
