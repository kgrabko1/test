package com.jpmc.theater;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TreeSet;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay());
    }

    private double getDiscount(int showDay) {
    	
    	TreeSet<Double> nums = new TreeSet<Double>();
    	
    	double timeDiscount = 0 ;
       	int hour = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault()).getHour() ;
    	if(hour <= 16 && hour >= 11)     
    	{
    		timeDiscount = ticketPrice * 0.25;  // 25% discount for time win
    		nums.add(timeDiscount);
    	}
    	
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
            nums.add(specialDiscount);
        }

        double dayDiscount = 0;
        if (showDay == 1) {
            dayDiscount = 3; // $3 discount for 1st show
        } else if (showDay == 2) {

            dayDiscount = 2; // $2 discount for 2nd show
        } else if (showDay == 7) {

            dayDiscount = 1; // $1 discount for 7nd show
        }
        
        nums.add(dayDiscount);
        
//        else {
//            throw new IllegalArgumentException("failed exception");
//        }

        // biggest discount wins
       
        return  nums.last();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}