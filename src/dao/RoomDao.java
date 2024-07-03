package dao;

import core.Db;
import entity.Reservation;
import entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class RoomDao {
    private final Connection connection;

    public RoomDao() {
        this.connection = Db.getInstance();
        //this.roomDao = new RoomDao();
    }

    //ID'ye göre odaları getiren metot.
    public Room getById(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
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

    public int save(Room room) {
        String query = "INSERT INTO public.room (hotel_id, room_type, pension_type, period, adult_price, child_price, stock, bed_count, " +
                "squaremeters, tv, minibar, game_console, safe, projector) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, room.getHotelId());
            pr.setString(2, room.getRoomType().toString());
            pr.setString(3, room.getPensionType().toString());
            pr.setString(4, room.getPeriod());
            pr.setDouble(5, room.getAdultPrice());
            pr.setDouble(6, room.getChildPrice());
            pr.setInt(7, room.getStock());
            pr.setInt(8, room.getBedCount());
            pr.setInt(9, room.getSquareMeters());
            pr.setBoolean(10, room.isTv());
            pr.setBoolean(11, room.isMinibar());
            pr.setBoolean(12, room.isGameConsole());
            pr.setBoolean(13, room.isSafe());
            pr.setBoolean(14, room.isProjector());

            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); //Room ID'yi döndürür.
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return -1; //Hata durumunda -1 döndür.
    }

    public boolean update(Room room) {
        String query = "UPDATE public.room SET hotel_id = ?, room_type = ?, pension_type = ?, period = ?, adult_price = ?, child_price = ?, stock = ?, bed_count = ?, " +
                       "squaremeters = ?, tv = ?, minibar = ?, game_console = ?, safe = ?, projector = ? WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, room.getHotelId());
            pr.setString(2, room.getRoomType());
            pr.setString(3, room.getPensionType());
            pr.setString(4, room.getPeriod());
            pr.setDouble(5, room.getAdultPrice());
            pr.setDouble(6, room.getChildPrice());
            pr.setInt(7, room.getStock());
            pr.setInt(8, room.getBedCount());
            pr.setInt(9, room.getSquareMeters());
            pr.setBoolean(10, room.isTv());
            pr.setBoolean(11, room.isMinibar());
            pr.setBoolean(12, room.isGameConsole());
            pr.setBoolean(13, room.isSafe());
            pr.setBoolean(14, room.isProjector());
            pr.setInt(15, room.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "DELETE from public.room WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //Oda Stoğunu kontrol etmek için yazılan metot.
    public boolean updateRoomStock(Room room) {
        String checkQuery = "SELECT stock FROM public.room WHERE id = ?";
        String updateQuery = "UPDATE public.room SET stock = ? WHERE id = ?";

        try {
            PreparedStatement checkPr = this.connection.prepareStatement(checkQuery);
            checkPr.setInt(1, room.getId());
            ResultSet rs = checkPr.executeQuery();

            if (rs.next()) {
                int currentStock = rs.getInt("stock");
                if (room.getStock() < 0) {
                    //Stok 0'ın altına düşerse, işlemi iptal eder.
                    return false;
                }
            }

            PreparedStatement pr = this.connection.prepareStatement(updateQuery);
            pr.setInt(1, room.getStock());
            pr.setInt(2, room.getId());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
