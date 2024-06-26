package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class UserView extends Layout {
    private JPanel container;
    private JTextField fld_username;
    private JTextField fld_password;
    private JTextField fld_role;
    private JButton bttn_save_userview;
    private User user;
    private UserManager userManager;

    public UserView(User user) {
        this.user = user;
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(300, 400);

        //Kullanıcının id'si sıfırdan farklıysa tabloya seçilenleri getirir.
        if (this.user.getId() != 0) {
            this.fld_username.setText(this.user.getUsername());
            this.fld_password.setText(this.user.getPassword());
            this.fld_role.setText(this.user.getRole());
        }

        this.bttn_save_userview.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[] { this.fld_username, this.fld_password, this.fld_role })) {
                Helper.showMessage("fill");
            } else {
                boolean result;
                this.user.setUsername(fld_username.getText());
                this.user.setPassword(fld_password.getText());
                this.user.setRole(fld_role.getText());

                //ID'si 0'dan farklıysa güncelleme işlemidir. Değilse ekleme işlemidir.
                if (this.user.getId() != 0) {
                    result = this.userManager.update(this.user);
                } else {
                    result = this.userManager.save(this.user);
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
