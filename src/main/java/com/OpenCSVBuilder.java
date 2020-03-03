package com;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

    public class OpenCSVBuilder<E> implements ICSVBuilder {
        public Iterator<E> getCSVIterator(Reader reader, Class csvClass) {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            Iterator<E> stateCSVIterator = csvToBean.iterator();
            return stateCSVIterator;
        }
    }
