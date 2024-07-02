package view;

import business.*;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EmployeeView extends Layout {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton bttn_logout;
    private JTabbedPane tab_menu;
    private JTable tbl_hotel;
    private JPanel pnl_hotel;
    private JScrollPane scrl_hotel;
    private JPanel pnl_room;
    private JPanel pnl_reservation;
    private JTable tbl_rez;
    private JTable tbl_room;
    private JScrollPane scrl_room;
    private JPanel pnl_room_search;
    private JTextField fld_hotel_name;
    private JTextField fld_city;
    private JFormattedTextField fld_checkin;
    private JFormattedTextField fld_checkout;
    private JButton bttn_search_room;
    private JButton bttn_clear_room;
    private JTable tbl_pension;
    private JScrollPane scrl_pension;
    private JTable tbl_period;
    private JScrollPane scrl_period;
    private JTextField fld_adult_count;
    private JTextField fld_child_count;
    private JScrollPane scrl_rez;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private DefaultTableModel tmdl_rez = new DefaultTableModel();
    private DefaultTableModel tmdl_pension = new DefaultTableModel();
    private DefaultTableModel tmdl_period = new DefaultTableModel();
    private JPopupMenu hotel_menu;
    private JPopupMenu room_menu;
    private JPopupMenu rez_menu;
    private JPopupMenu pension_menu;
    private HotelManager hotelManager;
    private PensionManager pensionManager;
    private PeriodManager periodManager;
    private RoomManager roomManager;
    private ReservationManager reservationManager;
    private Hotel hotel;
    private User user;
    private Period period;
    private Pension pension;
    private Room room;
    private Reservation reservation;
    private Object[] col_hotel;
    private Object[] col_room;
    private Object[] col_pension;
    private Object[] col_period;
    private Object[] col_rez;

    public EmployeeView() {}

    public EmployeeView(User user) {
        this.user = user;
        this.hotelManager = new HotelManager();
        this.hotel = hotel;
        this.pensionManager = new PensionManager();
        this.pension = pension;
        this.periodManager = new PeriodManager();
        this.period = period;
        this.roomManager = new RoomManager();
        this.room = room;
        this.reservationManager = new ReservationManager();
        this.reservation = reservation;

        this.add(container);
        this.guiInitilaze(1100, 600);

        if (this.user == null) {
            dispose();

        }

        this.lbl_welcome.setText("HOŞGELDİNİZ " + this.user.getUsername().toUpperCase());

        loadComponent();

        //Sezon ekleme özelliği hotelview'in içinde.
        loadHotelTable(null);
        loadHotelComponent();

        loadPensionTable(null);
        loadPensionComponent();

        loadPeriodTable(null);

        loadRoomTable(null);
        loadRoomComponent();

        loadReservationTable(null);
        loadReservationComponent();

    }

    private void loadComponent() {
        //Çıkış Yap butonunu çalıştırır.
        this.bttn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }

    public void loadHotelTable(ArrayList<Object[]> hotelList) {
        this.col_hotel = new Object[] {"ID", "Hotel Name", "City", "Region", "Address", "Email", "Phone", "Star", "Parking Area", "Wifi", "Pool", "Fitness Center", "Concierge", "SPA", "7/24 Room Service"};
        if (hotelList == null) {
            hotelList = this.hotelManager.getForTable(this.col_hotel.length, this.hotelManager.findAll());
        }
        createTable(this.tmdl_hotel, this.tbl_hotel, col_hotel, hotelList);
    }

    private void loadHotelComponent() {
        tableRowSelect(this.tbl_hotel);
        this.hotel_menu = new JPopupMenu();

        this.hotel_menu.add("Yeni Otel Ekle").addActionListener(e -> {
            HotelView hotelView = new HotelView(new Hotel());
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable(null);
                    loadPeriodTable(null);
                    loadPensionTable(null);
                    loadReservationTable(null);
                }
            });
        });

        this.tbl_hotel.setComponentPopupMenu(hotel_menu);
    }

    public void loadPensionTable(ArrayList<Object[]> pensionList) {
        this.col_pension = new Object[] {"ID", "Hotel ID", "Pansiyon Tipleri"};
        if (pensionList == null) {
           pensionList = (ArrayList<Object[]>) this.pensionManager.findAllHotelsWithPension(this.col_pension.length);
        }
        createTable(this.tmdl_pension, this.tbl_pension, col_pension, pensionList);

    }

    private void loadPensionComponent() {
        tableRowSelect(this.tbl_pension);
        this.pension_menu = new JPopupMenu();

        this.pension_menu.add("Yeni Pansiyon Tipi Ekle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_pension, 0);
            PensionView pensionView = new PensionView(selectId);
            pensionView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPensionTable(null);
                }
            });
        });
        this.tbl_pension.setComponentPopupMenu(pension_menu);
    }

    public void loadPeriodTable(ArrayList<Object[]> periodList) {
        this.col_period = new Object[] {"ID", "Hotel ID", "Başlangıç Tarihi", "Bitiş Tarihi"};
        if (periodList == null) {
            periodList = this.periodManager.getForTable(this.col_period.length, this.periodManager.findAll());
        }
        createTable(this.tmdl_period, this.tbl_period, col_period, periodList);
    }

    public void loadRoomTable(ArrayList<Object[]> roomList) {
        this.col_room = new Object[] {"ID", "Hotel ID", "Oda Tipi", "Pansiyon Tipi", "Dönem", "Yetişkin Ücreti", "Çocuk Ücreti", "Stok", "Yatak Kapasitesi", "Metrekare", "TV", "Minibar", "Oyun Konsolu", "Kasa", "Projektör"};
        if (roomList == null) {
            roomList = this.roomManager.getForTable(this.col_room.length, this.roomManager.findAll());
        }
        createTable(this.tmdl_room, this.tbl_room, col_room, roomList);
    }

    //EmployeeView'deki Oda ekleme,güncelleme ve silme işlemleri için.
    private void loadRoomComponent() {
        tableRowSelect(tbl_room);
        this.room_menu = new JPopupMenu();
        this.room_menu.add("Yeni Oda Ekle").addActionListener(e -> {
            RoomView roomView = new RoomView (new Room());
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable(null);
                    loadPensionTable(null);
                    loadPeriodTable(null);
                    loadRoomTable(null);
                    loadReservationTable(null);
                }
            });

        });

        this.room_menu.add("Güncelle").addActionListener(e -> {
            int selectRoomId = this.getTableSelectedRow(tbl_room, 0);
            RoomView roomView = new RoomView(this.roomManager.getById(selectRoomId));
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                    loadReservationTable(null);
                    loadHotelTable(null);
                    loadPensionTable(null);
                    loadPeriodTable(null);
                }
            });
        });

        this.room_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure"));
            int selectRoomId = this.getTableSelectedRow(tbl_room, 0);
            if (this.roomManager.delete(selectRoomId)) {
                Helper.showMessage("done");
                loadRoomTable(null);
                loadReservationTable(null);
                loadHotelTable(null);
            } else {
                Helper.showMessage("error");
            }
        });

        this.tbl_room.setComponentPopupMenu(room_menu);

        bttn_search_room.addActionListener(e -> {
            String hotelName = fld_hotel_name.getText();
            String city = fld_city.getText();
            String checkinStr = fld_checkin.getText();
            String checkoutStr = fld_checkout.getText();

            //Yetişkin ve çocuk sayısı boş ise varsayılan değer olarak 0 ayarlıyoruz.
            int adultCount = fld_adult_count.getText().isEmpty() ? 0 : Integer.parseInt(fld_adult_count.getText());
            int childCount = fld_child_count.getText().isEmpty() ? 0 : Integer.parseInt(fld_child_count.getText());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate checkinDate = null;
            LocalDate checkoutDate = null;

            //Giriş ve Çıkış Tarihleri boş değil ise tarih formatlarını dönüştürür.
            if (!checkinStr.isEmpty() && !checkoutStr.isEmpty()) {
                try {
                    checkinDate = LocalDate.parse(checkinStr, formatter);
                    checkoutDate = LocalDate.parse(checkoutStr, formatter);
                } catch (DateTimeParseException ex) {
                    Helper.showMessage("date");
                    return;
                }
            }

            ArrayList<Room> roomListBySearch = this.roomManager.searchForRoom(
                    hotelName,
                    city,
                    checkinDate == null ? null : java.sql.Date.valueOf(checkinDate),
                    checkoutDate == null ? null : java.sql.Date.valueOf(checkoutDate)
            );

            ArrayList<Object[]> roomRowListBySearch = this.roomManager.getForTable(this.col_room.length, roomListBySearch);
            loadRoomTable(roomRowListBySearch);

        });

        bttn_clear_room.addActionListener(e -> {
            this.fld_hotel_name.setText(null);
            this.fld_city.setText(null);
            this.fld_checkin.setText(null);
            this.fld_checkout.setText(null);
            this.fld_adult_count.setText(null);
            this.fld_child_count.setText(null);
            loadRoomTable(null);
        });

    }

    public void loadReservationTable(ArrayList<Object[]> rezList) {
        this.col_rez = new Object[] {"Rez. ID", "Otel ID", "Oda ID", "Check-in Tarihi", "Check-out Tarihi", "Toplam Tutar", "Müşteri Adı", "Vatandaşlık No:", "Email", "Telefon No:"};
        if (rezList == null) {
            if (this.reservationManager == null) {
                System.out.println("ReservationManager is null");
                return;
            }
            rezList = this.reservationManager.getForTable(this.col_rez.length, this.reservationManager.findAll());
        }
        createTable(this.tmdl_rez, this.tbl_rez, col_rez, rezList);
    }

    //EmployeeView'deki Rezervasyon ekleme,güncelleme ve silme işlemleri için.
    private void loadReservationComponent() {
        tableRowSelect(this.tbl_rez);
        this.rez_menu = new JPopupMenu();

        this.rez_menu.add("Yeni Rezervasyon Ekle").addActionListener(e -> {
            int selectRezId = getTableSelectedRow(tbl_rez, 0);
            Room selectedRoom = this.roomManager.getById(selectRezId);
            int stock = selectedRoom.getStock();

            /*if (!fld_checkin.getText().trim().isEmpty() && !fld_checkout.getText().trim().isEmpty() && !fld_adult_count.getText().trim().isEmpty()) {
                fld_child_count.setText(String.valueOf(0));*/
                ReservationView reservationView = new ReservationView(new Reservation(), selectedRoom /*this.roomManager.getById(selectRezId)*/, this.fld_checkin, this.fld_checkout, this.fld_adult_count, this.fld_child_count);

                reservationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadReservationTable(null);
                        loadRoomTable(null);
                        loadRoomComponent();
                    }
                });
                //Rezervasyon eklenince oda stoğunu azaltmak için.
                if (this.reservationManager.save(new Reservation())) {
                    stock -= 1;
                    if (this.roomManager.updateRoomStock(stock, selectedRoom)) {
                        loadRoomTable(null);
                    }
                } else {
                    Helper.showMessage("error ");
                }/*else {
                Helper.showMessage("fill");
            }*/
        });


        this.rez_menu.add("Güncelle").addActionListener(e -> {
            try {
                int selectRezId = this.getTableSelectedRow(tbl_rez, 0);
                ReservationView reservationView = new ReservationView(this.reservationManager.getById(selectRezId), this.roomManager.getById(selectRezId), this.fld_checkin, this.fld_checkout, this.fld_adult_count, this.fld_child_count);
                reservationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadReservationTable(null);
                        loadRoomTable(null);
                    }
                });
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });

        this.rez_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure"));
            try {
                int selectRezId = this.getTableSelectedRow(tbl_rez, 0);
                this.room = this.roomManager.getById(this.reservationManager.getById(selectRezId).getRoomId());
                int stock = room.getStock();
                if (this.reservationManager.delete(selectRezId)) {
                    Helper.showMessage("done");
                    loadReservationTable(null);
                    stock += 1;

                    if (this.roomManager.updateRoomStock(stock, this.room)) {
                        loadRoomTable(null);
                    }
                } else {
                    Helper.showMessage("error");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        this.tbl_rez.setComponentPopupMenu(rez_menu);
    }
}
