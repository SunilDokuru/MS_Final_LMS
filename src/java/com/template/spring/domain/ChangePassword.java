/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

public class ChangePassword {
    private String cPWD, nPWD, rPWD;

    public String getcPWD() {
        return cPWD;
    }

    public void setcPWD(String cPWD) {
        this.cPWD = cPWD;
    }

    public String getnPWD() {
        return nPWD;
    }

    public void setnPWD(String nPWD) {
        this.nPWD = nPWD;
    }

    public String getrPWD() {
        return rPWD;
    }

    public void setrPWD(String rPWD) {
        this.rPWD = rPWD;
    }
}
