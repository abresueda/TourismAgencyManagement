package business;

import core.Helper;
import dao.HotelDao;
import entity.Hotel;
import entity.Period;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDao = new HotelDao();

    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }

    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> hotelList) {
        ArrayList<Object[]> hotelObjList = new ArrayList<>();
        for (Hotel obj: hotelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getHotelId();
            rowObject[i++] = obj.getHotelName();
            rowObject[i++] = obj.getCity();
            rowObject[i++] = obj.getRegion();
            rowObject[i++] = obj.getAddress();
            rowObject[i++] = obj.getEmail();
            rowObject[i++] = obj.getPhone();
            rowObject[i++] = obj.getStar();
            rowObject[i++] = obj.isParking();
            rowObject[i++] = obj.isWifi();
            rowObject[i++] = obj.isPool();
            rowObject[i++] = obj.isFitness();
            rowObject[i++] = obj.isConcierge();
            rowObject[i++] = obj.isSpa();
            rowObject[i++] = obj.isRoomService();
            hotelObjList.add(rowObject);
        }
        return hotelObjList;
    }

    public boolean save(Hotel hotel, Period period) {
        if (this.getById(hotel.getHotelId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.hotelDao.save(hotel, period);
    }
}
