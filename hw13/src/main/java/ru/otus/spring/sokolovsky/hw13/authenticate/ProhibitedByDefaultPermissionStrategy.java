package ru.otus.spring.sokolovsky.hw13.authenticate;

import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.model.*;

import java.util.List;

public class ProhibitedByDefaultPermissionStrategy implements PermissionGrantingStrategy {

    private final DefaultPermissionGrantingStrategy wrappedStrategy;

    public ProhibitedByDefaultPermissionStrategy(AuditLogger auditLogger) {
        this.wrappedStrategy = new DefaultPermissionGrantingStrategy(auditLogger);
    }

    @Override
    public boolean isGranted(Acl acl, List<Permission> permission, List<Sid> sids, boolean administrativeMode) {
        try {
            return wrappedStrategy.isGranted(acl, permission, sids, administrativeMode);
        } catch (NotFoundException e) {
            return false;
        }
    }
}
