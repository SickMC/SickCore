package me.anton.sickcore.modules.staff.managing;

import me.anton.sickcore.modules.staff.StaffPlayer;

import java.util.concurrent.TimeUnit;

public class StaffRemoveAction {

    private final StaffPlayer staffPlayer;

    public StaffRemoveAction(StaffPlayer player){
        this.staffPlayer = player;
    }

    public void perform(){
        staffPlayer.setInActive(TimeUnit.DAYS, 30);
    }



}
