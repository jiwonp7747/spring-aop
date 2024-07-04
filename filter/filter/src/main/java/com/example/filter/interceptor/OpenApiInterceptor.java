package com.example.filter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


// 인터셉터를 통해 컨트롤러로 전달되는 정보를 컨트롤
// 핸들러 인터셉터는 Spring mvc 모델에서 http 요청을 처리하는 과정에서 특정 작업을 수행하도록 도와준다.

@Slf4j
@Component
public class OpenApiInterceptor implements HandlerInterceptor {
    // 요청이 실제 컨트롤러에 전달되기 전에 실행
    // 주로 인증, 권한 검사를 할 때 사용된다.
    // true를 반환하면 컨트롤러로 요청이 전달된다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //handler는 가야할 컨트롤러의 정보가 있다.
        //
        log.info("preHandle");
        // return true이면 controller 전달 false 전달 x

        var handlerMethod=(HandlerMethod)handler; // HandlerMethod 에서는 어노테이션과 클래스 레벨을 확인할 수 있다.

        var methodLevel=handlerMethod.getMethodAnnotation(OpenApi.class); // method레벨에서 open aipi를 가지고 있는가?
        if(methodLevel!=null){
            log.info("method level");
            return true;
        }
        var classLeve= handlerMethod.getBeanType().getAnnotation(OpenApi.class); //class에 open api가 달려있는가
        if(classLeve!=null){
            log.info("class level");
            return true;
        }

        log.info("open api 아닙니다 : {}", request.getRequestURI());
        return false;
    }

    // 컨트롤러가 요청을 처리한 후 DispatcherServlet 이 요청을 랜더링 하기 전에 호출된다.
    // 요청에 대해 반환한 모델이나 뷰에 추가적인 수정 작업을 할 수 있다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 요청 처리가 완료된 후 즉 뷰가 랜더링되고 호출
    // 요청 처리 중에 사용한 리소스 정리, 로그, 예외 처리
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
