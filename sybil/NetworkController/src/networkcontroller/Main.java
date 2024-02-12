/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkcontroller;

import de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel;
import javax.swing.UIManager;

/**
 *
 * @author Elcot
 */
public class Main {
    public static void main(String[] args) 
    {                                
        try
        {                    			
            UIManager.setLookAndFeel(new SyntheticaBlueLightLookAndFeel());
                                                
            NCFrame sf=new NCFrame();
            sf.setTitle("Network Controller");
            sf.setVisible(true);
            sf.setResizable(false); 
            
            NCReceiver sr=new NCReceiver(sf);
            sr.start();
	}
	catch (Exception ex)
	{            
            //System.out.println(ex);
	}   
    }
}
