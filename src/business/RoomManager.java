package business;

import core.Helper;
import dao.RoomDao;
import entity.Reservation;
import entity.Room;
import business.ReservationManager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class RoomManager {
    private final RoomDao roomDao;
    private ReservationManager reservationManager;
    //private ReservationManager reservationManager = new ReservationManager();

    public RoomManager() {
        this.roomDao = new RoomDao();
    }

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

    public boolean update(Room room) {
        if (this.getById(room.getId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.roomDao.update(room);
    }

    public boolean delete(int id) {
        Room room = this.roomDao.getById(id);
        if (this.getById(id) == null) {
            Helper.showMessage(id + "ID kayıtlı oda bulunmamaktadır.");
            return false;
        }
        return this.roomDao.delete(id);
    }

    public ArrayList<Room> findRoomByHotelId(int hotelId) {
        return this.roomDao.findRoomByHotelId(hotelId);
    }

    public boolean updateRoomStock(int roomStock, Room room) {
        return this.roomDao.updateRoomStock(roomStock, room);
    }

    //Revize sonrası buraya taşındı.
    public ArrayList<Room> searchForRoom(String hotelName, String city, Date startDate, Date endDate) {
            //ArrayList<Room> roomList = new ArrayList<>();
            String query = "SELECT r.id, r.hotel_id, r.room_type, r.pension_type, r.period, r.adult_price, r.child_price, r.stock, " +
                    "r.bed_count, r.squaremeters, r.tv, r.minibar, r.game_console, r.safe, r.projector " +
                    "FROM public.room r " +
                    "JOIN public.hotel h ON r.hotel_id = h.hotel_id " +
                    "JOIN public.period p ON r.hotel_id = p.hotel_id ";
            //"WHERE 1=1"; //Her zaman 'true' olur.

            //null veya boş değilse sorguya dahil edilirler.
            ArrayList<String> conditions = new ArrayList<>();

            if (city != null && !city.trim().isEmpty()) {
                conditions.add("LOWER(h.hotel_city) ILIKE LOWER('%" + city.toLowerCase() + "%')"); //city parametresi küçük harfe dönüştürülür ve LIKE operatörüyle karşılaştırılır.

            }

            if (hotelName != null && !hotelName.trim().isEmpty()) {
                conditions.add("LOWER(h.hotel_name) ILIKE LOWER ('%" + hotelName.toLowerCase() + "%')");
            }

            if (startDate != null && endDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                conditions.add("p.start_date <= '" + sdf.format(startDate) + "'");
                conditions.add("p.end_date >= '" + sdf.format(endDate) + "'");
            }

            if (!conditions.isEmpty()) {
                query += " WHERE " + String.join(" AND ", conditions);
            }

            return this.roomDao.selectByQuery(query.toString());
        }


        /*ArrayList<Room> roomList = roomDao.searchForRoom(hotelName, city, startDate, endDate, adultCount, childCount);
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : roomList) {
            if (!isRoomReserved(room, startDate, endDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
        //return filterAvailableRooms(roomList, startDate, endDate);
    }

    /*private ArrayList<Room> filterAvailableRooms(ArrayList<Room> roomList, LocalDate startDate, LocalDate endDate) {
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room: roomList) {
            if (!isRoomReserved(room, startDate, endDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }*/

    /*private boolean isRoomReserved(Room room, LocalDate startDate, LocalDate endDate) {
        if (reservationManager == null) {
            reservationManager = new ReservationManager(new RoomManager());
        }
        ArrayList<Reservation> reservations = this.reservationManager.findByRoomId(room.getId());

        for(Reservation reservation : reservations) {
            LocalDate checkinDate = reservation.getCheckinDate();
            LocalDate checkoutDate = reservation.getCheckoutDate();

            //Rezervasyonun bitiş tarihi, arama başlangıç tarihinden önce ise ve
            //rezervasyonun başlangıç tarihi, arama bitiş tarihinden sonra ise oda rezerve edilmiş demektir.
            if (!checkoutDate.isBefore(startDate) && !checkinDate.isAfter(endDate)) {
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
    }*/


}
