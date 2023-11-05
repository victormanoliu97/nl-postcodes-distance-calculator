package com.projects.nlpostcodesdistancecalculator.presentation.advice;

import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostCodeErrorResponse;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostCodeSubError;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostCodeValidationError;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.UpdatePostCodeResponse;
import com.projects.nlpostcodesdistancecalculator.service.exception.DistanceCalculationException;
import com.projects.nlpostcodesdistancecalculator.service.exception.PostCodeNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class PostcodeControllerAdvice {

    @ExceptionHandler({PostCodeNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public UpdatePostCodeResponse handlePostCodeNotFoundException(PostCodeNotFoundException ex) {
        return UpdatePostCodeResponse.builder()
                .message("an error occurred: " + ex.getMessage())
                .postCode(ex.getPostCode())
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public PostCodeErrorResponse handleConstraintViolationException(MethodArgumentNotValidException ex) {
        return sendResponse(ex);
    }

    @ExceptionHandler({DistanceCalculationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public PostCodeErrorResponse handleDistanceCalculationException(DistanceCalculationException ex) {
        return sendResponse(ex);
    }

    private PostCodeErrorResponse sendResponse(MethodArgumentNotValidException ex) {
        return new PostCodeErrorResponse(Instant.now(), extractErrorsMessages(ex));
    }

    private PostCodeErrorResponse sendResponse(DistanceCalculationException ex) {
        return new PostCodeErrorResponse(Instant.now(),  extractErrorsMessages(ex));
    }

    private List<PostCodeSubError> extractErrorsMessages(DistanceCalculationException ex) {
        return List.of(PostCodeSubError.builder().message(ex.getMessage()).build());
    }
    private List<PostCodeSubError> extractErrorsMessages(MethodArgumentNotValidException ex) {
        return ex.getAllErrors().stream()
                .map(e -> e.unwrap(ConstraintViolation.class))
                .map(constraintViolation -> new PostCodeValidationError(constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getInvalidValue(),
                        constraintViolation.getMessage())).collect(Collectors.toList());
    }
}
