package entity;

import core.ComboItem;

public class Room {
    private int id;
    private int hotelId;
    private String roomType;
    private String pensionType;
    private String period;
    private double adultPrice;
    private double childPrice;
    private int stock;
    private int bedCount;
    private int squareMeters;
    private boolean tv;
    private boolean minibar;
    private boolean gameConsole;
    private boolean safe;
    private boolean projector;

    public enum RoomType {
        SINGLE_ROOM,
        DELUXE_ROOM,
        SUITE_ROOM,
        FAMILY_ROOM,
        KING_ROOM
    }
    public enum PensionType {
        ultra_all_inclusive,
        all_inclusive,
        bed_and_breakfast,
        full_board,
        half_board,
        bed_only,
        non_alcohol_full_credit
    }

    public Room() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = String.valueOf(roomType);
    }

    public String getPensionType() {
        return pensionType;
    }

    public void setPensionType(PensionType pensionType) {
        this.pensionType = String.valueOf(pensionType);
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(boolean gameConsole) {
        this.gameConsole = gameConsole;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public ComboItem getComboItem() {
        return new ComboItem(this.getId(), this.getHotelId() + "-" + this.getRoomType() + "-" + this.getPensionType());
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", roomType='" + roomType + '\'' +
                ", pensionType='" + pensionType + '\'' +
                ", period='" + period + '\'' +
                ", adultPrice=" + adultPrice +
                ", childPrice=" + childPrice +
                ", stock=" + stock +
                ", bedCount=" + bedCount +
                ", squareMeters=" + squareMeters +
                ", tv=" + tv +
                ", minibar=" + minibar +
                ", gameConsole=" + gameConsole +
                ", safe=" + safe +
                ", projector=" + projector +
                '}';
    }
}
