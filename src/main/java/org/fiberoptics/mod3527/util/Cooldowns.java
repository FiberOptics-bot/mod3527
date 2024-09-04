package org.fiberoptics.mod3527.util;

public class Cooldowns {
    private int stunCooldown, agileCooldown;
    public Cooldowns(int stunCd,int agileCd) {
        stunCooldown=stunCd;
        agileCooldown=agileCd;
    }
    public int getAgileCooldown() {
        return agileCooldown;
    }
    public int getStunCooldown() {
        return stunCooldown;
    }

    public void setAgileCooldown(int agileCd) {
        agileCooldown = agileCd;
    }
    public void setStunCooldown(int stunCd) {
        stunCooldown = stunCd;
    }
}