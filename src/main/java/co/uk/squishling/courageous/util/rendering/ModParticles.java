package co.uk.squishling.courageous.util.rendering;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;

public class ModParticles {

    public static ParticleType<FallingWaterParticleData> FALLING_WATER_PARTICLE = new ParticleType<FallingWaterParticleData>(false, FallingWaterParticleData.DESERIALIZER);

}
