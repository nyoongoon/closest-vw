package com.example.closestv2.api.controller;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogStatusUsecase;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MyBlogStatusControllerTest {

    @Mock
    private MyBlogStatusUsecase myBlogStatusUsecase;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private MyBlogStatusController myBlogStatusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testMyBlogStatusPatch() {
        // Given
        Long expectedPrincipal = 123L;
        MyBlogStatusPatchRequest request = new MyBlogStatusPatchRequest();
        request.setMessage("New status message");

        when(authentication.getPrincipal()).thenReturn(expectedPrincipal);

        // When
        ResponseEntity<Void> response = myBlogStatusController.myBlogStatusPatch(request);

        // Then
        assertEquals(ResponseEntity.ok().build(), response);

        // Capture the argument passed to the usecase
        ArgumentCaptor<Long> principalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<MyBlogStatusPatchServiceRequest> serviceRequestCaptor = ArgumentCaptor.forClass(MyBlogStatusPatchServiceRequest.class);

        verify(myBlogStatusUsecase, times(1)).resetMyBlogStatusMessage(principalCaptor.capture(), serviceRequestCaptor.capture());

        // Verify the captured arguments
        assertEquals(expectedPrincipal, principalCaptor.getValue());
        assertEquals("New status message", serviceRequestCaptor.getValue().getMessage());
    }
}
