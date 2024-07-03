package business;

import core.Helper;
import dao.ReservationDao;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;

public class ReservationManager {
    private final ReservationDao reservationDao;
    private RoomManager roomManager;
    private Room room;

    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }

    public Reservation getById(int id) {
        return this.reservationDao.getById(id);
    }

    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> rezList) {
        ArrayList<Object[]> rezObjList = new ArrayList<>();
        for (Reservation obj: rezList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getReservationId();
            rowObject[i++] = obj.getHotelId();
            rowObject[i++] = obj.getRoomId();
            rowObject[i++] = obj.getCheckinDate();
            rowObject[i++] = obj.getCheckoutDate();
            rowObject[i++] = obj.getTotalPrice();
            rowObject[i++] = obj.getCustomerName();
            rowObject[i++] = obj.getCitizenNumber();
            rowObject[i++] = obj.getEmail();
            rowObject[i++] = obj.getPhoneNumber();
            rezObjList.add(rowObject);
        }
        return rezObjList;
    }

    public boolean save(Reservation reservation) {
        int saved = this.reservationDao.save(reservation);
        if (saved == -1) {
            Helper.showMessage("error");
            return false;
        }
        return true;
    }

    public boolean update(Reservation reservation) {
        if (this.getById(reservation.getReservationId()) == null) {
            Helper.showMessage(reservation.getReservationId() + "ID kayıtlı rezervasyon bulunamadı.");
            return false;
        }
        return this.reservationDao.update(reservation);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " ID kayıtlı rezervasyon bulunamadı.");
            return false;
        }
        return this.reservationDao.delete(id);
    }
}
