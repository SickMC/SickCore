package me.anton.sickcore.modules.staff.managing;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.modules.rank.Rank;
import me.anton.sickcore.modules.staff.Staff;
import me.anton.sickcore.modules.staff.StaffPlayer;

@Getter
public class StaffAddAction {

     private final APIPlayer player;
     private Rank rank;

     public StaffAddAction(APIPlayer player, Rank rank){
          this.player = player;
          this.rank = rank;
     }

     public void perform(){
          player.setRank(rank);
          if (new StaffPlayer(player).isNull())new Staff().addMember(player);
          else new StaffPlayer(player).setActive(rank);
     }
}
