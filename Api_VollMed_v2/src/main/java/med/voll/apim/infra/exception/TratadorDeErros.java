package med.voll.apim.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException execption) {
        var erros = execption.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream()
                .map(DadosError400DTO::new)
                .toList());
    }

    private record DadosError400DTO(String campo, String mensagem) {
        public DadosError400DTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
