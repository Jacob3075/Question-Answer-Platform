package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.CompanyDAO;
import com.jacob.questionanswerplatform.daos.QuestionDAO;
import com.jacob.questionanswerplatform.daos.SubTopicDAO;
import com.jacob.questionanswerplatform.daos.UserDAO;
import com.jacob.questionanswerplatform.dtos.GetAnswerDTO;
import com.jacob.questionanswerplatform.dtos.GetQuestionDTO;
import com.jacob.questionanswerplatform.dtos.PostQuestionDTO;
import com.jacob.questionanswerplatform.models.Question;
import com.jacob.questionanswerplatform.models.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

	private final QuestionDAO   questionDAO;
	private final CompanyDAO    companyDAO;
	private final SubTopicDAO   subTopicDAO;
	private final UserDAO       userDAO;
	private final AnswerService answerService;

	public QuestionService(QuestionDAO questionDAO, CompanyDAO companyDAO, SubTopicDAO subTopicDAO, UserDAO userDAO,
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
			companyDAO.findById(companyId)
			          .ifPresent(question.getCompanies()::add);
		}

		userDAO.findById(postQuestionDTO.getUserId())
		       .ifPresent(question::setUser);

//		TODO
//		question.setTopics();

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
		GetQuestionDTO     questionDTO      = new GetQuestionDTO();

		questionDTO.setQuestionText(question.getQuestionText());
		questionDTO.setCompanies(question.getCompanies());
		questionDTO.setTopics(question.getTopics());
		questionDTO.setTags(question.getTags());
		questionDTO.setLikes(question.getLikes());

		List<GetAnswerDTO> answerDTOs = new ArrayList<>();

		question.getAnswers()
		        .forEach(answer -> answerDTOs.add(answerService.getAnswerDTO(answer)));

		questionDTO.setAnswers(answerDTOs);

		return questionDTO;

	}
}
