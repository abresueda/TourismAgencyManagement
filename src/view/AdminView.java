package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JPanel pnl_top;
    private JButton bttn_logout;
    private JLabel lbl_welcome;
    private JTabbedPane tab_menu;
    private JPanel pnl_admin;
    private JScrollPane scrl_admin;
    private JTable tbl_admin;
    private JComboBox cmb_search_admin;
    private JButton bttn_clear_admin;
    private JButton bttn_search_admin;
    private UserManager userManager;
    private User user;
    private LoginView loginView;
    private DefaultTableModel tmdl_admin = new DefaultTableModel();
    private JPopupMenu admin_menu;
    private Object[] col_admin;

    public AdminView(User user) {
        this.userManager = new UserManager();
        this.user = user;
        this.add(container);
        this.guiInitilaze(500,400);

        this.lbl_welcome.setText("Hoş geldiniz, " + this.user.getUsername());

        loadComponent();

        loadAdminTable(null);
        loadAdminComponent();
        loadAdminFilter();

    }

    //Çıkış yap butonunun çalışması için.
    private void loadComponent() {
        this.bttn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }

    private void loadAdminTable(ArrayList<Object[]> userList) {
        this.col_admin = new Object[] {"ID", "Username", "Password", "Role"};
        if (userList == null) {
            userList = this.userManager.getForTable(this.col_admin.length, this.userManager.findAll());
        }
        createTable(this.tmdl_admin, this.tbl_admin, col_admin, userList);
    }

    private void loadAdminComponent() {
        tableRowSelect(this.tbl_admin);
        this.admin_menu = new JPopupMenu();

        this.admin_menu.add("Yeni").addActionListener(e -> {
            UserView userView = new UserView(new User());
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadAdminTable(null);
                }
            });
        });

        this.admin_menu.add("Güncelle").addActionListener(e -> {
            int selectUserId = this.getTableSelectedRow(tbl_admin, 0);
            UserView userView = new UserView(this.userManager.getById(selectUserId));
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadAdminTable(null);
                }
            });
        });

        this.admin_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure"));
            int selectUserId = this.getTableSelectedRow(tbl_admin, 0);
            if (this.userManager.delete(selectUserId)) {
                Helper.showMessage("done");
                loadAdminTable(null);
            } else {
                Helper.showMessage("error");
            }
        });

        this.tbl_admin.setComponentPopupMenu(admin_menu);

        //Arama yap butonunun istenilen sonuçları getirmesi için.
        this.bttn_search_admin.addActionListener(e -> {
            ComboItem selectedRoleItem = (ComboItem) this.cmb_search_admin.getSelectedItem();
            //Seçilen öğe null değilse, seçilen değeri küçük harfe çevirir, null ise all olarak ayarlar.
            String selectedRole = selectedRoleItem != null ? selectedRoleItem.getValue().toLowerCase() : "all";

            //Seçilen role göre kullanıcı listesini arar.
            ArrayList<User> userListBySearch = this.userManager.searchForTable(0, String.valueOf(selectedRoleItem));

            //Seçilen nesne listesi, tablo için uygun düzenlenmiş nesne listesine dönüştürülür ve tabloya yüklenir.
            ArrayList<Object[]> userRowListBySearch = this.userManager.getForTable(this.col_admin.length, userListBySearch);
            loadAdminTable(userRowListBySearch);
        });

        //Temizle butonunun çalışması için comboBoxları null olarak ayarlıyoruz.
        this.bttn_clear_admin.addActionListener(e -> {
            this.cmb_search_admin.setSelectedItem(null);
            loadAdminTable(null);
        });
    }

    //Comboboxların ekrana ilk olarak boş gelmesini sağlıyoruz.
    public void loadAdminFilter() {
        this.cmb_search_admin.addItem(new ComboItem(1, "All"));
        this.cmb_search_admin.addItem(new ComboItem(2, "Admin"));
        this.cmb_search_admin.addItem(new ComboItem(3, "Employee"));
        this.cmb_search_admin.setSelectedItem(null);

    }
}
