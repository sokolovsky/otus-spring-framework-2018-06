package ru.otus.spring.sokolovsky.hw13.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw13.domain.Book;

import java.util.List;

@Service
public class AccessTestServiceImpl implements AccessTestService {

    private final AclService aclService;

    @Autowired
    public AccessTestServiceImpl(AclService aclService) {
        this.aclService = aclService;
    }

    @Override
    public boolean canEditBook(Authentication authentication, Book book) {
        Acl acl = getBookAcl(book);
        if (acl == null) {
            return false;
        }
        Sid owner = new PrincipalSid(authentication);
        List<Permission> writePermissions = List.of(BasePermission.WRITE);
        return acl.isGranted(writePermissions, List.of(owner), false);
    }

    @Override
    public boolean canDeleteBook(Authentication authentication, Book book) {
        Acl acl = getBookAcl(book);
        if (acl == null) {
            return false;
        }

        Sid owner = new PrincipalSid(authentication);
        List<Permission> writePermissions = List.of(BasePermission.DELETE);
        return acl.isGranted(writePermissions, List.of(owner), false);
    }

    @Override
    public boolean canAddBook(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> {
            String authority = a.getAuthority();
            return authority.contains("EDITOR") || authority.contains("MAIN_EDITOR");
        });
    }

    @Override
    public boolean canLeaveBookComment(Authentication authentication, Book book) {
        return  authentication.isAuthenticated();
    }

    private Acl getBookAcl(Book book) {
        Acl acl;
        try {
            acl = aclService.readAclById(new ObjectIdentityImpl(Book.class, book.getId()));
        } catch (NotFoundException e) {
            return null;
        }
        return acl;
    }
}
