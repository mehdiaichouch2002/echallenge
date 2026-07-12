package com.echallenge.web;

import com.echallenge.dto.EnrollRequest;
import com.echallenge.dto.ExamDto;
import com.echallenge.dto.SubmitTestRequest;
import com.echallenge.entity.Candidate;
import com.echallenge.entity.Question;
import com.echallenge.entity.Result;
import com.echallenge.entity.TestSession;
import com.echallenge.entity.TimeSlot;
import com.echallenge.service.CandidateService;
import com.echallenge.service.ResultService;
import com.echallenge.service.TestService;
import com.echallenge.service.TestSessionService;
import com.echallenge.service.TimeSlotService;
import com.echallenge.service.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Endpoints for the authenticated candidate (enrollment, taking a test, own results). */
@RestController
@RequestMapping("/api/candidate")
public class CandidateAreaController {

    private final CandidateService candidateService;
    private final TestService testService;
    private final TestSessionService testSessionService;
    private final TimeSlotService timeSlotService;
    private final ResultService resultService;

    public CandidateAreaController(CandidateService candidateService,
                                   TestService testService,
                                   TestSessionService testSessionService,
                                   TimeSlotService timeSlotService,
                                   ResultService resultService) {
        this.candidateService = candidateService;
        this.testService = testService;
        this.testSessionService = testSessionService;
        this.timeSlotService = timeSlotService;
        this.resultService = resultService;
    }

    @GetMapping("/me")
    public Candidate me(Authentication authentication) {
        return currentCandidate(authentication);
    }

    @GetMapping("/timeslots/available")
    public List<TimeSlot> availableTimeSlots() {
        return timeSlotService.findAvailable();
    }

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public TestSession enroll(@Valid @RequestBody EnrollRequest request, Authentication authentication) {
        Candidate candidate = currentCandidate(authentication);
        return testSessionService.enroll(candidate.getId(), request.getTestId(), request.getTimeSlotId());
    }

    @GetMapping("/sessions")
    public List<TestSession> mySessions(Authentication authentication) {
        return testSessionService.findByCandidate(currentCandidate(authentication).getId());
    }

    @PostMapping("/sessions/{id}/start")
    public ExamDto startTest(@PathVariable Long id, Authentication authentication) {
        TestSession session = ownedSession(id, authentication);
        testSessionService.start(session.getId());

        List<Question> questions = testService.selectQuestionsForTest(session.getTest().getId());
        List<ExamDto.QuestionDto> questionDtos = questions.stream()
                .map(q -> new ExamDto.QuestionDto(
                        q.getId(),
                        q.getQuestionText(),
                        q.getPoints(),
                        q.getTheme().getName(),
                        q.getOptions().stream()
                                .map(o -> new ExamDto.OptionDto(o.getId(), o.getOptionText()))
                                .toList()))
                .toList();

        return new ExamDto(
                session.getId(),
                session.getTest().getName(),
                session.getTest().getTotalDurationMinutes(),
                session.getTest().getQuestionDurationSeconds(),
                questionDtos);
    }

    @PostMapping("/sessions/{id}/submit")
    public Result submitTest(@PathVariable Long id,
                             @RequestBody SubmitTestRequest submission,
                             Authentication authentication) {
        TestSession session = ownedSession(id, authentication);
        return resultService.submitAndGrade(session.getId(), submission);
    }

    @GetMapping("/results")
    public List<Result> myResults(Authentication authentication) {
        return resultService.findByCandidate(currentCandidate(authentication).getId());
    }

    private Candidate currentCandidate(Authentication authentication) {
        return candidateService.findByEmail(authentication.getName());
    }

    private TestSession ownedSession(Long sessionId, Authentication authentication) {
        TestSession session = testSessionService.findById(sessionId);
        Candidate candidate = currentCandidate(authentication);
        if (!session.getCandidate().getId().equals(candidate.getId())) {
            throw new BusinessException("This test session does not belong to you");
        }
        return session;
    }
}
