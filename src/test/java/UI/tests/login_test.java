package UI.tests;

import Base.Base;
import UI.pages.login_page;


import org.testng.annotations.Test;

public class login_test extends Base {

    login_page Login_page;

    @Test(groups = {"UI","API"})
    public void perform_login_test(){
        System.out.println("Driver created: " + driver);
        Login_page=new login_page(driver);
        Login_page.performLogin();
    }
}
