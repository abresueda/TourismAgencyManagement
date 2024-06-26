package business;

import core.Helper;
import dao.ReservationDao;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;
import java.util.Date;

public class ReservationManager {
    private final ReservationDao reservationDao = new ReservationDao();
    private RoomManager roomManager = new RoomManager();

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
        Room room = roomManager.getById(reservation.getRoomId());

        if (room.getStock() <= 0) {
            Helper.showMessage("stock");
            return false;
        }

        if (this.getById(reservation.getReservationId()) != null) {
            Helper.showMessage(reservation.getReservationId() + " ID kayıtlı rezervasyon bulunamadı.");
            return false;
        }

        boolean saved = reservationDao.save(reservation);
        if (saved) {
            roomManager.decreaseRoomStock(reservation.getRoomId());
        }
        return saved;
    }

    public boolean update(Reservation reservation) {
        Reservation existingReservation = reservationDao.getById(reservation.getReservationId());
        if (existingReservation == null) {
            Helper.showMessage("notFound");
            return false;
        }

        //Oda stoğunu geri yüklemek için.
        roomManager.increaseRoomStock(existingReservation.getRoomId());

        calculateAndSetTotalPrice(reservation, reservation.getAdultCount(), reservation.getChildCount());

        boolean updated = reservationDao.update(reservation);
        if (updated) {
            roomManager.decreaseRoomStock(reservation.getRoomId());
        }
        return updated;
    }

    public boolean delete(int id) {
        Reservation reservation = reservationDao.getById(id);
        if (this.getById(id) == null) {
            Helper.showMessage(id + " ID kayıtlı rezervasyon bulunamadı.");
            return false;
        }
        boolean deleted = reservationDao.delete(id);
        if (deleted) {
            roomManager.increaseRoomStock(reservation.getRoomId());
        }
        return deleted;
    }

    public void calculateAndSetTotalPrice(Reservation reservation, int adultCount, int childCount) {
        Date checkinDate = reservation.getCheckinDate();
        Date checkoutDate = reservation.getCheckoutDate();
        int nights = (int) ((checkoutDate.getTime() - checkinDate.getTime()) / (1000*60*60*24));

        Room room = roomManager.getById(reservation.getRoomId());
        double adultPrice = room.getAdultPrice();
        double childPrice = room.getChildPrice();
        reservation.calculateTotalPrice(adultCount, childCount, adultPrice, childPrice, nights);
    }
}
