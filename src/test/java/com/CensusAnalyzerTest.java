package com;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyzerTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String DELIMITER_PROBLEM_FILE = "./src/test/resources/DelimiterFile.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData (1).csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_FILE_TYPE);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenCSVFile_WhenDelimiterIncorrect_ShouldThrowException() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, DELIMITER_PROBLEM_FILE);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_PROBLEM, e.type);
        }
    }

    //Use Case 2
    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int indiaStateCode =censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV);
            Assert.assertEquals(29, indiaStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int censusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDataCount);
        }catch (CensusAnalyserException e){

        }
    }
}