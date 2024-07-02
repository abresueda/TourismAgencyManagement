package view;

import business.HotelManager;
import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.Room;

import javax.swing.*;

public class RoomView extends Layout {
    private JPanel container;
    private JTextField fld_hotel_id_room;
    private JComboBox cmb_room_type;
    private JComboBox cmb_pension_type;
    private JTextField fld_period_room;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JTextField fld_stock;
    private JTextField fld_bed_count;
    private JTextField fld_metres;
    private JTextField fld_tv;
    private JTextField fld_minibar;
    private JTextField fld_game_console;
    private JTextField fld_safe;
    private JTextField fld_projector;
    private JButton bttn_save_room;
    private Room room;
    private RoomManager roomManager;
    private HotelManager hotelManager;

    public RoomView(Room room) {
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.room = room;
        this.add(container);
        this.guiInitilaze(400, 700);

        //ComboBoxlara istenilenleri eklemek için.
        this.cmb_room_type.setModel(new DefaultComboBoxModel<>(Room.RoomType.values()));
        this.cmb_pension_type.setModel(new DefaultComboBoxModel<>(Room.PensionType.values()));



        bttn_save_room.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{fld_hotel_id_room, fld_period_room, fld_adult_price, fld_child_price, fld_stock, fld_bed_count, fld_metres })) {
                Helper.showMessage("fill");
            } else {
                try {
                    this.room.setHotelId(Integer.parseInt(fld_hotel_id_room.getText()));
                    this.room.setRoomType((Room.RoomType) cmb_room_type.getSelectedItem());
                    this.room.setPensionType((Room.PensionType) cmb_pension_type.getSelectedItem());
                    this.room.setPeriod(fld_period_room.getText());
                    this.room.setAdultPrice(Double.parseDouble(fld_adult_price.getText()));
                    this.room.setChildPrice(Double.parseDouble(fld_child_price.getText()));
                    this.room.setStock(Integer.parseInt(fld_stock.getText()));
                    this.room.setBedCount(Integer.parseInt(fld_bed_count.getText()));
                    this.room.setSquareMeters(Integer.parseInt(fld_metres.getText()));
                    this.room.setTv(Boolean.parseBoolean(fld_tv.getText()));
                    this.room.setMinibar(Boolean.parseBoolean(fld_minibar.getText()));
                    this.room.setSafe(Boolean.parseBoolean(fld_safe.getText()));
                    this.room.setGameConsole(Boolean.parseBoolean(fld_game_console.getText()));
                    this.room.setProjector(Boolean.parseBoolean(fld_projector.getText()));

                    boolean result = this.roomManager.save(this.room);

                    if (result) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                } catch (NumberFormatException ex) {
                    Helper.showMessage("Invalid number format");
                }
            }

        });
    }
}
