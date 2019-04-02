package ru.otus.spring.sokolovsky.hw13.authorize;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.sokolovsky.hw13.domain.Entity;
import ru.otus.spring.sokolovsky.hw13.services.AccessGrantTransaction;
import ru.otus.spring.sokolovsky.hw13.services.AccessGrantService;

import java.util.Arrays;

@Aspect
@Component
public class AssignAuthorizationAspect {

    private final AccessGrantService accessGrantService;

    @Autowired
    public AssignAuthorizationAspect(AccessGrantService accessGrantService) {
        this.accessGrantService = accessGrantService;
    }

    @AfterReturning(value = "@annotation(AssignAuthorization)", returning = "entity")
    public void assignAuthorizationToEntity(JoinPoint joinPoint, Object entity) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AssignAuthorization annotation = methodSignature.getMethod().getAnnotation(AssignAuthorization.class);
        if (annotation == null) {
            return;
        }
        AccessGrantTransaction accessGrantTransaction = accessGrantService.getAccessGrantTransaction();
        if (annotation.owner()) {
            accessGrantTransaction.currentUserIsOwner();
        }
        accessGrantTransaction
            .forEntity((Entity) entity)
            .withRoles(Arrays.asList(annotation.roles()))
            .userPermissions(Arrays.asList(annotation.permissions()))
            .execute();
    }
}
