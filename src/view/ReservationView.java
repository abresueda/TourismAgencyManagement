package view;

import business.ReservationManager;
import core.Helper;
import entity.Reservation;
import entity.Room;

import javax.swing.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationView extends Layout {

    private JPanel container;
    private JTextField fld_hotel_id;
    private JTextField fld_room_id;
    private JTextField fld_checkin;
    private JTextField fld_checkout;
    private JTextField fld_total_price;
    private JTextField fld_customer_name;
    private JTextField fld_citizen_no;
    private JTextField fld_email;
    private JTextField fld_phone_no;
    private JButton bttn_save_rez;
    private JTextField fld_adult_count;
    private JTextField fld_child_count;
    private Reservation reservation;
    private ReservationManager reservationManager;

    public ReservationView(Reservation reservation) {
        this.reservation = reservation;
        this.reservationManager = new ReservationManager();
        this.add(container);
        this.guiInitilaze(400, 600);

        //Rezervasyonları güncellemek için ekran açıldığında boşluklar dolu gelir.
        if (this.reservation.getReservationId() != 0) {
            this.fld_hotel_id.setText(String.valueOf(this.reservation.getHotelId()));
            this.fld_room_id.setText(String.valueOf(this.reservation.getRoomId()));
            this.fld_checkin.setText(String.valueOf(this.reservation.getCheckinDate()));
            this.fld_checkout.setText(String.valueOf(this.reservation.getCheckoutDate()));
            this.fld_customer_name.setText(this.reservation.getCustomerName());
            this.fld_citizen_no.setText(this.reservation.getCitizenNumber());
            this.fld_phone_no.setText(this.reservation.getPhoneNumber());
            this.fld_email.setText(this.reservation.getEmail());
            this.fld_adult_count.setText(String.valueOf(this.reservation.getAdultCount()));
            this.fld_child_count.setText(String.valueOf(this.reservation.getChildCount()));
        }

        bttn_save_rez.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_hotel_id, this.fld_room_id, this.fld_checkin, this.fld_checkout, this.fld_customer_name, this.fld_citizen_no, this.fld_email, this.fld_phone_no})) {
                Helper.showMessage("fill");
            } else {
                boolean result;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                try {
                    LocalDate checkinLocalDate = LocalDate.parse(fld_checkin.getText(), formatter);
                    LocalDate checkoutLocalDate = LocalDate.parse(fld_checkout.getText(), formatter);

                    Date checkinDate = Date.valueOf(checkinLocalDate);
                    Date checkoutDate = Date.valueOf(checkoutLocalDate);

                    this.reservation.setCheckinDate(checkinDate);
                    this.reservation.setCheckoutDate(checkoutDate);

                } catch (DateTimeParseException ex) {
                    Helper.showMessage("date");
                    return;
                }

                int adultCount = Integer.parseInt(fld_adult_count.getText());
                int childCount = Integer.parseInt(fld_child_count.getText());

                reservationManager.calculateAndSetTotalPrice(reservation, adultCount, childCount);

                fld_total_price.setText(String.valueOf(reservation.getTotalPrice()));

                this.reservation.setHotelId(Integer.parseInt(fld_hotel_id.getText()));
                this.reservation.setRoomId(Integer.parseInt(fld_room_id.getText()));
                this.reservation.setTotalPrice(Integer.parseInt(fld_total_price.getText()));
                this.reservation.setCustomerName(fld_customer_name.getText());
                this.reservation.setCitizenNumber(fld_citizen_no.getText());
                this.reservation.setEmail(fld_email.getText());
                this.reservation.setPhoneNumber(fld_phone_no.getText());
                this.reservation.setAdultCount(adultCount);
                this.reservation.setChildCount(childCount);

                //ID'si 0'dan farklıysa update işlemidir.
                if (this.reservation.getReservationId() != 0) {
                    result = this.reservationManager.update(this.reservation);
                } else {
                    result = this.reservationManager.save(this.reservation);
                }

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
