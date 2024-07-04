package com.example.filter.aop;

import com.example.filter.model.UserRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect // Aop 사용 class
@Component
public class TimerAop {

    // 스프링에서 관리하고 있는 빈들을 대상으로 동작
    // 예외로 빈 말고 다른 객체에서는 Aspectj 라이브러리 사용
    @Pointcut(value="within(com.example.filter.controller.UserApiController)") //기능을 어디에 적용시킬지
    public void timerPointCut(){

    }

    @Before(value="timerPointCut()") // 포인트 컷 지점 설정 //메소드가 실행되기 전
    public void before(JoinPoint joinPoint) {
        System.out.println("before");
    }

    @After(value="timerPointCut()") // 메소드 실행된 후
    public void after(JoinPoint joinPoint) {
        System.out.println("after");
    }

    @AfterReturning(value="timerPointCut()", returning = "result") // 실행되고 예외가 발생하지 않았을 때
    public void afterReturning(JoinPoint joinPoint, Object result) { // 여기서는 다르게 result를 통해 결과 값 리턴
        System.out.println("afterReturning");
    }

    @AfterThrowing(value="timerPointCut()", throwing = "throwable") //실행되고 예외가 발생하였을 때
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        System.out.println("after throwing");
    }

    @Around(value="timerPointCut()") // 메소드 전 후 시점 // 어떤 pointcut을 적용시킬시 value로 지정한다. //예외 x와 o 둘다에 해당
    public void around(ProceedingJoinPoint joinPoint) throws Throwable { // joinPoint는 pointcut을 지정한 위치
        System.out.println("메소드 실행 이전");

        Arrays.stream(joinPoint.getArgs()).forEach(
                it->{
                    if(it instanceof UserRequest){
                        var tempUser=(UserRequest)it; // 캐스팅
                        var phoneNumber=tempUser.getPhoneNumber().replace("-", ""); // '-' 문자 대체
                        tempUser.setPhoneNumber(phoneNumber);
                    }
                }
        ); //해당 메소드가 실행될 때 들어가는 모든 매개변수를 가져온다.

        // 암/복호화 /로깅
        // 로깅을 할 때 개인정보를 masking 해야하기 때문에
        // 여기서 암복호화를 해서 로깅을 남긴다

        //또한 특정 느린 서비스를 찾을 때 사용할 수 있다.
        var newObjs = Arrays.asList(
                new UserRequest()
        );
        var stopWatch=new StopWatch();
        stopWatch.start();

        joinPoint.proceed(newObjs.toArray()); //proceed는 메소드를 실행시키는 메소드 // 데이터 바꾸기 //예외 발생가능

        stopWatch.stop();
        System.out.println("총 소요된 시간: "+stopWatch.getTotalTimeMillis());
        System.out.println("메소드 실행 이후");

    } //around
}
