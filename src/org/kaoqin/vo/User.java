package org.kaoqin.vo;

/**
 * 考勤系统登录用户信息
 *
 * @author lenovo
 *
 */
public class User {

    private String username;
    private String password;
    private String yzm;//cookie里面的验证码，登录考勤系统在cookie里面查看
    private String ygyzm;//cookie里面的员工验证码，登录考勤系统在cookie里面查看
    private String ip;//用户ip

    public User() {
    }

    public User(String username, String password, String yzm, String ygyzm, String ip) {
        this.username = username;
        this.password = password;
        this.yzm = yzm;
        this.ygyzm = ygyzm;
        this.ip = ip;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getYgyzm() {
        return ygyzm;
    }

    public void setYgyzm(String ygyzm) {
        this.ygyzm = ygyzm;
    }
}
