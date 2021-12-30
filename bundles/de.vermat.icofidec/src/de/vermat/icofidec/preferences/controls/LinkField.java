package de.vermat.icofidec.preferences.controls;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

public class LinkField {

	private final Link control;

	public LinkField(String text, Composite parent) {
		this(text, parent, SWT.NONE);
	}
			
	public LinkField(String text, Composite parent, int style) {
		control = new Link(parent, style);
		control.setText(text);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		parent.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalSpan = layout.numColumns;
		control.setLayoutData(gd);
	}

	public Link getControl() {
		return control;
	}
	
	public LinkField addLinkSelectionListener(SelectionListener listener) {
		Assert.isNotNull(listener);
		control.addSelectionListener(listener);
		return this;
	}
}