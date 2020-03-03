package com;

import com.opencsv.bean.CsvBindByName;

    public class IndiaStateCodeCSV {
        @CsvBindByName(column = "StateName" , required = true)
        public String stateName;

        @CsvBindByName(column = "StateCode", required = true)
        public String stateCode;

        @Override
        public String toString() {
            return "IndiaCensusCSV{" +
                    "State='" + stateName + '\'' +
                    ", State Code='" + stateCode + '\'' +
                    '}';
        }
}
