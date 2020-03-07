package com;

public class CensusDTO {
    public String state;
    public double totalArea;
    public double populationDensity;
    public int population;
    public String stateCode;
    public String stateId;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.totalArea;
        populationDensity = indiaCensusCSV.populationDensity;
        population = indiaCensusCSV.population;
    }

    public CensusDTO(USCensusCSV usCensusCSV) {
        stateId = usCensusCSV.stateId;
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        totalArea =  usCensusCSV.totalArea;
        populationDensity = usCensusCSV.populationDensity;
    }
}
