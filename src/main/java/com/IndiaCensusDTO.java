package com;

public class IndiaCensusDTO {
    public String state;
    public int areaISqKm;
    public int densityPerSqKm;
    public int population;
    public String stateCode;


    public IndiaCensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaISqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }
}
