package entity;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private int hotelId;
    private int roomId;
    private String customerName;
    private String citizenNumber;
    private String email;
    private String phoneNumber;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private int totalPrice;
    private int adultCount;
    private int childCount;

    public Reservation() {
    }

    public Reservation(int reservationId, int hotelId, int roomId, String customerName, String citizenNumber, String email, String phoneNumber,
                       LocalDate checkinDate, LocalDate checkoutDate, int totalPrice, int adultCount, int childCount) {
        this.reservationId = reservationId;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.customerName = customerName;
        this.citizenNumber = citizenNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.totalPrice = totalPrice;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCitizenNumber() {
        return citizenNumber;
    }

    public void setCitizenNumber(String citizenNumber) {
        this.citizenNumber = citizenNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public void calculateTotalPrice(int adultCount, int childCount, double adultPrice, double childPrice, int nights) {
        this.totalPrice = (int) ((adultCount * adultPrice + childCount * childPrice) *  nights);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", hotelId=" + hotelId +
                ", roomId=" + roomId +
                ", customerName='" + customerName + '\'' +
                ", citizenNumber='" + citizenNumber + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", checkinDate=" + checkinDate +
                ", checkoutDate=" + checkoutDate +
                ", totalPrice=" + totalPrice +
                ", adultCount=" + adultCount +
                ", childCount=" + childCount +
                '}';
    }
}
