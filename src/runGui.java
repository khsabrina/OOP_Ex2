import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class runGui implements ActionListener {
    private DWGA graph;
    JLabel error;
    JButton button;
    JFrame frame1;
    JTextField textField;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JMenu Graph;
    JMenu AlgoGraph;
    JMenuItem AddNode;
    JMenuItem RemoveNode;
    JMenuItem AddEdge;
    JMenuItem RemoveEdge;
    JMenuItem GetNode;
    JMenuItem GetEdge;
    JMenuItem ShortPath;
    JMenuItem CenterNode;
    JMenuItem ShortPathDis;
    JMenuItem Save;
    JMenuItem Load;
    JMenuItem IsConnected;
    JMenuItem Tsp;
    JFrame frame;
    MyPanel1 The_paint;
    String s1, s2, s3, s4;
    String cases;

    public runGui(DirectedWeightedGraphAlgorithms graph) {
        this.graph = new DWGA(graph.copy());
        frame = new JFrame(); //creates a frame

        JMenuBar menuBar = new JMenuBar(); //creates a menu bar above inorder for the user to chose Algo/Graph
        menus(menuBar);

        //opening the GUI
        frame.setTitle("my GUI"); //
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit the app

        //frame.setSize(1000,1000); //sets the size of the window
        frame.setJMenuBar(menuBar);//adding the menu bar to the GUI
        this.The_paint = new MyPanel1(this.graph);
        frame.add(The_paint);
        frame.repaint();
        //pack is for the frame to be just on the right size
        frame.pack();
        frame.setVisible(true); //make frame visible
        // //setLayout is for the frame to be just on the right size
        frame.setLayout(new FlowLayout());//controls the size of things that we are adding(button)

        frame.getContentPane().setBackground(Color.white);//change the color of background

    }

    private void menus(JMenuBar menuBar) {
        Icon graphIcon = new ImageIcon("C:\\Users\\rivka\\IdeaProjects\\Oop\\data\\graph.png");
        Icon menuIcon = new ImageIcon("C:\\Users\\rivka\\IdeaProjects\\Oop\\data\\menuBar.png");
        this.AlgoGraph = new JMenu("AlgoGraph"); //creates the option for Algo
        this.Graph = new JMenu("Graph");//creates the option for Graph
        this.Graph.setIcon(graphIcon);
        this.AlgoGraph.setIcon(menuIcon);


        menuBar.add(Graph);
        menuBar.add(AlgoGraph);

        Icon saveIcon = new ImageIcon("C:\\Users\\rivka\\IdeaProjects\\Oop\\data\\save.png");
        //all the thing below will create a sub options in the Algo/Graph
        AddNode = new JMenuItem("Add Node");
        RemoveNode = new JMenuItem("Remove Node");
        AddEdge = new JMenuItem("Add Edge");
        RemoveEdge = new JMenuItem("Remove Edge");
        GetNode = new JMenuItem("Get Node");
        GetEdge = new JMenuItem("Get Edge");
        ShortPathDis = new JMenuItem("Short Path Distance");
        ShortPath = new JMenuItem("Short Path");
        Tsp = new JMenuItem("Tsp", graphIcon);
        CenterNode = new JMenuItem("Center");
        Save = new JMenuItem("Save", saveIcon);
        Load = new JMenuItem("Load");
        IsConnected = new JMenuItem("Is Connected");


        //allows to do things when click on it, the function that does it is below "actionPerformed"
        //I put in everyone of them "this" because of the implementation that I did to ActionListener
        AddEdge.addActionListener(this);
        AddNode.addActionListener(this);
        RemoveEdge.addActionListener(this);
        RemoveNode.addActionListener(this);
        GetEdge.addActionListener(this);
        GetNode.addActionListener(this);
        ShortPath.addActionListener(this);
        CenterNode.addActionListener(this);
        Tsp.addActionListener(this);
        ShortPathDis.addActionListener(this);
        Save.addActionListener(this);
        Load.addActionListener(this);
        IsConnected.addActionListener(this);

        //Adding to the main option sub option
        this.Graph.add(GetNode);
        this.Graph.add(AddNode);
        this.Graph.add(RemoveNode);
        Graph.add(GetEdge);
        Graph.add(AddEdge);
        Graph.add(RemoveEdge);
        AlgoGraph.add(ShortPath);
        AlgoGraph.add(Tsp);
        AlgoGraph.add(CenterNode);
        AlgoGraph.add(IsConnected);
        AlgoGraph.add(Save);
        AlgoGraph.add(Load);
        AlgoGraph.add(ShortPathDis);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if(e.getSource()=ShortPathDis)
        //All the if's below are sending to the button event where all of the changes in the graph happning
        if (e.getSource() == AddEdge) {
            cases = "AddEdge";
            Input3Values();
        }
        if (e.getSource() == AddNode) {
            cases = "AddNode";
            input4Values();
        }
        if (e.getSource() == RemoveEdge) {
            cases = "RemoveEdge";
            Input2Values();
        }
        if (e.getSource() == RemoveNode) {
            cases = "RemoveNode";
            Input1Value();
        }
        if (e.getSource() == GetEdge) {
            cases = "GetEdge";
            Input2Values();
        }
        if (e.getSource() == GetNode) {
            cases = "GetNode";
            Input1Value();
        }
        if (e.getSource() == ShortPath) {
            cases = "ShortPath";
            Input2Values();
        }
        if (e.getSource() == CenterNode) {
            The_paint.edges = new ArrayList<>();
            List<NodeData> nodes = new ArrayList<>();
            nodes.add(this.graph.center());
            if (nodes.size() == 0) {
                JFrame frame2 = new JFrame(); //creating a new window for the input from the user
                JLabel label = new JLabel();
                label.setText("the graph is not connected");
                frame2.add(label);
                frame2.setLayout(new FlowLayout());
                frame2.setVisible(true);
                frame2.pack();
                frame2.setLocationRelativeTo(null);
            }
            The_paint.setNodes(nodes);
            frame.repaint();
        }
        if (e.getSource() == Tsp) {
            inputTSP();
            cases = "TSP";
            //needs to get a list of nodes
            //not sure how input I sould get from the user
        }
        if (e.getSource() == ShortPathDis) {
            cases = "ShortPathDis";
            Input2Values();
        }
        if (e.getSource() == Save) {
            cases = "Save";
            InputSave();
        }
        if (e.getSource() == Load) {
            cases = "Load";
            InputLoad();
        }
        if (e.getSource() == IsConnected) {
            IsConnected(this.graph.isConnected());
        }
        if (e.getSource() == button) {
            if (cases.equals("AddNode")) {
                s1 = textField.getText();
                s2 = textField1.getText();
                s3 = textField2.getText();
                s4 = textField3.getText();
                if (isNumericInteger(s1) && isNumericDouble(s2) && isNumericDouble(s3) && isNumericDouble(s4)) {
                    frame1.dispose();
                    Geo_Location p = new Geo_Location(Double.parseDouble(s2), Double.parseDouble(s3), Double.parseDouble(s4));
                    NodeData n = new Node_Data(Integer.parseInt(s1), p);
                    this.graph.getGraph().addNode(n);
                    frame.repaint();
                    error.setForeground(frame.getBackground());
                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
            }
            if (cases.equals("RemoveNode")) {
                s1 = textField.getText();
                if (isNumericInteger(s1)) {
                    frame1.dispose();
                    The_paint.edges = new ArrayList<>();
                    The_paint.nodes = new ArrayList<>();
                    error.setForeground(frame.getBackground());
                    this.graph.getGraph().removeNode(Integer.parseInt(s1));
                    frame.repaint();
                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
                s1 = null;
            }
            if (cases.equals("AddEdge")) {
                s1 = textField.getText();
                s2 = textField1.getText();
                s3 = textField2.getText();
                if (isNumericInteger(s1) && isNumericInteger(s2) && isNumericDouble(s3)) {
                    frame1.dispose();
                    this.graph.getGraph().connect(Integer.parseInt(s1), Integer.parseInt(s2), Double.parseDouble(s3));
                    frame.repaint();
                    error.setForeground(frame.getBackground());


                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
                s1 = null;
                s2 = null;
                s3 = null;
            }
            if (cases.equals("RemoveEdge")) {
                s1 = textField.getText();
                s2 = textField1.getText();
                if (isNumericInteger(s1) && isNumericInteger(s2)) {
                    frame1.dispose();
                    The_paint.edges = new ArrayList<>();
                    The_paint.nodes = new ArrayList<>();
                    if (graph.getGraph().getEdge(Integer.parseInt(s1), Integer.parseInt(s2)) != null) {
                        graph.getGraph().removeEdge(Integer.parseInt(s1), Integer.parseInt(s2));
                    }
                    frame.repaint();
                    error.setForeground(frame.getBackground());
                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }

            }
            if (cases.equals("GetNode")) {
                s1 = textField.getText();
                if (isNumericInteger(s1)) {
                    frame1.dispose();
                    The_paint.edges = new ArrayList<>();
                    List<NodeData> nodes = new ArrayList<>();
                    nodes.add(this.graph.getGraph().getNode(Integer.parseInt(s1)));
                    The_paint.setNodes(nodes);
                    frame.repaint();

                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
            }
            if (cases.equals("GetEdge")) {
                s1 = textField.getText();
                s2 = textField1.getText();
                if (isNumericInteger(s1) && isNumericInteger(s2)) {
                    frame1.dispose();
                    The_paint.nodes = new ArrayList<>();
                    List<EdgeData> edges = new ArrayList<>();
                    EdgeData check = this.graph.getGraph().getEdge(Integer.parseInt(s1), Integer.parseInt(s2));
                    if (check != null) {
                        edges.add(check);
                    }
                    The_paint.edges = edges;
                    frame.repaint();
                    error.setForeground(frame.getBackground());

                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
            }
            if (cases.equals("ShortPath")) {
                s1 = textField.getText();
                s2 = textField1.getText();
                if (isNumericInteger(s1) && isNumericInteger(s2)) {
                    frame1.dispose();
                    if (this.graph.getGraph().getNode(Integer.parseInt(s1)) != null && this.graph.getGraph().getNode(Integer.parseInt(s2)) != null) {
                        List<NodeData> ans = this.graph.shortestPath(Integer.parseInt(s1), Integer.parseInt(s2));
                        List<EdgeData> edges = new ArrayList<>();
                        for (int i = 0; i < ans.size() - 1; i++) {
                            edges.add(this.graph.getGraph().getEdge(ans.get(i).getKey(), ans.get(i + 1).getKey()));
                        }
                        The_paint.edges = edges;
                        The_paint.nodes = ans;
                    }
                    frame.repaint();

                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }

            }
            if (cases.equals("TSP")) {
                s1 = textField.getText();
                boolean ifValid = true;
                String[] numbers = s1.split(",");
                for (String number : numbers) {
                    if (!isNumericInteger(number)) {
                        ifValid = false;
                    }
                }
                if (!ifValid) {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
                if (ifValid) {
                    frame1.dispose();
                    List<NodeData> cities = new ArrayList<>();
                    for (String number : numbers) {
                        if (this.graph.getGraph().getNode(Integer.parseInt(number)) != null) {
                            cities.add(this.graph.getGraph().getNode(Integer.parseInt(number)));
                        }
                    }
                    List<NodeData> ans = this.graph.tsp(cities);
                    List<EdgeData> edges = new ArrayList<>();
                    for (int i = 0; i < ans.size() - 1; i++) {
                        if (this.graph.getGraph().getEdge(ans.get(i).getKey(), ans.get(i + 1).getKey()) == null) {
                            edges = new ArrayList<>();
                            ans = new ArrayList<>();
                            break;
                        }
                        edges.add(this.graph.getGraph().getEdge(ans.get(i).getKey(), ans.get(i + 1).getKey()));
                    }
                    if (ans.size() == 1) {
                        ans = new ArrayList<>();
                    }
                    The_paint.edges = edges;
                    The_paint.nodes = ans;
                    frame.repaint();
                }

            }
            if (cases.equals("ShortPathDis")) {
                s1 = textField.getText();
                s2 = textField1.getText();
                if (isNumericInteger(s1) && isNumericInteger(s2)) {
                    frame1.dispose();
                    if (this.graph.getGraph().getNode(Integer.parseInt(s1)) != null && this.graph.getGraph().getNode(Integer.parseInt(s2)) != null) {
                        Double w = this.graph.shortestPathDist(Integer.parseInt(s1), Integer.parseInt(s2));
                        DisplayDist(w, Integer.parseInt(s1), Integer.parseInt(s2));
                    }

                } else {
                    error.setForeground(Color.red);
                    frame1.pack();
                }
            }
            if (cases.equals("Save")) {
                s1 = textField.getText();
                this.graph.save(s1);
            }
            if (cases.equals("Load")) {
                s1 = textField.getText();
                this.graph.load(s1);
                frame.repaint();
            }
        }
    }

    public void Input2Values() {
        frame1 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        label.setText("Src:");
        JLabel label1 = new JLabel();
        label1.setText("Dest:");
        error = new JLabel();
        error.setText("Your input is invalid");
        error.setForeground(frame.getBackground());
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the app
        frame1.setLayout(new FlowLayout()); //controls the size of the items in the window
        textField = new JTextField(); // declaring the another one
        textField.setPreferredSize(new Dimension(60, 60)); //the size of the text box
        textField1 = new JTextField(); // declaring the another one
        textField1.setPreferredSize(new Dimension(60, 60)); //the size of the text box

        //for the text field it will be next to it in order to submit the values
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        frame1.add(label);
        frame1.add(textField); //adding the first text box
        frame1.add(label1);
        frame1.add(textField1);
        frame1.add(button); //adding the button
        frame1.add(error);
        //frame1.getRootPane().setDefaultButton(button);
        frame1.setVisible(true);
        frame1.pack(); //making it look good in a window
    }

    public void Input1Value() {
        frame1 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        label.setText("ID:");
        error = new JLabel();
        error.setText("Your input is invalid");
        error.setForeground(frame1.getBackground());
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the app
        frame1.setLayout(new FlowLayout()); //controls the size of the items in the window
        textField = new JTextField(); // declaring the another one
        textField.setPreferredSize(new Dimension(60, 60)); //the size of the text box

        //for the text field it will be next to it in order to submit the values
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        frame1.add(label);
        frame1.add(textField); //adding the first text box
        frame1.add(button); //adding the butten
        frame1.add(error);
        frame1.setVisible(true);
        frame1.pack(); //making it look good in a window
    }

    public void input4Values() {
        frame1 = new JFrame(); //creating a new window for the input from the user
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the app
        error = new JLabel();
        error.setText("Your input is invalid");
        error.setForeground(frame1.getBackground());
        frame1.setLayout(new FlowLayout()); //controls the size of the items in the window
        JLabel label = new JLabel();
        label.setText("ID:");
        JLabel label1 = new JLabel();
        label1.setText("X:");
        JLabel label2 = new JLabel();
        label2.setText("Y:");
        JLabel label3 = new JLabel();
        label3.setText("Z:");
        // declaring the another one
        textField = new JTextField();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        //the size of the text box
        textField.setPreferredSize(new Dimension(60, 60));
        textField1.setPreferredSize(new Dimension(60, 60));
        textField2.setPreferredSize(new Dimension(60, 60));
        textField3.setPreferredSize(new Dimension(60, 60));
        //for the text field it will be next to it in order to submit the values
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        //adding the text boxs
        frame1.add(label);
        frame1.add(textField);
        frame1.add(label1);
        frame1.add(textField1);
        frame1.add(label2);
        frame1.add(textField2);
        frame1.add(label3);
        frame1.add(textField3);
        frame1.add(button); //adding the butten
        frame1.add(error, BorderLayout.PAGE_END);
        frame1.setVisible(true);
        frame1.pack(); //making it look good in a window
    }

    public void Input3Values() {
        frame1 = new JFrame(); //creating a new window for the input from the user
        error = new JLabel();
        error.setText("Your input is invalid");
        error.setForeground(frame1.getBackground());
        frame1.add(error, BorderLayout.PAGE_END);
        JLabel label = new JLabel();
        label.setText("Src:");
        JLabel label1 = new JLabel();
        label1.setText("Dest:");
        JLabel label2 = new JLabel();
        label2.setText("Weight:");
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the app
        frame1.setLayout(new FlowLayout()); //controls the size of the items in the window
        // declaring the another one
        textField = new JTextField();
        textField1 = new JTextField();
        textField2 = new JTextField();
        //the size of the text box
        textField.setPreferredSize(new Dimension(60, 60));
        textField1.setPreferredSize(new Dimension(60, 60));
        textField2.setPreferredSize(new Dimension(60, 60));
        //for the text field it will be next to it in order to submit the values
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        //adding the text boxs
        frame1.add(label);
        frame1.add(textField);
        frame1.add(label1);
        frame1.add(textField1);
        frame1.add(label2);
        frame1.add(textField2);
        frame1.add(button); //adding the butten
        frame1.setVisible(true);
        frame1.pack(); //making it look good in a window
    }

    public void inputTSP() {
        frame1 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        label.setText("Please input the list of number with ',' between every number");
        error = new JLabel();
        error.setText("Your input is invalid");
        error.setForeground(frame1.getBackground());
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the app
        frame1.setLayout(new FlowLayout()); //controls the size of the items in the window
        textField = new JTextField(); // declaring the another one
        textField.setPreferredSize(new Dimension(100, 60)); //the size of the text box

        //for the text field it will be next to it in order to submit the values
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        frame1.add(label);
        frame1.add(textField); //adding the first text box
        frame1.add(button); //adding the butten
        frame1.add(error);
        frame1.setVisible(true);
        frame1.pack(); //making it look good in a window

    }

    public void DisplayDist(Double w, int src, int dest) {
        JFrame frame2 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        label.setText("The wight of the shorts path between " + src + "-->" + dest + " is: " + w);
        frame2.add(label);
        frame2.setLayout(new FlowLayout());
        frame2.setVisible(true);
        frame2.pack();
    }

    public void IsConnected(Boolean ans) {
        JFrame frame2 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        label.setText(String.valueOf(ans));
        frame2.add(label);
        frame2.setLayout(new FlowLayout());
        frame2.setVisible(true);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
    }

    public void InputSave() {
        JFrame frame2 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 60));
        frame2.setLayout(new FlowLayout());
        label.setText("Please enter that name you want it to be saved");
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        frame2.add(label);
        frame2.add(textField);
        frame2.add(button);
        frame2.setLayout(new FlowLayout());
        frame2.setVisible(true);
        frame2.pack();
    }

    public void InputLoad() {
        JFrame frame2 = new JFrame(); //creating a new window for the input from the user
        JLabel label = new JLabel();
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 60));
        frame2.setLayout(new FlowLayout());
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setCurrentDirectory(new File("data"));
//       int response = fileChooser.showOpenDialog(frame2);//select which file to open
//        if(response==JFileChooser.APPROVE_OPTION){
//            File selectedFile = fileChooser.getSelectedFile();
//            String load = selectedFile.getPath();
//                textField.setText(load);
//        }
        label.setText("Please input the file put that you want to load");
        button = new JButton("ok"); //add a button to the screen
        button.addActionListener(this);//will do the action in the function above
        button.setText("Submit");//sets a text on the button
        frame2.add(label);

        frame2.add(textField);
        frame2.add(button);
        frame2.setLayout(new FlowLayout());
        frame2.setVisible(true);
        frame2.pack();
    }

    public static boolean isNumericDouble(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumericInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
