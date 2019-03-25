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

/**
 *
 */
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

    /**
     * Create new item of Audit table after create new banner
     * @param jp
     * @param result
     */
    @AfterReturning(pointcut = "createMethods()", returning = "result")
    public void createMethodCall(JoinPoint jp, Object result) {
        Audit audit = new Audit();
        audit.setCrud(Crud.CREATE);
        createUpdateAuditCompile(audit, result);
        auditAction(audit);
    }

    /**
     * Create new item of Audit table after update exists banner
     * @param jp
     * @param result
     */
    @AfterReturning(pointcut = "updateMethods()", returning = "result")
    public void updateMethodCall(JoinPoint jp, Object result) {
        Audit audit = new Audit();
        audit.setCrud(Crud.UPDATE);
        createUpdateAuditCompile(audit, result);
        auditAction(audit);
    }


    /**
     * Fill item audit for create report about create or update banner
     * @param audit
     * @param result
     */
    private void createUpdateAuditCompile(Audit audit, Object result){
        audit.setIdUser(getUserId());
        audit.setIdBanner(((Banner)result).getId());
        audit.setDescription(((Banner)result).toString());
        audit.setDate(new Date());
    }

    /**
     * Create new item of Audit table after delete exists banner
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("deleteMethods()")
    public Object deleteMethodCall(ProceedingJoinPoint jp) throws Throwable {

        Audit audit = new Audit();
        audit.setIdUser(getUserId());
        audit.setIdBanner((Integer) jp.getArgs()[0]);
        audit.setCrud(Crud.DELETE);
        audit.setDescription("Deleted");
        audit.setDate(new Date());

        Object result = jp.proceed();
        if(!((boolean) result))
            return result;

        auditAction(audit);

        return result;
    }

    /**
     * Create new item of Audit table after switch activity of exist banner
     * @param jp
     */
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

    /**
     * Create few new item of Audit table after change banners priority
     * @param jp
     * @param result
     */
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


    /**
     * Get current user for create new item in Audit table
     * @return
     */
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


    /**
     * Create new item of Audit table
     * @param audit
     */
    private void auditAction(Audit audit){
        auditService.create(audit);
    }

}
