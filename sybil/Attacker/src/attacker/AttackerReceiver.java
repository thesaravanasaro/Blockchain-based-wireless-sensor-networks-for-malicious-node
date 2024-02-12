/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attacker;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elcot
 */
public class AttackerReceiver extends Thread{
    AttackerFrame nf;    
    public static ArrayList neighbors=new ArrayList();
    
    AttackerReceiver(AttackerFrame f)
    {
        nf=f;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(7000);
            while(true)
            {                
                byte data[]=new byte[200000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                String str=new String(dp.getData()).trim(); 
                System.out.println("Received: "+str);
                String req[]=str.split("#");
                if(req[0].equals("ComputerDetails"))       
                {
                    if(req[1].trim().contains("\n"))
                    {
                        String sp[]=req[1].trim().split("\n");
                        for(int i=0;i<sp.length;i++)
                        {
                            String kp[]=sp[i].trim().split("@");                                                        
                            
                            if(!(neighbors.contains(sp[i].trim())))
                            {
                                neighbors.add(sp[i].trim());
                            }                            
                        }
                    }
                    else
                    {
                        String kp[]=req[1].trim().split("@");
                        if(!(neighbors.contains(req[1].trim())))
                        {
                            neighbors.add(req[1].trim());
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
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
