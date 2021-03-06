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
package org.terasology.fluidTransport.ui;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.fluidTransport.systems.ExtendedFluidManager;
import org.terasology.rendering.nui.BaseInteractionScreen;
import org.terasology.rendering.nui.databinding.ReadOnlyBinding;
import org.terasology.rendering.nui.widgets.UILabel;

public class TankScreen extends BaseInteractionScreen {

    UILabel fluidContainerWidget;

    @Override
    protected void initialise() {
        fluidContainerWidget = find("fluidWidget", UILabel.class);
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected void initializeWithInteractionTarget(EntityRef interactionTarget) {
        if (ExtendedFluidManager.isTank(interactionTarget)) {
            fluidContainerWidget.bindText(new ReadOnlyBinding<String>() {
                @Override
                public String get() {
                    float tankFluidVolume = ExtendedFluidManager.getTankFluidVolume(getInteractionTarget());
                    float tankTotalVolume = ExtendedFluidManager.getTankTotalVolume(getInteractionTarget());
                    return tankFluidVolume + "/" + tankTotalVolume + "mL";
                }
            });
        }
    }
}
