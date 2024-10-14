package com.perjpasample.jpapersistance.security.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perjpasample.jpapersistance.security.Model.ErrorResponse;
import com.perjpasample.jpapersistance.security.Model.SessionModel;
import com.perjpasample.jpapersistance.security.service.SessionService;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/session")
    public SessionModel createSession(@RequestBody SessionModel sessionModel) {
        return sessionService.save(sessionModel);
    }

    @GetMapping("/session")
    public List<SessionModel> getSessions() {
        return sessionService.getSessions();
    }

    @GetMapping("/session/{id}")
public ResponseEntity<?> getSessionById(@PathVariable Integer id) {
    SessionModel sessionModel = sessionService.getSessionById(id);
    if (sessionModel != null) {
        return ResponseEntity.ok(sessionModel);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorResponse("Session not found"));
    }
}


    @DeleteMapping("/session/{id}")
    public void deleteSessionById(@PathVariable Integer id) {
        sessionService.deleteSessionById(id);
    }

    @GetMapping("/session/validate/{token}")
    public ResponseEntity<?> isSessionValid(@PathVariable String token) {
        boolean isValid = sessionService.isSessionValid(token);
        return ResponseEntity.ok(isValid);
    }

}
