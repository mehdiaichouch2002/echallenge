package com.echallenge.service.impl;

import com.echallenge.dto.SubmitTestRequest;
import com.echallenge.entity.CandidateAnswer;
import com.echallenge.entity.Question;
import com.echallenge.entity.QuestionOption;
import com.echallenge.entity.Result;
import com.echallenge.entity.TestSession;
import com.echallenge.repository.CandidateAnswerRepository;
import com.echallenge.repository.QuestionOptionRepository;
import com.echallenge.repository.QuestionRepository;
import com.echallenge.repository.ResultRepository;
import com.echallenge.repository.TestSessionRepository;
import com.echallenge.service.ResultService;
import com.echallenge.service.exception.BusinessException;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final TestSessionRepository testSessionRepository;
    private final CandidateAnswerRepository candidateAnswerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public ResultServiceImpl(ResultRepository resultRepository,
                             TestSessionRepository testSessionRepository,
                             CandidateAnswerRepository candidateAnswerRepository,
                             QuestionRepository questionRepository,
                             QuestionOptionRepository questionOptionRepository) {
        this.resultRepository = resultRepository;
        this.testSessionRepository = testSessionRepository;
        this.candidateAnswerRepository = candidateAnswerRepository;
        this.questionRepository = questionRepository;
        this.questionOptionRepository = questionOptionRepository;
    }

    @Override
    public Result submitAndGrade(Long sessionId, SubmitTestRequest submission) {
        TestSession session = testSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("TestSession not found: " + sessionId));

        if (session.getStatus() == TestSession.SessionStatus.COMPLETED) {
            throw new BusinessException("This test session was already submitted");
        }

        int correctCount = 0;
        List<SubmitTestRequest.AnswerDto> answers =
                submission.getAnswers() == null ? List.of() : submission.getAnswers();

        for (SubmitTestRequest.AnswerDto dto : answers) {
            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + dto.getQuestionId()));

            QuestionOption selected = null;
            boolean correct = false;
            if (dto.getSelectedOptionId() != null) {
                selected = questionOptionRepository.findById(dto.getSelectedOptionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Option not found: " + dto.getSelectedOptionId()));
                correct = selected.isCorrect();
            }
            if (correct) {
                correctCount++;
            }

            CandidateAnswer answer = new CandidateAnswer(session, question, selected, correct);
            answer.setTimeSpentSeconds(dto.getTimeSpentSeconds());
            candidateAnswerRepository.save(answer);
        }

        session.setStatus(TestSession.SessionStatus.COMPLETED);
        session.setEndTime(LocalDateTime.now());
        testSessionRepository.save(session);

        Result result = new Result(session, answers.size(), correctCount);
        return resultRepository.save(result);
    }

    @Override
    @Transactional(readOnly = true)
    public Result findById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Result findByTestSession(Long testSessionId) {
        return resultRepository.findByTestSessionId(testSessionId)
                .orElseThrow(() -> new ResourceNotFoundException("No result for session: " + testSessionId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Result> findByCandidate(Long candidateId) {
        return resultRepository.findByTestSessionCandidateId(candidateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        resultRepository.delete(findById(id));
    }
}
