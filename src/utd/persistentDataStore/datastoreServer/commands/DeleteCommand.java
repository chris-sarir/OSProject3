package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DeleteCommand extends ServerCommand {

	@Override
	public void run() throws IOException, ServerException {

		String name = StreamUtil.readLine(inputStream);

		FileUtil.deleteData(name);

		sendOK();
	}

}
