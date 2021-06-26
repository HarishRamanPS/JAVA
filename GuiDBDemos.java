import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

import java.util.*;

public class GuiDBDemos extends JFrame implements ActionListener
{
	public Connection conn;
        public String[] v;
	
	public JLabel jlbl1,jlbl2, jlbl3, jlbl4;
	public JButton jb1, jb2, jb3, jb4;
	public JTextField jtf1,jtf2,jtf3,jtf4;

	public GuiDBDemos()
	{
	    super("GUI Demo");
	    
	    setLayout (new GridLayout(6, 2));
            jlbl1 = new JLabel("Name");
            jlbl2 = new JLabel("Reg.No");
            jlbl3 = new JLabel("Dept");
            jlbl4 = new JLabel("Year");


            jtf1 = new JTextField();
            jtf2 = new JTextField();
            jtf3 = new JTextField();
            jtf4 = new JTextField();

            jb1 = new JButton("Add");
            jb2 = new JButton("Update");
            jb3 = new JButton("Delete");
            jb4 = new JButton("Retreive");

  	    jb1.addActionListener(this);
  	    jb2.addActionListener(this);
  	    jb3.addActionListener(this);
  	    jb4.addActionListener(this);

	    this.add(jlbl2);
	    this.add(jtf2);

	    this.add(jlbl1);
	    this.add(jtf1);

	    this.add(jlbl3);
	    this.add(jtf3);

	    this.add(jlbl4);
	    this.add(jtf4);

	    this.add(jb1);
	    this.add(jb2);
	    this.add(jb3);
	    this.add(jb4);


	    this.setSize(300,300);
	    this.setVisible(true);
 	 
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

      public void actionPerformed(ActionEvent ae)
	{

	   if(ae.getSource()==jb1)
 	   {	      
	      String insert_qry  = "insert into students values('"+ jtf1.getText() +"',"+ Integer.parseInt(jtf2.getText()) +",'"+ jtf3.getText() +"',"+ Integer.parseInt(jtf4.getText()) +")";
            int resp = dbOperations(insert_qry, 1);

	      if(resp == 1)
	      { 
		  JOptionPane.showMessageDialog (this, "info added successfully", "Gui DB Demo", JOptionPane.INFORMATION_MESSAGE); 
		  clearFields();
		}

	      else
	      { JOptionPane.showMessageDialog (this, "Error occured while inserting.", "Gui DB Demo", JOptionPane.ERROR_MESSAGE); }

	   }

	   else if(ae.getSource()==jb2)
 	   {	      
	      String update_qry  = "update students set sName = '"+ jtf1.getText() +"', rNo = "+ Integer.parseInt(jtf2.getText()) +", dept = '"+ jtf3.getText() +"', year = "+ Integer.parseInt(jtf4.getText()) +" where rNo = "+ Integer.parseInt(jtf2.getText());
            int resp = dbOperations(update_qry, 1);

	      if(resp == 1)
	      { 
		  JOptionPane.showMessageDialog (this, "info updated successfully", "Gui DB Demo", JOptionPane.INFORMATION_MESSAGE); 
		  clearFields();
		}

	      else
	      { JOptionPane.showMessageDialog (this, "Error occured while updating.", "Gui DB Demo", JOptionPane.ERROR_MESSAGE); }
	   }

	   else if(ae.getSource()==jb3)
 	   {	      
	      String delete_qry  = "delete * from students where rNo = "+ Integer.parseInt(jtf2.getText());
            int resp = dbOperations(delete_qry, 1);

	      if(resp == 1)
	      { 
		  JOptionPane.showMessageDialog (this, "info deleted successfully", "Gui DB Demo", JOptionPane.INFORMATION_MESSAGE);
		  clearFields();
	      }

	      else
	      { JOptionPane.showMessageDialog (this, "Error occured while deleting.", "Gui DB Demo", JOptionPane.ERROR_MESSAGE); }

	   }

	   else if(ae.getSource()==jb4)
 	   {	      
	      String select_qry  = "select * from students where rNo = "+ Integer.parseInt(jtf2.getText());
            int resp = dbOperations(select_qry, 2);

	      if(resp == 1)
	      { 
		    jtf1.setText(v[0]);	// name 
		    jtf3.setText(v[2]);	// dept
		    jtf4.setText(v[3]);	// year
		    JOptionPane.showMessageDialog (this, "info retreived successfully", "Gui DB Demo", JOptionPane.INFORMATION_MESSAGE); 
		    v = null;
		}

	      else
	      { JOptionPane.showMessageDialog (this, "Error occured while retreiving.", "Gui DB Demo", JOptionPane.ERROR_MESSAGE); }
	   }	   

	}

        public int dbOperations(String Qry, int option)
        {

	   int transFlag = 0;

	   try
	   {
          	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		conn = DriverManager.getConnection("jdbc:odbc:demo","","");
		Statement stmt = conn.createStatement();

		if(option == 1)
	 	{
	         stmt.executeUpdate(Qry);
		   transFlag = 1;
		}

		else if(option == 2)
	 	{
	         ResultSet rs = stmt.executeQuery(Qry);
		   
		   if (rs.next())
		   {	
		     while(rs.next())
		     {
		        v = new String[4];
		        v[0] = rs.getString(1);
		        v[1] = new Integer(rs.getInt(2)).toString();
		        v[2] = rs.getString(3);
		        v[3] = new Integer(rs.getInt(4)).toString();
		     }
		   transFlag = 1;
		   }

		   else
		   { transFlag = 0; }

		}

            conn.close();

	   }
	   catch(ClassNotFoundException cnfe)
	   { System.out.println (cnfe.getMessage()); }

	   catch(SQLException sqle)
	   { System.out.println (sqle.getMessage()); }

	   return transFlag; 
        }

        public void clearFields()
	  {
          jtf1.setText("");
          jtf2.setText("");
          jtf3.setText("");
          jtf4.setText("");
	  }



      public static void main(String args[])
	{
		GuiDBDemos gdb = new GuiDBDemos();
	}



}