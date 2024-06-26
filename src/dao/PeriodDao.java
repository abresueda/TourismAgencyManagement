package dao;

import core.Db;
import entity.Period;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodDao {
    private Connection connection;

    public PeriodDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Period> findAll() {
        return this.selectByQuery("SELECT * FROM public.period");

    }

    public ArrayList<Period> selectByQuery(String query) {
        ArrayList<Period> periodList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                periodList.add(this.match(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return periodList;
    }

    public Period match(ResultSet rs) throws SQLException {
        Period period = new Period();
        period.setPeriodId(rs.getInt("period_id"));
        period.setHotelId(rs.getInt("hotel_id"));
        period.setStartDate(rs.getDate("start_date").toLocalDate());
        period.setEndDate(rs.getDate("end_date").toLocalDate());
        return period;
    }
}
