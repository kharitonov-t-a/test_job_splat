package banner.aspect;

import banner.annotation.AuditCreate;
import banner.model.Audit;
import banner.model.Banner;
import banner.model.enums.Crud;
import banner.service.interfaces.AuditService;
import banner.service.interfaces.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Aspect
public class AuditLogger {

    @Autowired
    AuditService auditService;
    @Autowired
    UserService userService;

    private Logger logger = Logger.getLogger(AuditLogger.class.getName());

    @Pointcut("@annotation(banner.annotation.AuditCreate)")
    public void createMethods() {
    }

    @Pointcut("@annotation(banner.annotation.AuditUpdate)")
    public void updateMethods() {
    }

    @Pointcut("@annotation(banner.annotation.AuditDelete)")
    public void deleteMethods() {
    }

    @Pointcut("@annotation(banner.annotation.AuditSort)")
    public void sortMethods() {
    }

    @Pointcut("@annotation(banner.annotation.AuditSwitchActivity)")
    public void switchActivityMethods() {
    }

    @AfterReturning(pointcut = "createMethods()", returning = "result")
    public void createMethodCall(JoinPoint jp, Object result) {
        Audit audit = new Audit();
        audit.setCrud(Crud.CREATE);
        createUpdateAuditCompile(audit, result);
        auditAction(audit);
    }

    @AfterReturning(pointcut = "updateMethods()", returning = "result")
    public void updateMethodCall(JoinPoint jp, Object result) {
        Audit audit = new Audit();
        audit.setCrud(Crud.UPDATE);
        createUpdateAuditCompile(audit, result);
        auditAction(audit);
    }

    @Around("deleteMethods()")
    public Object deleteMethodCall(ProceedingJoinPoint jp) throws Throwable {

        Audit audit = new Audit();
        audit.setIdUser(getUserId());
        audit.setIdBanner((Integer) jp.getArgs()[0]);
        audit.setCrud(Crud.DELETE);
        audit.setDescription("Deleted");
        audit.setDate(new Date());
        auditAction(audit);

        Object result = null;
        try {
            result = jp.proceed();
        } catch (Throwable throwable) {
            auditService.delete(audit.getId());
            throw throwable;
        }

        if(!((boolean) result))
            auditService.delete(audit.getId());

        return result;
    }

    @AfterReturning(pointcut = "switchActivityMethods()")
    public void switchActivityMethodCall(JoinPoint jp) {
        Audit audit = new Audit();
        audit.setIdUser(getUserId());
        audit.setIdBanner((Integer) jp.getArgs()[0]);
        audit.setCrud(Crud.SWITCH_ACTIVITY);
        audit.setDescription("New activity: " + jp.getArgs()[1].toString());
        audit.setDate(new Date());
        auditAction(audit);
    }

    @AfterReturning(pointcut = "sortMethods()", returning = "result")
    public void sortMethodCall(JoinPoint jp, Object result) {
        Audit audit = new Audit();
        audit.setIdUser(getUserId());
        audit.setCrud(Crud.SORT);
        audit.setDate(new Date());


        ((List<Banner>)result).forEach(banner -> {
            audit.setIdBanner(banner.getId());
            audit.setDescription("New priority: " + banner.getPriority());
            auditAction(audit);
            audit.setId(null);
        });
    }

    private void createUpdateAuditCompile(Audit audit, Object result){
        audit.setIdUser(getUserId());
        audit.setIdBanner(((Banner)result).getId());
        audit.setDescription(((Banner)result).toString());
        audit.setDate(new Date());
    }

    private Integer getUserId(){
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userService.getUserByName(userName).getId();
    }


    private void auditAction(Audit audit){
        auditService.create(audit);
    }

}
