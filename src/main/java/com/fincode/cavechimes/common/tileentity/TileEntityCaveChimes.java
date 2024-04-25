package com.fincode.cavechimes.common.tileentity;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.ConfigClient;
import com.fincode.cavechimes.ConfigCommon;
import com.fincode.cavechimes.common.block.BlockCaveChimes;
import com.fincode.cavechimes.init.CaveChimesBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "cavechimes", value=Dist.CLIENT)
public class TileEntityCaveChimes extends TileEntity {
    @OnlyIn(Dist.CLIENT)
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
        super(CaveChimesBlocks.CAVE_CHIMES_TYPE);

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
            this.signal = world.getBlockState(pos).get(BlockCaveChimes.VOLUME).shortValue();
            return;
        }

        this.soundLoop = new com.fincode.cavechimes.client.audio.CaveChimesSound(pos);

        soundLoop.setVolume(signal);
        if (listeners.size() < ConfigClient.client.priority.limit.get())
            addListener();
        else
            CaveChimesMod.getLogger().info("Cave Chimes limit exceeded! (" + listeners.size() + ")");
        setPlaying(true);
        //CaveChimesMod.getLogger().info("Loading");
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();

        if (!world.isRemote) return;

        removeListener();
        setPlaying(false);
        //CaveChimesMod.getLogger().info("Unloading");
    }

    @Override
    public void invalidateCaps() { // Before 1.12 we had invalidate(). Now we have INVALIDATE()!!1!
        super.invalidateCaps();

        if (!world.isRemote) return;

        removeListener();
        setPlaying(false);
        //CaveChimesMod.getLogger().info("Invalidating");
    }

    public void setVolume(short volume) {
        if (signal == volume) return;
        signal = volume;

        CaveChimesMod.getLogger().info("OH NO " + world.isRemote);

        if (!world.isRemote) return;
        setPlaying(volume != 15);
        soundLoop.setVolume(volume);
    }

    public short getVolume() {
        return signal;
    }

    @OnlyIn(Dist.CLIENT)
    private void setPlayingInternal(boolean value) {
        if (value && !net.minecraft.client.Minecraft.getInstance().getSoundHandler().isPlaying(soundLoop)) {
            net.minecraft.client.Minecraft.getInstance().getSoundHandler().stop(soundLoop);
            net.minecraft.client.Minecraft.getInstance().getSoundHandler().play(soundLoop);
        }
        else {
            net.minecraft.client.Minecraft.getInstance().getSoundHandler().stop(soundLoop);
            soundLoop.stop();
        }
        isPlaying = value;
    }

    @OnlyIn(Dist.CLIENT)
    private void setPlaying(boolean value) {
        if (value == isPlaying) return;

        if (playing.size() >= 8) {
            if (value) return;
        }

        setPlayingInternal(value);

        if (value) playing.add(this);
        else playing.remove(this);
    }

    @OnlyIn(Dist.CLIENT)
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
            if (!oldPlaying.contains(chime) || !chime.isPlaying || !net.minecraft.client.Minecraft.getInstance().getSoundHandler().isPlaying(chime.soundLoop))
                chime.setPlayingInternal(true);
            //CaveChimesMod.getLogger().info("Distance from player: " + player.getDistanceSq(chime.pos));
        }
    }

    @SubscribeEvent // My bad, I forgot to add this annotation LOL
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.CLIENT) return;

        int refreshInterval = ConfigClient.client.priority.refreshInterval.get() * 20;

        if (!ConfigClient.client.priority.enabled.get() || refreshInterval == 0) return;
        ++interval;

        if (interval < refreshInterval) return;
        //CaveChimesMod.getLogger().info("Shuffling your cards");
        interval = 0;
        refresh(event.player);
    }
}
