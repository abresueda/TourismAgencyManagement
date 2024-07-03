package view;

import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import dao.RoomDao;
import entity.Reservation;
import entity.Room;

import javax.swing.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

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
    private Room room;
    private RoomDao roomDao;
    private RoomManager roomManager;
    private int roomId;

    public ReservationView(Reservation reservation, int selectRoom, String checkin, String checkout, int adultCount, int childCount) {
        this.reservation = reservation;
        this.reservationManager = new ReservationManager();
        this.roomDao = new RoomDao();
        this.roomManager = new RoomManager();
        this.add(container);
        this.guiInitilaze(400, 600);

        //Rezervasyonları güncellemek için ekran açıldığında boşluklar dolu gelir.
        if (this.reservation.getReservationId() != 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate checkinDate = this.reservation.getCheckinDate();
            LocalDate checkoutDate = this.reservation.getCheckoutDate();
            this.fld_checkin.setText(checkinDate.format(formatter));
            this.fld_checkout.setText(checkoutDate.format(formatter));

            this.fld_hotel_id.setText(String.valueOf(this.reservation.getHotelId()));
            this.fld_room_id.setText(String.valueOf(this.reservation.getRoomId()));
            this.fld_customer_name.setText(this.reservation.getCustomerName());
            this.fld_citizen_no.setText(this.reservation.getCitizenNumber());
            this.fld_phone_no.setText(this.reservation.getPhoneNumber());
            this.fld_email.setText(this.reservation.getEmail());
            this.fld_adult_count.setText(String.valueOf(this.reservation.getAdultCount()));
            this.fld_child_count.setText(String.valueOf(this.reservation.getChildCount()));
        }


        //Rezervasyon işlemini kaydetmek için.
        bttn_save_rez.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_hotel_id, this.fld_room_id, this.fld_checkin, this.fld_checkout, this.fld_customer_name, this.fld_citizen_no, this.fld_email, this.fld_phone_no})) {
                Helper.showMessage("fill");
            } else {
                boolean result;
                //Rezervasyon tarihlerini ve fiyatını hesaplar.
                String check_in = String.valueOf(this.fld_checkin.getText());
                String check_out = String.valueOf(this.fld_checkout.getText());
                int adult_Count = Integer.parseInt(this.fld_adult_count.getText());
                int child_Count = Integer.parseInt(this.fld_child_count.getText());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate checkinDate = LocalDate.parse(check_in, formatter);
                LocalDate checkoutDate = LocalDate.parse(check_out, formatter);

                long days = ChronoUnit.DAYS.between(checkinDate, checkoutDate);

                this.roomId = Integer.parseInt(this.fld_room_id.getText());
                this.room = roomManager.getById(roomId);

                int totalPrice = (int) (days * ((adult_Count * this.room.getAdultPrice()) + (child_Count * this.room.getChildPrice())));
                this.fld_total_price.setText(String.valueOf(totalPrice));


                this.reservation.setHotelId(Integer.parseInt(this.fld_hotel_id.getText()));
                this.reservation.setRoomId(Integer.parseInt(fld_room_id.getText()));
                this.reservation.setTotalPrice(Integer.parseInt(fld_total_price.getText()));
                this.reservation.setCustomerName(fld_customer_name.getText());
                this.reservation.setCitizenNumber(fld_citizen_no.getText());
                this.reservation.setEmail(fld_email.getText());
                this.reservation.setPhoneNumber(fld_phone_no.getText());
                this.reservation.setAdultCount(adult_Count);
                this.reservation.setChildCount(child_Count);
                this.reservation.setCheckinDate(checkinDate);
                this.reservation.setCheckoutDate(checkoutDate);

                //ID'si 0'dan farklıysa update işlemidir.
                if (this.reservation.getReservationId() != 0) {
                    result = this.reservationManager.update(this.reservation);
                } else {
                    //Rezervasyon eklenmeden önce stoğu kontrol etmek için.
                    if (this.room.getStock() > 0) {
                        result = this.reservationManager.save(this.reservation);
                        this.room.setStock(this.room.getStock() - 1); //Rezervasyon eklenince oda stoğunu azaltır.
                        boolean stockUpdateResult = this.roomManager.updateRoomStock(room);

                        if (stockUpdateResult) {
                            Helper.showMessage("done");
                            dispose();
                        } else {
                            Helper.showMessage("Stok güncellenirken hata oluştu.");
                        }
                    } else {
                        Helper.showMessage("stock");
                        dispose();
                        result = false;
                    }
                }
            }
        });
    }
}
