package entity;

import java.time.LocalDate;

public class Period {
    private int periodId;
    private int hotelId;
    private LocalDate startDate;
    private LocalDate endDate;

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Period{" +
                "periodId=" + periodId +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
