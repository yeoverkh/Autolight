package ua.yehor.autolightbackend.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ua.yehor.autolightbackend.exception.RoleAlreadyExistsException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Controller advising global exception handling for authentication, access denial
 * and also for invalid data provided.
 */
@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandlerController implements AuthenticationEntryPoint, AccessDeniedHandler {
    /**
     * Source for retrieving error messages based on locales.
     */
    private final MessageSource messageSource;

    /**
     * Handles unauthorized access exceptions during authentication.
     *
     * @param request       HttpServletRequest representing the request
     * @param response      HttpServletResponse representing the response
     * @param authException AuthenticationException thrown during unauthorized access
     * @throws IOException if an input or output error occurs
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String errorMessage = getUnauthorizedMessage(request.getRequestURI());
        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
    }

    /**
     * Handles access denied exceptions.
     *
     * @param request               HttpServletRequest representing the request
     * @param response              HttpServletResponse representing the response
     * @param accessDeniedException AccessDeniedException thrown during access denial
     * @throws IOException if an input or output error occurs
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String errorMessage = getMessageSourceAccessor().getMessage("error.access_denied");
        sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, errorMessage);
    }

    /**
     * Retrieves a MessageSourceAccessor based on the current locale.
     *
     * @return MessageSourceAccessor for obtaining messages
     */
    private MessageSourceAccessor getMessageSourceAccessor() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return new MessageSourceAccessor(messageSource, currentLocale);
    }

    /**
     * Retrieves an error message for unauthorized access based on the requested URI.
     *
     * @param uri Requested URI
     * @return Error message for unauthorized access
     */
    private String getUnauthorizedMessage(String uri) {
        String messageBasedOnUri = uri.equals("/login") ? "error.unauthorized.credentials" : "error.unauthorized";
        return getMessageSourceAccessor().getMessage(messageBasedOnUri);
    }

    /**
     * Sends an error response with the specified status code and message.
     *
     * @param response     HttpServletResponse to send the error response
     * @param statusCode   HTTP status code for the error response
     * @param errorMessage Error message to send
     * @throws IOException if an input or output error occurs
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setStatus(statusCode);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();
        out.print(errorMessage);
        out.flush();
    }

    /**
     * Handles HTTP message not readable exceptions.
     *
     * @return ResponseEntity containing the error message and HTTP status UNPROCESSABLE_ENTITY
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseException() {
        String errorMessage = getMessageSourceAccessor().getMessage("error.unprocessable_json");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handles EntityExistsException by providing a conflict response.
     *
     * @return ResponseEntity containing the error message and HTTP status CONFLICT
     */
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleCreatingAlreadyExistsEntity() {
        String errorMessage = getMessageSourceAccessor().getMessage("error.entity_exists");
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    /**
     * Handles EntityNotFoundException by providing a not found response.
     *
     * @return ResponseEntity containing the error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleUpdatingNotFoundEntity() {
        String errorMessage = getMessageSourceAccessor().getMessage("error.entity_not_found");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles RoleAlreadyExistsException by providing a conflict response.
     *
     * @return ResponseEntity containing the error message and HTTP status CONFLICT
     */
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<String> handleRoleAlreadyExists() {
        String errorMessage = getMessageSourceAccessor().getMessage("error.role_exists");
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
