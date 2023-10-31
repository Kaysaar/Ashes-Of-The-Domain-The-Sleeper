package data.colonyevents.ui;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import data.colonyevents.models.AoTDColonyEvent;

import java.util.Map;

public class AoTDColonyEventOutomeDP implements InteractionDialogPlugin {
    public InteractionDialogAPI dialog;
    public AoTDColonyEvent event;

    static enum OptionID {
        INIT,
        LEAVE
    }

    @Override
    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        this.dialog.getTextPanel();
        dialog.hideTextPanel();
        dialog.setPromptText("");
        dialog.getVisualPanel().finishFadeFast();
        dialog.setBackgroundDimAmount(0f);
        optionSelected(null, OptionID.INIT);
    }

    public void setEvent(AoTDColonyEvent event) {
        this.event = event;
    }

    @Override
    public void optionSelected(String optionText, Object optionData) {
        if (optionData == OptionID.INIT) {
            //this is where the size of the panel is set, automatically centered
            dialog.showCustomVisualDialog(AoTDColonyEventOutcomeUI.WIDTH + 21, AoTDColonyEventOutcomeUI.HEIGHT + 21, new AoTDColonyEventOutcomeDelegate(new AoTDColonyEventOutcomeUI(),dialog));

        }
    }

    @Override
    public void optionMousedOver(String optionText, Object optionData) {

    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public void backFromEngagement(EngagementResultAPI battleResult) {

    }

    @Override
    public Object getContext() {
        return null;
    }

    @Override
    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }
}
