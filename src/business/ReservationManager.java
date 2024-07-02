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

    public ArrayList<Reservation> findByRoomId(int roomId) {
        return this.reservationDao.findByRoomId(roomId);
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
        //Room room = this.roomManager.getById(reservation.getRoomId());

        /*if (!isRoomAvailable(reservation.getRoomId(), reservation.getCheckinDate(), reservation.getCheckoutDate())) {
            Helper.showMessage("stock");
            return false;
        }

        /*if (this.getById(reservation.getReservationId()) != null) {
            Helper.showMessage(reservation.getReservationId() + " ID kayıtlı rezervasyon bulunamadı.");
            return false;
        }

        boolean saved = reservationDao.save(reservation);
        if (saved) {
            this.roomManager.decreaseRoomStock(reservation.getRoomId());
        }
        return saved;*/
        return this.reservationDao.save(reservation);
    }

    public boolean update(Reservation reservation) {
        /*Reservation existingReservation = this.reservationDao.getById(reservation.getReservationId());
        if (existingReservation == null) {
            Helper.showMessage("notFound");
            return false;
        }

        //Oda stoğunu geri yüklemek için.
        this.roomManager.increaseRoomStock(existingReservation.getRoomId());

        if (!isRoomAvailable(reservation.getRoomId(), reservation.getCheckinDate(), reservation.getCheckoutDate())) {
            Helper.showMessage("stock");
            return false;
        }

        calculateAndSetTotalPrice(reservation, reservation.getAdultCount(), reservation.getChildCount());

        boolean updated = this.reservationDao.update(reservation);
        if (updated) {
            this.roomManager.decreaseRoomStock(reservation.getRoomId());
        }
        return updated;*/
        return this.reservationDao.update(reservation);
    }

    public boolean delete(int id) {
        //Reservation reservation = this.reservationDao.getById(id);
        if (this.getById(id) == null) {
            Helper.showMessage(id + " ID kayıtlı rezervasyon bulunamadı.");
            return false;
        }
        /*boolean deleted = this.reservationDao.delete(id);
        if (deleted) {
            this.roomManager.increaseRoomStock(reservation.getRoomId());
        }
        return deleted;*/
        return this.reservationDao.delete(id);
    }

    /*public void calculateAndSetTotalPrice(Reservation reservation, int adultCount, int childCount) {
        if (roomManager == null) {
            throw new IllegalStateException("Room manager is not initialized");
        }
        LocalDate checkinDate = reservation.getCheckinDate();
        Date checkoutDate = reservation.getCheckoutDate();
        int nights = (int) ((checkoutDate.getTime() - checkinDate.getTime()) / (1000*60*60*24));

        Room room = this.roomManager.getById(reservation.getReservationId());
        double adultPrice = room.getAdultPrice();
        double childPrice = room.getChildPrice();
        reservation.calculateTotalPrice(adultCount, childCount, adultPrice, childPrice, nights);
    }

    //Belirli bir oda için, belirtilen tarihler arasında mevcut stok olup olmadığını kontrol eder.
    private boolean isRoomAvailable(int roomId, Date checkinDate, Date checkoutDate) {
        ArrayList<Reservation> reservations = this.reservationDao.findByRoomId(roomId); //Bütün rezervasyonlar alınır.
        Room room = this.roomManager.getById(roomId);

        int totalReserved = 0;
        for (Reservation reservation : reservations) {
            if (checkinDate.before(reservation.getCheckoutDate()) && checkoutDate.after(reservation.getCheckinDate())) {
                totalReserved++;
            }
        }
        return (room.getStock() - totalReserved) > 0;
    }*/
}
