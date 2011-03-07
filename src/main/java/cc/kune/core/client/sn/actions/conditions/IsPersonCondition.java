package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsPersonCondition implements GuiAddCondition {

    @Inject
    public IsPersonCondition() {
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {

        final Object target = descr.getTarget();
        if (target instanceof UserSimpleDTO) {
            return true;
        }
        return (((GroupDTO) target).isPersonal());
    }
}
