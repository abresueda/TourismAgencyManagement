package dao;

import core.Db;
import entity.Room;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomDao {
    private Connection connection;
    //private final RoomDao roomDao;

    public RoomDao() {
        this.connection = Db.getInstance();
        //this.roomDao = new RoomDao();
    }

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

    public boolean save(Room room) {
        String query = "INSERT INTO public.room (hotel_id, room_type, pension_type, period, adult_price, child_price, stock, bed_count, " +
                "squaremeters, tv, minibar, game_console, safe, projector) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
            return pr.executeUpdate() != -1;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
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

        /*try {
            PreparedStatement pr = connection.prepareStatement(query);
            /*pr.setString(1, "%" + city.toLowerCase() + "%"); //%...% formatında karşılaştırma yapılacak şekle dönüştürülür.
            pr.setString(2, "%" + hotelName.toLowerCase() + "%");
            pr.setObject(3, startDate);
            pr.setObject(4, endDate);*/

            /*for (int i = 0; i< params.size(); i++) {
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
    }*/

    //Revize sonrası eklendi.
    public ArrayList<Room> findRoomByHotelId (int selectedHotelId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM public.room WHERE hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, selectedHotelId);
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                rooms.add(this.match(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return rooms;
    }

    //Revizeden sonra eklendi.
    public boolean updateRoomStock(int roomStock, Room room) {
        String query = "UPDATE room SET stock = ? WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, roomStock);
            pr.setInt(2, room.getId());
            ResultSet rs = pr.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
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
