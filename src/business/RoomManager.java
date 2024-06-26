package business;

import core.Helper;
import dao.RoomDao;
import entity.Reservation;
import entity.Room;

import java.time.LocalDate;
import java.util.ArrayList;

public class RoomManager {
    private final RoomDao roomDao = new RoomDao();
    private ReservationManager reservationManager = new ReservationManager();

    public Room getById(int id) {
        return this.roomDao.getById(id);
    }

    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> roomList) {
        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Room obj: roomList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotelId();
            rowObject[i++] = obj.getRoomType();
            rowObject[i++] = obj.getPensionType();
            rowObject[i++] = obj.getPeriod();
            rowObject[i++] = obj.getAdultPrice();
            rowObject[i++] = obj.getChildPrice();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getBedCount();
            rowObject[i++] = obj.getSquareMeters();
            rowObject[i++] = obj.isTv();
            rowObject[i++] = obj.isMinibar();
            rowObject[i++] = obj.isGameConsole();
            rowObject[i++] = obj.isSafe();
            rowObject[i++] = obj.isProjector();
            roomObjList.add(rowObject);
        }
        return roomObjList;
    }

    public boolean save(Room room) {
        if (this.getById(room.getId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.roomDao.save(room);
    }


    public ArrayList<Room> searchForRoom(String hotelName, String city, LocalDate startDate, LocalDate endDate, double adultCount, double childCount) {
        ArrayList<Room> roomList = roomDao.searchForRoom(hotelName, city, startDate, endDate, adultCount, childCount);
        return filterAvailableRooms(roomList, startDate, endDate);
    }

    private ArrayList<Room> filterAvailableRooms(ArrayList<Room> roomList, LocalDate startDate, LocalDate endDate) {
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room: roomList) {
            if (!isRoomReserved(room, startDate, endDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomReserved(Room room, LocalDate startDate, LocalDate endDate) {
        ArrayList<Reservation> reservations = reservationManager.getById(room.getId());

        for(Reservation reservation : reservations) {
            LocalDate checkinDateLocal = reservation.getCheckinDate();
            LocalDate checkoutDate = reservation.getCheckoutDate();
            if (checkoutDate.isBefore(startDate) && checkinDateLocal.isAfter(endDate)) {
                return true;
            }
        }
        return false;
    }

    public void decreaseRoomStock(int roomId) {
        Room room = roomDao.getById(roomId);
        room.setStock(room.getStock() - 1);
        roomDao.update(room);
    }

    public void increaseRoomStock(int roomId) {
        Room room = roomDao.getById(roomId);
        room.setStock(room.getStock() + 1);
        roomDao.update(room);
    }


}
