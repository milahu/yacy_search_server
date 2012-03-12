package interaction;

//ViewLog_p.java 
//-----------------------
//part of the AnomicHTTPD caching proxy
//(C) by Michael Peter Christen; mc@yacy.net
//first published on http://www.anomic.de
//Frankfurt, Germany, 2004
//
//This File is contributed by Alexander Schier
//last major change: 14.12.2004
//
//This program is free software; you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation; either version 2 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


//You must compile this file with
//javac -classpath .:../classes ViewLog_p.java
//if the shell's current path is HTROOT

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import com.hp.hpl.jena.rdf.model.Model;

import net.yacy.cora.protocol.HeaderFramework;
import net.yacy.cora.protocol.RequestHeader;
import net.yacy.interaction.Interaction;
import net.yacy.interaction.TripleStore;
import net.yacy.kelondro.logging.Log;
import net.yacy.search.Switchboard;
import de.anomic.data.UserDB;
import de.anomic.server.serverObjects;
import de.anomic.server.serverSwitch;

public class GetRDF {
    
    public static serverObjects respond(final RequestHeader header, final serverObjects post, final serverSwitch env) {
    	
    	final Switchboard sb = (Switchboard) env;
    	
        final serverObjects prop = new serverObjects();

        String url = "";
        String s = "";        
        String p = "";
        String o = "";
        
        Boolean global = false;
        
        if(post != null){
                       
            global = post.containsKey("global");
            
        }

       	
        if (global) {
        	
        	ByteArrayOutputStream fout;
   			
    			
    		fout = new ByteArrayOutputStream();
    			
    		TripleStore.model.write(fout);
    			
        	prop.put("resultXML", fout.toString());
        	
        } else {
        	
        	Model tmp = TripleStore.privatestorage.get(Interaction.GetLoggedOnUser(header));
        	
        	if (tmp != null) {
        		
        		ByteArrayOutputStream fout;       			    		
        		fout = new ByteArrayOutputStream();        			
        		tmp.write(fout);
        		
        		prop.put("resultXML", fout.toString());
        		
        	} else {
        	
        		prop.put("resultXML", "");
        	}
        }
        
        

        
        
        return prop;
    }
}
