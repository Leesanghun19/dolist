package to.dolist.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 email 중복응답
 */
@RestControllerAdvice
public class ExistEmail {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResult> ex(IllegalStateException e){
        ErrorResult errorResult = new ErrorResult("EXIST-EMAIL",e.getMessage());
        return new ResponseEntity(errorResult,HttpStatus.BAD_REQUEST);
    }
}
