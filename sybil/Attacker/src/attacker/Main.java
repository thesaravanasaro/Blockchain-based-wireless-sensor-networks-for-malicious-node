/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attacker;

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
                                                
            AttackerFrame bmf=new AttackerFrame();
            bmf.setTitle("Malicious Vehicle");
            bmf.setVisible(true);
            bmf.setResizable(false); 
            
            AttackerReceiver bmr=new AttackerReceiver(bmf);
            bmr.start();
	}
	catch (Exception ex)
	{            
            //System.out.println(ex);
	}   
    }
}
