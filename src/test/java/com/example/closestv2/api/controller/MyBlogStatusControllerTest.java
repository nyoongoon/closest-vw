package com.example.closestv2.api.controller;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogStatusUsecase;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("myBlogStatusPatch가 호출 될 때 세션에서 유저 정보를 조회하여 전달한다.")
    @Test
    void myBlogStatusPatch() {
        // given
        Long expectedPrincipal = 123L;
        MyBlogStatusPatchRequest request = new MyBlogStatusPatchRequest();
        request.setMessage("New status message");

        when(authentication.getPrincipal()).thenReturn(expectedPrincipal);

        // when
        ResponseEntity<Void> response = myBlogStatusController.myBlogStatusPatch(request);

        // then
        assertEquals(ResponseEntity.ok().build(), response);

        ArgumentCaptor<Long> principalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<MyBlogStatusPatchServiceRequest> serviceRequestCaptor = ArgumentCaptor.forClass(MyBlogStatusPatchServiceRequest.class);

        verify(myBlogStatusUsecase, times(1)).resetMyBlogStatusMessage(principalCaptor.capture(), serviceRequestCaptor.capture());

        assertEquals(123L, principalCaptor.getValue());
        assertEquals("New status message", serviceRequestCaptor.getValue().getMessage());
    }
}
