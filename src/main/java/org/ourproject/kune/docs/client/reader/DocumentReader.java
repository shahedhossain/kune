package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.workspace.client.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.ContextItemDTO;

public interface DocumentReader extends WorkspaceComponent {
    void load(String contextRef, ContextItemDTO selectedItem);
}
