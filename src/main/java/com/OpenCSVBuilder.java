package com;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {
        public Iterator<E> getCSVIterator(Reader reader, Class csvClass) throws CSVBuilderException {
            return this.getCSVBean(reader, csvClass).iterator();
        }

        @Override
        public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
            return this.getCSVBean(reader, csvClass).parse();
        }

        private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) {
            try {
                CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<E> csvToBean = csvToBeanBuilder.build();
                return csvToBean;
            } catch (IllegalStateException e) {
                throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
            }
        }
}
