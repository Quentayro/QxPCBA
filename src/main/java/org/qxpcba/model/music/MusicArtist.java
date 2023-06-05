package org.qxpcba.model.music;

import java.util.HashSet;

public class MusicArtist {
    private String firstRankDate;
    HashSet<String> genres;
    private int id;
    private String name;
    private int originalANumber;
    private int originalBNumber;
    private int originalCNumber;
    private int originalDNumber;
    private float originalScore;
    private int originalSNumber;
    private int originalTracksDuration;
    private int originalTracksNumber;
    private String picture;
    private int remixANumber;
    private int remixBNumber;
    private int remixCNumber;
    private int remixDNumber;
    private int remixSNumber;
    private int remixTracksDuration;
    private int remixTracksNumber;
    private String spotifyId;
    private int totalANumber;
    private int totalBNumber;
    private int totalCNumber;
    private int totalDNumber;
    private float totalScore;
    private int totalSNumber;
    private int totalTracksDuration;
    private int totalTracksNumber;

    public MusicArtist(String firstRankDate, int id, String name, int originalANumber, int originalBNumber,
            int originalCNumber, int originalDNumber, float originalScore, int originalSNumber,
            int originalTracksDuration, int originalTracksNumber, String picture, int remixANumber, int remixBNumber,
            int remixCNumber, int remixDNumber, int remixSNumber, int remixTracksDuration, int remixTracksNumber,
            String spotifyId, int totalANumber, int totalBNumber, int totalCNumber, int totalDNumber, float totalScore,
            int totalSNumber, int totalTracksDuration, int totalTracksNumber) {
        this.firstRankDate = firstRankDate;
        this.id = id;
        this.name = name;
        this.originalANumber = originalANumber;
        this.originalBNumber = originalBNumber;
        this.originalCNumber = originalCNumber;
        this.originalDNumber = originalDNumber;
        this.originalScore = originalScore;
        this.originalSNumber = originalSNumber;
        this.originalTracksDuration = originalTracksDuration;
        this.originalTracksNumber = originalTracksNumber;
        this.picture = picture;
        this.remixANumber = remixANumber;
        this.remixBNumber = remixBNumber;
        this.remixCNumber = remixCNumber;
        this.remixDNumber = remixDNumber;
        this.remixSNumber = remixSNumber;
        this.remixTracksDuration = remixTracksDuration;
        this.remixTracksNumber = remixTracksNumber;
        this.spotifyId = spotifyId;
        this.totalANumber = totalANumber;
        this.totalBNumber = totalBNumber;
        this.totalCNumber = totalCNumber;
        this.totalDNumber = totalDNumber;
        this.totalScore = totalScore;
        this.totalSNumber = totalSNumber;
        this.totalTracksDuration = totalTracksDuration;
        this.totalTracksNumber = totalTracksNumber;
    }

    public String getFirstRankDate() {
        return this.firstRankDate;
    }

    public HashSet<String> getGenres() {
        return this.genres;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getOriginalANumber() {
        return this.originalANumber;
    }

    public int getOriginalBNumber() {
        return this.originalBNumber;
    }

    public int getOriginalCNumber() {
        return this.originalCNumber;
    }

    public int getOriginalDNumber() {
        return this.originalDNumber;
    }

    public float getOriginalScore() {
        return this.originalScore;
    }

    public int getOriginalSNumber() {
        return this.originalSNumber;
    }

    public int getOriginalTracksDuration() {
        return this.originalTracksDuration;
    }

    public int getOriginalTracksNumber() {
        return this.originalTracksNumber;
    }

    public String getPicture() {
        return this.picture;
    }

    public int getRemixANumber() {
        return this.remixANumber;
    }

    public int getRemixBNumber() {
        return this.remixBNumber;
    }

    public int getRemixCNumber() {
        return this.remixCNumber;
    }

    public int getRemixDNumber() {
        return this.remixDNumber;
    }

    public int getRemixSNumber() {
        return this.remixSNumber;
    }

    public int getRemixTracksDuration() {
        return this.remixTracksDuration;
    }

    public int getRemixTracksNumber() {
        return this.remixTracksNumber;
    }

    public String getSpotifyId() {
        return this.spotifyId;
    }

    public int getTotalANumber() {
        return this.totalANumber;
    }

    public int getTotalBNumber() {
        return this.totalBNumber;
    }

    public int getTotalCNumber() {
        return this.totalCNumber;
    }

    public int getTotalDNumber() {
        return this.totalDNumber;
    }

    public float getTotalScore() {
        return this.totalScore;
    }

    public int getTotalSNumber() {
        return this.totalSNumber;
    }

    public int getTotalTracksDuration() {
        return this.totalTracksDuration;
    }

    public int getTotalTracksNumber() {
        return this.totalTracksNumber;
    }

    public void setFirstRankDate(String firstRankDate) {
        this.firstRankDate = firstRankDate;
    }

    public void setGenres(HashSet<String> genres) {
        this.genres = genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalANumber(int originalANumber) {
        this.originalANumber = originalANumber;
    }

    public void setOriginalBNumber(int originalBNumber) {
        this.originalBNumber = originalBNumber;
    }

    public void setOriginalCNumber(int originalCNumber) {
        this.originalCNumber = originalCNumber;
    }

    public void setOriginalDNumber(int originalDNumber) {
        this.originalDNumber = originalDNumber;
    }

    public void setOriginalScore(float originalScore) {
        this.originalScore = originalScore;
    }

    public void setOriginalSNumber(int originalSNumber) {
        this.originalSNumber = originalSNumber;
    }

    public void setOriginalTracksDuration(int originalTracksDuration) {
        this.originalTracksDuration = originalTracksDuration;
    }

    public void setOriginalTracksNumber(int originalTracksNumber) {
        this.originalTracksNumber = originalTracksNumber;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setRemixANumber(int remixANumber) {
        this.remixANumber = remixANumber;
    }

    public void setRemixBNumber(int remixBNumber) {
        this.remixBNumber = remixBNumber;
    }

    public void setRemixCNumber(int remixCNumber) {
        this.remixCNumber = remixCNumber;
    }

    public void setRemixDNumber(int remixDNumber) {
        this.remixDNumber = remixDNumber;
    }

    public void setRemixSNumber(int remixSNumber) {
        this.remixSNumber = remixSNumber;
    }

    public void setRemixTracksDuration(int remixTracksDuration) {
        this.remixTracksDuration = remixTracksDuration;
    }

    public void setRemixTracksNumber(int remixTracksNumber) {
        this.remixTracksNumber = remixTracksNumber;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public void setTotalANumber(int totalANumber) {
        this.totalANumber = totalANumber;
    }

    public void setTotalBNumber(int totalBNumber) {
        this.totalBNumber = totalBNumber;
    }

    public void setTotalCNumber(int totalCNumber) {
        this.totalCNumber = totalCNumber;
    }

    public void setTotalDNumber(int totalDNumber) {
        this.totalDNumber = totalDNumber;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public void setTotalSNumber(int totalSNumber) {
        this.totalSNumber = totalSNumber;
    }

    public void setTotalTracksDuration(int totalTracksDuration) {
        this.totalTracksDuration = totalTracksDuration;
    }

    public void setTotalTracksNumber(int totalTracksNumber) {
        this.totalTracksNumber = totalTracksNumber;
    }
}
