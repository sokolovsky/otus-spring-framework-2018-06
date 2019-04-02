package ru.otus.spring.sokolovsky.hw13.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw13.domain.Book;

@Service
public class AccessGrantServiceImpl implements AccessGrantService {

    private final MutableAclService aclService;

    @Autowired
    public AccessGrantServiceImpl(MutableAclService aclService) {
        this.aclService = aclService;
    }

    @Override
    public AccessGrantTransaction getAccessGrantTransaction() {
        return new AccessGrantTransactionImpl(this);
    }

    @Override
    public void grantAccess(AccessGrantTransaction transaction) {

        if (transaction.getEntity() == null) {
            return;
        }

        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Book.class, transaction.getEntity().getId());
        final MutableAcl mutableAcl = aclService.createAcl(objectIdentity);

        Sid owner = null;
        if (transaction.isCurrentUserIsOwner()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            owner = new PrincipalSid(authentication);
            mutableAcl.setOwner(owner);
        }

        for (String sPermission :transaction.getPermissions()) {
            Permission permission = null;
            switch (sPermission) {
                case "READ":
                    permission = BasePermission.READ;
                    break;
                case "WRITE":
                    permission = BasePermission.WRITE;
                    break;
                case "DELETE":
                    permission = BasePermission.DELETE;
                    break;
            }
            if (permission == null) {
                continue;
            }
            for (String role: transaction.getRoles()) {
                Sid sid = new GrantedAuthoritySid(role);
                mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, sid, true);
            }
            if (owner != null) {
                mutableAcl.insertAce(mutableAcl.getEntries().size(), permission, owner, true);
            }
        }
        aclService.updateAcl(mutableAcl);
    }
}
