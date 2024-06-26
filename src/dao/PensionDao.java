package dao;

import core.Db;

import entity.Pension;
import entity.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PensionDao {
    private Connection connection;


    public PensionDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Pension> findAll() {
        return this.selectByQuery("SELECT * FROM public.pension ORDER BY pension_type_id ASC");
    }

    //Tablo için veri hazırlanıyor.
    /*public ArrayList<Object[]> getForTable(int size, ArrayList<Pension> pensionList) {
        ArrayList<Object[]> pensionObjList = new ArrayList<>();
        for (Pension obj: pensionList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getPensionTypeId();
            rowObject[i++] = obj.getHotelId();
            rowObject[i++] = getPensionType(obj);
            pensionObjList.add(rowObject);
        }
        return pensionObjList;
    }

    //Pension nesnesinin özelliklerine göre uygun tipi döndürüyor.
    public Object getPensionType(Pension pension) {
        if (pension.isUltraAllInclusive()) return "Ultra All Inclusive";
        if (pension.isAllInclusive()) return "All Inclusive";
        if (pension.isBedAndBreakfast()) return "Bed And Breakfast";
        if (pension.isFullBoard()) return "Full Board";
        if (pension.isHalfBoard()) return "Half Board";
        if (pension.isBedOnly()) return "Bed Only";
        if (pension.isNonAlcoholFullCredit()) return "Non Alcohol Full Credit";
        return "Unknown";
    }*/

    public ArrayList<Pension> selectByQuery(String query) {
        ArrayList<Pension> pensionList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                pensionList.add(this.match(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return pensionList;
    }

    //Oteller ve onların pansiyon tiplerini hakkında bilgi alarak bir liste olarak döndürür.
    public List<Object[]> findAllHotelsWithPensionTypes(int columnCount) {
        List<Object[]> pensionList = new ArrayList<>();
        String query = "SELECT p.pension_type_id, p.hotel_id, " +
                       //CASE ifadesi ile pansiyon tipi true olduğunda, uygun pansiyon tipine döndürülür.
                       "CASE " +
                       "WHEN p.ultra_all_inclusive THEN 'Ultra All Inclusive' " +
                       "WHEN p.all_inclusive THEN 'All Inclusive' " +
                       "WHEN p.bed_and_breakfast THEN 'Bed And Breakfast' " +
                       "WHEN p.full_board THEN 'Full Board' " +
                       "WHEN p.half_board THEN 'Half Board' " +
                       "WHEN p.bed_only THEN 'Bed Only' " +
                       "WHEN p.non_alcohol_full_credit THEN 'Non Alcohol Full Credit' " +
                       "END as pension_type " +
                       "FROM public.pension p " +
                       "ORDER BY p.hotel_id";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                //ResultSet'ten her satır için dizi oluşturulup pensionList'e eklenir.
                Object[] row = new Object[columnCount];
                int i = 0;
                row[i++] = rs.getInt("pension_type_id");
                row[i++] = rs.getInt("hotel_id");
                row[i++] = rs.getString("pension_type");

                pensionList.add(row);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionList;
    }

    public Pension match(ResultSet rs) throws SQLException {
        Pension pension = new Pension();
        pension.setPensionTypeId(rs.getInt("pension_type_id"));
        pension.setHotelId(rs.getInt("hotel_id"));
        pension.setUltraAllInclusive(rs.getBoolean("ultra_all_inclusive"));
        pension.setAllInclusive(rs.getBoolean("all_inclusive"));
        pension.setBedAndBreakfast(rs.getBoolean("bed_and_breakfast"));
        pension.setFullBoard(rs.getBoolean("full_board"));
        pension.setHalfBoard(rs.getBoolean("half_board"));
        pension.setBedOnly(rs.getBoolean("bed_only"));
        pension.setNonAlcoholFullCredit(rs.getBoolean("non_alcohol_full_credit"));
        return pension;
    }

}
