package com.joweria.trial.DirectoryWatcher;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class DirectoryWatcher{

	public static void main(String[] args) throws IOException,InterruptedException {			
		   
	    File dir = new File("K:\\DirectoryWatch");				 // Folder we are going to watch
	    watchDirectoryPath(dir.toPath());
		}

		public static void watchDirectoryPath(Path path) {
		       
		        
		    	try {				 // Check if path is a folder
		            Boolean isFolder = (Boolean) Files.getAttribute(path,"basic:isDirectory", NOFOLLOW_LINKS);
		            if (!isFolder) {
		                throw new IllegalArgumentException("Path: " + path+ " is not a folder");
		            		}
		            
		        		} catch (IOException ioe) {
		            
		            ioe.printStackTrace();		// Folder does not exists
		        		}

		   System.out.println("Watching path: " + path);
		        
		   
		   FileSystem fs = path.getFileSystem();		// obtaining the file system of the Path

		        
		   try (WatchService watcher = fs.newWatchService()) {			//creating the new WatchService using the new try() block
		        	path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
		            
		            
		            WatchKey key = null;
		            while (true) {					// loop
		                
		            	key = watcher.take();
		                Kind<?> kind = null;
		                for (WatchEvent<?> watchEvent : key.pollEvents()) {
		                  
		                    kind = watchEvent.kind();				  // Get the type of the event
		                    if (OVERFLOW == kind) {
		                        continue; 
		                        
		                    } else if (ENTRY_CREATE == kind) {			// A new file was created
		                        
		                        @SuppressWarnings("unchecked")
								Path newPath = ((WatchEvent<Path>) watchEvent).context();
		                      
		                        System.out.println("New file is created: " + newPath);			 // modified
		                    } else if (ENTRY_MODIFY == kind) {
		                       
		                        @SuppressWarnings("unchecked")
								Path newPath = ((WatchEvent<Path>) watchEvent).context();
		                        System.out.println("file is modified: " + newPath);
		                   
		                    }else if(ENTRY_DELETE == kind){
		                    	
		                    	@SuppressWarnings("unchecked")
								Path newPath = ((WatchEvent<Path>) watchEvent).context();	
		                    	System.out.println("file is deleted: " + newPath);
		                    }
		                }

		                if (!key.reset()) {
		                    break; // loop
		                }
		            }

		        } catch (IOException ioe) {
		            ioe.printStackTrace();
		            
		        } catch (InterruptedException ie) {
		            ie.printStackTrace();
		        }

		}
		
}
 
 
