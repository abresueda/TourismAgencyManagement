package entity;

public class Pension {
    private int pensionTypeId;
    private int hotelId;
    private boolean ultraAllInclusive;
    private boolean allInclusive;
    private boolean bedAndBreakfast;
    private boolean fullBoard;
    private boolean halfBoard;
    private boolean bedOnly;
    private boolean nonAlcoholFullCredit;

    public int getPensionTypeId() {
        return pensionTypeId;
    }

    public void setPensionTypeId(int pensionTypeId) {
        this.pensionTypeId = pensionTypeId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public boolean isUltraAllInclusive() {
        return ultraAllInclusive;
    }

    public void setUltraAllInclusive(boolean ultraAllInclusive) {
        this.ultraAllInclusive = ultraAllInclusive;
    }

    public boolean isAllInclusive() {
        return allInclusive;
    }

    public void setAllInclusive(boolean allInclusive) {
        this.allInclusive = allInclusive;
    }

    public boolean isBedAndBreakfast() {
        return bedAndBreakfast;
    }

    public void setBedAndBreakfast(boolean bedAndBreakfast) {
        this.bedAndBreakfast = bedAndBreakfast;
    }

    public boolean isFullBoard() {
        return fullBoard;
    }

    public void setFullBoard(boolean fullBoard) {
        this.fullBoard = fullBoard;
    }

    public boolean isHalfBoard() {
        return halfBoard;
    }

    public void setHalfBoard(boolean halfBoard) {
        this.halfBoard = halfBoard;
    }

    public boolean isBedOnly() {
        return bedOnly;
    }

    public void setBedOnly(boolean bedOnly) {
        this.bedOnly = bedOnly;
    }

    public boolean isNonAlcoholFullCredit() {
        return nonAlcoholFullCredit;
    }

    public void setNonAlcoholFullCredit(boolean nonAlcoholFullCredit) {
        this.nonAlcoholFullCredit = nonAlcoholFullCredit;
    }

    @Override
    public String toString() {
        return "Pension{" +
                "pensionTypeId=" + pensionTypeId +
                ", hotelId=" + hotelId +
                ", ultraAllInclusive=" + ultraAllInclusive +
                ", allInclusive=" + allInclusive +
                ", bedAndBreakfast=" + bedAndBreakfast +
                ", fullBoard=" + fullBoard +
                ", halfBoard=" + halfBoard +
                ", bedOnly=" + bedOnly +
                ", nonAlcoholFullCredit=" + nonAlcoholFullCredit +
                '}';
    }
}
