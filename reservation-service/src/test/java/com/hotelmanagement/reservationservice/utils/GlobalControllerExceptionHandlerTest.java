package com.hotelmanagement.reservationservice.utils;

import com.hotelmanagement.reservationservice.utils.exeption.DuplicateStaff;
import com.hotelmanagement.reservationservice.utils.exeption.InvalidInputException;
import com.hotelmanagement.reservationservice.utils.exeption.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;

class GlobalControllerExceptionHandlerTest {

    @Mock
    private NotFoundException notFoundException;
    @Mock
    private InvalidInputException invalidInputsException;
    @Mock
    private DuplicateStaff duplicateEmployeeException;

    private GlobalControllerExceptionHandler globalControllerExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        globalControllerExceptionHandler = new GlobalControllerExceptionHandler();
    }

    @Test
    public void handleNotFoundException_ShouldReturnHttpErrorInfoWithNotFound() {
        //arrange
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        //act
        HttpErrorInfo httpErrorInfo = globalControllerExceptionHandler.handleNotFoundException(webRequest, notFoundException);
        //assert
        assertEquals(HttpStatus.NOT_FOUND, httpErrorInfo.getHttpStatus());
    }

    @Test
    public void handleInvalidInputsException_ShouldReturnHttpErrorInfoWithUnprocessable() {
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());

        HttpErrorInfo httpErrorInfo = globalControllerExceptionHandler.handleInvalidInputException(webRequest, invalidInputsException);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpErrorInfo.getHttpStatus());
    }

    @Test
    public void handleDuplicateSupplyCodeException_ShouldReturnHttpErrorInfoWithUnprocessable() {
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());

        HttpErrorInfo httpErrorInfo = globalControllerExceptionHandler.handleDuplicateStaffException(webRequest, duplicateEmployeeException);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpErrorInfo.getHttpStatus());
    }


}