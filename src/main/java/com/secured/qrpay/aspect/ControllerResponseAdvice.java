package com.secured.qrpay.aspect;

// todo - not needed for now
/*@Aspect
@Component*/
public class ControllerResponseAdvice {
/*    @AfterReturning(value = "allRestControllers()", returning = "response")
    public void addSuccessStatus(ResponseEntity<BaseResponse> response) {
        System.out.println("returning response with success!");
        response.getBody().setStatus(Status.SUCCESS);
    }*/

/*    @AfterThrowing(value = "allRestControllers()", throwing = "ex")
    public void addFailedStatus(Exception ex) {
        System.out.println("returning response with failure!" + ex.getMessage());
    }*/

/*    @Pointcut("execution(* com.secured.qrpay.controller.PayeeController.*(..))")
    private void allRestControllers() {

    }*/
}