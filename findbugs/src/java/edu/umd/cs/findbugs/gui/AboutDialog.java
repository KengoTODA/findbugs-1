/*
 * FindBugs - Find bugs in Java programs
 * Copyright (C) 2003,2004 University of Maryland
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * AboutDialog.java
 *
 * Created on April 6, 2003, 7:05 PM
 */

package edu.umd.cs.findbugs.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.regex.*;

import edu.umd.cs.findbugs.Version;

/**
 * The Help:About dialog.
 *
 * @author David Hovemeyer
 */
public class AboutDialog extends javax.swing.JDialog {

	private FindBugsFrame parent;

	/**
	 * Creates new form AboutDialog
	 */
	public AboutDialog(FindBugsFrame parent, boolean modal) {
		super(parent, modal);
		this.parent = parent;

		initComponents();

		try {
			processPage(aboutEditorPane, "edu/umd/cs/findbugs/gui/help/About.html");
			licenseEditorPane.setPage(getClass().getClassLoader().getResource("edu/umd/cs/findbugs/gui/help/License.html"));
			acknowldgementsEditorPane.setPage(getClass().getClassLoader().getResource("edu/umd/cs/findbugs/gui/help/Acknowledgements.html"));
		} catch (IOException e) {
			parent.getLogger().logMessage(ConsoleLogger.ERROR, e.toString());
		}

		setTitle(MessageFormat.format(L10N.getLocalString("dlg.aboutfindbugs_ttl", "About FindBugs {0}"), Version.RELEASE));
	}

	/**
	 * Process an HTML page to replace certain substitution patterns.
	 * Right now, we just expand @VERSION@.
	 */
	private void processPage(javax.swing.JEditorPane pane, String fileName) throws IOException {
		InputStream in = null;

		try {
			StringBuffer buf = new StringBuffer();

			// Open the file as a stream
			in = getClass().getClassLoader().getResourceAsStream(fileName);
			if (in == null)
				throw new IOException(MessageFormat.format(L10N.getLocalString("msg.couldntload_txt", "Couldn't load {0}"), fileName));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			// Replace instances of @VERSION@ with actual version number
			Pattern pattern = Pattern.compile("@VERSION@");
			String line;
			while ((line = reader.readLine()) != null) {
				line = pattern.matcher(line).replaceAll(Version.RELEASE);
				buf.append(line);
				buf.append('\n');
			}

			// Load the page into the editor pane
			String text = buf.toString();
			pane.setContentType("text/html");
			pane.setText(text);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		aboutTabPane = new javax.swing.JTabbedPane();
		aboutScrollPane = new javax.swing.JScrollPane();
		aboutEditorPane = new javax.swing.JEditorPane();
		licenseScrollPane = new javax.swing.JScrollPane();
		licenseEditorPane = new javax.swing.JEditorPane();
		acknowledgmentsScrollPane = new javax.swing.JScrollPane();
		acknowldgementsEditorPane = new javax.swing.JEditorPane();
		jSeparator1 = new javax.swing.JSeparator();
		okButton = new javax.swing.JButton();

		getContentPane().setLayout(new java.awt.GridBagLayout());

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		aboutEditorPane.setEditable(false);
		aboutScrollPane.setViewportView(aboutEditorPane);

		aboutTabPane.addTab("About", aboutScrollPane);

		licenseEditorPane.setEditable(false);
		licenseScrollPane.setViewportView(licenseEditorPane);

		aboutTabPane.addTab("License", licenseScrollPane);

		acknowldgementsEditorPane.setEditable(false);
		acknowledgmentsScrollPane.setViewportView(acknowldgementsEditorPane);

		aboutTabPane.addTab("Acknowledgments", acknowledgmentsScrollPane);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		getContentPane().add(aboutTabPane, gridBagConstraints);

		{
			aboutTabPane.setTitleAt(0, L10N.getLocalString("dlg.about_tab", "About"));
			aboutTabPane.setTitleAt(1, L10N.getLocalString("dlg.license_tab", "License"));
			aboutTabPane.setTitleAt(2, L10N.getLocalString("dlg.acknowledgements_tab", "Acknowledgements"));
		}
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		getContentPane().add(jSeparator1, gridBagConstraints);

		okButton.setMnemonic('O');
		okButton.setText("OK");
		okButton.setText(L10N.getLocalString("dlg.ok_btn", "OK"));
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		getContentPane().add(okButton, gridBagConstraints);

		pack();
	}//GEN-END:initComponents

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		closeDialog();
	}//GEN-LAST:event_okButtonActionPerformed

	/**
	 * Closes the dialog
	 */
	private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
		closeDialog();
	}//GEN-LAST:event_closeDialog

	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JEditorPane aboutEditorPane;
	private javax.swing.JScrollPane aboutScrollPane;
	private javax.swing.JTabbedPane aboutTabPane;
	private javax.swing.JEditorPane acknowldgementsEditorPane;
	private javax.swing.JScrollPane acknowledgmentsScrollPane;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JEditorPane licenseEditorPane;
	private javax.swing.JScrollPane licenseScrollPane;
	private javax.swing.JButton okButton;
	// End of variables declaration//GEN-END:variables

}
