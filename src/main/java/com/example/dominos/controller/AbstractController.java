package com.example.dominos.controller;

import com.example.dominos.model.dto.ErrorDTO;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public abstract class AbstractController {

    public static final String LOGGED = "LOGGED";
    public static final String USER_ID = "USER_ID";
    public static final String REMOTE_IP = "REMOTE_IP";
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e){
        return buildErrorInfo(e, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e){
        return buildErrorInfo(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e){
        return buildErrorInfo(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllOthers(Exception e){
        return buildErrorInfo(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO buildErrorInfo(Exception e, HttpStatus httpStatus) {
        e.printStackTrace();
        //TODO add to log file - log4j
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(httpStatus.value());
        errorDTO.setTime(LocalDateTime.now());
        return errorDTO;
    }

    public void logUser(HttpServletRequest req, long id){
        HttpSession session = req.getSession();
        session.setAttribute(LOGGED, true);
        session.setAttribute(USER_ID, id);
        session.setAttribute(REMOTE_IP, req.getRemoteAddr());
    }

    public long getLoggedUserId(HttpServletRequest req){
        HttpSession session = req.getSession();
        String ip = req.getRemoteAddr();
        //check if user is logged and ip matches
        if(     session.isNew() ||
                session.getAttribute(LOGGED) == null ||
                (!(boolean) session.getAttribute(LOGGED)) ||
                !session.getAttribute(REMOTE_IP).equals(ip)){
            throw new UnauthorizedException("You have to login!");
        }
        return (long) session.getAttribute(USER_ID);
    }
}
