// CS 326 HW8 Nick Alvarez Spring 2020
// File path needs to be modified to your operating environment and directories to run properly on computer

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class ColorApp extends JFrame
{
  private static final long serialVersionUID = 1;
  protected JList listColors;
  public String windowSaved = "Color Sampler", windowUnsaved = "Color Sampler*";
  protected ColorBox cBox;
  protected JLabel redLabel, greenLabel, blueLabel;
  protected JTextField redText, greenText, blueText;
  protected JButton rp, rm, gp, gm, bp, bm, save, reset;
  protected int red = 255, green = 255, blue = 255;
  protected File colorFile = new File("colors.txt");

  public static void main(String argv[])
  {
    /*JFrame frame = new JFrame("Color Sampler");
    frame.setSize(350,150);
    frame.addWindowListener(new WindowDestroyer());
    frame.setVisible(true);*/
    new ColorApp("Color Sampler");
  }

  public class ColorInput //Must be placed above ColorApp
  {
    public String c;
    public int r,g,b;
  }
  protected ColorInput[] colorsTXT = new ColorInput[11];

  public void FileInput(ColorInput[] myColors) throws IOException
  {
    //System.out.println("Attempted to run FileInput()\n");
    //System.out.println(new File(".").getAbsolutePath());
    FileInputStream stream = new FileInputStream(colorFile);
    System.out.println("colors.txt has been accessed.\n");
    InputStreamReader reader = new InputStreamReader(stream);
    StreamTokenizer tokens = new StreamTokenizer(reader);
    int totalColors = 0;

    while(tokens.nextToken() != StreamTokenizer.TT_EOF) // Did not like tokens.TT_EOF
    {
      myColors[totalColors] = new ColorInput();
      myColors[totalColors].c = (String) tokens.sval;
      tokens.nextToken();
      myColors[totalColors].r = (int) tokens.nval;
      tokens.nextToken();
      myColors[totalColors].g = (int) tokens.nval;
      tokens.nextToken();
      myColors[totalColors].b = (int) tokens.nval;
      totalColors++;
    }
    stream.close();
  }

  public ColorApp(String title)
  {
    super(title);
    setSize(500,500);
    addWindowListener(new WindowDestroyer());
    WindowSetup();
    try
    {
      FileInput(colorsTXT);
    }
    catch (IOException e)
    {
      System.out.println("Oops! You did not define your file directory. Please place 'colors.txt' in this path: " + new File(".").getAbsolutePath());
      System.exit(0);
    }
    String names[] = new String[11];
    for (int i=0; i<11; i++)
    {
      names[i] = colorsTXT[i].c;
    }
    listColors.setListData(names);

    cBox = new ColorBox();
    getContentPane().add(cBox);
    cBox.setLocation(10,10);
    cBox.setSize(380, 230);

    //getContentPane().setLayout(null);
    //getContentPane().add(cBox);
    //getContentPane().add(new JScrollPane(listColors));

    //listColors.setBounds(320, 10, 490, 490);
    //cBox.setBounds(10, 10, 300, 200);

    setVisible(true);

    //String colors[] = {"Red", "Yellow", "Blue"};

  }
  private class ColorBox extends JComponent // The graphical part that displays the colors
  {
    private static final long serialVersionUID = 1;
    public void paint(Graphics g)
    {
      Dimension d = getSize();

      g.setColor(new Color(red, green, blue, 255)); //Sets to white
      g.fillRect(0,0,d.width-2, d.height-2);
    }
  }

  public void WindowSetup() // Setting up all items, sizes, locations in content pane
  {
    //Item instances
    //R
    rp = new JButton("+");
    rp.addActionListener(new ActionHandler());
    rm = new JButton("-");
    rm.addActionListener(new ActionHandler());
    redText = new JTextField();
    redLabel = new JLabel("Red:");

    //G
    gp = new JButton("+");
    gp.addActionListener(new ActionHandler());
    gm = new JButton("-");
    gm.addActionListener(new ActionHandler());
    greenText = new JTextField();
    greenLabel = new JLabel("Green:");

    //B
    bp = new JButton("+");
    bp.addActionListener(new ActionHandler());
    bm = new JButton("-");
    bm.addActionListener(new ActionHandler());
    blueText = new JTextField();
    blueLabel = new JLabel("Blue:");

    //List (from ColorApp)
    listColors = new JList();
    listColors.addListSelectionListener(new ListHandler());

    //Function buttons
    save = new JButton("SAVE");
    save.addActionListener(new ActionHandler());
    reset = new JButton("RESET");
    reset.addActionListener(new ActionHandler());

    //Content setup (from ColorApp)
    getContentPane().setLayout(null);

    getContentPane().add(redLabel);
    getContentPane().add(redText);
    getContentPane().add(rp);
    getContentPane().add(rm);

    getContentPane().add(greenLabel);
    getContentPane().add(greenText);
    getContentPane().add(gp);
    getContentPane().add(gm);

    getContentPane().add(blueLabel);
    getContentPane().add(blueText);
    getContentPane().add(bp);
    getContentPane().add(bm);

    getContentPane().add(save);
    getContentPane().add(reset);
    getContentPane().add(listColors);

    //Item locations
    redLabel.setLocation(10, 250);
    redLabel.setSize(40,40);
    redText.setLocation(60, 250);
    redText.setSize(90,40);
    rp.setLocation(160, 250);
    rp.setSize(90,40);
    rm.setLocation(260, 250);
    rm.setSize(90,40);

    greenLabel.setLocation(10, 300);
    greenLabel.setSize(40,40);
    greenText.setLocation(60, 300);
    greenText.setSize(90,40);
    gp.setLocation(160, 300);
    gp.setSize(90,40);
    gm.setLocation(260, 300);
    gm.setSize(90,40);

    blueLabel.setLocation(10, 350);
    blueLabel.setSize(40,40);
    blueText.setLocation(60, 350);
    blueText.setSize(90,40);
    bp.setLocation(160, 350);
    bp.setSize(90,40);
    bm.setLocation(260, 350);
    bm.setSize(90,40);

    save.setLocation(10, 420);
    save.setSize(90,20);
    reset.setLocation(110, 420);
    reset.setSize(90,20);
    listColors.setLocation(400, 10);
    listColors.setSize(90, 480);
  }

  private class WindowDestroyer extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      try
      {
        FileOutputStream ostream = new FileOutputStream(colorFile); // Save any changes on close
        PrintWriter writer = new PrintWriter(ostream);
        for (int i=0; i<11; i++)
        {
          writer.println(colorsTXT[i].c + " " + colorsTXT[i].r + " " + colorsTXT[i].g + " " + colorsTXT[i].b);
        }
        writer.flush();
        ostream.close();
      }
      catch (IOException f)
      {
        System.out.println("File write error");
      }
      System.exit(0);
    }
  }

  private class ActionHandler implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      //Save
      if (e.getSource() == save)
      {
        ColorApp.this.setTitle(windowSaved); // No unsaved changes
        int i = listColors.getSelectedIndex();
        colorsTXT[i].r = red; // Update colors, to be written later
        colorsTXT[i].g = green;
        colorsTXT[i].b = blue;
      }
      //Reset
      else if (e.getSource() == reset)
      {
        ColorApp.this.setTitle(windowSaved);
        int i = listColors.getSelectedIndex();
        red = colorsTXT[i].r;
        redText.setText(red + "");
        green = colorsTXT[i].g;
        greenText.setText(green + "");
        blue = colorsTXT[i].b;
        blueText.setText(blue + "");
        repaint();
      }

      //Red +
      else if (e.getSource() == rp)
      {
        if (red<255)
        {
          red += 5;
          redText.setText(red + ""); //Found out the hard way that adding "" ensures it is a String
          repaint();
          ColorApp.this.setTitle(windowUnsaved);
        }
      }
      //Red -
      else if (e.getSource() == rm)
      {
        if (red>0)
        {
          ColorApp.this.setTitle(windowUnsaved);
          red -= 5;
          redText.setText(red + "");
          repaint();
        }
      }

      //Green +
      else if (e.getSource() == gp)
      {
        if (green<255)
        {
          ColorApp.this.setTitle(windowUnsaved);
          green += 5;
          greenText.setText(green + "");
          repaint();
        }
      }
      else if (e.getSource() == gm)
      {
        if (green>0)
        {
          ColorApp.this.setTitle(windowUnsaved);
          green -= 5;
          greenText.setText(green +"");
          repaint();
        }
      }

      //Blue +
      else if (e.getSource() == bp)
      {
        if (blue<255)
        {
          ColorApp.this.setTitle(windowUnsaved);
          blue += 5;
          blueText.setText(blue + "");
          repaint();
        }
      }
      //Blue -
      else if (e.getSource() == bm)
      {
        if (blue>0)
        {
          ColorApp.this.setTitle(windowUnsaved);
          blue -= 5;
          blueText.setText(blue + "");
          repaint();
        }
      }
    }
  }

  private class ListHandler implements ListSelectionListener
  {
    public void valueChanged(ListSelectionEvent e)
    {
      if (e.getSource() == listColors)
      {
        if (!e.getValueIsAdjusting()) //We change our selection so we must change the displayed color
        {
          int i = listColors.getSelectedIndex();
          ColorApp.this.setTitle(windowSaved); // Unsaved data lost
          red = colorsTXT[i].r;
          redText.setText(red + "");
          green = colorsTXT[i].g;
          greenText.setText(green + "");
          blue = colorsTXT[i].b;
          blueText.setText(blue + "");
          repaint();
        }
      }
    }
  }
}

