package ledtech.modid.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;

public class LedBlock extends Block {

    public static final BooleanProperty LIT =
            BooleanProperty.create("lit");

    public static final MapCodec<LedBlock> CODEC =
            simpleCodec(LedBlock::new);


    public LedBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(LIT, false)
        );
    }


    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(LIT);
    }


    @Override
    protected void onPlace(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState oldState,
            boolean movedByPiston
    ) {
        boolean powered = level.hasNeighborSignal(pos);

        if (powered != state.getValue(LIT)) {
            level.setBlock(
                    pos,
                    state.setValue(LIT, powered),
                    Block.UPDATE_ALL
            );
        }

        super.onPlace(
                state,
                level,
                pos,
                oldState,
                movedByPiston
        );
    }


    @Override
    protected void neighborChanged(
            BlockState state,
            Level level,
            BlockPos pos,
            Block block,
            Orientation orientation,
            boolean movedByPiston
    ) {
        boolean powered = level.hasNeighborSignal(pos);

        if (powered != state.getValue(LIT)) {
            level.setBlock(
                    pos,
                    state.setValue(LIT, powered),
                    Block.UPDATE_ALL
            );
        }

        super.neighborChanged(
                state,
                level,
                pos,
                block,
                orientation,
                movedByPiston
        );
    }
}