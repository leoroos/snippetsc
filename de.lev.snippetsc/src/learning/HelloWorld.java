package learning;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class HelloWorld extends ApplicationWindow {
  public static void main(String[] args) {
    new HelloWorld().run();
  }
  public HelloWorld() {
    super(null);
  }
  public void run() {
    setBlockOnOpen(true);
    open();
    Display.getCurrent().dispose();
  }
  protected Control createContents(Composite parent) {
    Label label = new Label(parent, SWT.CENTER);
    label.setText("Hello, World");
    return label;
  }
}