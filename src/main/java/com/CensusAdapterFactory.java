package com;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDTO> getCensusAdapter(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        else
            throw new CensusAnalyserException("Invalid Country", (CensusAnalyserException.ExceptionType.INVALID_COUNTRY));
    }
}
