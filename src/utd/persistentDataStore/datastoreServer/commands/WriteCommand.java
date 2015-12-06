package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand {

	@Override
	public void run() throws IOException, ServerException {

		String name = StreamUtil.readLine(inputStream);
		String amount = StreamUtil.readLine(inputStream);

		byte[] data = StreamUtil.readData(Integer.parseInt(amount), inputStream);

		FileUtil.writeData(name, data);

//		String response = "ok\n";
//
//		StreamUtil.writeLine(response, outputStream);

		sendOK();

	}

}
