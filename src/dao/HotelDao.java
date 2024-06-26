package dao;

import business.PeriodManager;
import core.Db;
import entity.Hotel;
import entity.Period;

import java.sql.*;
import java.util.ArrayList;

public class HotelDao {
    private Connection connection;
    private PeriodManager periodManager = new PeriodManager();
    private PeriodDao periodDao = new PeriodDao();
    private Period period;

    public HotelDao() {
        this.connection = Db.getInstance();
    }

    public Hotel getById(int id) {
        Hotel obj = null;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ?";

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

    public ArrayList<Hotel> findAll() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String query = "SELECT * FROM public.hotel\n " +
                "JOIN public.hotel_features\n " +
                "ON public.hotel.hotel_id = public.hotel_features.hotel_id";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Hotel hotel = this.match(rs);
                hotelList.add(hotel);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return hotelList;
    }

    public ArrayList<Hotel> getByListHotelId(int hotelId) {
        return this.selectByQuery("SELECT * FROM public.hotel WHERE hotel_id = " + hotelId);
    }

    public ArrayList<Hotel> selectByQuery(String query) {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                hotelList.add(this.match(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return hotelList;
    }

    public boolean save(Hotel hotel, Period period) {
        String hotelQuery = "INSERT INTO public.hotel (hotel_name, hotel_city, hotel_region, hotel_address, hotel_email, hotel_phone, hotel_star) VALUES (?,?,?,?,?,?,?)";
        String featuresQuery = "INSERT INTO public.hotel_features (hotel_id, parking, wifi, pool, fitness, concierge, spa, room_service) VALUES (?,?,?,?,?,?,?,?)";
        String periodQuery = "INSERT INTO public.period (hotel_id, start_date, end_date) VALUES (?,?,?)";

        try {
            //Veritabanı işlemlerini manuel olarak yönetebilmek için false ayarlanır.
            connection.setAutoCommit(false);

            //generated keys, yeni eklenen otel kaydının ID'sini elde etmeye yarar.
            PreparedStatement hotelPr = connection.prepareStatement(hotelQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            hotelPr.setString(1, hotel.getHotelName());
            hotelPr.setString(2, hotel.getCity());
            hotelPr.setString(3, hotel.getRegion());
            hotelPr.setString(4, hotel.getAddress());
            hotelPr.setString(5, hotel.getEmail());
            hotelPr.setString(6, hotel.getPhone());
            hotelPr.setInt(7, hotel.getStar());
            int affectedRows = hotelPr.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating hotel failed, no rows affected.");
            }

            //Otel bilgileri eklendiyse, eklenen otelin ID'si alınır ve 'Hotel' nesnesine atanır.
            try (ResultSet generatedKeys = hotelPr.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hotel.setHotelId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating hotel failed, no ID obtained.");
                }
            }

            PreparedStatement featuresPr = connection.prepareStatement(featuresQuery);

            featuresPr.setInt(1, hotel.getHotelId());
            featuresPr.setBoolean(2, hotel.isParking());
            featuresPr.setBoolean(3, hotel.isWifi());
            featuresPr.setBoolean(4, hotel.isPool());
            featuresPr.setBoolean(5, hotel.isFitness());
            featuresPr.setBoolean(6, hotel.isConcierge());
            featuresPr.setBoolean(7, hotel.isSpa());
            featuresPr.setBoolean(8, hotel.isRoomService());
            featuresPr.executeUpdate();

            PreparedStatement periodPr = connection.prepareStatement(periodQuery);

            periodPr.setInt(1, hotel.getHotelId());
            periodPr.setDate(2, Date.valueOf(period.getStartDate()));
            periodPr.setDate(3, Date.valueOf(period.getEndDate()));
            periodPr.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Hotel match(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(rs.getInt("hotel_id"));
        hotel.setHotelName(rs.getString("hotel_name"));
        hotel.setCity(rs.getString("hotel_city"));
        hotel.setRegion(rs.getString("hotel_region"));
        hotel.setAddress(rs.getString("hotel_address"));
        hotel.setEmail(rs.getString("hotel_email"));
        hotel.setPhone(rs.getString("hotel_phone"));
        hotel.setStar(rs.getInt("hotel_star"));


        hotel.setParking(rs.getBoolean("parking"));
        hotel.setWifi(rs.getBoolean("wifi"));
        hotel.setPool(rs.getBoolean("pool"));
        hotel.setFitness(rs.getBoolean("fitness"));
        hotel.setConcierge(rs.getBoolean("concierge"));
        hotel.setSpa(rs.getBoolean("spa"));
        hotel.setRoomService(rs.getBoolean("room_service"));


        return hotel;
    }
}
