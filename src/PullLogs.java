import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 
 * @author Jonas J. Schreiber
 *
 * Code to speed up the repetitive task of downloading server logs for review on local machine
 */

public class PullLogs {

    public static void main(String[] args) throws Exception
    {
    	//Determine Desktop location dynamically
    	Map<String, String> env = System.getenv();
        String desktop = env.get("USERPROFILE") + File.separator + "Desktop";
        
        //Open the file for writing
		File output = new File(desktop +  File.separator + "logs.txt");
		
		//Ensure Output isn't overwriting other server logs
		if (output.exists())
		{
			for (int i = 0; i < 100; i++)
			{
				output = new File(desktop +  File.separator + "logs" + i + ".txt");
				if (!output.exists())
					break;
			}
		}
        if (args.length < 1)
        {
        	System.out.println("USAGE: PullLogs <Logs URL>");
        	return;
        }
        
        URL url;
        try {
            url = new URL(args[0]);
            FileOutputStream fos = new FileOutputStream(output);
            OutputStreamWriter out = new OutputStreamWriter(fos);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            
            while ((inputLine = in.readLine()) != null)
                out.write(inputLine + '\n');
            
            out.flush();
            out.close();
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
   
//http://cs352-1.rutgers.edu/btlog/RUBT11ODYNLFNCIMETWX-debug.log