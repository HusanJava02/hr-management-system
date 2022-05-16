package uz.pdp.hrmanagementsystem.configurations.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.hrmanagementsystem.dto.errors.ApiError;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        }catch (RuntimeException exception){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,exception.getMessage());
            String content = writeAsString(apiError);
            if (content != null)
            writer.write(content);
        }
    }

    private String writeAsString(@NotNull ApiError error){
        try {
            return objectMapper.writeValueAsString(error);
        } catch (JsonProcessingException e) {
            log.error("Exception occured {e}",e);
            return null;
        }
    }
}
