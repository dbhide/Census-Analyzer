package com;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

    public Map<String, CensusDTO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return this.loadCensusData(USCensusCSV.class, csvFilePath);
        else throw new CensusAnalyserException("Invalid Country", (CensusAnalyserException.ExceptionType.INVALID_COUNTRY));
    }

    private  <E> Map<String, CensusDTO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDTO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVIterator(reader, IndiaCensusCSV.class);
            Iterable<E> csvIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("com.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(loadCensus -> censusMap.put(loadCensus.state, new CensusDTO(loadCensus)));
            }else if (censusCSVClass.getName().equals("com.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(loadCensus -> censusMap.put(loadCensus.state, new CensusDTO(loadCensus)));
            }

            if (csvFilePath.length == 1) return  censusMap;
            this.loadIndiaStateCode(censusMap, csvFilePath[1]);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_PROBLEM);
        }
    }

    private int loadIndiaStateCode(Map<String, CensusDTO> censusMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.stateName) != null)
                    .forEach(csvState -> censusMap.get(csvState.stateName).stateCode = csvState.stateCode);
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}
