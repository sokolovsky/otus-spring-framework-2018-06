package ru.otus.spring.sokolovsky.dao;

import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.domain.Question;
import ru.otus.spring.sokolovsky.readers.DataReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionDaoImpl implements QuestionDao {

    List<Question> questions = new ArrayList<>();

    public QuestionDaoImpl(DataReader dataReader) {
        String[] row;
        while ((row = dataReader.getNext()) != null ) {
            questions.add(
                new Question(row[0], Integer.parseInt(row[1]), Arrays.copyOfRange(row, 2, row.length))
            );
        }
    }

    @Override
    public Question getByIndex(int index) {
        return questions.get(index);
    }

    @Override
    public int size() {
        return questions.size();
    }
}
