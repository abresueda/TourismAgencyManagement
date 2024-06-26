package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class LoginView extends Layout {
    private final UserManager userManager;
    private JPanel container;
    private JPanel w_top;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JButton bttn_login;
    private JPasswordField fld_password;
    private JLabel lbl_username;
    private JLabel lbl_password;

    public LoginView() {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(400,400);

        bttn_login.addActionListener(e -> {
            JTextField [] checkFieldList = {this.fld_username, this.fld_password};
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMessage("fiil");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_password.getText());
                if (loginUser == null) {
                    Helper.showMessage("notFound");
                } else {
                    if ("admin".equals(loginUser.getRole())) {
                        AdminView adminView = new AdminView(loginUser);
                        adminView.setVisible(true);

                    } else {
                        EmployeeView employeeView = new EmployeeView(loginUser);
                        employeeView.setVisible(true);
                    }
                    this.dispose();
                }
            }
        });
    }
}
