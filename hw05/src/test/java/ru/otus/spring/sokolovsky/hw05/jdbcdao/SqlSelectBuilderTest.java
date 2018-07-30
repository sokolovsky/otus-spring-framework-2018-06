package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SqlSelectBuilderTest {

    @Test
    public void simpleSelect() {
        SqlSelectBuilder builder = new SqlSelectBuilder("test");
        builder
                .useFilterFields("one", "two")
                .useFilterFields(Set.of());
        builder.limit(10);

        assertThat(builder.toString(), is("select * from `test` where one = :one and two = :two limit 10"));
    }

    @Test
    public void customSelect() {
        SqlSelectBuilder builder = new SqlSelectBuilder("test");
        builder
                .select("t.id as t_id, t.name as t_name")
                .useAlias("t")
                .join("inner join some_table st on test.id=st.testId")
                .addWhere("t.name = :name")
                .addWhere("t.id = :id");

        assertThat(builder.toString(), is(
                "select t.id as t_id, t.name as t_name from `test` t inner join" +
                " some_table st on test.id=st.testId where t.name = :name and t.id = :id"
            )
        );

    }
}
