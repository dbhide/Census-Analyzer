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

    CensusAnalyser censusAnalyser = new CensusAnalyser();

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_FILE_TYPE);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenCSVFile_WhenDelimiterIncorrect_ShouldThrowException() {
        try{
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, DELIMITER_PROBLEM_FILE);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        try {
            int indiaStateCode =censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV);
            Assert.assertEquals(29, indiaStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CensusAnalyserException {
        try {
            censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
            IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AP", censusCSV[censusCSV.length - 1].stateCode);
        }catch (CensusAnalyserException e){
        }
    }

     @Test
     public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() throws CensusAnalyserException {
         censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV);
         String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
         IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
         Assert.assertEquals(199812341, censusCSV[0].population);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(1102, censusCSV[0].populationDensity);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTALAREA);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(342239, censusCSV[0].totalArea);
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        try {
            int censusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDataCount);
        }catch (CensusAnalyserException e){

        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("Wyoming", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals(3.7253956E7, censusCSV[0].population, 0.0);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        System.out.println(sortedCensusData);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals(3805.61, censusCSV[0].populationDensity, 0.00);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTALAREA);
        System.out.println(sortedCensusData);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals(1723338.01, censusCSV[0].totalArea, 0.0);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        System.out.println(sortedCensusData);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("WY", censusCSV[0].stateId);
    }

    @Test
    public void givenIndiaAndUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        USCensusCSV[] usCensusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("WY", usCensusCSV[0].stateId);

        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV);
        String sortedIndiaCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedIndiaCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(1102, indiaCensusCSV[0].populationDensity);
    }
}