package data.scripts.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class MikoshiFacility extends BaseIndustry {
    //Yes this is reference to Cyberpunk 2077
    public boolean conversionStarted = false;
    public float conversion = 0f;
    public float threshold = 15f;
    public boolean hasMetDemand = false;
    @Override
    public void apply() {
        super.apply(true);
        demand("water",4);
        demand("electronics",4);
        demand(Commodities.MARINES,5);
        demand(Commodities.VOLATILES,6);
         hasMetDemand = getMaxDeficit("water","electronics",Commodities.MARINES,Commodities.VOLATILES).two<=0;


    }

    @Override
    public void unapply() {
        super.unapply();
    }

    @Override
    public void advance(float amount) {
        super.advance(amount);
        if(conversionStarted&&hasMetDemand){
            conversion+=Global.getSector().getClock().convertToDays(amount);
            if((conversion>=threshold)){
                conversion=0;
                conversionStarted=false;
                market.getSubmarket(Submarkets.SUBMARKET_STORAGE).getCargo().addCommodity("theta_core",1);
            }
        }
    }

    @Override
    protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
        super.addPostDemandSection(tooltip, hasDemand, mode);
        if(mode.equals(IndustryTooltipMode.NORMAL)){
            if(conversionStarted){
                if(hasMetDemand){
                    tooltip.addPara("Soulkiller process active!\nTransfering data into Theta Core.", Misc.getNegativeHighlightColor(),10f);
                    if(threshold-conversion<=1){
                        tooltip.addPara("Process will end in %s day",10f, Color.ORANGE,""+(int)(threshold-conversion));
                    }
                    else{
                        tooltip.addPara("Process will end in %s days",10f, Color.ORANGE,""+(int)(threshold-conversion));
                    }
                }
                else{
                    tooltip.addPara("Soulkiller process halted!\nDemand has not been met.", Misc.getNegativeHighlightColor(),10f);
                }

            }
            tooltip.addPara("Warning! We should not overuse Soulkiller! This will lead to get unwanted attention from our population, therefore decreasing stability by a lot! Recommend waiting around 180 days, for every usage of Soulkiller.",Misc.getNegativeHighlightColor(),10f);
        }
    }

    @Override
    public boolean canInstallAICores() {
        return false;
    }

}
