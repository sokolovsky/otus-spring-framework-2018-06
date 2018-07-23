package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

public class BaseDaoTest {

    @Test
    public void shouldCreateSqlBuilder() {
        BaseDao mock = mock(BaseDao.class);
        when(mock.getTableName()).thenReturn("test_table");
        when(mock.createSelectBuilder()).thenCallRealMethod();

        assertThat(mock.createSelectBuilder().toString(), is("select * from `test_table`"));
    }

    @Test
    public void rowMapper() {
        BaseDao.RowMapper mapper = mock(BaseDao.RowMapper.class);
        BaseDao.IdleColumnNameTranslator translator = mock(BaseDao.IdleColumnNameTranslator.class);
        when(translator.translate(anyString())).thenReturn("translated_column");
        when(mapper.getColumnNameTranslator()).thenReturn(translator);
        when(mapper.getColumnId(anyString())).thenCallRealMethod();

        assertThat(mapper.getColumnId("column"), is("translated_column"));

        verify(translator, times(1)).translate("column");
    }

    @Test
    public void prefixAppendTranslatorCheck() {
        BaseDao.ColumnNameTranslator translator = new BaseDao.PrefixAppendTranslator("prefix_");
        assertThat(translator.translate("column"), is("prefix_column"));
    }
}
