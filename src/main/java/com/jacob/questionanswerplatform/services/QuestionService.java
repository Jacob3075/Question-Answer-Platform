package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.*;
import com.jacob.questionanswerplatform.dtos.GetAnswerDTO;
import com.jacob.questionanswerplatform.dtos.GetCommentDTO;
import com.jacob.questionanswerplatform.dtos.GetQuestionDTO;
import com.jacob.questionanswerplatform.dtos.PostQuestionDTO;
import com.jacob.questionanswerplatform.models.AnswerComment;
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

	private final QuestionDAO questionDAO;
	private final CompanyDAO  companyDAO;
	private final SubTopicDAO subTopicDAO;
	private final TagDAO      tagDAO;
	private final UserDAO     userDAO;

	public QuestionService(QuestionDAO questionDAO, CompanyDAO companyDAO, SubTopicDAO subTopicDAO, TagDAO tagDAO,
	                       UserDAO userDAO) {
		this.questionDAO = questionDAO;
		this.companyDAO = companyDAO;
		this.subTopicDAO = subTopicDAO;
		this.tagDAO = tagDAO;
		this.userDAO = userDAO;
	}

	public List<Question> getAllQuestions() {
		return questionDAO.findAll();
	}

	public boolean updateQuestion(Long id, Question question) {
		return Question.stream(question)
		               .ifNotNewQuestion(id, questionDAO)
		               .updateProperties(id, questionDAO)
		               .save(questionDAO);

	}

	public void deleteQuestion(Long id) {
		questionDAO.deleteById(id);
	}

	public Long addNewQuestion(PostQuestionDTO postQuestionDTO) {
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

	public GetQuestionDTO findQuestionById(Long id) {
		GetQuestionDTO     questionDTO      = new GetQuestionDTO();
		Optional<Question> optionalQuestion = questionDAO.findById(id);
		if (optionalQuestion.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		Question question = optionalQuestion.get();

		questionDTO.setQuestionText(question.getQuestionText());
		questionDTO.setCompanies(question.getCompanies());
		questionDTO.setTopics(question.getTopics());
		questionDTO.setTags(question.getTags());
		questionDTO.setLikes(question.getLikes());

		List<GetAnswerDTO> answerDTOs = new ArrayList<>();

		question.getAnswers()
		        .forEach(answer -> {
			        GetAnswerDTO answerDTO = new GetAnswerDTO();
			        answerDTO.setAnswerText(answer.getAnswerText());
			        answerDTO.setUserId(answer.getUser().getId());
			        answerDTO.setLikes(answer.getLikes());

			        List<GetCommentDTO> commentDTOs    = new ArrayList<>();
			        List<AnswerComment> answerComments = answer.getAnswerComments();

			        answerComments.forEach(comment -> {
				        GetCommentDTO commentDTO = new GetCommentDTO();
				        commentDTO.setCommentText(comment.getComment());
				        commentDTO.setUserId(comment.getUser().getId());
				        commentDTO.setDate(comment.getDate());
				        commentDTOs.add(commentDTO);
			        });

			        answerDTO.setAnswerComments(commentDTOs);
			        answerDTOs.add(answerDTO);

		        });

		questionDTO.setAnswers(answerDTOs);

		return questionDTO;
	}
}
