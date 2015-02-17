package kendzi.josm.kendzi3d.ui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class RoofList {

	private final Map<String, ImageIcon> imageMap;
	@SuppressWarnings("rawtypes")
	public JList list;
	String[] nameList = {"0_0","0_1"};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JList getRoofList(){

		JList roofList =  new JList();
		list = new JList(nameList);
		roofList= list;
		
		return roofList;

	}

	@SuppressWarnings({ "unchecked"})
	public RoofList() {
		imageMap = createImageMap(nameList);
		getRoofList().setCellRenderer(new RoofListRenderer());
	}
	public void createAndShowGui(){
		JScrollPane scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(300, 400));

//		JFrame frame = new JFrame();
		JPanel frame = new JPanel();
		frame.add(scroll);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public class RoofListRenderer extends DefaultListCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9108362683919058583L;
		Font font = new Font("helvitica", Font.BOLD, 24);

		@SuppressWarnings("rawtypes")
		@Override
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {

			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(imageMap.get((String) value));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			label.setFont(font);
			return label;
		}
	}

	private Map<String, ImageIcon> createImageMap(String[] list) {
		Map<String, ImageIcon> map = new HashMap<>();
		try{
			for (String s : list) {
				map.put(s, new ImageIcon("C://Users//anma8806//Desktop//Kendzi3D_Roof_Type//Roof" + s + ".jpg"));
				//            getClass().getResource(
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	//    public static void main(String[] args) {
	//        SwingUtilities.invokeLater(new Runnable() {
	//            public void run() {
	//                new RoofList();
	//            }
	//        });
	//    }
}