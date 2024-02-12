/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkcontroller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elcot
 */
public class NCReceiver extends Thread{
    
    NCFrame nf;    
    int rowid=0;
    public static ArrayList allSybil=new ArrayList();
    
    NCReceiver(NCFrame f)
    {
        nf=f;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(5000);
            while(true)
            {                
                byte data[]=new byte[200000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim(); 
                System.out.println("Received: "+str);
                String req[]=str.split("#");
                if(req[0].equals("Connect"))       
                {
                    DefaultTableModel dm=(DefaultTableModel)nf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1].trim());
                    v.add(req[2].trim());                    
                    dm.addRow(v); 
                    
                    String nei="";
                    for(int i=0;i<nf.jTable1.getRowCount();i++)
                    {
                        String computerid=nf.jTable1.getValueAt(i,0).toString().trim();
                        String ipadrs=nf.jTable1.getValueAt(i,1).toString().trim();                        
                        nei=nei+computerid.trim()+"@"+ipadrs.trim()+"\n";
                    }
                    if(!(nei.trim().equals("")))
                    {
                        String neighbor=nei.substring(0,nei.lastIndexOf('\n'));
                        String msg1="Neighbors#"+neighbor.trim();
                        
                        for(int i=0;i<nf.jTable1.getRowCount();i++)
                        {
                            String nodeid=nf.jTable1.getValueAt(i,0).toString().trim();
                            int pt1=Integer.parseInt(nodeid.trim())+6000;
                            packetTransmission(msg1,pt1);
                        }
                    }
                }
                if(req[0].equals("ShareMessage"))       
                {
                    rowid++;
                    
                    if(req[4].trim().equals("Pending"))
                    {
                       long endTime=System.currentTimeMillis();
                       long startTime=Long.parseLong(req[5].trim());
                       
                       long rssi=endTime-startTime;
                        
                        nf.jComboBox1.addItem(rowid);
                        DefaultTableModel dm=(DefaultTableModel)nf.jTable2.getModel();
                        Vector v=new Vector();
                        v.add(rowid);
                        v.add(req[1].trim());
                        v.add(req[2].trim());  
                        v.add(req[3].trim());
                        v.add(req[4].trim());
                        v.add(""+rssi);
                        dm.addRow(v);
                    }
                }
                if(req[0].equals("ShareMsg"))       
                {
                    rowid++;
                    
                    if(req[4].trim().equals("Pending"))
                    {
                        long endTime=System.currentTimeMillis();
                        long startTime=Long.parseLong(req[5].trim());
                       
                        long rssi=endTime-startTime;
                        
                        nf.jComboBox1.addItem(rowid);
                        DefaultTableModel dm=(DefaultTableModel)nf.jTable2.getModel();
                        Vector v=new Vector();
                        v.add(rowid);
                        v.add(req[1].trim());
                        v.add(req[2].trim());  
                        v.add(req[3].trim());
                        v.add(req[4].trim());
                        v.add(""+rssi);
                        dm.addRow(v);
                        
                        if(!(allSybil.contains(req[1].trim())))
                        {
                            allSybil.add(req[1].trim());
                        }
                    }                                        
                }
                if(req[0].equals("Hack"))       
                {
                    String nei="";
                    for(int i=0;i<nf.jTable1.getRowCount();i++)
                    {
                        String computerid=nf.jTable1.getValueAt(i,0).toString().trim();
                        String ipadrs=nf.jTable1.getValueAt(i,1).toString().trim();                        
                        nei=nei+computerid.trim()+"@"+ipadrs.trim()+"\n";
                    }
                    if(!(nei.trim().equals("")))
                    {
                        String neighbor=nei.substring(0,nei.lastIndexOf('\n'));
                        String msg1="ComputerDetails#"+neighbor.trim();                        
                        int pt1=7000;
                        packetTransmission(msg1,pt1);                        
                    }
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
