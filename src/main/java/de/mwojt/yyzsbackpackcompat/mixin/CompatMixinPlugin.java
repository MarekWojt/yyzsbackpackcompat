package de.mwojt.yyzsbackpackcompat.mixin;

import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CompatMixinPlugin implements IMixinConfigPlugin {

    private static final String COMPAT_PREFIX = "de.mwojt.yyzsbackpackcompat.mixin.compat.";

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(COMPAT_PREFIX)) return true;
        // Package name after COMPAT_PREFIX is the mod ID (e.g. "aether", "farmersdelight")
        String remainder = mixinClassName.substring(COMPAT_PREFIX.length());
        int dot = remainder.indexOf('.');
        if (dot < 0) return true;
        String modId = remainder.substring(0, dot);
        return LoadingModList.get().getModFileById(modId) != null;
    }

    @Override public void onLoad(String mixinPackage) {}
    @Override public String getRefMapperConfig() { return null; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
