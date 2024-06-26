package com.fincode.cavechimes.common.block.entity;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.block.BlockCaveChimes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "cavechimes", value=Side.CLIENT)
public class TileEntityCaveChimes extends TileEntity {
    @SideOnly(Side.CLIENT)
    private com.fincode.cavechimes.client.audio.CaveChimesSound soundLoop;

    private boolean isPlaying = false;
    private short signal;

    public float discSpeedX = 0;
    public float discSpeedZ = 0;

    public int[] chimeOffsets = new int[4];

    private static final List<TileEntityCaveChimes> listeners = new ArrayList<>();
    private static final List<TileEntityCaveChimes> playing = new ArrayList<>();
    private static int interval = 0;

    public TileEntityCaveChimes() {

    }

    private void addListener() {
        listeners.add(this);
    }

    private void removeListener() {
        listeners.remove(this);
    }

    @Override
    public void validate() {
        super.validate();

        //CaveChimesMod.getLogger().info("Validating");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        //if (!this.world.isChunkGeneratedAt(pos.getX() / 16, pos.getZ() / 16)) return;
        Random rand = world.rand;

        discSpeedX = rand.nextFloat() + 0.5f;
        discSpeedZ = rand.nextFloat() + 0.5f;

        for (int o = 0; o < 4; ++o) {
            chimeOffsets[o] = rand.nextInt(8) - 7;
        }

        if (!world.isRemote)  {
            this.signal = world.getBlockState(pos).getValue(BlockCaveChimes.VOLUME).shortValue();
            return;
        }

        this.soundLoop = new com.fincode.cavechimes.client.audio.CaveChimesSound(pos);

        soundLoop.setVolume(signal);
        if (listeners.size() < Config.client.priority.limit)
            addListener();
        else
            CaveChimesMod.getLogger().info("Cave Chimes limit exceeded! (" + listeners.size() + ")");
        setPlaying(true);
        //CaveChimesMod.getLogger().info("Loading");
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (!world.isRemote) return;

        removeListener();
        setPlaying(false);
        //CaveChimesMod.getLogger().info("Unloading");
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if (!world.isRemote) return;

        removeListener();
        setPlaying(false);
        //CaveChimesMod.getLogger().info("Invalidating");
    }

    public void setVolume(short volume) {
        if (signal == volume) return;
        signal = volume;

        if (!world.isRemote) return;
        setPlaying(volume != 15);
        soundLoop.setVolume(volume);
    }

    public short getVolume() {
        return signal;
    }

    @SideOnly(Side.CLIENT)
    private void setPlayingInternal(boolean value) {
        if (value && !net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(soundLoop)) {
            net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().stopSound(soundLoop);
            net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().playSound(soundLoop);
        }
        else {
            net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().stopSound(soundLoop);
            soundLoop.stop();
        }
        isPlaying = value;
    }

    @SideOnly(Side.CLIENT)
    private void setPlaying(boolean value) {
        if (value == isPlaying) return;

        if (playing.size() >= 8) {
            if (value) return;
        }

        setPlayingInternal(value);

        if (value) playing.add(this);
        else playing.remove(this);

        soundLoop.ping(value);
    }

    @SideOnly(Side.CLIENT)
    private static void refresh(EntityPlayer player) {
        listeners.sort((c1, c2) -> (int)Math.signum(player.getDistanceSq(c1.pos) - player.getDistanceSq(c2.pos)));

        List<TileEntityCaveChimes> oldPlaying = playing;
        playing.clear();
        int l = 8;
        for (int c = 0; c < l && c < listeners.size(); ++c) {
            if (listeners.get(c).getVolume() != 15)
                playing.add(listeners.get(c));
            else
                ++l;
        }

        for (TileEntityCaveChimes chime : oldPlaying) {
            if (!playing.contains(chime))
                chime.setPlayingInternal(false);
        }
        for (TileEntityCaveChimes chime : playing) {
            if (!oldPlaying.contains(chime) || !chime.isPlaying || !net.minecraft.client.Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(chime.soundLoop))
                chime.setPlayingInternal(true);
            //CaveChimesMod.getLogger().info("Distance from player: " + player.getDistanceSq(chime.pos));
        }
    }

    @SubscribeEvent // My bad, I forgot to add this annotation LOL
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.CLIENT) return;

        int refreshInterval = Config.client.priority.refreshInterval * 20;

        if (!Config.client.priority.enabled || refreshInterval == 0) return;
        ++interval;

        if (interval < refreshInterval) return;
        //CaveChimesMod.getLogger().info("Shuffling your cards");
        interval = 0;
        refresh(event.player);
    }
}
