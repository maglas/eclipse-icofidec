package de.vermat.icofidec.preferences.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LabelField {

	private final Label control;

	public LabelField(String text, Composite parent) {
		this(text, parent, SWT.NONE);
	}

	public LabelField(String text, Composite parent, int style) {
		control = new Label(parent, style);
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

	public Label getControl() {
		return control;
	}
	
	public static LabelField spacer(Composite parent) {
		return new LabelField("", parent);
	}
}