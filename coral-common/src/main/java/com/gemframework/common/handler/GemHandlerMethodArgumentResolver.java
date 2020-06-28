
package com.gemframework.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletRequest;

import static com.gemframework.common.constant.GemConstant.MediaType.APPLICATION_XWWW_FORM_URLENCODED;
import static com.gemframework.common.constant.GemConstant.MediaType.APPLICATION_XWWW_FORM_URLENCODED_UTF_8;

/**
 * @Title: MyHandlerMethodArgumentResolver.java
 * @Package: com.gemframework.handler
 * @Date: 2019/11/29 13:44
 * @Version: v1.0
 * @Description: 自定义参数解析器
 * * 注: 自定义参数解析器, 实现对RequestResponseBodyMethodProcessor的扩展
 * *
 * * 提示: 此解析器要实现的功能是: 若controller方法的参数前, 使用了@RequestBody注解, 那么解析此参数时，
 * *                           1、若Content-Type为application/x-www-form-urlencoded，
 * *                              那么走ServletModelAttributeMethodProcessor解析器
 * *                           2、若Content-Type不为application/x-www-form-urlencoded，
 * *                              那么走本应该走的RequestResponseBodyMethodProcessor解析器
 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Slf4j
public class GemHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 解析Content-Type为application/json的默认解析器是RequestResponseBodyMethodProcessor
     */
    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    /**
     * 解析Content-Type为application/x-www-form-urlencoded的默认解析器是ServletModelAttributeMethodProcessor
     */
    private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor;

    /**
     * 全参构造
     */
    public GemHandlerMethodArgumentResolver(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor,
                                            ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor) {
        this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
        this.servletModelAttributeMethodProcessor = servletModelAttributeMethodProcessor;
    }

    /**
     * 当参数前有@RequestBody注解时， 解析该参数 会使用此 解析器
     * 注:此方法的返回值将决定:是否使用此解析器解析该参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    /**
     * 解析参数
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            throw new RuntimeException(" request must not be null!");
        }
        String contentType = request.getContentType().replace(" ", "").trim();
        /*
         * 如果ContentType是application/x-www-form-urlencoded，那么使用ServletModelAttributeMethodProcessor解析器
         * 注:其实默认的，当系统识别到参数前有@RequestBody注解时，就会走RequestResponseBodyMethodProcessor解析器;这里就
         *    相当于在走默认的解析器前走了个判断而已。
         */
        if (APPLICATION_XWWW_FORM_URLENCODED_UTF_8.equals(contentType)||APPLICATION_XWWW_FORM_URLENCODED.equals(contentType)) {
            return servletModelAttributeMethodProcessor.resolveArgument(methodParameter,
                    modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        }
        return requestResponseBodyMethodProcessor.resolveArgument(methodParameter,
                modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
    }
}
