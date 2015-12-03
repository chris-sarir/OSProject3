package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class ReadCommand extends ServerCommand {

	@Override
	public void run() throws IOException, ServerException {
		
		String name = StreamUtil.readLine(inputStream);
		
		byte[] data = FileUtil.readData(name);
		
		StreamUtil.writeData(data, outputStream);
		
		sendOK();
	}

}
