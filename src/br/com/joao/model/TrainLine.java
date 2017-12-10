package br.com.joao.model;

import java.util.Objects;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 10 Dec.
 */
public class TrainLine {

    private Station station;

    private Train train;

    private String departureTime, returnTime;

    /**
     * Creates a train line.
     *
     * @param station Station.
     * @param train Train.
     * @param departureTime Departure time.
     * @param returnTime Return time.
     */
    public TrainLine(final Station station, final Train train, final String departureTime, final String returnTime) {
        this.station = station;
        this.train = train;
        this.departureTime = departureTime;
        this.returnTime = returnTime;
    }

    /**
     * @return The station.
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station.
     *
     * @param station Station to set.
     */
    public void setStation(final Station station) {
        this.station = station;
    }

    /**
     * @return The train.
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Sets the train.
     *
     * @param train Train to set.
     */
    public void setTrain(final Train train) {
        this.train = train;
    }

    /**
     * @return The departure time.
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time.
     *
     * @param departureTime Departure time to set.
     */
    public void setDepartureTime(final String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return The return time.
     */
    public String getReturnTime() {
        return returnTime;
    }

    /**
     * Sets the return time.
     *
     * @param returnTime Return time to set.
     */
    public void setReturnTime(final String returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.station);
        hash = 59 * hash + Objects.hashCode(this.train);
        hash = 59 * hash + Objects.hashCode(this.departureTime);
        hash = 59 * hash + Objects.hashCode(this.returnTime);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof TrainLine) {
            TrainLine line = (TrainLine) obj;
            return line.departureTime.equals(departureTime) && line.returnTime.equals(returnTime)
                    && line.station.equals(station) && line.train.equals(train);
        }
        return false;
    }

}
