package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.CompanyDAO;
import com.jacob.questionanswerplatform.daos.QuestionDAO;
import com.jacob.questionanswerplatform.daos.SubTopicDAO;
import com.jacob.questionanswerplatform.daos.UserDAO;
import com.jacob.questionanswerplatform.dtos.*;
import com.jacob.questionanswerplatform.models.Question;
import com.jacob.questionanswerplatform.models.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionDAO questionDAO;
    private final CompanyDAO companyDAO;
    private final SubTopicDAO subTopicDAO;
    private final UserDAO userDAO;
    private final AnswerService answerService;

    public QuestionService(
            QuestionDAO questionDAO,
            CompanyDAO companyDAO,
            SubTopicDAO subTopicDAO,
            UserDAO userDAO,
            AnswerService answerService) {
        this.questionDAO = questionDAO;
        this.companyDAO = companyDAO;
        this.subTopicDAO = subTopicDAO;
        this.userDAO = userDAO;
        this.answerService = answerService;
    }

    public Long postQuestion(PostQuestionDTO postQuestionDTO) {
        Question question = new Question();
        question.setQuestionText(postQuestionDTO.getQuestionText());
        question.setAnswers(new ArrayList<>());

        Long companyId = postQuestionDTO.getCompanyId();
        if (companyId != null) {
            companyDAO.findById(companyId).ifPresent(question.getCompanies()::add);
        }

        userDAO.findById(postQuestionDTO.getUserId()).ifPresent(question::setUser);

        subTopicDAO
                .findById(postQuestionDTO.getSubTopicId())
                .ifPresent(question.getSubTopics()::add);

        List<Tag> tags = postQuestionDTO.getTags();
        if (tags != null) {
            question.getTags().addAll(tags);
        }

        return questionDAO.saveAndFlush(question).getId();
    }

    public GetQuestionDTO getQuestionDTO(Long id) {
        Optional<Question> optionalQuestion = questionDAO.findById(id);
        if (optionalQuestion.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        Question question = optionalQuestion.get();

        return getQuestionDTO(question);
    }

    public GetQuestionDTO getQuestionDTO(Question question) {
        GetQuestionDTO questionDTO = new GetQuestionDTO();

        questionDTO.setQuestionText(question.getQuestionText());
        questionDTO.setCompanies(question.getCompanies());
        questionDTO.setSubTopics(question.getSubTopics());
        questionDTO.setTags(question.getTags());
        questionDTO.setLikes(question.getLikes());

        List<GetAnswerDTO> answerDTOs = new ArrayList<>();

        question.getAnswers().forEach(answer -> answerDTOs.add(answerService.getAnswerDTO(answer)));

        questionDTO.setAnswers(answerDTOs);

        return questionDTO;
    }

    public void like(QuestionLikeDTO questionLikeDTO) {
        if (userDAO.findById(questionLikeDTO.getUserId()).isEmpty())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        Optional<Question> optionalQuestion = questionDAO.findById(questionLikeDTO.getQuestionId());

        if (optionalQuestion.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        Question question = optionalQuestion.get();
        question.setLikes(question.getLikes() + 1);

        questionDAO.saveAndFlush(question);
    }

    public List<GetQuestionFilterDTO> filterBy(PostQuestionFilterDTO questionFilterDTO) {
        return Question.stream(questionDAO.findAll())
                .filterByLikesGreaterThan(questionFilterDTO.getLikesCount())
                .filterByDatePostedOn(questionFilterDTO.getDate())
                .filterByContains(questionFilterDTO.getCompanies(), Question::getCompanies)
                .filterByContains(questionFilterDTO.getTags(), Question::getTags)
                .filterByContains(questionFilterDTO.getSubTopics(), Question::getSubTopics)
                .toFilterDTO()
                .collect(Collectors.toList());
    }
}
