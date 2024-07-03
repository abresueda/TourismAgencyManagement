package business;

import core.Helper;
import dao.RoomDao;
import entity.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomManager {
    private final RoomDao roomDao;
    private ReservationManager reservationManager;

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
        int saved = this.roomDao.save(room);
        if (saved == -1) {
            Helper.showMessage("error");
            return false;
        }
        return true;
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

    public boolean updateRoomStock(Room room) {
        return this.roomDao.updateRoomStock(room);
    }

    //Odalar sekmesinde arama butonu için yazıldı.
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
}
