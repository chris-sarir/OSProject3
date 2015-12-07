package utd.persistentDataStore.datastoreServer.commands;

import java.io.File;
import java.io.IOException;

import utd.persistentDataStore.utils.ServerException;

public class DirectoryCommand extends ServerCommand {

	@Override
	public void run() throws IOException, ServerException {

		File file = new File(System.getProperty("user.dir"));
		String[] names = file.list();
		String directoryInfo="OK\n";
		outputStream.write(directoryInfo.getBytes());
		outputStream.flush();

		directoryInfo = ((Integer) names.length).toString()+"\n";
		outputStream.write(directoryInfo.getBytes());
		outputStream.flush();

		for( String name : names){
			directoryInfo=name+"\n";
			outputStream.write(directoryInfo.getBytes());
			outputStream.flush();
		}
	}

}
