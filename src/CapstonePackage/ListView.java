package CapstonePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;
import javafx.util.Pair;

public class ListView extends JPanel {
	
        public UIManager manager;
        
        public final int COMP_WIDTH = 350;
        
        public ListView(){
        }

        public ListView(UIManager parent){        
            manager = parent;
        }
        
        // COMPONENTS
        private JLabel txt;
        private JTextField search; //Filter text box
        private JList<Graph> list; //Graph list
        private JScrollPane scroller; //Scroll pane to contain list
        private JButton newFile; //Button to request loading new files
        private JFileChooser selector; //File selection
        
        private ArrayList<String> graphIDs; //Stores all loaded Graph IDs
        
        public static ArrayList<Integer> loaded = new ArrayList<>(); //Notes which graphs are currently loaded into the visualizer
       
        /**
         * Initialize UI components.
         */
	public void setupGUI() {
                graphIDs = new ArrayList<>();
            
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setPreferredSize(new Dimension(COMP_WIDTH, 500));

		//Add the text above the list
		txt = new JLabel("Filter selection:");
		add(txt);

		//Search Field
		search = new JTextField();
		add(search);		
		
		//Add the list, and set it's settings correctly
                if(manager == null){
                    return;
                }
                
                Pair<ArrayList<Graph>, ArrayList<String>> input = manager.getKeys();
                
                graphIDs.addAll(input.getValue());
                
		list = new JList(input.getKey().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
                
                ListCellRenderer renderer = new ListCellRenderer(){
                    @Override
                    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                        JLabel label = new JLabel(((Graph)value).key);
                        if(ListView.loaded.contains(index)) label.setForeground(Color.GREEN);
                        
                        label.setOpaque(true);
                        label.setBackground(Color.WHITE);
                        if(isSelected)label.setBackground(list.getSelectionBackground());
                        return label;
                    }
                };
                
                list.setCellRenderer(renderer);
                
                scroller = new JScrollPane();
                scroller.setViewportView(list);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setPreferredSize(new Dimension(COMP_WIDTH, 650));
                
		add(scroller); //This adds a scroll bar for the list
                           
		//Change list when searched
		search.setPreferredSize(new Dimension(COMP_WIDTH, 20));
                search.addKeyListener(new KeyAdapter(){
                    @Override
                    public void keyReleased(KeyEvent e){
                        if(search.getText().equals("")) {
                        Pair<ArrayList<Graph>, ArrayList<String>> input = manager.getKeys();
                        list.setListData(input.getKey().toArray(new Graph[0]));
                        graphIDs.clear();
                        graphIDs.addAll(input.getValue());
                    } else {
                        Pair<ArrayList<Graph>, ArrayList<String>> input = manager.getKeys();
                        Graph[] nList = simString(search.getText(), input.getKey());
                        list.setListData(nList);
                    }
                    }
                });
                
                //Listener for selection of graphs from list
                MouseListener listener = new MouseAdapter() {
                    public void mouseClicked(MouseEvent e){
                        if(e.getClickCount() == 2 && e.getComponent().equals(list)){
                            if (list.getSelectedIndex() != -1){
                                Graph target = list.getSelectedValue();
                                boolean flag = manager.renderGraph(target, graphIDs.get(list.getSelectedIndex()));
                                if(flag && !loaded.contains(list.getSelectedIndex())){
                                    loaded.add(list.getSelectedIndex());
                                }else if(!flag && loaded.contains(list.getSelectedIndex())){
                                    loaded.remove(loaded.indexOf(list.getSelectedIndex()));
                                }
                            }
                        }
                        e.getComponent().getParent().repaint();
                    }
                };
                list.addMouseListener(listener);
                
                //Setup of file selector
                selector = new JFileChooser();
                
                newFile = new JButton("Add");
                newFile.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        int return_val = selector.showOpenDialog(ListView.this);
                        
                        if(return_val == JFileChooser.APPROVE_OPTION){
                            File toLoad = selector.getSelectedFile();
                            
                            manager.filesToLoad.add(toLoad);
                            manager.addFiles();
                            refresh();
                        }
                    }
                });
                
                add(newFile);
	}
        
        /**
         * Refreshes the ListView on acquiring new data.
         */
        public void refresh(){
            Pair<ArrayList<Graph>, ArrayList<String>> input = manager.getKeys();
            list.setListData(input.getKey().toArray(new Graph[0]));
            graphIDs.clear();
            graphIDs.addAll(input.getValue());
            this.repaint();
        }

        /**
         * Filters the list of offered graphs based on the contents of the search bar.
         * @param s Current contents of the search bar.
         * @param list ArrayList of graph keys
         * @return An array containing all matching graph keys.
         */
	public Graph[] simString(String s, ArrayList<Graph> list) {
		ArrayList<Graph> nList = new ArrayList();
                
                
                ArrayList<String> findList = new ArrayList<>();
                
                while(s.toLowerCase().contains("|contains:")){
                    String find = s.substring(s.indexOf("|contains:")+10);
                    if(!find.equals("") && find.indexOf("|")!=-1){
                        find = find.substring(0, find.indexOf("|"));
                        s = s.replace("|contains:"+find+"|", "");
                        findList.add(find);
                    }else if(find.indexOf("|")==-1){
                        break;
                    }
                }

		for (Graph entry : list) {
			if (entry.key.toLowerCase().contains(s.toLowerCase())) {
                            if(findList.size()>0){
                                boolean found = true;
                                for(String find : findList){
                                    found = entry.labelInGraph(find);
                                    if(!found)break;
                                }
                                if(found)nList.add(entry);
                            }else{
                                nList.add(entry);
                            }
			}
		}
		Graph[] nListArr = new Graph[nList.size()];
		nList.toArray(nListArr);
		return nListArr;
	}
}