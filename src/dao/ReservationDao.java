package dao;

import core.Db;
import entity.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection;
    private RoomDao roomDao;
    private HotelDao hotelDao;

    public ReservationDao() {
        this.connection = Db.getInstance();
        this.roomDao = new RoomDao();
        this.hotelDao = new HotelDao();

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


    public int save(Reservation reservation) {
        String query = "INSERT INTO public.reservation (hotel_id, room_id, customer_name, citizen_number, " +
                "email, phone_number, check_in, check_out, total_price) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            // Statement.RETURN_GENERATED_KEYS ile PreparedStatement oluşturuyoruz
            PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setInt(1, reservation.getHotelId());
            pr.setInt(2, reservation.getRoomId());
            pr.setString(3, reservation.getCustomerName());
            pr.setString(4, reservation.getCitizenNumber());
            pr.setString(5, reservation.getEmail());
            pr.setString(6, reservation.getPhoneNumber());
            pr.setDate(7, Date.valueOf(reservation.getCheckinDate()));
            pr.setDate(8, Date.valueOf(reservation.getCheckoutDate()));
            pr.setDouble(9, reservation.getTotalPrice());

            int affectedRows = pr.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Rezervasyon ID'sini döndürüyoruz
                    } else {
                        throw new SQLException("Rezervasyon ID alınamadı, kayıt başarısız oldu.");
                    }
                }
            } else {
                throw new SQLException("Kayıt başarısız oldu, etkilenen satır yok.");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return -1; // Başarısızlık durumunda -1 döndürüyoruz
    }

    public boolean update(Reservation reservation) {
        String query = "UPDATE public.reservation SET room_id = ?, customer_name = ?, citizen_number = ?, email = ?, " +
                "phone_number = ?, check_in = ?, check_out = ?, total_price = ? WHERE rez_id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            //pr.setInt(1, reservation.getHotelId());
            pr.setInt(1, reservation.getRoomId());
            pr.setString(2, reservation.getCustomerName());
            pr.setString(3, reservation.getCitizenNumber());
            pr.setString(4, reservation.getEmail());
            pr.setString(5, reservation.getPhoneNumber());
            pr.setDate(6, Date.valueOf(reservation.getCheckinDate()));
            pr.setDate(7, Date.valueOf(reservation.getCheckoutDate()));
            pr.setInt(8, reservation.getTotalPrice());
            pr.setInt(9, reservation.getReservationId());
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
        String checkinDateString = rs.getString("check_in");
        if (checkinDateString != null && !checkinDateString.isEmpty()) {
            LocalDate checkinDate = LocalDate.parse(checkinDateString);
            reservation.setCheckinDate(checkinDate);
        } else {
            reservation.setCheckinDate(null);
        }

        String checkoutDateString = rs.getString("check_out");
        if (checkoutDateString != null && !checkoutDateString.isEmpty()) {
            LocalDate checkoutDate = LocalDate.parse(checkoutDateString);
            reservation.setCheckoutDate(checkoutDate);
        } else {
            reservation.setCheckoutDate(null);
        }
        reservation.setTotalPrice(rs.getInt("total_price"));
        return reservation;
    }
}
