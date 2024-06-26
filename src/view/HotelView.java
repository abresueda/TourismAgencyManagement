package view;

import business.HotelManager;
import core.Helper;
import entity.Hotel;
import entity.Period;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class HotelView extends Layout {
    private JPanel container;
    private JTextField fld_hotel_name;
    private JTextField fld_city;
    private JTextField fld_region;
    private JTextField fld_address;
    private JTextField fld_email;
    private JTextField fld_phone;
    private JTextField fld_star;
    private JTextField fld_parking;
    private JTextField fld_wifi;
    private JTextField fld_pool;
    private JTextField fld_fitness;
    private JTextField fld_concierge;
    private JTextField fld_spa;
    private JTextField fld_room_service;
    private JButton bttn_save_hotel;
    private JTextField fld_start_date;
    private JTextField fld_end_date;
    private Hotel hotel;
    private HotelManager hotelManager;
    private Period period;

    public HotelView(Hotel hotel) {
        this.hotel = hotel;
        this.hotelManager = new HotelManager();
        this.add(container);
        this.guiInitilaze(400,700);

        bttn_save_hotel.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_hotel_name, this.fld_city, this.fld_region, this.fld_address, this.fld_email, this.fld_phone, this.fld_star,
                                                         this.fld_parking, this.fld_wifi, this.fld_pool, this.fld_fitness, this.fld_spa, this.fld_concierge ,this.fld_room_service, fld_start_date, fld_end_date })) {
                Helper.showMessage("fill");
            } else {
                this.hotel.setHotelName(fld_hotel_name.getText());
                this.hotel.setCity(fld_city.getText());
                this.hotel.setRegion(fld_region.getText());
                this.hotel.setAddress(fld_address.getText());
                this.hotel.setEmail(fld_email.getText());
                this.hotel.setPhone(fld_phone.getText());
                this.hotel.setStar(Integer.parseInt(fld_star.getText()));
                this.hotel.setParking(Boolean.parseBoolean(fld_parking.getText()));
                this.hotel.setWifi(Boolean.parseBoolean(fld_wifi.getText()));
                this.hotel.setPool(Boolean.parseBoolean(fld_pool.getText()));
                this.hotel.setFitness(Boolean.parseBoolean(fld_fitness.getText()));
                this.hotel.setConcierge(Boolean.parseBoolean(fld_concierge.getText()));
                this.hotel.setSpa(Boolean.parseBoolean(fld_spa.getText()));
                this.hotel.setRoomService(Boolean.parseBoolean(fld_room_service.getText()));

                this.period = new Period();
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    period.setHotelId(this.hotel.getHotelId());
                    period.setStartDate(LocalDate.parse(fld_start_date.getText(), formatter));
                    period.setEndDate(LocalDate.parse(fld_end_date.getText(), formatter));

                } catch (DateTimeParseException ex) {
                    ex.printStackTrace();
                    Helper.showMessage("Tarih dd-MM-yyyy formatında yazılmalıdır!");
                    return;
                }
                    boolean result = this.hotelManager.save(this.hotel, period);

                    if (result) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }

                }
        });
    }
}
