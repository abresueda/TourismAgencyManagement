package dao;

import core.Db;
import core.Helper;
import entity.Room;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private Connection connection;

    public RoomDao() {
        this.connection = Db.getInstance();
    }

    public Room getById(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room ORDER BY hotel_id ASC");

    }

    public ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                roomList.add(this.match(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return roomList;
    }

    public boolean save(Room room) {
        String query = "INSERT INTO public.room (id, hotel_id, room_type, pension_type, period, adult_price, child_price, stock, bed_count, " +
                "squaremeters, tv, minibar, game_console, safe, projector) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, room.getId());
            pr.setInt(2, room.getHotelId());
            pr.setString(3, room.getRoomType().toString());
            pr.setString(4, room.getPensionType().toString());
            pr.setString(5, room.getPeriod());
            pr.setDouble(6, room.getAdultPrice());
            pr.setDouble(7, room.getChildPrice());
            pr.setInt(8, room.getStock());
            pr.setInt(9, room.getBedCount());
            pr.setInt(10, room.getSquareMeters());
            pr.setBoolean(11, room.isTv());
            pr.setBoolean(12, room.isMinibar());
            pr.setBoolean(13, room.isGameConsole());
            pr.setBoolean(14, room.isSafe());
            pr.setBoolean(15, room.isProjector());
            //return pr.executeUpdate() != -1;

            int result = pr.executeUpdate();

            if (result != 1) {
                Helper.showMessage("error");
                return false;
            }
            return true;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Helper.showMessage("error");
            return false;
        }
    }

    public boolean update(Room room) {
        String query = "UPDATE public.room SET stock = ?, adult_price = ?, child_price = ? WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, room.getStock());
            pr.setDouble(2, room.getAdultPrice());
            pr.setDouble(3, room.getChildPrice());
            pr.setInt(4, room.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Room> searchForRoom(String hotelName, String city, LocalDate startDate, LocalDate endDate, double adult, double child) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT r.id, r.hotel_id, r.room_type, r.pension_type, r.period, r.adult_price, r.child_price, r.stock, " +
                "r.bed_count, r.squaremeters, r.tv, r.minibar, r.game_console, r.safe, r.projector " +
                "FROM public.room r " +
                "JOIN public.hotel h ON r.hotel_id = h.hotel_id " +
                "JOIN public.period p ON r.hotel_id = p.hotel_id " +
                 "WHERE 1=1"; //Her zaman 'true' olur.

        //null veya boş değilse sorguya dahil edilirler.
        List <Object> params = new ArrayList<>();
        if (city != null && !city.trim().isEmpty()) {
            query+= "AND LOWER(h.hotel_city) LIKE LOWER (?) "; //city parametresi küçük harfe dönüştürülür ve LIKE operatörüyle karşılaştırılır.
            params.add("%" + city.toLowerCase() + "%");
        }
        if (hotelName != null && !hotelName.trim().isEmpty()) {
            query+= "AND LOWER(h.hotel_name) LIKE LOWER (?) ";
            params.add("%" + hotelName.toLowerCase() + "%");
        }
        if (startDate != null && endDate != null) {
            query+= "AND (p.start_date <= ?::date AND p.end_date >= ?::date)";
            params.add(Date.valueOf(endDate));
            params.add(Date.valueOf(startDate));
        }

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            /*pr.setString(1, "%" + city.toLowerCase() + "%"); //%...% formatında karşılaştırma yapılacak şekle dönüştürülür.
            pr.setString(2, "%" + hotelName.toLowerCase() + "%");
            pr.setObject(3, startDate);
            pr.setObject(4, endDate);*/

            for (int i = 0; i< params.size(); i++) {
                pr.setObject(i+1, params.get(i));
            }
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setHotelId(rs.getInt("hotel_id"));
                room.setRoomType(Room.RoomType.valueOf(rs.getString("room_type")));
                room.setPensionType(Room.PensionType.valueOf(rs.getString("pension_type")));
                room.setPeriod(rs.getString("period"));
                room.setAdultPrice(rs.getDouble("adult_price"));
                room.setChildPrice(rs.getDouble("child_price"));
                room.setStock(rs.getInt("stock"));
                room.setBedCount(rs.getInt("bed_count"));
                room.setSquareMeters(rs.getInt("squaremeters"));
                room.setTv(rs.getBoolean("tv"));
                room.setMinibar(rs.getBoolean("minibar"));
                room.setGameConsole(rs.getBoolean("game_console"));
                room.setSafe(rs.getBoolean("safe"));
                room.setProjector(rs.getBoolean("projector"));
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public Room match(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setHotelId(rs.getInt("hotel_id"));
        room.setRoomType(Room.RoomType.valueOf(rs.getString("room_type")));
        room.setPensionType(Room.PensionType.valueOf(rs.getString("pension_type")));
        room.setPeriod(rs.getString("period"));
        room.setAdultPrice(rs.getDouble("adult_price"));
        room.setChildPrice(rs.getDouble("child_price"));
        room.setStock(rs.getInt("stock"));
        room.setBedCount(rs.getInt("bed_count"));
        room.setSquareMeters(rs.getInt("squaremeters"));
        room.setTv(rs.getBoolean("tv"));
        room.setMinibar(rs.getBoolean("minibar"));
        room.setGameConsole(rs.getBoolean("game_console"));
        room.setSafe(rs.getBoolean("safe"));
        room.setProjector(rs.getBoolean("projector"));
        return room;
    }
}
