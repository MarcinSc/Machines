/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.machines.world;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import org.terasology.asset.AssetUri;
import org.terasology.math.Rotation;
import org.terasology.math.Side;
import org.terasology.math.Yaw;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.family.BlockBuilderHelper;
import org.terasology.world.block.family.BlockFamily;
import org.terasology.world.block.family.BlockFamilyFactory;
import org.terasology.world.block.family.RegisterBlockFamilyFactory;
import org.terasology.world.block.loader.BlockDefinition;

import java.util.Map;

@RegisterBlockFamilyFactory("surfacePlacement")
public class SurfacePlacementFamilyFactory implements BlockFamilyFactory {

    private static final String TOP = "top";
    private static final String SIDES = "sides";
    private static final String BOTTOM = "bottom";

    @Override
    public BlockFamily createBlockFamily(BlockBuilderHelper blockBuilder, AssetUri blockDefUri, BlockDefinition blockDefinition, JsonObject blockDefJson) {
        Map<Side, Block> blockMap = Maps.newEnumMap(Side.class);
        BlockDefinition topDef = blockBuilder.getBlockDefinitionForSection(blockDefJson, TOP);
        if (topDef != null) {
            Block block = blockBuilder.constructSimpleBlock(blockDefUri, topDef);
            block.setDirection(Side.TOP);
            blockMap.put(Side.TOP, block);
        }
        BlockDefinition sideDef = blockBuilder.getBlockDefinitionForSection(blockDefJson, SIDES);
        if (sideDef != null) {
            blockMap.put(Side.FRONT, blockBuilder.constructTransformedBlock(blockDefUri, sideDef, Rotation.rotate(Yaw.NONE)));
            blockMap.put(Side.LEFT, blockBuilder.constructTransformedBlock(blockDefUri, sideDef, Rotation.rotate(Yaw.CLOCKWISE_90)));
            blockMap.put(Side.BACK, blockBuilder.constructTransformedBlock(blockDefUri, sideDef, Rotation.rotate(Yaw.CLOCKWISE_180)));
            blockMap.put(Side.RIGHT, blockBuilder.constructTransformedBlock(blockDefUri, sideDef, Rotation.rotate(Yaw.CLOCKWISE_270)));
        }
        BlockDefinition bottomDef = blockBuilder.getBlockDefinitionForSection(blockDefJson, BOTTOM);
        if (bottomDef != null) {
            Block block = blockBuilder.constructSimpleBlock(blockDefUri, bottomDef);
            block.setDirection(Side.BOTTOM);
            blockMap.put(Side.BOTTOM, block);
        }
        return new SurfacePlacementFamily(new BlockUri(blockDefUri.getModuleName(), blockDefUri.getAssetName()), blockMap, blockDefinition.categories);
    }

}
