package com.fincode.cavechimes.client.audio;

import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.init.CaveChimesSounds;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CaveChimesSound extends PositionedSound implements ITickableSound {
    public static final float BASE_VOLUME = 2;
    private boolean playing = true;
    private short power = 0;
    private float configVolume;

    public CaveChimesSound(BlockPos pos) {
        super(CaveChimesSounds.CAVE_CHIMES, SoundCategory.BLOCKS);
        this.attenuationType = AttenuationType.LINEAR;
        this.repeat = true;
        this.repeatDelay = 0;
        this.configVolume = configure();
        this.volume = BASE_VOLUME * configVolume;
        this.setPos(pos);
    }

    public void stop() {
        this.playing = false;
        this.repeat = false;
    }

    public void setVolume(short strength) {
        this.power = strength;
        updateVolume();
    }

    private void updateVolume() {
        this.volume = configure() * BASE_VOLUME * (1 - (float)power / 15f); // Volume changes based on Redstone signal strength. A signal of 15 makes it silent.
    }

    private float configure() {
        return (float)Config.client.chimeVolume;
    }

    public void setPos(BlockPos pos) {
        this.xPosF = pos.getX();
        this.yPosF = pos.getY(); // Embarrassing...
        this.zPosF = pos.getZ();
        //CaveChimesMod.getLogger().info("WAHAHA! At " + pos + " " + power + " " + configVolume + " " + volume + "!");
    }

    public void ping(boolean value) {
        //CaveChimesMod.getLogger().info("Pinging (" + value + ") [" + this.xPosF + ", " + this.yPosF + ", " + this.zPosF + "] " + power + " " + configVolume + " " + volume + "!");
    }

    @Override
    public boolean isDonePlaying() {
        return !playing;
    }

    @Override
    public void update() {
        if (configVolume == configure()) return;
        configVolume = configure();
        updateVolume();
    }
}
