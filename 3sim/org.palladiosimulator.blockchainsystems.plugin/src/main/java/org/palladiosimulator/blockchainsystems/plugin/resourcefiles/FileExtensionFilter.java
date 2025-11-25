package org.palladiosimulator.blockchainsystems.plugin.resourcefiles;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * A filter for Eclipse viewers that allows only files with specific extensions to be displayed.
 *
 * @author Yannik Sproll
 */
public class FileExtensionFilter extends ViewerFilter {
    private final String[] extensions;

    public FileExtensionFilter(String[] extensions) {
        this.extensions = extensions;
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof IResource resource) {
            if (resource instanceof IFile) {
                String fileExtension = resource.getFileExtension();
                if (fileExtension != null) {
                    for (String extension : extensions) {
                        if (fileExtension.equalsIgnoreCase(extension)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            // Allow folders to be displayed
            return true;
        }
        return false;
    }
}