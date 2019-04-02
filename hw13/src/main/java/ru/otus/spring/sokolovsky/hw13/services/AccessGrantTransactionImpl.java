package ru.otus.spring.sokolovsky.hw13.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.spring.sokolovsky.hw13.domain.Entity;

import java.util.List;

public class AccessGrantTransactionImpl implements AccessGrantTransaction {

    private AccessGrantService service;
    private Entity entity;
    private UserDetails userDetails;
    private boolean currentUserIsOwner = false;
    private List<String> roles;
    private List<String> permissions;

    AccessGrantTransactionImpl(AccessGrantService accessGrantService) {
        service = accessGrantService;
    }

    @Override
    public AccessGrantTransaction forEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public AccessGrantTransaction currentUserIsOwner() {
        currentUserIsOwner = true;
        return this;
    }

    @Override
    public AccessGrantTransaction withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public AccessGrantTransaction userPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public void execute() {
        service.grantAccess(this);
    }

    public Entity getEntity() {
        return entity;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public boolean isCurrentUserIsOwner() {
        return currentUserIsOwner;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
