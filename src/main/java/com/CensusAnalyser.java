package com;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<CensusDTO> censusList = null;
    Map<String, CensusDTO> censusMap = null;

    public CensusAnalyser() {
        censusList = new ArrayList<CensusDTO>();
        censusMap = new HashMap<String, CensusDTO>();
    }

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {

        censusMap = new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        censusList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public int loadUSCensusData(String...  csvFilePath) throws CensusAnalyserException {

        censusMap = new CensusLoader().loadCensusData(USCensusCSV.class, csvFilePath);
        censusList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<CensusDTO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDTO census1 = censusList.get(j);
                CensusDTO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
}




