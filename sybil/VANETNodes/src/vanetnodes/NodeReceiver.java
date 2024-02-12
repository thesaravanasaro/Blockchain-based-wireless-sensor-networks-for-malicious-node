/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanetnodes;

import java.awt.Color;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elcot
 */
public class NodeReceiver extends Thread{
    
    NodeFrame nf;    
    int nodeid,port;
    public static ArrayList neighbors=new ArrayList();
    int cou=0;
    public static int nodestatus=0;
    
    NodeReceiver(NodeFrame f, int nid)
    {
        nf=f;
        nodeid=nid;
        port=nodeid+6000;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(port);
            while(true)
            {                
                byte data[]=new byte[200000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim(); 
                System.out.println("Received: "+str);
                String req[]=str.split("#");
                if(req[0].equals("Neighbors"))       
                {
                    if(req[1].trim().contains("\n"))
                    {
                        String sp[]=req[1].trim().split("\n");
                        for(int i=0;i<sp.length;i++)
                        {
                            String kp[]=sp[i].trim().split("@");
                            
                            String eachds=kp[0].trim();
                            
                            if(!(eachds.trim().equals(""+nodeid)))
                            {
                                if(!(neighbors.contains(sp[i].trim())))
                                {
                                    neighbors.add(sp[i].trim());
                                }
                            }
                        }
                    }
                    else
                    {
                        String kp[]=req[1].trim().split("@");
                        if(!(kp[0].trim().equals(""+nodeid)))
                        {
                            if(!(neighbors.contains(req[1].trim())))
                            {
                                neighbors.add(req[1].trim());
                            }
                        }
                    }
                    
                    DefaultTableModel dm1=(DefaultTableModel)nf.jTable1.getModel();
                    dm1.setRowCount(0);  
                    nf.jComboBox1.removeAllItems();
                    
                    for(int i=0;i<neighbors.size();i++)
                    {
                        String eachds=neighbors.get(i).toString().trim();
                        String kp[]=eachds.trim().split("@");
                        
                        DefaultTableModel dm=(DefaultTableModel)nf.jTable1.getModel();
                        Vector v=new Vector();
                        v.add(kp[0].trim());
                        v.add(kp[1].trim());                        
                        dm.addRow(v); 
                        
                        nf.jComboBox1.addItem(kp[0].trim());
                    }
                }
                if(req[0].equals("ShareMessage"))       
                {
                    JOptionPane.showMessageDialog(nf,"Message Received Successfully!");
                    
                    DefaultTableModel dm=(DefaultTableModel)nf.jTable2.getModel();
                    Vector v=new Vector();
                    v.add(req[1].trim());
                    v.add(req[2].trim());                    
                    dm.addRow(v);
                }
                if(req[0].equals("Malware"))       
                {
                    nf.jPanel1.setBackground(Color.red);
                    nf.jPanel6.setBackground(Color.red);
                    nf.jTextArea1.setEditable(false);
                    
                    nodestatus=1;                    
                }
                if(req[0].equals("SybilAttack"))       
                {                    
                    String maliciousmessage=req[2].trim();
                    
                    nf.jTextArea1.setText(maliciousmessage.trim());
                }                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void packetTransmission(String msg, int pt) 
    {
        try
        {
            byte data1[]=msg.getBytes();
            DatagramSocket ds1=new DatagramSocket();
            DatagramPacket dp1=new DatagramPacket(data1,0,data1.length,InetAddress.getByName("127.0.0.1"),pt);
            ds1.send(dp1);
            System.out.println("Port is "+pt+"\n");                        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }   
    }
}
