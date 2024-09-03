package com.spectra.asr.businessobject.watch;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;

@Slf4j
public abstract class DirectoryWatcherImpl implements DirectoryWatcher {
	//private Logger log = Logger.getLogger(DirectoryWatcherImpl.class);
	
	public void watch(Path dirPath) throws IOException, InterruptedException{
		if(dirPath != null){
	        WatchService watchService = FileSystems.getDefault().newWatchService();
	 
	        //Path path = Paths.get(System.getProperty("user.home"));
	 
	        //path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
	        dirPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
	 
	        WatchKey key;
	        while((key = watchService.take()) != null) {
	            for(WatchEvent<?> event : key.pollEvents()) {
	                WatchEvent.Kind<?> kind = event.kind();

	                // This key is registered only
	                // for ENTRY_CREATE events,
	                // but an OVERFLOW event can
	                // occur regardless if events
	                // are lost or discarded.
	                if (kind == StandardWatchEventKinds.OVERFLOW) {
	                    continue;
	                }	            	
	                
	                ////System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
	                log.warn("watch(): Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
	                
	                // The filename is the
	                // context of the event.
	                WatchEvent<Path> ev = (WatchEvent<Path>)event;
	                Path filename = ev.context();
	                this.handleEvent(filename);
	            }
	            key.reset();
	        }
		}
	}
}
