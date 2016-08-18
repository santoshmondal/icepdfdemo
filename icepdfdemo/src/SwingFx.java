import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SwingFx extends Application {

         @Override
         public void start(Stage stage) {
             final SwingNode swingNode = new SwingNode();
             createAndSetSwingContent(swingNode);

             StackPane pane = new StackPane();
             pane.getChildren().add(swingNode);

             stage.setScene(new Scene(pane, 900, 900));
             stage.show();
         }

         private void createAndSetSwingContent(final SwingNode swingNode) {
             /*SwingUtilities.invokeLater(new Runnable() {
                 @Override
                 public void run() {
                     swingNode.setContent(new JButton("Click me!"));
                 }
             });*/
        	 
        	// Get a file from the command line to open
             final String filePath = "/Users/santosh/Documents/fileshare/GATE_ME_2013.pdf";

             SwingUtilities.invokeLater(new Runnable() {
                 public void run() {
                     // build a component controller
                     SwingController controller = new SwingController();
                     controller.setIsEmbeddedComponent(true);

                     PropertiesManager properties = new PropertiesManager(
                             System.getProperties(),
                             ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

                     // read/store the font cache.
                     ResourceBundle messageBundle = ResourceBundle.getBundle(
                             PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                     new FontPropertiesManager(properties, System.getProperties(), messageBundle);

                     properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");

                     SwingViewBuilder factory = new SwingViewBuilder(controller, properties);

                     // add interactive mouse link annotation support via callback
                     controller.getDocumentViewController().setAnnotationCallback(
                             new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
                     JPanel viewerComponentPanel = factory.buildViewerPanel();
                     //JFrame applicationFrame = new JFrame();
                     //applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                     //applicationFrame.getContentPane().add(viewerComponentPanel);
                     // Now that the GUI is all in place, we can try openning a PDF
                     controller.openDocument(filePath);

                     // add the window event callback to dispose the controller and
                     // currently open document.
                     //applicationFrame.addWindowListener(controller);

                     // show the component
                     //applicationFrame.pack();
                     //applicationFrame.setVisible(true);
                     
                     swingNode.setContent(viewerComponentPanel);
                 }
             });
        	 
         }
 
         public static void main(String[] args) {
             launch(args);
         }
     }