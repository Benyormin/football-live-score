package com.beny.livescore;

import java.util.ArrayList;

public class MatchDetails {
    public static ArrayList<MatchDetails> matchDetails_List = new ArrayList<>();
    String awayTeamName;
    int away_team_score = 0;
    String homeTeamName;
    int home_team_score = 0;
    int id;
    String status;
    String utcDate;

    public String getHomeTeamName() {
        return this.homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName2) {
        this.homeTeamName = homeTeamName2;
    }

    public String getAwayTeamName() {
        return this.awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName2) {
        this.awayTeamName = awayTeamName2;
    }

    public MatchDetails(String status2, String utcDate2, int home_team_score2, int away_team_score2, int id2) {
        this.status = status2;
        this.utcDate = utcDate2;
        this.home_team_score = home_team_score2;
        this.away_team_score = away_team_score2;
        this.id = id2;
    }

    public MatchDetails() {
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public String getUtcDate() {
        return this.utcDate;
    }

    public void setUtcDate(String utcDate2) {
        this.utcDate = utcDate2;
    }

    public int getHome_team_score() {
        return this.home_team_score;
    }

    public void setHome_team_score(int home_team_score2) {
        this.home_team_score = home_team_score2;
    }

    public int getAway_team_score() {
        return this.away_team_score;
    }

    public void setAway_team_score(int away_team_score2) {
        this.away_team_score = away_team_score2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String toString() {
        return "MatchDetails{status='" + this.status + '\'' + ", utcDate='" + this.utcDate + '\'' + ", homeTeamName='" + this.homeTeamName + '\'' + ", awayTeamName='" + this.awayTeamName + '\'' + ", home_team_score=" + this.home_team_score + ", away_team_score=" + this.away_team_score + ", id=" + this.id + '}';
    }
}
