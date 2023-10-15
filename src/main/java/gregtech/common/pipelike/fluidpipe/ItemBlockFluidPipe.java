package gregtech.common.pipelike.fluidpipe;

import gregtech.api.pipenet.block.material.BlockMaterialPipe;
import gregtech.api.pipenet.block.material.ItemBlockMaterialPipe;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBlockFluidPipe extends ItemBlockMaterialPipe<FluidPipeType, FluidPipeProperties> {

    public ItemBlockFluidPipe(BlockFluidPipe block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        FluidPipeProperties pipeProperties = blockPipe.createItemProperties(stack);
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_transfer_rate", pipeProperties.getThroughput()));
        tooltip.add(I18n.format("gregtech.fluid_pipe.capacity", pipeProperties.getThroughput() * 20));
        tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", pipeProperties.getMaxFluidTemperature()));
        if (pipeProperties.getTanks() > 1) tooltip.add(I18n.format("gregtech.fluid_pipe.channels", pipeProperties.getTanks()));

        BlockMaterialPipe<?, ?, ?> blockMaterialPipe = (BlockMaterialPipe<?, ?, ?>) blockPipe;
        if (TooltipHelper.isShiftDown()) {
            if (pipeProperties.isGasProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.gas_proof"));
            else if (ModHandler.isMaterialWood(blockMaterialPipe.getItemMaterial(stack)))
                tooltip.add(I18n.format("gregtech.fluid_pipe.not_gas_proof"));
            if (pipeProperties.isAcidProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.acid_proof"));
            if (pipeProperties.isCryoProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.cryo_proof"));
            if (pipeProperties.isPlasmaProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.plasma_proof"));
            tooltip.add(I18n.format("gregtech.tool_action.wrench.connect_and_block"));
            tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
            tooltip.add(I18n.format("gregtech.tool_action.crowbar"));
        } else {
            tooltip.add(I18n.format("gregtech.tooltip.tool_fluid_hold_shift"));
        }

        if (ConfigHolder.misc.debug) {
            tooltip.add("MetaItem Id: " + blockMaterialPipe.getPrefix().name + blockMaterialPipe.getItemMaterial(stack).toCamelCaseString());
        }
    }
}
