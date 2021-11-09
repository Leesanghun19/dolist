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
    public ResponseEntity<String> ex(IllegalStateException e){
        return new ResponseEntity<>("이미존제하는 email 입니다", HttpStatus.BAD_REQUEST);
    }
}
