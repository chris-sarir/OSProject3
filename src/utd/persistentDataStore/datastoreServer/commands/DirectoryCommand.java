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

		directoryInfo += ((Integer) names.length).toString()+"\n";

		for( String name : names){
			directoryInfo+=name+"\n";
		}
		//System.out.print(directoryInfo);
		//System.out.println("Sending Directory information");
		directoryInfo+= ((char)8); //end of file marker

		outputStream.write(directoryInfo.getBytes());
		outputStream.flush();

	}

}
