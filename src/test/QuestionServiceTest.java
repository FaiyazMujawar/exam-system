package test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import main.java.models.Question;
import main.java.services.QuestionService;

public class QuestionServiceTest {
  @Test
  public void testGetQuestionsCount() throws FileNotFoundException, IOException {
    Properties properties=new Properties();
     properties.load(new FileReader("app.properties"));
     properties.setProperty("dbURI", "jdbc:mysql://remotemysql.com:3306/j0SVPByzmc");
     properties.setProperty("username", "j0SVPByzmc");
     properties.setProperty("password", "8XZPmSofCh");
    QuestionService qService = new QuestionService();
    Question question = new Question(1, "Who is more idiot?", "You", "Me", "He", "She", "A");
    qService.addQuestion(question);
    assertEquals(1, qService.getQuestionsCount());
    qService.deleteQuestionById(1);
  }

  @Test
  public void testGetAllQuestions() throws FileNotFoundException, IOException {
     Properties properties=new Properties();
     List<Question> actual=new ArrayList<Question>();
     List<Question> expected=new ArrayList<Question>();
     boolean expectedVal=true;

     properties.load(new FileReader("app.properties"));
     properties.setProperty("dbURI", "jdbc:mysql://remotemysql.com:3306/j0SVPByzmc");
     properties.setProperty("username", "j0SVPByzmc");
     properties.setProperty("password", "8XZPmSofCh");

     QuestionService qService=new QuestionService();

     Question question1 = new Question(1, "Who is more idiot?", "You", "Me", "He", "She", "A");
     qService.addQuestion(question1);
     actual.add(question1);

     Question question2 = new Question(2, "Who is more idiot?", "You", "Me", "He", "She", "A");
     qService.addQuestion(question2);
     actual.add(question2);

     Question question3 = new Question(3, "Who is more idiot?", "You", "Me", "He", "She", "A");
     qService.addQuestion(question3);
     actual.add(question3);

     expected=qService.getAllQuestions();

     for (int i = 0; i < actual.size(); i++) {

       Question temp1=actual.get(i);
       Question temp2=expected.get(i);

       if( temp1.getId()!=temp2.getId() 
            || !(temp1.getOptionA().equals(temp2.getOptionA()))
            || !(temp1.getOptionB().equals(temp2.getOptionB()))
            || !(temp1.getOptionC().equals(temp2.getOptionC()))
            || !(temp1.getOptionD().equals(temp2.getOptionD()))
            || !(temp1.getQuestion().equals(temp2.getQuestion()))
            || !(temp1.getcorrectAnswer().equals(temp2.getcorrectAnswer())))
        {
            expectedVal=false;
        }
     }

     assertEquals(true, expectedVal);
     qService.deleteQuestionById(1);
     qService.deleteQuestionById(2);
     qService.deleteQuestionById(3);
   }
 

 @Test
 public void testGetQuestion(){
   QuestionService questionService=new QuestionService();
   Question question1 = new Question(1, "Who is more idiot?", "You", "Me", "He", "She", "A");
   questionService.addQuestion(question1);
   assertEquals("Who is more idiot?", questionService.getQuestionById(1).getQuestion());
   questionService.deleteQuestionById(1);
 }


 @Test
 public void testUpdateQuestion(){
   QuestionService questionService=new QuestionService();
   Question question = new Question(1, "Who is more idiot?", "You", "Me", "He", "She", "A");
   questionService.addQuestion(question);
   Question question1=questionService.getQuestionById(1);
   question1.setcorrectAnswer("C");
   questionService.updatequestion(question1);
   assertEquals("C", questionService.getQuestionById(1).getcorrectAnswer());
   questionService.deleteQuestionById(1);
 }


 @Test
 public void testAddQuestion(){
   QuestionService questionService=new QuestionService();
   Question question = new Question(1, "Who is more idiot?", "You", "Me", "He", "She", "A");
   int initialCount=questionService.getQuestionsCount();
   questionService.addQuestion(question); 
   int finalCount=questionService.getQuestionsCount();
   assertEquals(initialCount+1, finalCount);
   questionService.deleteQuestionById(1);
  }



@Test
 public void testDeleteQuestion(){
   QuestionService questionService=new QuestionService();
   Question question = new Question(1, "Who is more idiot?", "You", "Me", "He", "She", "A");
   questionService.addQuestion(question); 
   int initialCount=questionService.getQuestionsCount();
   questionService.deleteQuestionById(1);
   int finalCount=questionService.getQuestionsCount();
   assertEquals(initialCount-1, finalCount);
   questionService.deleteQuestionById(1);
  }
}


