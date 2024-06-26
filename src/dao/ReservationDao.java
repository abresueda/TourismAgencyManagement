package dao;

import core.Db;
import entity.Reservation;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection;

    public ReservationDao() {
        this.connection = Db.getInstance();
    }

    public Reservation getById(int id) {
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE rez_id = ?";

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

    public ArrayList<Reservation> findAll() {
        return this.selectByQuery("SELECT * FROM public.reservation ORDER BY rez_id ASC");
    }

    public ArrayList<Reservation> selectByQuery(String query) {
        ArrayList<Reservation> rezList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                rezList.add(this.match(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return rezList;
    }

    public boolean save(Reservation reservation) {
        String query = "INSERT INTO public.reservation (hotel_id, room_id, customer_name, citizen_number, " +
                       "email, phone_number, check_in, check_out, total_price) " +
                       "VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, reservation.getHotelId());
            pr.setInt(2, reservation.getRoomId());
            pr.setString(3, reservation.getCustomerName());
            pr.setString(4, reservation.getCitizenNumber());
            pr.setString(5, reservation.getEmail());
            pr.setString(6, reservation.getPhoneNumber());
            pr.setDate(7, Date.valueOf(String.valueOf(reservation.getCheckinDate())));
            pr.setDate(8, Date.valueOf(String.valueOf(reservation.getCheckoutDate())));
            pr.setDouble(9, reservation.getTotalPrice());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
    }

    public boolean update(Reservation reservation) {
        String query = "UPDATE public.reservation SET hotel_id = ?, room_id = ?, customer_name = ?, citizen_number = ?, email = ?, " +
                "phone_number = ?, check_in = ?, check_out = ?, total_price = ? WHERE rez_id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, reservation.getHotelId());
            pr.setInt(2, reservation.getRoomId());
            pr.setString(3, reservation.getCustomerName());
            pr.setString(4, reservation.getCitizenNumber());
            pr.setString(5, reservation.getEmail());
            pr.setString(6, reservation.getPhoneNumber());
            pr.setDate(7, Date.valueOf(String.valueOf(reservation.getCheckinDate())));
            pr.setDate(8, Date.valueOf(String.valueOf(reservation.getCheckoutDate())));
            pr.setInt(9, reservation.getTotalPrice());
            pr.setInt(10, reservation.getReservationId());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
    }

    public boolean delete(int rez_id) {
        String query = "DELETE FROM public.reservation WHERE rez_id = ?";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, rez_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
    }

    public Reservation match(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rs.getInt("rez_id"));
        reservation.setHotelId(rs.getInt("hotel_id"));
        reservation.setRoomId(rs.getInt("room_id"));
        reservation.setCustomerName(rs.getString("customer_name"));
        reservation.setCitizenNumber(rs.getString("citizen_number"));
        reservation.setEmail(rs.getString("email"));
        reservation.setPhoneNumber(rs.getString("phone_number"));
        reservation.setCheckinDate(rs.getDate("check_in"));
        reservation.setCheckoutDate(rs.getDate("check_out"));
        reservation.setTotalPrice(rs.getInt("total_price"));
        return reservation;
    }
}
