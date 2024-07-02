package view;

import business.PensionManager;
import core.Helper;
import entity.Hotel;
import entity.Pension;

import javax.swing.*;

public class PensionView extends Layout {
    private JPanel container;
    private JPanel tbl_pension1;
    private JLabel lbl_welcome;
    private JLabel lbl_pansion_type;
    private JComboBox<String> cmb_pansion_type;
    private JLabel lbl_hotel_id;
    private JTextField fld_hotel_id;
    private JButton btn_pansion_type_save;
    private Pension pension;
    private PensionManager pensionManager;
    private Hotel hotel;
    private int hotelId;

    public PensionView(int hotelId) {
        this.pension = new Pension();
        this.pensionManager = new PensionManager();
        this.hotel = new Hotel();
        this.hotelId = hotelId;
        this.add(container);
        guiInitilaze(300, 300);

        // Otel ID'sini yazar
        lbl_hotel_id.setText("Otel ID : " + hotelId);

        //Combobox'ı dolu getirir.
        cmb_pansion_type.setModel(new DefaultComboBoxModel<>(new String[]{
                "Ultra Her Şey Dahil", "Her Şey Dahil", "Oda Kahvaltı",
                "Tam Pansiyon", "Yarım Pansiyon", "Sadece Yatak",
                "Alkol Hariç Full credit"
        }));

        // Pansiyon türü kaydet butonuna tıklandığında.
        this.btn_pansion_type_save.addActionListener(e -> {

                // Seçilen pansiyon türünü alır
                String selectPansionType = this.cmb_pansion_type.getSelectedItem().toString();
                int pensionTypeId = convertPensionTypeToId(selectPansionType);

                // PansionType nesnesini günceller
                pension.setPensionTypeId(pensionTypeId);
                pension.setHotelId(hotelId);

                //Seçilen pansiyon türüne göre boolean değerleri set eder.
                updatePensionBooleans(pensionTypeId);

                // Pansion türünü kaydeder
                boolean result = this.pensionManager.save(pension);

                //Sonuca göre bilgi mesajı gösterir
                if (result) {
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
        });
    }


    private int convertPensionTypeToId(String pensionType) {
        switch (pensionType) {
            case "Ultra Her Şey Dahil":
                return 1;
            case "Her Şey Dahil":
                return 2;
            case "Oda Kahvaltı":
                return 3;
            case "Tam Pansiyon":
                return 4;
            case "Yarım Pansiyon":
                return 5;
            case "Sadece Yatak":
                return 6;
            case "Alkol Hariç Full credit":
                return 7;
            default:
                return -1;
        }
    }

    private void updatePensionBooleans(int pensionTypeId) {
        this.pension.setUltraAllInclusive(pensionTypeId == 1);
        this.pension.setAllInclusive(pensionTypeId == 2);
        this.pension.setBedAndBreakfast(pensionTypeId == 3);
        this.pension.setFullBoard(pensionTypeId == 4);
        this.pension.setHalfBoard(pensionTypeId == 5);
        this.pension.setBedOnly(pensionTypeId == 6);
        this.pension.setNonAlcoholFullCredit(pensionTypeId == 7);
    }
}
