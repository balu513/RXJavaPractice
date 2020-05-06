package com.balu.rxjavapractice.constants;

public class Consts {

    public static class Network {
        public static final String API_KEY = "4plJwrN0ODfTCQBxVZVaasc4pNj1";
        public static final String BASE_URL_CRIC = "http://cricapi.com/api/";
        public static final String UNIQUE_ID = "unique_id";

        public static final String PLAYER_ID = "playerId";

        public static final String BATTING1 = "batting1";
        public static final String BATTING2 = "batting2";

        public static final String BOWLING1 = "bowling1";
        public static final String BOWLING2 = "bowling2";

        public static final String PLAYERS = "players";
        public static final String DATA = "data";
    }



    // ballibalakrishna2334 & Afbb@513
    //https://newsapi.org/docs/endpoints/top-headlines
    public static class News {
        public static String API_KEY = "be0a5563a77c48d28f85e45ddf8a8a8e";
        public static String URL_PREFIX = "https://newsapi.org/v2/";
    }

    // ballibalakrishna2334 & Afbb@513
    //https://weatherstack.com/documentation
    //http://api.weatherstack.com/current?access_key=d292c9d0d539f743f7ccbc10e496cbd3&query=Hyderabad
    public static class Whether {
        public static String URL_PREFIX_WEATHER = "http://api.weatherstack.com/";
        public static String API_KEY_WEATHER = "d292c9d0d539f743f7ccbc10e496cbd3";

    }

    // komal mail id
    //https://www.cricapi.com/member-test.aspx
    public static class Cricket{
        public static String URL_PREFIX_CRICKET = "https://cricapi.com/api/";
        public static String API_KEY_CRICKET = "xtHqJY7jB6PeJzpRycNnMmPqxfp1";

    }

    public static class Covid{
        public static String URL_PREFIX_COVID_WORLD = "https://api.covid19api.com/";
        public static String URL_PREFIX_COVID_IND = "https://www.mohfw.gov.in/dashboard/data/";
    }
}