package com;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
    public Iterator<E> getCSVIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException, CSVBuilderException;
    public List<E> getCSVFileList(Reader reader, Class<E> csvClass) throws CensusAnalyserException, CSVBuilderException;
}
