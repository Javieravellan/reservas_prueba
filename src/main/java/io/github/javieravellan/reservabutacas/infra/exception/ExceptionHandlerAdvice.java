package io.github.javieravellan.reservabutacas.infra.exception;

import io.github.javieravellan.reservabutacas.infra.web.response.BodyErrorVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomRequestException.class)
    public ResponseEntity<BodyErrorVM> handlerCustomRequestException(CustomRequestException ex, NativeWebRequest request) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new BodyErrorVM(
                        ex.getMessage(),
                        ex.getHttpStatus().getReasonPhrase(),
                        request.getContextPath(),
                        ex.getHttpStatus().value(),
                        System.currentTimeMillis()
                ));
    }
}
