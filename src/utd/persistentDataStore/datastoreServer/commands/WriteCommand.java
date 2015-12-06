package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand {

	@Override
	public void run() throws IOException, ServerException {

		String name = StreamUtil.readLine(inputStream);
		logger.debug("Received file name: " + name);
		String data = StreamUtil.readLine(inputStream);
		logger.debug("Received data: " + data);
		
		byte[] bytes = data.getBytes();
		int byteLength = bytes.length;
		int numBits = Integer.bitCount(byteLength);
		int i = (int)Math.ceil( (numBits / 8) );
		
		data += "\n" + StreamUtil.readData(i, inputStream);
		logger.debug("All bytes recieved");
		
		FileUtil.writeData(name, data.getBytes());
		logger.debug("File created");
		sendOK();

	}

}
